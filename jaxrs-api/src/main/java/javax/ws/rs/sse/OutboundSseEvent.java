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

import java.lang.reflect.Type;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Representation of a single outbound Server-sent event.
 * <p>
 * Used on the server side, when creating and sending an event to a client or when broadcasting.
 *
 * @author Marek Potociar
 * @since 2.1
 */
public interface OutboundSseEvent extends SseEvent {

    /**
     * A builder class used for creating {@link OutboundSseEvent} instances.
     */
    interface Builder {

        /**
         * Set the event id.
         * <p>
         * Will be send as a value of the SSE {@code "id"} field. This field is optional.
         *
         * @param id event id.
         * @return updated builder instance.
         */
        Builder id(String id);

        /**
         * Set event name.
         * <p>
         * Will be send as a value of the SSE {@code "event"} field. This field is optional.
         *
         * @param name event name.
         * @return updated builder instance.
         */
        public Builder name(String name);

        /**
         * Set reconnection delay (in milliseconds) that indicates how long the event receiver should wait
         * before attempting to reconnect in case a connection to SSE event source is lost.
         * <p>
         * Will be send as a value of the SSE {@code "retry"} field. This field is optional.
         * <p>
         * Absence of a value of this field in an {@link OutboundSseEvent} instance
         * is indicated by {@link SseEvent#RECONNECT_NOT_SET} value returned from
         * {@link #getReconnectDelay()}.
         *
         * @param milliseconds reconnection delay in milliseconds. Negative values un-set the reconnection delay.
         * @return updated builder instance.
         */
        Builder reconnectDelay(long milliseconds);

        /**
         * Set the {@link MediaType media type} of the event data.
         * <p>
         * This information is mandatory. The default value is {@link MediaType#TEXT_PLAIN}.
         *
         * @param mediaType {@link MediaType} of event data. Must not be {@code null}.
         * @return updated builder instance.
         * @throws NullPointerException in case the {@code mediaType} parameter is {@code null}.
         */
        Builder mediaType(final MediaType mediaType);

        /**
         * Set comment string associated with the event.
         * <p>
         * The comment will be serialized with the event, before event data are serialized. If the event
         * does not contain any data, a separate "event" that contains only the comment will be sent.
         * This information is optional, provided the event data are set.
         * <p>
         * Note that multiple invocations of this method result in a previous comment being replaced with a new one.
         * To achieve multi-line comments, a multi-line comment string has to be used.
         *
         * @param comment comment string.
         * @return updated builder instance.
         */
        Builder comment(String comment);

        /**
         * Set event data and java type of event data.
         * <p>
         * Type information will be used for {@link javax.ws.rs.ext.MessageBodyWriter} lookup.
         * <p>
         * Note that multiple invocations of this method result in previous even data being replaced with new one.
         *
         * @param type java type of supplied data. Must not be {@code null}.
         * @param data event data. Must not be {@code null}.
         * @return updated builder instance.
         * @throws NullPointerException in case either {@code type} or {@code data} parameter is {@code null}.
         */
        Builder data(Class type, Object data);

        /**
         * Set event data and a generic java type of event data.
         * <p>
         * Type information will be used for {@link javax.ws.rs.ext.MessageBodyWriter} lookup.
         * <p>
         * Note that multiple invocations of this method result in previous even data being replaced with new one.
         *
         * @param type generic type of supplied data. Must not be {@code null}.
         * @param data event data. Must not be {@code null}.
         * @return updated builder instance.
         * @throws NullPointerException in case either {@code type} or {@code data} parameter is {@code null}.
         */
        Builder data(GenericType type, Object data);

        /**
         * Set event data and java type of event data.
         * <p>
         * This is a convenience method that derives the event data type information from the runtime type of
         * the event data. The supplied event data may be represented as {@link javax.ws.rs.core.GenericEntity}.
         * <p>
         * Note that multiple invocations of this method result in previous even data being replaced with new one.
         *
         * @param data event data. Must not be {@code null}.
         * @return updated builder instance.
         * @throws NullPointerException in case the {@code data} parameter is {@code null}.
         */
        Builder data(Object data);

        /**
         * Build {@link OutboundSseEvent}.
         * <p>
         * There are two valid configurations:
         * <ul>
         * <li>if a {@link Builder#comment(String) comment} is set, all other parameters are optional.
         * If event {@link Builder#data(Class, Object) data} and {@link Builder#mediaType(MediaType) media type} is set,
         * event data will be serialized after the comment.</li>
         * <li>if a {@link Builder#comment(String) comment} is not set, at least the event
         * {@link Builder#data(Class, Object) data} must be set. All other parameters are optional.</li>
         * </ul>
         *
         * @return new {@link OutboundSseEvent} instance.
         * @throws IllegalStateException when called with invalid configuration (neither a comment nor event data are set).
         */
        OutboundSseEvent build();
    }

    /**
     * Get data type.
     * <p>
     * This information is used to select a proper {@link javax.ws.rs.ext.MessageBodyWriter} to be used for
     * serializing the {@link #getData() event data}.
     *
     * @return data type. May return {@code null}, if the event does not contain any data.
     */
    Class<?> getType();

    /**
     * Get generic data type.
     * <p>
     * This information is used to select a proper {@link javax.ws.rs.ext.MessageBodyWriter} to be used for
     * serializing the {@link #getData() event data}.
     *
     * @return generic data type. May return {@code null}, if the event does not contain any data.
     */
    Type getGenericType();

    /**
     * Get {@link MediaType media type} of the event data.
     * <p>
     * This information is used to a select proper {@link javax.ws.rs.ext.MessageBodyWriter} to be used for
     * serializing the {@link #getData() event data}.
     *
     * @return data {@link MediaType}.
     */
    MediaType getMediaType();

    /**
     * Get event data.
     * <p>
     * The event data, if specified, are serialized and sent as one or more SSE event {@code "data"} fields
     * (depending on the line breaks in the actual serialized data content). The data are serialized
     * using an available {@link javax.ws.rs.ext.MessageBodyWriter} that is selected based on the event
     * {@link #getType() type}, {@link #getGenericType()} generic type} and {@link #getMediaType()} media type}.
     *
     * @return event data. May return {@code null}, if the event does not contain any data.
     */
    Object getData();
}
