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

package jakarta.ws.rs.client;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.MessageBodyWriter;

/**
 * Client request filter context.
 *
 * A mutable class that provides request-specific information for the filter, such as request URI, message headers,
 * message entity or request-scoped properties. The exposed setters allow modification of the exposed request-specific
 * information.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface ClientRequestContext {

    /**
     * Returns the property with the given name registered in the current request/response exchange context, or {@code null}
     * if there is no property by that name.
     * <p>
     * A property allows a JAX-RS filters and interceptors to exchange additional custom information not already provided by
     * this interface.
     * </p>
     * <p>
     * A list of supported properties can be retrieved using {@link #getPropertyNames()}. Custom property names should
     * follow the same convention as package names.
     * </p>
     *
     * @param name a {@code String} specifying the name of the property.
     * @return an {@code Object} containing the value of the property, or {@code null} if no property exists matching the
     * given name.
     * @see #getPropertyNames()
     */
    public Object getProperty(String name);

    /**
     * Returns an immutable {@link Collection collection} containing the property names available within the context of the
     * current request/response exchange context.
     * <p>
     * Use the {@link #getProperty} method with a property name to get the value of a property.
     * </p>
     *
     * @return an immutable {@link Collection collection} of property names.
     * @see #getProperty
     */
    public Collection<String> getPropertyNames();

    /**
     * Returns {@code true} if the property with the given name is registered in the current request/response exchange
     * context, or {@code false} if there is no property by that name.
     * <p>
     * Use the {@link #getProperty} method with a property name to get the value of a property.
     * </p>
     *
     * @param name a {@code String} specifying the name of the property.
     * @return {@code true} if this property is registered in the context, or {@code false} if no property exists matching
     * the given name.
     * @see #getPropertyNames()
     */
    public default boolean hasProperty(String name) {
        return getProperty(name) != null;
    }

    /**
     * Binds an object to a given property name in the current request/response exchange context. If the name specified is
     * already used for a property, this method will replace the value of the property with the new value.
     * <p>
     * A property allows a JAX-RS filters and interceptors to exchange additional custom information not already provided by
     * this interface.
     * </p>
     * <p>
     * A list of supported properties can be retrieved using {@link #getPropertyNames()}. Custom property names should
     * follow the same convention as package names.
     * </p>
     * <p>
     * If a {@code null} value is passed, the effect is the same as calling the {@link #removeProperty(String)} method.
     * </p>
     *
     * @param name a {@code String} specifying the name of the property.
     * @param object an {@code Object} representing the property to be bound.
     */
    public void setProperty(String name, Object object);

    /**
     * Removes a property with the given name from the current request/response exchange context. After removal, subsequent
     * calls to {@link #getProperty} to retrieve the property value will return {@code null}.
     *
     * @param name a {@code String} specifying the name of the property to be removed.
     */
    public void removeProperty(String name);

    /**
     * Get the request URI.
     *
     * @return request URI.
     */
    public URI getUri();

    /**
     * Set a new request URI.
     *
     * @param uri new request URI.
     */
    public void setUri(URI uri);

    /**
     * Get the request method.
     *
     * @return the request method.
     * @see jakarta.ws.rs.HttpMethod
     */
    public String getMethod();

    /**
     * Set the request method.
     *
     * @param method new request method.
     * @see jakarta.ws.rs.HttpMethod
     */
    public void setMethod(String method);

    /**
     * Get the mutable request headers multivalued map.
     *
     * @return mutable multivalued map of request headers.
     * @see #getStringHeaders()
     * @see #getHeaderString(String)
     */
    public MultivaluedMap<String, Object> getHeaders();

    /**
     * Get a string view of header values associated with the message.
     *
     * Changes in the underlying {@link #getHeaders() headers map} are reflected in this view.
     * <p>
     * The method converts the non-string header values to strings using a
     * {@link jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one is available via
     * {@link jakarta.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)} for the class of the value or using the
     * values {@code toString} method if a header delegate is not available.
     * </p>
     *
     * @return response headers as a string view of header values.
     * @see #getHeaders()
     * @see #getHeaderString(String)
     */
    public abstract MultivaluedMap<String, String> getStringHeaders();

    /**
     * Get a message header as a single string value.
     *
     * Each single header value is converted to String using a {@link jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one
     * is available via {@link jakarta.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)} for the header value
     * class or using its {@code toString} method if a header delegate is not available.
     *
     * @param name the message header.
     * @return the message header value. If the message header is not present then {@code null} is returned. If the message
     * header is present but has no value then the empty string is returned. If the message header is present more than once
     * then the values of joined together and separated by a ',' character.
     * @see #getHeaders()
     * @see #getStringHeaders()
     */
    public String getHeaderString(String name);

    /**
     * Checks whether a header with a specific name and value (or item of the comma-separated value list) exists.
     *
     * Each single header value is converted to String using a {@link jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one
     * is available via {@link jakarta.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)} for the header value
     * class or using its {@code toString} method if a header delegate is not available.
     *
     * <p>
     * For example: {@code containsHeaderString("cache-control", "no-store"::equalsIgnoreCase)} will return {@code true} if
     * a {@code Cache-Control} header exists that has the value {@code no-store}, the value {@code No-Store} or the value
     * {@code Max-Age, NO-STORE, no-transform}, but {@code false} when it has the value {@code no-store;no-transform}
     * (missing comma), or the value {@code no - store} (whitespace within value).
     *
     * @param name the message header.
     * @param valuePredicate value must fulfil this predicate.
     * @return {@code true} if and only if a header with the given name exists, having either a whitespace-trimmed value
     * matching the predicate, or having at least one whitespace-trimmed single value in a comma-separated list of single values.
     * @see #getHeaders()
     * @see #getHeaderString(String)
     * @since 4.0
     */
    public boolean containsHeaderString(String name, Predicate<String> valuePredicate);

    /**
     * Get message date.
     *
     * @return the message date, otherwise {@code null} if not present.
     */
    public Date getDate();

    /**
     * Get the language of the entity.
     *
     * @return the language of the entity or {@code null} if not specified
     */
    public Locale getLanguage();

    /**
     * Get the media type of the entity.
     *
     * @return the media type or {@code null} if not specified (e.g. there's no request entity).
     */
    public MediaType getMediaType();

    /**
     * Get a list of media types that are acceptable for the response.
     *
     * @return a read-only list of requested response media types sorted according to their q-value, with highest preference
     * first.
     */
    public List<MediaType> getAcceptableMediaTypes();

    /**
     * Get a list of languages that are acceptable for the response.
     *
     * @return a read-only list of acceptable languages sorted according to their q-value, with highest preference first.
     */
    public List<Locale> getAcceptableLanguages();

    /**
     * Get any cookies that accompanied the request.
     *
     * @return a read-only map of cookie name (String) to {@link Cookie}.
     */
    public Map<String, Cookie> getCookies();

    /**
     * Check if there is an entity available in the request.
     *
     * The method returns {@code true} if the entity is present, returns {@code false} otherwise.
     *
     * @return {@code true} if there is an entity present in the message, {@code false} otherwise.
     */
    public boolean hasEntity();

    /**
     * Get the message entity Java instance.
     *
     * Returns {@code null} if the message does not contain an entity.
     *
     * @return the message entity or {@code null} if message does not contain an entity body.
     */
    public Object getEntity();

    /**
     * Get the raw entity type information.
     *
     * @return raw entity type.
     */
    public Class<?> getEntityClass();

    /**
     * Get the generic entity type information.
     *
     * @return generic entity type.
     */
    public Type getEntityType();

    /**
     * Set a new message entity. The existing entity {@link #getEntityAnnotations() annotations} and {@link #getMediaType()
     * media type} are preserved.
     * <p>
     * It is the callers responsibility to wrap the actual entity with {@link jakarta.ws.rs.core.GenericEntity} if
     * preservation of its generic type is required.
     * </p>
     *
     * @param entity entity object.
     * @see #setEntity(Object, java.lang.annotation.Annotation[], jakarta.ws.rs.core.MediaType)
     * @see MessageBodyWriter
     */
    public void setEntity(final Object entity);

    /**
     * Set a new message entity, including the attached annotations and the media type.
     * <p>
     * It is the callers responsibility to wrap the actual entity with {@link jakarta.ws.rs.core.GenericEntity} if
     * preservation of its generic type is required.
     * </p>
     *
     * @param entity entity object.
     * @param annotations annotations attached to the entity instance.
     * @param mediaType entity media type.
     * @see #setEntity(Object)
     * @see MessageBodyWriter
     */
    public void setEntity(
            final Object entity,
            final Annotation[] annotations,
            final MediaType mediaType);

    /**
     * Get the annotations attached to the entity instance.
     * <p>
     * Note that the returned annotations array contains only those annotations explicitly attached to entity instance (such
     * as the ones attached using
     * {@link Entity#Entity(Object, jakarta.ws.rs.core.MediaType, java.lang.annotation.Annotation[])} method). The entity
     * instance annotations array does not include annotations declared on the entity implementation class or its ancestors.
     * </p>
     *
     * @return annotations attached to the entity instance.
     */
    public Annotation[] getEntityAnnotations();

    /**
     * Get the entity output stream. The JAX-RS runtime is responsible for closing the output stream.
     *
     * @return entity output stream.
     */
    public OutputStream getEntityStream();

    /**
     * Set a new entity output stream. The JAX-RS runtime is responsible for closing the output stream.
     *
     * @param outputStream new entity output stream.
     */
    public void setEntityStream(OutputStream outputStream);

    /**
     * Get the client instance associated with the request.
     *
     * @return client instance associated with the request.
     */
    public Client getClient();

    /**
     * Get the immutable configuration of the request.
     *
     * @return immutable request configuration.
     */
    public Configuration getConfiguration();

    /**
     * Abort the filter chain with a response.
     *
     * This method breaks the filter chain processing and returns the provided response back to the client. The provided
     * response goes through the chain of applicable response filters.
     *
     * @param response response to be sent back to the client.
     */
    public void abortWith(Response response);
}
