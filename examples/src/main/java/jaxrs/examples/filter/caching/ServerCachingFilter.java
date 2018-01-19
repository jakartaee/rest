/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.filter.caching;

import java.io.IOException;

import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import javax.annotation.Priority;

/**
 * ServerCachingFilter class.
 *
 * @author Santiago Pericas-Geertsen
 */
@Provider
@Priority(Priorities.USER)
public class ServerCachingFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Response.ResponseBuilder res = getCachedResponse(requestContext);
        if (res != null) {
            // stop the filter chain
            throw new WebApplicationException(res.build());
        }
    }

    private Response.ResponseBuilder getCachedResponse(ContainerRequestContext requestContext) {
        // implemetation goes here
        return null;
    }
}
