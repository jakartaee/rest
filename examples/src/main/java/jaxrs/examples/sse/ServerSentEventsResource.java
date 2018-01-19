/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.sse;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Pavel Bucek (pavel.bucek at oracle.com)
 */
@Path("server-sent-events")
@Singleton
public class ServerSentEventsResource {

    private final Object outputLock = new Object();
    private final Sse sse;
    private volatile SseEventSink eventSink;

    @Resource
    private ManagedExecutorService executorService;

    @Inject
    public ServerSentEventsResource(Sse sse) {
        this.sse = sse;
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getMessageQueue(@Context SseEventSink eventSink) {
        synchronized (outputLock) {
            if (this.eventSink != null) {
                throw new IllegalStateException("Server sink already served.");
            }
            this.eventSink = eventSink;
        }
    }

    @POST
    public void addMessage(final String message) throws IOException {
        if (eventSink == null) {
            throw new IllegalStateException("No client connected.");
        }
        eventSink.send(sse.newEvent("custom-message"));
    }

    @DELETE
    public void close() throws IOException {
        synchronized (outputLock) {
            if (eventSink != null) {
                eventSink.close();
                eventSink = null;
            }
        }
    }

    @POST
    @Path("domains/{id}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void startDomain(@PathParam("id") final String id,
                            @Context SseEventSink eventSink) {

        executorService.submit(() -> {
            try {
                eventSink.send(sse.newEventBuilder().name("domain-progress")
                                  .data(String.class, "starting domain " + id + " ...").build());
                Thread.sleep(200);
                eventSink.send(sse.newEvent("domain-progress", "50%"));
                Thread.sleep(200);
                eventSink.send(sse.newEvent("domain-progress", "60%"));
                Thread.sleep(200);
                eventSink.send(sse.newEvent("domain-progress", "70%"));
                Thread.sleep(200);
                eventSink.send(sse.newEvent("domain-progress", "99%"));
                Thread.sleep(200);
                eventSink.send(sse.newEvent("domain-progress", "Done."));
                eventSink.close();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
