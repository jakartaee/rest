/*
 * Copyright (c) 2015, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.sse;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

/**
 * Examples of Client-side Server-sent events processing.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public class SseClient {

    public static final WebTarget target = ClientBuilder.newClient().target("server-sent-events");

    public static void main(String[] args) {
        consumeAllEvents();
    }

    private static void consumeAllEvents() {

        // EventSource#register(Consumer<InboundSseEvent>)
        // consumes all events, writes then on standard out (System.out::println)
        try (final SseEventSource eventSource =
                     SseEventSource.target(target)
                                   .build()) {

            eventSource.register(System.out::println);
            eventSource.open();

            for (int counter = 0; counter < 5; counter++) {
                target.request().post(Entity.text("message " + counter));
            }

            Thread.sleep(500); // make sure all the events have time to arrive
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // EventSource#register(Consumer<InboundSseEvent>, Consumer<Throwable>)
        // consumes all events and all exceptions, writing both on standard out.
        try (final SseEventSource eventSource = SseEventSource.target(target).build()) {

            eventSource.register(System.out::println, Throwable::printStackTrace);
            eventSource.open();

            for (int counter = 0; counter < 5; counter++) {
                target.request().post(Entity.text("message " + counter));
            }

            Thread.sleep(500); // make sure all the events have time to arrive
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // EventSource#register(Consumer<InboundSseEvent>, Consumer<Throwable>, Runnable)
        // consumes all events and all exceptions, writing both on standard out.
        // registers onComplete callback, which will print out a message "There will be no further events."
        try (final SseEventSource eventSource = SseEventSource.target(target).build()) {

            eventSource.register(
                    System.out::println,
                    Throwable::printStackTrace,
                    () -> System.out.println("There will be no further events."));
            eventSource.open();

            for (int counter = 0; counter < 5; counter++) {
                target.request().post(Entity.text("message " + counter));
            }

            Thread.sleep(500); // make sure all the events have time to arrive
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
