/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.sse;

import java.io.IOException;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

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
    public void getMessageQueue(SseEventSink sseEventSink) {
        synchronized (outputLock) {
            if (this.eventSink != null) {
                throw new IllegalStateException("Server sink already served.");
            }
            this.eventSink = sseEventSink;
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
    public void startDomain(@PathParam("id") final String id, SseEventSink sseEventSink) {

        executorService.submit(() -> {
            try {
                sseEventSink.send(sse.newEventBuilder().name("domain-progress")
                        .data(String.class, "starting domain " + id + " ...").build());
                Thread.sleep(200);
                sseEventSink.send(sse.newEvent("domain-progress", "50%"));
                Thread.sleep(200);
                sseEventSink.send(sse.newEvent("domain-progress", "60%"));
                Thread.sleep(200);
                sseEventSink.send(sse.newEvent("domain-progress", "70%"));
                Thread.sleep(200);
                sseEventSink.send(sse.newEvent("domain-progress", "99%"));
                Thread.sleep(200);
                sseEventSink.send(sse.newEvent("domain-progress", "Done."));
                sseEventSink.close();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
