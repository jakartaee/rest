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
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 * Asynchronous event-based request processing example.
 *
 * @author Marek Potociar
 */
@Path("/async/nextMessage")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class AsyncEventResource implements CompletionCallback {
    private static final BlockingQueue<String> messages = new ArrayBlockingQueue<String>(5);

    @GET
    public void readMessage(@Suspended final AsyncResponse ar) {
        ar.register(AsyncEventResource.class);
        Executors.newSingleThreadExecutor().submit(new Runnable() {

            @Override
            public void run() {
                try {
                    ar.resume(messages.take());
                } catch (InterruptedException ex) {
                    Logger.getLogger(AsyncEventResource.class.getName()).log(Level.SEVERE, null, ex);
                    ar.cancel(); // close the open connection
                }
            }
        });
    }

    @POST
    public void postMessage(final String message, @Suspended final AsyncResponse asyncResponse) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {

            @Override
            public void run() {
                try {
                    messages.put(message);
                    asyncResponse.resume("Message stored.");
                } catch (InterruptedException ex) {
                    Logger.getLogger(AsyncEventResource.class.getName()).log(Level.SEVERE, null, ex);
                    asyncResponse.resume(ex); // propagate info about the problem
                }
            }
        });
    }

    @Override
    public void onComplete(Throwable throwable) {
        if (throwable == null) {
            System.out.println("Completed with a response.");
        } else {
            System.out.println("Completed with an unmapped exception.");
        }
    }
}
