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

package ee.jakarta.tck.ws.rs.spec.context.cdi.client;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/produced-client")
public class ProducedClientResource {

    @Inject
    Client client;

    @Inject
    ClientBuilderProducer producer;

    @Inject
    ConfigurationFilter configurationFilter;

    @Inject
    UriInfo uriInfo;

    @Inject
    HttpHeaders httpHeaders;

    /**
     * Returns the header the {@link ConfigurationFilter} added to the client request.
     */
    @GET
    @Path("/echo")
    @Produces(MediaType.TEXT_PLAIN)
    public String echo() {
        return httpHeaders.getHeaderString(ConfigurationFilter.CLIENT_HEADER);
    }

    @GET
    @Path("/produced")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> produced() {
        return Map.of("echo", call(client),
                "count", String.valueOf(producer.count()));
    }

    @GET
    @Path("/shared")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> shared() {
        try (Client second = ClientBuilder.newBuilder()
                .property(ConfigurationFilter.CLIENT_NAME, "second")
                .register(configurationFilter)
                .register(new NameResolver("second"))
                .build()) {
            return Map.of("produced", call(client),
                    "second", call(second));
        }
    }

    private String call(final Client invoker) {
        return invoker.target(uriInfo.getBaseUriBuilder().path("produced-client/echo"))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);
    }
}
