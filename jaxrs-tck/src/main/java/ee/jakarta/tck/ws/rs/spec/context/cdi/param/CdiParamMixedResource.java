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

package ee.jakarta.tck.ws.rs.spec.context.cdi.param;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/mixed")
@RequestScoped
public class CdiParamMixedResource {

    private final String query;
    private final UriInfo uriInfo;
    private final String customHeader;

    CdiParamMixedResource() {
        this.query = null;
        this.uriInfo = null;
        this.customHeader = null;
    }

    @Inject
    public CdiParamMixedResource(@QueryParam("q") final String query,
                                 final UriInfo uriInfo,
                                 @HeaderParam("Custom") final String customHeader) {
        this.query = query;
        this.uriInfo = uriInfo;
        this.customHeader = customHeader;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject get() {
        return Json.createObjectBuilder()
                .add("query", query != null ? query : "")
                .add("path", uriInfo != null ? uriInfo.getPath() : "")
                .add("customHeader", customHeader != null ? customHeader : "")
                .build();
    }
}
