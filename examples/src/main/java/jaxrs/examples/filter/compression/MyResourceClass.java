/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.filter.compression;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Santiago Pericas-Geertsen
 */
@Path("/")
public class MyResourceClass {

    @Gzipped
    @GET
    @Produces("text/plain")
    @Path("{name}")
    public String hello(@PathParam("name") String name) {
        return "Hello " + name;
    }
}
