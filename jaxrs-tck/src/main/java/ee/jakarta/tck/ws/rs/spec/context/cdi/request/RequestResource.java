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

package ee.jakarta.tck.ws.rs.spec.context.cdi.request;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/request")
@Produces(MediaType.TEXT_PLAIN)
public class RequestResource {

    private static final Executor DELAYED = CompletableFuture.delayedExecutor(100, TimeUnit.MILLISECONDS);

    @Inject
    Configuration configuration;

    @Inject
    SuspendedResponses suspendedResponses;

    @GET
    @Path("/configured")
    public String configured() {
        return property();
    }

    @GET
    @Path("/unconfigured")
    public String unconfigured() {
        return property();
    }

    @GET
    @Path("/async")
    public void async(final AsyncResponse asyncResponse) {
        DELAYED.execute(() -> asyncResponse.resume("async"));
    }

    @GET
    @Path("/stage")
    public CompletionStage<String> stage() {
        return CompletableFuture.supplyAsync(() -> "stage", DELAYED);
    }

    @GET
    @Path("/suspend/{id}")
    public void suspend(@PathParam("id") final String id, final AsyncResponse asyncResponse) {
        asyncResponse.setTimeout(30, TimeUnit.SECONDS);
        suspendedResponses.suspend(id, asyncResponse);
    }

    @GET
    @Path("/resume/{id}")
    public Response resume(@PathParam("id") final String id) throws InterruptedException, ExecutionException {
        try {
            final AsyncResponse asyncResponse = suspendedResponses.take(id).get(10, TimeUnit.SECONDS);
            if (!asyncResponse.resume("suspended")) {
                return Response.serverError().entity("Failed to resume request %s".formatted(id)).build();
            }
            return Response.ok("resumed").build();
        } catch (TimeoutException e) {
            return Response.serverError().entity("Request %s was not suspended".formatted(id)).build();
        }
    }

    private String property() {
        final Object value = configuration.getProperty(ConfigurationFeature.PROPERTY);
        return value == null ? "none" : String.valueOf(value);
    }
}
