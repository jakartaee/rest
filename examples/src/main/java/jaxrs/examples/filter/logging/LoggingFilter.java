/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.filter.logging;

import java.io.IOException;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import javax.annotation.Priority;

/**
 * Example of a logging resource filter (server-side).
 *
 * @author Santiago Pericas-Geertsen
 * @author Marek Potociar
 */
@Provider
@Logged // name-bound => resource filter
@Priority(Priorities.USER)
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log(requestContext);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log(responseContext);
    }

    private static void log(ContainerRequestContext context) {
        // implementation goes here
    }

    private static void log(ContainerResponseContext context) {
        // implementation goes here
    }
}
