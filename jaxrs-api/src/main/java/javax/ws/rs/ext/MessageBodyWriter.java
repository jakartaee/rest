/*
 * Copyright (c) 2010, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.ext;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Contract for a provider that supports the conversion of a Java type to a
 * stream.
 *
 * A {@code MessageBodyWriter} implementation may be annotated
 * with {@link javax.ws.rs.Produces} to restrict the media types for which it will
 * be considered suitable.
 * <p>
 * Providers implementing {@code MessageBodyWriter} contract must be either programmatically
 * registered in an API runtime or must be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} annotation to be automatically discovered
 * by the runtime during a provider scanning phase.
 * </p>
 *
 * @param <T> the type that can be written.
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see Provider
 * @see javax.ws.rs.Produces
 * @since 1.0
 */
public interface MessageBodyWriter<T> {

    /**
     * Ascertain if the MessageBodyWriter supports a particular type.
     *
     * @param type        the class of instance that is to be written.
     * @param genericType the type of instance to be written, obtained either
     *                    by reflection of a resource method return type or via inspection
     *                    of the returned instance. {@link javax.ws.rs.core.GenericEntity}
     *                    provides a way to specify this information at runtime.
     * @param annotations an array of the annotations attached to the message entity instance.
     * @param mediaType   the media type of the HTTP entity.
     * @return {@code true} if the type is supported, otherwise {@code false}.
     */
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType);

    /**
     * Originally, the method has been called before {@code writeTo} to ascertain the length in bytes of
     * the serialized form of {@code t}. A non-negative return value has been used in a HTTP
     * {@code Content-Length} header.
     * <p>
     * As of version 2.0 of this API, the method has been deprecated and the value returned by the method is ignored
     * by an API runtime. All {@code MessageBodyWriter} implementations are advised to return {@code -1}
     * from the method. Responsibility to compute the actual {@code Content-Length} header value has been
     * delegated to the runtime.
     * </p>
     *
     * @param t           the instance to write
     * @param type        the class of instance that is to be written.
     * @param genericType the type of instance to be written. {@link javax.ws.rs.core.GenericEntity}
     *                    provides a way to specify this information at runtime.
     * @param annotations an array of the annotations attached to the message entity instance.
     * @param mediaType   the media type of the HTTP entity.
     * @return length in bytes or -1 if the length cannot be determined in advance.
     */
    public default long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType) {
        return -1L;
    }

    /**
     * Write a type to an HTTP message. The message header map is mutable
     * but any changes must be made before writing to the output stream since
     * the headers will be flushed prior to writing the message body.
     *
     * @param t            the instance to write.
     * @param type         the class of instance that is to be written.
     * @param genericType  the type of instance to be written. {@link javax.ws.rs.core.GenericEntity}
     *                     provides a way to specify this information at runtime.
     * @param annotations  an array of the annotations attached to the message entity instance.
     * @param mediaType    the media type of the HTTP entity.
     * @param httpHeaders  a mutable map of the HTTP message headers.
     * @param entityStream the {@link OutputStream} for the HTTP entity. The
     *                     implementation should not close the output stream.
     * @throws java.io.IOException if an IO error arises.
     * @throws javax.ws.rs.WebApplicationException
     *                             if a specific HTTP error response needs to be produced.
     *                             Only effective if thrown prior to the message being committed.
     */
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream)
            throws java.io.IOException, javax.ws.rs.WebApplicationException;
}
