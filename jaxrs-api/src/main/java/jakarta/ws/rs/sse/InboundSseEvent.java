/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.sse;

import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

/**
 * Inbound Server-sent event.
 * <p>
 * Used on the client side, when accepting incoming Server-sent events.
 *
 * @author Marek Potociar
 * @since 2.1
 */
public interface InboundSseEvent extends SseEvent {

    /**
     * Check if the event is empty (i.e. does not contain any data).
     *
     * @return {@code true} if current instance does not contain any data, {@code false} otherwise.
     */
    boolean isEmpty();

    /**
     * Get the original event data as {@link String}.
     *
     * @return event data de-serialized into a string.
     * @throws jakarta.ws.rs.ProcessingException when provided type can't be read. The thrown exception wraps the original
     * cause.
     */
    String readData();

    /**
     * Read event data as a given Java type.
     *
     * @param <T> generic event data type
     * @param type Java type to be used for event data de-serialization.
     * @return event data de-serialized as an instance of a given type.
     * @throws jakarta.ws.rs.ProcessingException when provided type can't be read. The thrown exception wraps the original
     * cause.
     */
    <T> T readData(Class<T> type);

    /**
     * Read event data as a given generic type.
     *
     * @param <T> generic event data type
     * @param type generic type to be used for event data de-serialization.
     * @return event data de-serialized as an instance of a given type.
     * @throws jakarta.ws.rs.ProcessingException when provided type can't be read. The thrown exception wraps the original
     * cause.
     */
    <T> T readData(GenericType<T> type);

    /**
     * Read event data as a given Java type.
     *
     * @param <T> generic event data type
     * @param messageType Java type to be used for event data de-serialization.
     * @param mediaType {@link MediaType media type} to be used for event data de-serialization.
     * @return event data de-serialized as an instance of a given type.
     * @throws jakarta.ws.rs.ProcessingException when provided type can't be read. The thrown exception wraps the original
     * cause.
     */
    <T> T readData(Class<T> messageType, MediaType mediaType);

    /**
     * Read event data as a given generic type.
     *
     * @param <T> generic event data type
     * @param type generic type to be used for event data de-serialization.
     * @param mediaType {@link MediaType media type} to be used for event data de-serialization.
     * @return event data de-serialized as an instance of a given type.
     * @throws jakarta.ws.rs.ProcessingException when provided type can't be read. The thrown exception wraps the original
     * cause.
     */
    <T> T readData(GenericType<T> type, MediaType mediaType);

}
