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

package ee.jakarta.tck.ws.rs.spec.validation;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * A resource with constrained values the implementation is required to validate. Each constraint allows at most
 * three characters, and each resource method records the ID of the request it was invoked for.
 */
@Path("/validation")
@Produces(MediaType.TEXT_PLAIN)
public class ValidationResource {

    @Inject
    @QueryParam("field")
    @Size(max = 3)
    String field;

    @Inject
    @BeanParam
    @Valid
    SearchParams searchParams;

    private String property;

    @Inject
    @QueryParam("property")
    public void setProperty(final String property) {
        this.property = property;
    }

    @Size(max = 3)
    public String getProperty() {
        return property;
    }

    @GET
    @Path("/injected")
    public String injected(@QueryParam("id") final String id) {
        Invocations.record(id);
        return "invoked";
    }

    @GET
    @Path("/parameter")
    public String parameter(@QueryParam("id") final String id,
                            @QueryParam("value") @Size(max = 3) final String value) {
        Invocations.record(id);
        return value;
    }

    @POST
    @Path("/entity")
    @Consumes(MediaType.TEXT_PLAIN)
    public String entity(@QueryParam("id") final String id, @Size(max = 3) final String entity) {
        Invocations.record(id);
        return entity;
    }

    @GET
    @Path("/return")
    @Size(max = 3)
    public String returnValue(@QueryParam("value") final String value) {
        return value;
    }

    @GET
    @Path("/exception")
    public String exception() {
        throw new ValidationException("Not a constraint violation");
    }

    @GET
    @Path("/invoked/{id}")
    public String invoked(@PathParam("id") final String id) {
        return String.valueOf(Invocations.invoked(id));
    }
}
