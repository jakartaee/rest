/*
 * Copyright (c) 2024 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.ws.rs.jaxrs40.ee.rs.core.uriinfo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

@Path("/resource")
public class UriInfoTestResource {
    public static final String ONE_POST = "{name:[a-zA-Z][a-zA-Z_0-9]*}";
    public static final String TWO_GET = "{Prefix}{p:/?}{id: ((\\d+)?)}/abc{p2:/?}{number: (([A-Za-z0-9]*)?)}";
    public static final String TWO_POST = "{Prefix}{p:/?}{id: ((\\d+)?)}/abc/{yeah}";
    public static final String THREE_SUB = "{x:[a-z]}";

    public static class SubGet {
        @PUT
        @Path(THREE_SUB)
        public String get(@Context UriInfo uriInfo) {
            return listToString(uriInfo.getMatchedResourceTemplates());
        }
    }

    @POST
    @Path("one/" + ONE_POST)
    public String post(@Context UriInfo info) {
        return listToString(info.getMatchedResourceTemplates());
    }

    @GET
    @Path("two/" + TWO_GET)
    public String get(@Context UriInfo info) {
        return listToString(info.getMatchedResourceTemplates());
    }

    @POST
    @Path("two/" + TWO_POST)
    public String postTwo(@Context UriInfo info) {
        return listToString(info.getMatchedResourceTemplates());
    }

    @Path("three/" + THREE_SUB)
    public SubGet doAnything4() {
        return new SubGet();
    }

    private static String listToString(List<String> list) {
        return list.stream().reduce("", (a, b) -> a + ";" + b);
    }
}
