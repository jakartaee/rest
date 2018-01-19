/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.rc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

/**
 * Example of using resource context to get a resource instance for a class.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
@Path("foo/{fooId}")
public class FooResource {
    public static class BarResource {
        @PathParam("fooId")
        private String fooId;

        @GET
        public String getIt() {
            return String.format("Got it from %s!", fooId);
        }
    }

    @Path("bar")
    public BarResource getBar(@Context ResourceContext rc) {
        return rc.getResource(BarResource.class);
    }

}
