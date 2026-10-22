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

package ee.jakarta.tck.ws.rs.spec.context.cdi.servlet;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/servlet")
@Produces(MediaType.APPLICATION_JSON)
public class ServletResource {

    public static final String RESPONSE_HEADER = "servlet-response";

    @Inject
    @Context
    ServletConfig servletConfig;

    @Inject
    @Context
    ServletContext servletContext;

    @Inject
    @Context
    HttpServletRequest httpServletRequest;

    @Inject
    @Context
    HttpServletResponse httpServletResponse;

    @GET
    @Path("/fields")
    public Map<String, String> fields() {
        return describe(servletConfig, servletContext, httpServletRequest, httpServletResponse, "fields");
    }

    @GET
    @Path("/parameters/unqualified")
    public Map<String, String> unqualifiedParameters(final ServletConfig config, final ServletContext context,
                                                     final HttpServletRequest request,
                                                     final HttpServletResponse response) {
        return describe(config, context, request, response, "unqualified");
    }

    @GET
    @Path("/parameters/qualified")
    public Map<String, String> qualifiedParameters(@Context final ServletConfig config,
                                                   @Context final ServletContext context,
                                                   @Context final HttpServletRequest request,
                                                   @Context final HttpServletResponse response) {
        return describe(config, context, request, response, "qualified");
    }

    /**
     * Sets the response header to the value, and returns the servlet name, the context path and the value of the
     * request header the client sent.
     */
    private static Map<String, String> describe(final ServletConfig config, final ServletContext context,
                                                final HttpServletRequest request, final HttpServletResponse response,
                                                final String value) {
        response.setHeader(RESPONSE_HEADER, value);
        return Map.of("servletName", config.getServletName(),
                "contextPath", context.getContextPath(),
                "header", String.valueOf(request.getHeader("test-header")));
    }
}
