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

package javax.ws.rs.container;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * Container response filter context.
 *
 * A mutable class that provides response-specific information for the filter,
 * such as message headers, message entity or request-scoped properties.
 * The exposed setters allow modification of the exposed response-specific
 * information.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface ContainerResponseContext {

    /**
     * Get the status code associated with the response.
     *
     * @return the response status code or -1 if the status was not set.
     */
    public int getStatus();

    /**
     * Set a new response status code.
     *
     * @param code new status code.
     */
    public void setStatus(int code);

    /**
     * Get the complete status information associated with the response.
     *
     * @return the response status information or {@code null} if the status was
     *         not set.
     */
    public Response.StatusType getStatusInfo();

    /**
     * Set the complete status information (status code and reason phrase) associated
     * with the response.
     *
     * @param statusInfo the response status information.
     */
    public void setStatusInfo(Response.StatusType statusInfo);

    /**
     * Get the mutable response headers multivalued map.
     *
     * @return mutable multivalued map of response headers.
     * @see #getStringHeaders()
     * @see #getHeaderString(String)
     */
    public MultivaluedMap<String, Object> getHeaders();

    /**
     * Get a string view of header values associated with the message.
     *
     * Changes in the underlying {@link #getHeaders() headers map} are reflected
     * in this view.
     * <p>
     * The method converts the non-string header values to strings using a
     * {@link javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one is available via
     * {@link javax.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)} for the
     * class of the value or using the values {@code toString} method if a header delegate is
     * not available.
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
     * Each single header value is converted to String using a
     * {@link javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one is available
     * via {@link javax.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)}
     * for the header value class or using its {@code toString} method  if a header
     * delegate is not available.
     *
     * @param name the message header.
     * @return the message header value. If the message header is not present then
     *         {@code null} is returned. If the message header is present but has no
     *         value then the empty string is returned. If the message header is present
     *         more than once then the values of joined together and separated by a ','
     *         character.
     * @see #getHeaders()
     * @see #getStringHeaders()
     */
    public String getHeaderString(String name);

    /**
     * Get the allowed HTTP methods from the Allow HTTP header.
     *
     * @return the allowed HTTP methods, all methods will returned as upper case
     *         strings.
     */
    public Set<String> getAllowedMethods();

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
     * Get Content-Length value.
     *
     * @return Content-Length as integer if present and valid number. In other
     *         cases returns -1.
     */
    public int getLength();

    /**
     * Get the media type of the entity.
     *
     * @return the media type or {@code null} if not specified (e.g. there's no
     *         response entity).
     */
    public MediaType getMediaType();

    /**
     * Get any new cookies set on the response message.
     *
     * @return a read-only map of cookie name (String) to a {@link NewCookie new cookie}.
     */
    public Map<String, NewCookie> getCookies();

    /**
     * Get the entity tag.
     *
     * @return the entity tag, otherwise {@code null} if not present.
     */
    public EntityTag getEntityTag();

    /**
     * Get the last modified date.
     *
     * @return the last modified date, otherwise {@code null} if not present.
     */
    public Date getLastModified();

    /**
     * Get the location.
     *
     * @return the location URI, otherwise {@code null} if not present.
     */
    public URI getLocation();

    /**
     * Get the links attached to the message as header.
     *
     * @return links, may return empty {@link Set} if no links are present. Never
     *         returns {@code null}.
     */
    public Set<Link> getLinks();

    /**
     * Check if link for relation exists.
     *
     * @param relation link relation.
     * @return {@code true} if the for the relation link exists, {@code false}
     *         otherwise.
     */
    boolean hasLink(String relation);

    /**
     * Get the link for the relation.
     *
     * @param relation link relation.
     * @return the link for the relation, otherwise {@code null} if not present.
     */
    public Link getLink(String relation);

    /**
     * Convenience method that returns a {@link javax.ws.rs.core.Link.Builder Link.Builder}
     * for the relation.
     *
     * @param relation link relation.
     * @return the link builder for the relation, otherwise {@code null} if not
     *         present.
     */
    public Link.Builder getLinkBuilder(String relation);

    /**
     * Check if there is an entity available in the response.
     *
     * The method returns {@code true} if the entity is present, returns
     * {@code false} otherwise.
     *
     * @return {@code true} if there is an entity present in the message,
     *         {@code false} otherwise.
     */
    public boolean hasEntity();

    /**
     * Get the message entity Java instance.
     *
     * Returns {@code null} if the message does not contain an entity.
     *
     * @return the message entity or {@code null} if message does not contain an
     *         entity body.
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
     * @return declared generic entity type.
     */
    public Type getEntityType();

    /**
     * Set a new message entity. The existing entity {@link #getEntityAnnotations() annotations}
     * and {@link #getMediaType() media type} are preserved.
     * <p>
     * It is the callers responsibility to wrap the actual entity with
     * {@link javax.ws.rs.core.GenericEntity} if preservation of its generic
     * type is required.
     * </p>
     *
     * @param entity entity object.
     * @see #setEntity(Object, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
     * @see MessageBodyWriter
     */
    public void setEntity(final Object entity);

    /**
     * Set a new message entity, including the attached annotations and the media type.
     * <p>
     * It is the callers responsibility to wrap the actual entity with
     * {@link javax.ws.rs.core.GenericEntity} if preservation of its generic
     * type is required.
     * </p>
     *
     * @param entity      entity object.
     * @param annotations annotations attached to the entity instance.
     * @param mediaType   entity media type.
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
     * Note that the returned annotations array contains only those annotations
     * explicitly attached to entity instance (such as the ones attached using
     * {@link javax.ws.rs.core.Response.ResponseBuilder#entity(Object, java.lang.annotation.Annotation[])} method
     * as well as the ones attached to the resource method that has returned the response).
     * The entity instance annotations array does not include annotations declared on the entity
     * implementation class or its ancestors.
     * </p>
     * <p>
     * Note that container response filters invoked earlier in the filter chain may modify the entity annotations value,
     * in which case this getter method would return the last annotations value set by a container response filter invoked
     * earlier in the filter chain.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>
     * &#64;Path("my-resource")
     * public class MyResource {
     *   private final Annotations[] extras = ... ;
     *
     *   &#64;GET
     *   &#64;Custom
     *   public String getAnnotatedMe() {
     *     return Response.ok().entity("Annotated me", extras).build();
     *   }
     *   ...
     * }
     * </pre>
     * <p>
     * The container response context for a response returned from the {@code getMe()} method above would contain all
     * the annotations declared on the {@code getAnnotatedMe()} method (<tt>&#64;GET, &#64;Custom</tt>) as well as all
     * the annotations from the {@code extras} field, provided this value has not been replaced by any container response filter
     * invoked earlier.
     * </p>
     * <p>
     * Similarly:
     * </p>
     * <pre>
     * &#64;Custom
     * public class AnnotatedMe { ... }
     *
     * &#64;Path("my-resource")
     * public class MyResource {
     *   private final Annotations[] extras = ... ;
     *
     *   &#64;GET
     *   public AnnotatedMe getMe() {
     *     return Response.ok().entity(new AnnotatedMe(), extras).build();
     *   }
     *   ...
     * }
     * </pre>
     * <p>
     * Provided that the value has not been replaced by any container response filter invoked earlier,
     * the container response context for a response returned from the {@code getMe()} method above would contain all
     * the annotations on the {@code getMe()} method (<tt>&#64;GET</tt>) as well as all the annotations from the
     * {@code extras} field. It would however not contain any annotations declared on the {@code AnnotatedMe} class.
     * </p>
     *
     * @return annotations attached to the entity instance.
     */
    public Annotation[] getEntityAnnotations();

    /**
     * Get the entity output stream. The JAX-RS runtime is responsible for
     * closing the output stream.
     *
     * @return entity output stream.
     */
    public OutputStream getEntityStream();

    /**
     * Set a new entity output stream. The JAX-RS runtime is responsible for
     * closing the output stream.
     *
     * @param outputStream new entity output stream.
     */
    public void setEntityStream(OutputStream outputStream);
}
