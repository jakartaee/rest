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
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Server-Sent events broadcasting facility.
 * <p>
 * Server broadcaster can be used to manage multiple {@link SseEventSink server sinks}. It enables
 * sending events to all registered event outputs and provides facility to effectively handle
 * exceptions and closures of individual registered event outputs.
 * <p>
 * Instance of this interface is thread safe, meaning that it can be shared and its method invoked
 * from different threads without causing inconsistent internal state.
 *
 * @author Marek Potociar
 * @since 2.1
 */
public interface SseBroadcaster extends AutoCloseable {

    /**
     * Register a listener, which will be called when an exception was thrown by a given SSE event output when trying
     * to write to it or close it.
     * <p>
     * This operation is potentially slow, especially if large number of listeners get registered in the broadcaster.
     * The {@code SseBroadcaster} implementation is optimized to efficiently handle small amounts of
     * concurrent listener registrations and removals and large amounts of registered listener notifications.
     *
     * @param onError bi-consumer, taking two parameters: {@link SseEventSink}, which is the source of the
     *                error and the actual {@link Throwable} instance.
     */
    void onError(BiConsumer<SseEventSink, Throwable> onError);

    /**
     * Register a listener, which will be called when the SSE event output has been closed (either by client closing
     * the connection or by calling {@link SseEventSink#close()} on the server side.
     * <p>
     * This operation is potentially slow, especially if large number of listeners get registered in the broadcaster.
     * The {@code SseBroadcaster} implementation is optimized to efficiently handle small amounts of
     * concurrent listener registrations and removals and large amounts of registered listener notifications.
     *
     * @param onClose consumer taking single parameter, a {@link SseEventSink}, which was closed.
     */
    void onClose(Consumer<SseEventSink> onClose);

    /**
     * Register provided {@link SseEventSink} instance to this {@code SseBroadcaster}.
     *
     * @param sseEventSink to be registered.
     */
    void register(SseEventSink sseEventSink);

    /**
     * Publish an SSE event to all registered {@link SseEventSink} instances.
     *
     * @param event SSE event to be published.
     * @return completion stage that completes when the event has been broadcast to all registered event sinks.
     */
    CompletionStage<?> broadcast(final OutboundSseEvent event);

    /**
     * Close the broadcaster and all registered {@link SseEventSink} instances.
     * <p>
     * Any other resources associated with the {@link SseBroadcaster} should be released.
     * <p>
     * Subsequent calls have no effect and are ignored. Once the {@link SseBroadcaster} is closed,
     * invoking any other method on the broadcaster instance would result in an {@link IllegalStateException}
     * being thrown.
     */
    @Override
    void close();
}
