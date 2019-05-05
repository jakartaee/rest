/*
 * Copyright (c) 2010, 2019 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.ext;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

/**
 * Contract for a provider that supports the conversion of a Java type to a stream.
 *
 * A {@code MessageBodyWriter} implementation may be annotated with {@link jakarta.ws.rs.Produces} to restrict the media
 * types for which it will be considered suitable. The {@code MessageBodyWriter} pipeline is only invoked if there is 
 * a non-null response entity.
 * <p>
 * Providers implementing {@code MessageBodyWriter} contract must be either programmatically registered in a JAX-RS
 * runtime or must be annotated with {@link jakarta.ws.rs.ext.Provider &#64;Provider} annotation to be automatically
 * discovered by the JAX-RS runtime during a provider scanning phase.
 * </p>
 *
 * @param <T> the type that can be written.
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see Provider
 * @see jakarta.ws.rs.Produces
 * @since 1.0
 */
public interface MessageBodyWriter<T> {

    /**
     * Ascertain if the MessageBodyWriter supports a particular type.
     *
     * @param type the class of instance that is to be written.
     * @param genericType the type of instance to be written, obtained either by reflection of a resource method return type
     * or via inspection of the returned instance. {@link jakarta.ws.rs.core.GenericEntity} provides a way to specify this
     * information at runtime.
     * @param annotations an array of the annotations attached to the message entity instance.
     * @param mediaType the media type of the HTTP entity.
     * @return {@code true} if the type is supported, otherwise {@code false}.
     */
    public boolean isWriteable(Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType);

    /**
     * Originally, the method has been called before {@code writeTo} to ascertain the length in bytes of the serialized form
     * of {@code t}. A non-negative return value has been used in a HTTP {@code Content-Length} header.
     * <p>
     * As of JAX-RS 2.0, the method has been deprecated and the value returned by the method is ignored by a JAX-RS runtime.
     * All {@code MessageBodyWriter} implementations are advised to return {@code -1} from the method. Responsibility to
     * compute the actual {@code Content-Length} header value has been delegated to JAX-RS runtime.
     * </p>
     *
     * @param t the instance to write
     * @param type the class of instance that is to be written.
     * @param genericType the type of instance to be written. {@link jakarta.ws.rs.core.GenericEntity} provides a way to
     * specify this information at runtime.
     * @param annotations an array of the annotations attached to the message entity instance.
     * @param mediaType the media type of the HTTP entity.
     * @return length in bytes or -1 if the length cannot be determined in advance.
     */
    public default long getSize(final T t, final Class<?> type, final Type genericType, final Annotation[] annotations,
            final MediaType mediaType) {
        return -1L;
    }

    /**
     * Write a type to an HTTP message. The message header map is mutable but any changes must be made before writing to the
     * output stream since the headers will be flushed prior to writing the message body.
     *
     * @param t the instance to write.
     * @param type the class of instance that is to be written.
     * @param genericType the type of instance to be written. {@link jakarta.ws.rs.core.GenericEntity} provides a way to
     * specify this information at runtime.
     * @param annotations an array of the annotations attached to the message entity instance.
     * @param mediaType the media type of the HTTP entity.
     * @param httpHeaders a mutable map of the HTTP message headers.
     * @param entityStream the {@link OutputStream} for the HTTP entity. The implementation must not close the output
     * stream.
     * @throws java.io.IOException if an IO error arises.
     * @throws jakarta.ws.rs.WebApplicationException if a specific HTTP error response needs to be produced. Only effective if
     * thrown prior to the message being committed.
     */
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream)
            throws java.io.IOException, jakarta.ws.rs.WebApplicationException;
}
