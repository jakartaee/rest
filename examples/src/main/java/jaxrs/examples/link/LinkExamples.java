/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.link;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * LinkExamples class.
 *
 * @author Santiago Pericas-Geertsen (santiago.pericasgeertsen at oracle.com)
 */
public class LinkExamples {

    /**
     * 3-step process: Build URI, build Link and build Response.
     *
     * @return response.
     */
    public Response example1() {
        URI uri = UriBuilder.fromUri("http://foo.bar/employee/john").build();
        Link link = Link.fromUri(uri).rel("emp").title("employee").build();
        return Response.ok().links(link).build();
    }

    /**
     * 2-step process: Build Link from String and build Response.
     *
     * @return response.
     */
    public Response example2() {
        Link link = Link.fromUri("http://foo.bar/employee/john").rel("manager").rel("friend")
                .title("employee").type("application/xml").build();
        System.out.println("Link = " + link);
        return Response.ok().links(link).build();
    }

    /**
     * 1-step process: Build Response and add a link directly to it
     * using either a String or a URI.
     *
     * @return response.
     * @throws URISyntaxException
     */
    public Response example3() throws URISyntaxException {
        Response r;
        r = Response.ok().link("http://foo.bar/employee/john", "manager").build();
        r = Response.ok().link(new URI("http://foo.bar/employee/john"),"manager").build();
        return r;
    }
}
