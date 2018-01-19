/*
 * Copyright (c) 2015, 2017 Oracle and/or its affiliates. All rights reserved.
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

/**
 * Base Server Sent Event definition.
 * <p>
 * This interface provides basic properties of the Server Sent Event, namely ID, Name, and Comment.
 * It also provides access to the Reconnect delay property.
 * <p>
 * {@code SseEvent} is extended by another two interfaces, {@link InboundSseEvent} and
 * {@link OutboundSseEvent}. The main difference is in how are instances created and how the stored
 * data can be accessed (or provided).
 *
 * @author Marek Potociar
 * @since 2.1
 */
public interface SseEvent {

    /**
     * A "reconnection not set" value for the SSE reconnect delay set via SSE event {@code retry} field.
     */
    long RECONNECT_NOT_SET = -1;

    /**
     * Get event identifier.
     * <p>
     * Contains value of SSE {@code "id"} field. This field is optional. Method may return {@code null}, if the event
     * identifier is not specified.
     *
     * @return event id.
     */
    String getId();

    /**
     * Get event name.
     * <p>
     * Contains value of SSE {@code "event"} field. This field is optional. Method may return {@code null}, if the event
     * name is not specified.
     *
     * @return event name, or {@code null} if not set.
     */
    String getName();

    /**
     * Get a comment string that accompanies the event.
     * <p>
     * Contains value of the comment associated with SSE event. This field is optional. Method may return {@code null},
     * if the event comment is not specified.
     *
     * @return comment associated with the event.
     */
    String getComment();

    /**
     * Get new connection retry time in milliseconds the event receiver should wait before attempting to
     * reconnect after a connection to the SSE event source is lost.
     * <p>
     * Contains value of SSE {@code "retry"} field. This field is optional. Method returns {@link #RECONNECT_NOT_SET}
     * if no value has been set.
     *
     * @return reconnection delay in milliseconds or {@link #RECONNECT_NOT_SET} if no value has been set.
     */
    long getReconnectDelay();

    /**
     * Check if the connection retry time has been set in the event.
     *
     * @return {@code true} if new reconnection delay has been set in the event, {@code false} otherwise.
     */
    boolean isReconnectDelaySet();

}
