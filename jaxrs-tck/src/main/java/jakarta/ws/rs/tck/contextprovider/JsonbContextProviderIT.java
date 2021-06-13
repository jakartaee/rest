/*
 * Copyright (c) 2021 Markus Karg. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package jakarta.ws.rs.tck.contextprovider;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.SECONDS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static jakarta.ws.rs.RuntimeType.CLIENT;
import static jakarta.ws.rs.RuntimeType.SERVER;
import static jakarta.ws.rs.SeBootstrap.Configuration.FREE_PORT;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.SeBootstrap;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Compliance Test for Jsonb Context Provider of Jakarta REST API
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
@Timeout(value = 1, unit = HOURS)
public final class JsonbContextProviderIT {

    /**
     * Verifies that an implementation will use the {@link Jsonb} instance
     * offered by an application-provided context resolver.
     *
     * @throws ExecutionException
     *             if the instance didn't boot correctly
     * @throws InterruptedException
     *             if the test took much longer than usually expected
     */
    @Test
    public final void shouldUseApplicationProvidedJsonbInstance() throws InterruptedException, ExecutionException {
        // given
        final Application application = new EchoApplication();
        final UriBuilder baseUri = UriBuilder.newInstance().scheme("http").host("localhost").port(FREE_PORT);
        final SeBootstrap.Configuration requestedConfiguration = SeBootstrap.Configuration.builder().build();
        final CompletionStage<SeBootstrap.Instance> completionStage = SeBootstrap.start(application, requestedConfiguration);
        final SeBootstrap.Instance instance = completionStage.toCompletableFuture().get();
        final SeBootstrap.Configuration actualConfiguration = instance.configuration();
        final int actualPort = actualConfiguration.port();
        final UriBuilder effectiveUri = baseUri.port(actualPort).path("echo");

        try (final Client client = ClientBuilder.newBuilder().register(new CustomJsonbProvider(CLIENT)).build()) {
            // when
            final String origin = String.format("Origin(%d)", mockInt());
            final POJO requestPojo = new POJO();
            requestPojo.setSeenBy(origin);
            final POJO responsePojo = client.target(effectiveUri)
                                            .request(APPLICATION_JSON_TYPE)
                                            .buildPost(Entity.entity(requestPojo, APPLICATION_JSON_TYPE))
                                            .invoke(POJO.class);

            // then
            final String expectedWaypoints = String.join(",", origin,
                                                              "CustomSerializer(CLIENT)",
                                                              "CustomDeserializer(SERVER)",
                                                              "EchoResource",
                                                              "CustomSerializer(SERVER)",
                                                              "CustomDeserializer(CLIENT)");
            assertThat(responsePojo.getSeenBy(), is(expectedWaypoints));
        }

        instance.stop().toCompletableFuture().get();
    }

    public static final class CustomJsonbProvider implements ContextResolver<Jsonb> {
        private final RuntimeType runtimeType;

        private CustomJsonbProvider(final RuntimeType runtimeType) {
            this.runtimeType = runtimeType;
        }

        public final Jsonb getContext(final Class<?> type) {
            if (!POJO.class.isAssignableFrom(type))
                return null;

            return JsonbBuilder.create(new JsonbConfig().withSerializers(new CustomSerializer()).withDeserializers(new CustomDeserializer()));
        }

        private final class CustomSerializer implements JsonbSerializer<POJO> {
            @Override
            public final void serialize(final POJO pojo, final JsonGenerator generator, final SerializationContext ctx) {
                generator.writeStartObject();
                generator.write("seenBy", String.format("%s,CustomSerializer(%s)", pojo.getSeenBy(), CustomJsonbProvider.this.runtimeType));
                generator.writeEnd();
            }
        }

        private final class CustomDeserializer implements JsonbDeserializer<POJO> {
            @Override
            public final POJO deserialize(final JsonParser parser, final DeserializationContext ctx, final Type rtType) {
                final POJO pojo = new POJO();
                pojo.setSeenBy(String.format("%s,CustomDeserializer(%s)", parser.getObject().getString("seenBy"), CustomJsonbProvider.this.runtimeType));
                return pojo;
            }
        }
    }

    public static final class POJO {
        private String seenBy;

        public final String getSeenBy() {
            return this.seenBy;
        }

        public final void setSeenBy(final String seenBy) {
            this.seenBy = seenBy;
        }
    }

    private static final class EchoApplication extends Application {
        @Override
        public final Set<Class<?>> getClasses() {
            return Collections.singleton(EchoResource.class);
        }

        @Override
        public final Set<Object> getSingletons() {
            return Collections.singleton(new CustomJsonbProvider(SERVER));
        }

        @Path("echo")
        public static class EchoResource {
            @POST
            @Consumes(APPLICATION_JSON)
            @Produces(APPLICATION_JSON)
            public POJO echo(final POJO pojo) {
                pojo.setSeenBy(String.join(",", pojo.getSeenBy(), "EchoResource"));
                return pojo;
            }
        }
    }

    private static final int mockInt() {
        return (int) Math.round(Integer.MAX_VALUE * Math.random());
    }
}
