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
 * Server-side entry point for creating {@link OutboundSseEvent} and {@link SseBroadcaster}.
 * <p>
 * Instance of this interface can be injected into a field or as a parameter of a method or
 * a constructor. Also, the instance is thread safe, meaning that it can be shared and its
 * method invoked from different threads without causing inconsistent internal state.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 * @since 2.1
 */
public interface Sse {

    /**
     * Get a new outbound event builder.
     *
     * @return SSE outbound event builder.
     */
    OutboundSseEvent.Builder newEventBuilder();

    /**
     * Create new {@link OutboundSseEvent} from provided data.
     * <p>
     * The data can be string only and must not be null.
     *
     * @param data event data.
     * @return created {@code OutboundSseEvent}.
     * @throws IllegalArgumentException when data is {@code null}.
     */
    default OutboundSseEvent newEvent(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter 'data' must not be null.");
        }

        return newEventBuilder().data(String.class, data).build();
    }

    /**
     * Create new {@link OutboundSseEvent} from provided data and name.
     *
     * @param name event name. (see {@link OutboundSseEvent#getName()}).
     * @param data event data.
     * @return created {@code OutboundSseEvent}.
     * @throws IllegalArgumentException when name or data is {@code null}.
     */
    default OutboundSseEvent newEvent(String name, String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter 'data' must not be null.");
        }

        if (name == null) {
            throw new IllegalArgumentException("Parameter 'name' must not be null.");
        }

        return newEventBuilder().data(String.class, data).name(name).build();
    }

    /**
     * Get a new Server-sent event broadcaster.
     *
     * @return new Server-sent event broadcaster instance.
     */
    SseBroadcaster newBroadcaster();
}
