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

import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanContainer;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/default-client")
@Produces(MediaType.TEXT_PLAIN)
public class DefaultClientResource {

    @Inject
    Client client;

    @Inject
    BeanContainer beanContainer;

    @Inject
    UriInfo uriInfo;

    @GET
    @Path("/scope")
    public String scope() {
        final Bean<?> bean = beanContainer.resolve(beanContainer.getBeans(Client.class));
        return bean == null ? "none" : bean.getScope().getName();
    }

    @GET
    @Path("/call")
    public String call() {
        return client.target(uriInfo.getBaseUriBuilder().path("default-client/echo"))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);
    }

    @GET
    @Path("/echo")
    public String echo() {
        return "echo";
    }
}
