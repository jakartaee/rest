/*
 * Copyright (c) 2010, 2023 Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.ws.rs.core.MediaType;

/**
 * An injectable interface providing runtime lookup of provider instances.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see MessageBodyReader
 * @see MessageBodyWriter
 * @see ContextResolver
 * @see ExceptionMapper
 * @since 1.0
 */
public interface Providers {

    /**
     * Get a message body reader that matches a set of criteria. The set of readers is first filtered by comparing the
     * supplied value of {@code mediaType} with the value of each reader's {@link jakarta.ws.rs.Consumes}, ensuring the
     * supplied value of {@code type} is assignable to the generic type of the reader, and eliminating those that do not
     * match. The list of matching readers is then ordered with those with the best matching values of
     * {@link jakarta.ws.rs.Consumes} (x/y &gt; x&#47;* &gt; *&#47;*) sorted first. Finally, the
     * {@link MessageBodyReader#isReadable(Class, Type, Annotation[], MediaType)} method is called on each reader in order
     * using the supplied criteria and the first reader that returns {@code true} is selected and returned.
     *
     * @param <T> type of the the object that is to be read.
     * @param type the class of the object that is to be read.
     * @param genericType the type of object to be produced. E.g. if the message body is to be converted into a method
     * parameter, this will be the formal type of the method parameter as returned by
     * {@code Class.getGenericParameterTypes}.
     * @param annotations an array of the annotations on the declaration of the artifact that will be initialized with the
     * produced instance. E.g. if the message body is to be converted into a method parameter, this will be the annotations
     * on that parameter returned by {@code Class.getParameterAnnotations}.
     * @param mediaType the media type of the data that will be read.
     * @return a MessageBodyReader that matches the supplied criteria or {@code null} if none is found.
     */
    <T> MessageBodyReader<T> getMessageBodyReader(Class<T> type,
            Type genericType, Annotation[] annotations, MediaType mediaType);

    /**
     * Get a message body writer that matches a set of criteria. The set of writers is first filtered by comparing the
     * supplied value of {@code mediaType} with the value of each writer's {@link jakarta.ws.rs.Produces}, ensuring the
     * supplied value of {@code type} is assignable to the generic type of the reader, and eliminating those that do not
     * match. The list of matching writers is then ordered with those with the best matching values of
     * {@link jakarta.ws.rs.Produces} (x/y &gt; x&#47;* &gt; *&#47;*) sorted first. Finally, the
     * {@link MessageBodyWriter#isWriteable(Class, Type, Annotation[], MediaType)} method is called on each writer in order
     * using the supplied criteria and the first writer that returns {@code true} is selected and returned.
     *
     * @param <T> type of the object that is to be written.
     * @param type the class of the object that is to be written.
     * @param genericType the type of object to be written. E.g. if the message body is to be produced from a field, this
     * will be the declared type of the field as returned by {@code Field.getGenericType}.
     * @param annotations an array of the annotations on the declaration of the artifact that will be written. E.g. if the
     * message body is to be produced from a field, this will be the annotations on that field returned by
     * {@code Field.getDeclaredAnnotations}.
     * @param mediaType the media type of the data that will be written.
     * @return a MessageBodyReader that matches the supplied criteria or {@code null} if none is found.
     */
    <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> type,
            Type genericType, Annotation[] annotations, MediaType mediaType);

    /**
     * Get an exception mapping provider for a particular class of exception. Returns the provider whose generic type is the
     * nearest superclass of {@code type}.
     *
     * @param <T> type of the exception handled by the exception mapping provider.
     * @param type the class of exception.
     * @return an {@link ExceptionMapper} for the supplied type or {@code null} if none is found.
     */
    <T extends Throwable> ExceptionMapper<T> getExceptionMapper(Class<T> type);

    /**
     * Get a context resolver for a particular type of context and media type. The set of resolvers is first filtered by
     * comparing the supplied value of {@code mediaType} with the value of each resolver's {@link jakarta.ws.rs.Produces},
     * ensuring the generic type of the context resolver is assignable to the supplied value of {@code contextType}, and
     * eliminating those that do not match. If only one resolver matches the criteria then it is returned. If more than one
     * resolver matches then the list of matching resolvers is ordered with those with the best matching values of
     * {@link jakarta.ws.rs.Produces} (x/y &gt; x&#47;* &gt; *&#47;*) sorted first. A proxy is returned that delegates calls
     * to {@link ContextResolver#getContext(java.lang.Class)} to each matching context resolver in order and returns the
     * first non-null value it obtains or null if all matching context resolvers return null.
     *
     * @param <T> type of the context.
     * @param contextType the class of context desired.
     * @param mediaType the media type of data for which a context is required.
     * @return a matching context resolver instance or {@code null} if no matching context providers are found.
     */
    <T> ContextResolver<T> getContextResolver(Class<T> contextType,
            MediaType mediaType);
}
