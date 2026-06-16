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

package ee.jakarta.tck.ws.rs.api.rs.params.defaultnames;

import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

/**
 * Test resource for verifying optional parameter names in Jakarta REST annotations.
 * This resource demonstrates that parameter binding annotations can infer parameter
 * names when no explicit value is provided.
 * 
 * <p><strong>Important:</strong> This resource must be compiled with the
 * {@code -parameters} compiler flag to preserve method parameter names in bytecode.</p>
 */
@Path("resource")
public class DefaultParamNamesResource {

    /**
     * Tests @PathParam with inferred parameter names.
     * Path: /resource/path/{name}/{surname}
     */
    @GET
    @Path("path/{name}/{surname}")
    public String testPathParam(@PathParam String name, @PathParam String surname) {
        return name + " " + surname;
    }

    /**
     * Tests @QueryParam with inferred parameter names.
     * Path: /resource/query?search=value&filter=value
     */
    @GET
    @Path("query")
    public String testQueryParam(@QueryParam String search, @QueryParam String filter) {
        return "search=" + search + " filter=" + filter;
    }

    /**
     * Tests @HeaderParam with inferred parameter name.
     * Expects Authorization header.
     */
    @GET
    @Path("header")
    public String testHeaderParam(@HeaderParam String authorization) {
        return authorization;
    }

    /**
     * Tests @MatrixParam with inferred parameter names.
     * Path: /resource/matrix/{path};color=value;size=value
     */
    @GET
    @Path("matrix/{path}")
    public String testMatrixParam(@PathParam String path,
                                  @MatrixParam String color,
                                  @MatrixParam String size) {
        return "color=" + color + " size=" + size;
    }

    /**
     * Tests @CookieParam with inferred parameter name.
     * Expects sessionId cookie.
     */
    @GET
    @Path("cookie")
    public String testCookieParam(@CookieParam String sessionId) {
        return sessionId;
    }

    /**
     * Tests @FormParam with inferred parameter names.
     * Expects application/x-www-form-urlencoded with username and password.
     */
    @POST
    @Path("form")
    public String testFormParam(@FormParam String username, @FormParam String password) {
        return "username=" + username + " password=" + password;
    }

    /**
     * Tests mixed usage of explicit and inferred parameter names.
     * Demonstrates backward compatibility.
     * Path: /resource/mixed/{id}?page=value
     * Expects User-Agent header.
     */
    @GET
    @Path("mixed/{id}")
    public String testMixedParams(@PathParam String id,
                                  @QueryParam("page") String page,
                                  @HeaderParam String userAgent) {
        return "id=" + id + " page=" + page + " userAgent=" + userAgent;
    }

    /**
     * Field injection with inferred parameter names.
     * Field names are always available at runtime, so -parameters flag is not required.
     */
    @QueryParam
    private String userId;

    @QueryParam
    private String page;

    /**
     * Tests field injection with inferred parameter names.
     * Path: /resource/field?userId=value&page=value
     */
    @GET
    @Path("field")
    public String testFieldInjection() {
        return "userId=" + userId + " page=" + page;
    }

    /**
     * Tests all six parameter binding annotations together.
     * Path: /resource/all/{pathId};color=value?filter=value
     * Expects Authorization header, sessionId cookie, and form data.
     */
    @POST
    @Path("all/{pathId}")
    public String testAllParamTypes(@PathParam String pathId,
                                    @MatrixParam String color,
                                    @QueryParam String filter,
                                    @HeaderParam String authorization,
                                    @CookieParam String sessionId,
                                    @FormParam String username,
                                    @FormParam String action) {
        return "pathId=" + pathId + " " +
               "color=" + color + " " +
               "filter=" + filter + " " +
               "authorization=" + authorization + " " +
               "sessionId=" + sessionId + " " +
               "username=" + username + " " +
               "action=" + action;
    }
}