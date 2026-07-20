/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation
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

package ee.jakarta.tck.ws.rs.spec.context.cdi;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

import ee.jakarta.tck.ws.rs.common.provider.StringBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Providers;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

@Path("/inject")
@Produces(MediaType.TEXT_PLAIN)
public class RequiredContextResource {

    @Inject
    Application application;

    @Inject
    UriInfo uriInfo;

    @Inject
    Request request;

    @Inject
    HttpHeaders httpHeaders;

    @Inject
    SecurityContext securityContext;

    @Inject
    Providers providers;

    @Inject
    ResourceContext resourceContext;

    @Inject
    Configuration configuration;

    @Inject
    ResourceInfo resourceInfo;

    @Inject
    Sse sse;

    @GET
    @Path("method")
    public String method(final Application application, final UriInfo info,
                         final Request request, final HttpHeaders headers,
                         final SecurityContext security, final Providers providers,
                         final ResourceContext resources, final Configuration configuration) {
        return StringBeanEntityProviderWithInjectables.computeMask(application, info, request, headers, security, providers, resources, configuration);
    }

    @GET
    @Path("/application/{propertyName}")
    public Response application(@PathParam("propertyName") final String propertyName) {
        final var properties = application.getProperties();
        if (properties.containsKey(propertyName)) {
            return Response.ok(properties.get(propertyName)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Failed to find property %s in %s".formatted(propertyName, properties))
                .build();
    }

    @GET
    @Path("/configuration")
    public Response configuration() {
        if (configuration == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The configuration field is null.").build();
        }
        return Response.ok(configuration.getRuntimeType().toString()).build();
    }

    @GET
    @Path("/httpHeaders/{name}")
    public Response httpHeaders(@PathParam("name") final String name) {
        if (httpHeaders == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The httpHeaders field is null.").build();
        }
        return Response.ok(httpHeaders.getHeaderString(name)).build();
    }

    @GET
    @Path("/providers")
    public Response providers() {
        final var mbw = providers.getMessageBodyWriter(StringBean.class, null, null, MediaType.WILDCARD_TYPE);
        if (!(mbw instanceof StringBeanEntityProviderWithInjectables instance)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to find MessageBodyWriter for StringBean. This should be %s, but was %s"
                            .formatted(StringBeanEntityProviderWithInjectables.class, mbw))
                    .build();
        }
        return Response.ok(instance.computeMask())
                .build();
    }

    @GET
    @Path("/request")
    public Response request() {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The request field is null.").build();
        }
        return Response.ok(request.getMethod()).build();
    }

    @GET
    @Path("resourceContext")
    public Response resourceContext() {
        if (resourceContext == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The resourceContext field is null.").build();
        }
        return Response.ok(resourceContext.getResource(getClass()).getClass().getCanonicalName()).build();
    }

    @GET
    @Path("resourceInfo")
    public Response resourceInfo() {
        if (resourceInfo == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The resourceInfo field is null.").build();
        }
        return Response.ok(resourceInfo.getResourceMethod().getName()).build();
    }

    @GET
    @Path("/securityContext")
    public Response securityContext() {
        if (securityContext == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The securityContext field is null.").build();
        }
        return Response.ok(securityContext.isSecure()).build();
    }

    @GET
    @Path("/sse")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public CompletionStage<?> sse(final SseEventSink eventSink) throws IOException {
        if (eventSink == null) {
            return CompletableFuture.failedFuture(new WebApplicationException("The eventSink parameter is null"));
        }
        if (sse == null) {
            return CompletableFuture.failedFuture(new WebApplicationException("The sse field is null"));
        }
        return eventSink.send(sse.newEvent("test"))
                .whenComplete((BiConsumer<Object, Throwable>) (unused, throwable) -> {
                    try {
                        eventSink.close();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    @GET
    @Path("/uriInfo")
    public Response uriInfo() {
        if (uriInfo == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The uriInfo field is null.").build();
        }
        return Response.ok(uriInfo.getPath()).build();
    }

}
