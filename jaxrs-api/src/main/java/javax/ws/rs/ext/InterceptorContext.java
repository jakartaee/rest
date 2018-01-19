/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

/**
 * Context shared by message body interceptors that can be used to wrap
 * calls to {@link javax.ws.rs.ext.MessageBodyReader#readFrom} and
 * {@link javax.ws.rs.ext.MessageBodyWriter#writeTo}. The getters and
 * setters in this context class correspond to the parameters in
 * the aforementioned methods.
 *
 * @author Santiago Pericas-Geertsen
 * @author Bill Burke
 * @see ReaderInterceptor
 * @see WriterInterceptor
 * @see ReaderInterceptorContext
 * @see WriterInterceptorContext
 * @since 2.0
 */
public interface InterceptorContext {

    /**
     * Returns the property with the given name registered in the current request/response
     * exchange context, or {@code null} if there is no property by that name.
     * <p>
     * A property allows a JAX-RS filters and interceptors to exchange
     * additional custom information not already provided by this interface.
     * </p>
     * <p>
     * A list of supported properties can be retrieved using {@link #getPropertyNames()}.
     * Custom property names should follow the same convention as package names.
     * </p>
     * <p>
     * In a Servlet container, on the server side, the properties are backed by the
     * {@code ServletRequest} and contain all the attributes available in the {@code ServletRequest}.
     * </p>
     *
     * @param name a {@code String} specifying the name of the property.
     * @return an {@code Object} containing the value of the property, or
     *         {@code null} if no property exists matching the given name.
     * @see #getPropertyNames()
     */
    public Object getProperty(String name);

    /**
     * Returns an immutable {@link java.util.Collection collection} containing the property
     * names available within the context of the current request/response exchange context.
     * <p>
     * Use the {@link #getProperty} method with a property name to get the value of
     * a property.
     * </p>
     * <p>
     * In a Servlet container, the properties are synchronized with the {@code ServletRequest}
     * and expose all the attributes available in the {@code ServletRequest}. Any modifications
     * of the properties are also reflected in the set of properties of the associated
     * {@code ServletRequest}.
     * </p>
     *
     * @return an immutable {@link java.util.Collection collection} of property names.
     * @see #getProperty
     */
    public Collection<String> getPropertyNames();

    /**
     * Binds an object to a given property name in the current request/response
     * exchange context. If the name specified is already used for a property,
     * this method will replace the value of the property with the new value.
     * <p>
     * A property allows a JAX-RS filters and interceptors to exchange
     * additional custom information not already provided by this interface.
     * </p>
     * <p>
     * A list of supported properties can be retrieved using {@link #getPropertyNames()}.
     * Custom property names should follow the same convention as package names.
     * </p>
     * <p>
     * If a {@code null} value is passed, the effect is the same as calling the
     * {@link #removeProperty(String)} method.
     * </p>
     * <p>
     * In a Servlet container, on the server side, the properties are backed by the
     * {@code ServletRequest} and contain all the attributes available in the {@code ServletRequest}.
     * </p>
     *
     * @param name   a {@code String} specifying the name of the property.
     * @param object an {@code Object} representing the property to be bound.
     */
    public void setProperty(String name, Object object);

    /**
     * Removes a property with the given name from the current request/response
     * exchange context. After removal, subsequent calls to {@link #getProperty}
     * to retrieve the property value will return {@code null}.
     * <p>
     * In a Servlet container, on the server side, the properties are backed by the
     * {@code ServletRequest} and contain all the attributes available in the {@code ServletRequest}.
     * </p>
     *
     * @param name a {@code String} specifying the name of the property to be removed.
     */
    public void removeProperty(String name);

    /**
     * Get an array of the annotations formally declared on the artifact that
     * initiated the intercepted entity provider invocation.
     *
     * E.g. if the message body is to be converted into a method parameter, this
     * will be the annotations on that parameter returned by
     * {@link java.lang.reflect.Method#getParameterAnnotations Method.getParameterAnnotations()};
     * if the server-side response entity instance is to be converted into an
     * output stream, this will be the annotations on the matched resource method
     * returned by {@link java.lang.reflect.Method#getAnnotations() Method.getAnnotations()}.
     *
     * This method may return an empty array in case the interceptor is
     * not invoked in a context of any particular resource method
     * (e.g. as part of the client API), but will never return {@code null}.
     *
     * @return annotations declared on the artifact that initiated the intercepted
     *         entity provider invocation.
     */
    public Annotation[] getAnnotations();

    /**
     * Update annotations on the formal declaration of the artifact that
     * initiated the intercepted entity provider invocation.
     *
     * Calling this method has no effect in the client API.
     *
     * @param annotations updated annotations declarataion of the artifact that
     *                    initiated the intercepted entity provider invocation.
     *                    Must not be {@code null}.
     * @throws NullPointerException in case the input parameter is {@code null}.
     */
    public void setAnnotations(Annotation[] annotations);

    /**
     * Get Java type supported by corresponding message body provider.
     *
     * @return java type supported by provider
     */
    Class<?> getType();

    /**
     * Update Java type before calling message body provider.
     *
     * @param type java type for provider
     */
    public void setType(Class<?> type);

    /**
     * Get the type of the object to be produced or written.
     *
     * @return type of object produced or written
     */
    Type getGenericType();

    /**
     * Update type of the object to be produced or written.
     *
     * @param genericType new type for object
     */
    public void setGenericType(Type genericType);

    /**
     * Get media type of HTTP entity.
     *
     * @return media type of HTTP entity
     */
    public MediaType getMediaType();

    /**
     * Update media type of HTTP entity.
     *
     * @param mediaType new type for HTTP entity
     */
    public void setMediaType(MediaType mediaType);
}
