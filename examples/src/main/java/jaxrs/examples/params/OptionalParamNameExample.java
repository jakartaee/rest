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

package jaxrs.examples.params;

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
 * Examples demonstrating optional parameter names in Jakarta REST annotations.
 * 
 * <p>Starting with Jakarta REST 4.0, parameter binding annotations support optional
 * parameter names. When the annotation value is omitted, the parameter name is
 * inferred from the annotated element.</p>
 * 
 * <h3>Compiler Configuration Required</h3>
 * <p>For method parameters, the {@code -parameters} compiler flag must be enabled
 * to preserve parameter names in bytecode. Field and property injection always works
 * without this flag.</p>
 * 
 * <h4>Maven Configuration:</h4>
 * <pre>{@code
 * <plugin>
 *     <groupId>org.apache.maven.plugins</groupId>
 *     <artifactId>maven-compiler-plugin</artifactId>
 *     <configuration>
 *         <compilerArgs>
 *             <arg>-parameters</arg>
 *         </compilerArgs>
 *     </configuration>
 * </plugin>
 * }</pre>
 * 
 * @see <a href="https://github.com/jakartaee/rest/issues/579">Issue #579</a>
 */
@Path("examples")
public class OptionalParamNameExample {

    /**
     * Example 1: Path parameters with optional names.
     * 
     * <p><strong>Before (explicit names required):</strong></p>
     * <pre>{@code
     * public String hello(@PathParam("name") String name, 
     *                    @PathParam("surname") String surname)
     * }</pre>
     * 
     * <p><strong>After (optional names):</strong></p>
     * <pre>{@code
     * public String hello(@PathParam String name, @PathParam String surname)
     * }</pre>
     */
    @GET
    @Path("hello/{name}/{surname}")
    public String hello(@PathParam String name, @PathParam String surname) {
        return "Hello, " + name + " " + surname;
    }

    /**
     * Example 2: Query parameters with optional names.
     * The parameter names 'query' and 'filter' are inferred from method parameters.
     */
    @GET
    @Path("search")
    public String search(@QueryParam String query, @QueryParam String filter) {
        return "Searching for: " + query + " with filter: " + filter;
    }

    /**
     * Example 3: Header parameters with optional names.
     * The parameter name 'authorization' is inferred from the method parameter.
     */
    @GET
    @Path("secure")
    public String secure(@HeaderParam String authorization) {
        return "Authorization: " + authorization;
    }

    /**
     * Example 4: Matrix parameters with optional names.
     * Matrix parameters are semicolon-separated in the path.
     */
    @GET
    @Path("matrix/{path}")
    public String matrix(@PathParam String path, 
                        @MatrixParam String color,
                        @MatrixParam String size) {
        return "Path: " + path + ", Color: " + color + ", Size: " + size;
    }

    /**
     * Example 5: Cookie parameters with optional names.
     * The parameter name 'sessionId' is inferred from the method parameter.
     */
    @GET
    @Path("session")
    public String session(@CookieParam String sessionId) {
        return "Session ID: " + sessionId;
    }

    /**
     * Example 6: Form parameters with optional names.
     * Used with application/x-www-form-urlencoded content type.
     */
    @POST
    @Path("login")
    public String login(@FormParam String username, @FormParam String password) {
        return "Login attempt for user: " + username;
    }

    /**
     * Example 7: Mixed usage - combining explicit and inferred names.
     * This demonstrates backward compatibility.
     */
    @GET
    @Path("mixed/{id}")
    public String mixed(@PathParam String id,
                       @QueryParam("q") String query,
                       @HeaderParam String userAgent) {
        return "ID: " + id + ", Query: " + query + ", User-Agent: " + userAgent;
    }

    /**
     * Example 8: Field injection with optional names.
     * Field injection always works without the -parameters compiler flag.
     */
    @PathParam
    private String userId;

    @QueryParam
    private String page;

    @GET
    @Path("user")
    public String getUser() {
        return "User ID: " + userId + ", Page: " + page;
    }

    /**
     * Example 9: Multiple parameters of the same type.
     * Each parameter name is inferred independently.
     */
    @GET
    @Path("profile/{userId}")
    public String profile(@PathParam String userId,
                         @QueryParam String tab,
                         @QueryParam String section,
                         @HeaderParam String accept) {
        return String.format("User: %s, Tab: %s, Section: %s, Accept: %s",
                           userId, tab, section, accept);
    }
}