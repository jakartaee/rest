/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 * Simple asynchronous event-based request processing example.
 *
 * @author Marek Potociar
 */
@Path("/async/nextMessage")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class SimpleAsyncEventResource {
    private static final BlockingQueue<AsyncResponse> SUSPENDED = new ArrayBlockingQueue<AsyncResponse>(5);

    @GET
    public void readMessage(@Suspended final AsyncResponse ar) throws InterruptedException {
        SUSPENDED.put(ar);
    }

    @POST
    public String postMessage(final String message) throws InterruptedException {
        SUSPENDED.take().resume(message);
        return "Message sent";
    }
}
