/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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

package ee.jakarta.tck.ws.rs.jaxrs31.ee.multipart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ee.jakarta.tck.ws.rs.common.client.JaxrsCommonClient;
import ee.jakarta.tck.ws.rs.lib.util.TestUtil;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 * @author <a href="mailto:jckofbyron@gmail.com">Jim Krueger</a>
 */

// To-do:  Currently the find method is used to find EntityParts in a returned List because the order can not be 
// guarenteed.   If this is addressed then tests should be added or modified.

@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class MultipartSupportIT {

    private static final String LS = System.lineSeparator();
    static InputStream xmlFile() {
        String xml =
                "<root>" + LS +
                "  <mid attr1=\"value1\" attr2=\"value2\">" + LS +
                "    <inner attr3=\"value3\"/>" + LS +
                "  </mid>" + LS +
                "  <mid attr1=\"value4\" attr2=\"value5\">" + LS +
                "    <inner attr3=\"value6\"/>" + LS +
                "  </mid>" + LS +
                "</root>";
        return new ByteArrayInputStream(xml.getBytes());
    }


    @BeforeEach
    void logStartTest(TestInfo testInfo) {
        TestUtil.logMsg("STARTING TEST : "+testInfo.getDisplayName());
    }

    @AfterEach
    void logFinishTest(TestInfo testInfo) {
        TestUtil.logMsg("FINISHED TEST : "+testInfo.getDisplayName());
    }

    @Deployment
    public static WebArchive deployment() {
        return ShrinkWrap.create(WebArchive.class, MultipartSupportIT.class.getSimpleName() + ".war")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @ArquillianResource
    private URI uri;

    /**
     * Verify sending a List of {@link EntityPart} and receiving a List sent back from the resource method containing
     * the same parts plus an additional part added by thye resource method containing extra headers.
     *
     * @throws Exception if an error occurs in the test
     */
    @Test
    public void basicTest() throws Exception {
        try (Client client = ClientBuilder.newClient()) {
            final List<EntityPart> multipart = List.of(
                    EntityPart.withName("octet-stream")
                            .content("test string".getBytes(StandardCharsets.UTF_8))
                            .mediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                            .build(),
                    EntityPart.withName("file")
                            .content("test file", xmlFile())
                            .mediaType(MediaType.APPLICATION_XML)
                            .build()
                    );
            try (
                    Response response = client.target(createCombinedUri(uri, "test/basicTest"))
                            .request(MediaType.MULTIPART_FORM_DATA_TYPE)
                            .post(Entity.entity(new GenericEntity<>(multipart) {
                            }, MediaType.MULTIPART_FORM_DATA))) {
                Assertions.assertEquals(200, response.getStatus());
                final List<EntityPart> entityParts = response.readEntity(new GenericType<>() {
                });
                if (entityParts.size() != 3) {
                    final String msg = "Expected 3 entries, received " +
                            entityParts.size() +
                            '.' +
                            System.lineSeparator() +
                            getMessage(entityParts);
                    Assertions.fail(msg);
                }
                EntityPart part = find(entityParts, "received-string");
                Assertions.assertNotNull(part, getMessage(entityParts));
                Assertions.assertEquals("test string", part.getContent(String.class));

                // The javadoc for EntityPart.getContent(Class<T> type) states:
                // "Subsequent invocations will result in an {@code IllegalStateException}. 
                // Likewise this method will throw an {@code IllegalStateException} if it is 
                // called after calling {@link #getContent} or {@link #getContent(GenericType)}.
                try {
                    part.getContent(String.class);
                    Assertions.fail("IllegalStateException is expected when getContent() is " +
                            "invoked more than once.");
                } catch (IllegalStateException e) {
                    // expected exception
                } catch (Throwable t) {
                    Assertions.fail("Incorrect Throwable received: " + t);
                }
                
                part = find(entityParts, "received-file");
                Assertions.assertNotNull(part, getMessage(entityParts));
                Assertions.assertEquals("test file", part.getFileName().get());
                Assertions.assertTrue(part.getContent(String.class).contains("value6"));

                part = find(entityParts, "added-input-stream");
                Assertions.assertNotNull(part, getMessage(entityParts));
                Assertions.assertEquals("Add part on return.", part.getContent(String.class));

                // Check headers.  Should be 4:  Content-Disposition, Content-Type, and the 2 headers
                // that were added.
                if ((part.getHeaders() == null) || (part.getHeaders().size() != 4)) {
                    final String msg = "Expected 4 headers, received " +
                            part.getHeaders().size();
                    Assertions.fail(msg);
                }
                Assertions.assertEquals("Test1", part.getHeaders().get("Header1").get(0));
                Assertions.assertEquals("Test2", part.getHeaders().get("Header2").get(0));
            }
        }
    }

    /**
     * Verify sending a {@link List} containing three {@link EntityPart}, each injected as a different type.
     * <p>
     * The returned result will be {@code multipart/form-data} content with a new name and the content for each
     * injected field.
     * </p>
     *
     * @throws Exception if an error occurs in the test
     */
    @Test
    public void multiFormParamTest() throws Exception {
        try (Client client = ClientBuilder.newClient()) {
            final List<EntityPart> multipart = List.of(
                    EntityPart.withName("entity-part")
                            .content("test entity part")
                            .mediaType(MediaType.TEXT_PLAIN_TYPE)
                            .build(),
                    EntityPart.withName("string-part")
                            .content("test string")
                            .mediaType(MediaType.TEXT_PLAIN_TYPE)
                            .build(),
                    EntityPart.withName("input-stream-part")
                            .content("test input stream".getBytes(StandardCharsets.UTF_8))
                            .mediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                            .build());
            try (
                    Response response = client.target(createCombinedUri(uri, "test/multi-form-param"))
                            .request(MediaType.MULTIPART_FORM_DATA_TYPE)
                            .post(Entity.entity(new GenericEntity<>(multipart) {
                            }, MediaType.MULTIPART_FORM_DATA))) {
                Assertions.assertEquals(200, response.getStatus());
                final List<EntityPart> entityParts = response.readEntity(new GenericType<>() {
                });
                if (entityParts.size() != 3) {
                    final String msg = "Expected 3 entries, received " +
                            entityParts.size() +
                            '.' +
                            System.lineSeparator() +
                            getMessage(entityParts);
                    Assertions.fail(msg);
                }
                verifyEntityPart(entityParts, "received-entity-part", "test entity part");
                verifyEntityPart(entityParts, "received-string", "test string");
                verifyEntityPart(entityParts, "received-input-stream", "test input stream");
            }
        }
    }

    private static void verifyEntityPart(final List<EntityPart> parts, final String name, final String text)
            throws IOException {
        final EntityPart part = find(parts, name);
        Assertions.assertNotNull(part,
                String.format("Failed to find entity part %s in: %s", name, getMessage(parts)));
        Assertions.assertEquals(text, part.getContent(String.class));
    }

    private static Supplier<String> getMessage(final List<EntityPart> parts) {
        return () -> {
            final StringBuilder msg = new StringBuilder();
            final Iterator<EntityPart> iter = parts.iterator();
            while (iter.hasNext()) {
                final EntityPart part = iter.next();
                try {
                    msg.append('[')
                            .append(part.getName())
                            .append("={")
                            .append("headers=")
                            .append(part.getHeaders())
                            .append(", mediaType=")
                            .append(part.getMediaType())
                            .append(", body=")
                            .append(toString(part.getContent()))
                            .append('}');
                } catch (IOException e) {
                    Assertions.fail("Unable to proces Entityparts." + e);                    
                }
                if (iter.hasNext()) {
                    msg.append("], ");
                } else {
                    msg.append(']');
                }
            }
            return msg.toString();
        };
    }
    
    private static String toString(final InputStream in) throws IOException {
        // try-with-resources fails here due to a bug in the
        //noinspection TryFinallyCanBeTryWithResources
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[32];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return out.toString(StandardCharsets.UTF_8);
        } finally {
            in.close();
        }
    }

    private static EntityPart find(final Collection<EntityPart> parts, final String name) {
        for (EntityPart part : parts) {
            if (name.equals(part.getName())) {
                return part;
            }
        }
        return null;
    }

    private static URI createCombinedUri(final URI uri, final String path) throws URISyntaxException {
        if (path == null || path.isEmpty()) {
            return uri;
        }
        String uriString = uri.toString();

        TestUtil.logMsg("Initial uri string: " + uriString);

        final StringBuilder builder = new StringBuilder(uriString);
        if (builder.charAt(builder.length() - 1) == '/') {
            if (path.charAt(0) == '/') {
                builder.append(path.substring(1));
            } else {
                builder.append(path);
            }
        } else if (path.charAt(0) == '/') {
            builder.append(path.substring(1));
        } else {
            builder.append('/').append(path);
        }

        String builderString = builder.toString();
        TestUtil.logMsg("Combined uri string: " + builderString);

        return new URI(builderString);
    }

    @ApplicationPath("/")
    public static class MultipartTestApplication extends Application {
    }

    @Path("/test")
    public static class TestResource {

        @POST
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.MULTIPART_FORM_DATA)
        @Path("/basicTest")
        public Response basicTest(final List<EntityPart> parts) throws IOException {
            final List<EntityPart> multipart = List.of(
                    EntityPart.withName("received-string")
                            .content(find(parts,"octet-stream").getContent(byte[].class))
                            .mediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                            .build(),
                    EntityPart.withName("received-file")
                            .content(find(parts,"file").getFileName().get(),find(parts,"file").getContent())
                            .mediaType(MediaType.APPLICATION_XML)
                            .build(),
                    EntityPart.withName("added-input-stream")
                            .content(new ByteArrayInputStream("Add part on return.".getBytes()))
                            .mediaType("text/asciidoc")
                            .header("Header1","Test1")
                            .header("Header2","Test2")
                            .build());
            return Response.ok(new GenericEntity<>(multipart) {
                    }, MediaType.MULTIPART_FORM_DATA).build();
        }

        @POST
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.MULTIPART_FORM_DATA)
        @Path("/multi-form-param")
        public Response multipleFormParamTest(@FormParam("string-part") final String string,
                @FormParam("entity-part") final EntityPart entityPart,
                @FormParam("input-stream-part") final InputStream in) throws IOException {
            final List<EntityPart> multipart = List.of(
                    EntityPart.withName("received-entity-part")
                            .content(entityPart.getContent(String.class))
                            .mediaType(entityPart.getMediaType())
                            .fileName(entityPart.getFileName().orElse(null))
                            .build(),
                    EntityPart.withName("received-input-stream")
                            .content(MultipartSupportIT.toString(in).getBytes(StandardCharsets.UTF_8))
                            .mediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                            .build(),
                    EntityPart.withName("received-string")
                            .content(string)
                            .mediaType(MediaType.TEXT_PLAIN_TYPE)
                            .build());
            return Response.ok(new GenericEntity<>(multipart) {
                    }, MediaType.MULTIPART_FORM_DATA).build();
        }

    }

}