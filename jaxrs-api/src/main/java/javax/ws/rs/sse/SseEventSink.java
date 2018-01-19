/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.ws.rs.sse;

import java.util.concurrent.CompletionStage;

/**
 * Outbound Server-Sent Events stream.
 * <p>
 * The instance of {@link SseEventSink} can be only acquired by injection of a resource method parameter:
 * <pre>
 * &#64;GET
 * &#64;Path("eventStream")
 * &#64;Produces(MediaType.SERVER_SENT_EVENTS)
 * public void eventStream(@Context SseEventSink eventSink) {
 *     // ...
 * }
 * </pre>
 * The injected instance is then considered as a return type, so the resource method doesn't return anything,
 * similarly as in server-side async processing.
 * <p>
 * The underlying client connection is kept open and the application code
 * is able to send events. A server-side instance implementing the interface
 * corresponds exactly to a single client HTTP connection.
 * <p>
 * The injected instance is thread safe.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 * @since 2.1
 */
public interface SseEventSink extends AutoCloseable {

    /**
     * Check if the stream has been closed already.
     * <p>
     * Please note that the client connection represented by this {@code SseServerSink} can be closed by the
     * client side when a client decides to close connection and disconnect from the server.
     *
     * @return {@code true} when closed, {@code false} otherwise.
     */
    boolean isClosed();

    /**
     * Send an outbound Server-sent event to this sink.
     * <p>
     * Event will be serialized and sent to the client.
     *
     * @param event event to be written.
     * @return completion stage that completes when the event has been sent. If there is a problem during sending of
     * an event, completion stage will be completed exceptionally.
     */
    public CompletionStage<?> send(OutboundSseEvent event);

    /**
     * Close the {@link SseEventSink} instance and release all associated resources.
     * <p>
     * Subsequent calls have no effect and are ignored. Once the {@link SseEventSink} is closed,
     * invoking any method other than this one and {@link #isClosed()} would result in
     * an {@link IllegalStateException} being thrown.
     */
    @Override
    void close();
}
