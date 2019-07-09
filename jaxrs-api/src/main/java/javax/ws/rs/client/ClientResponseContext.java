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

package javax.ws.rs.client;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Client response filter context.
 *
 * A mutable class that provides response-specific information for the filter,
 * such as message headers, message entity or request-scoped properties.
 * The exposed setters allow modification of the exposed response-specific
 * information.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface ClientResponseContext {

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
     * @see #getHeaderString(String)
     */
    public MultivaluedMap<String, String> getHeaders();

    /**
     * Get a message header as a single string value.
     *
     * @param name the message header.
     * @return the message header value. If the message header is not present then
     *         {@code null} is returned. If the message header is present but has no
     *         value then the empty string is returned. If the message header is present
     *         more than once then the values of joined together and separated by a ','
     *         character.
     * @see #getHeaders()
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
     * Check if there is a non-empty entity input stream is available in the response
     * message.
     *
     * The method returns {@code true} if the entity is present, returns
     * {@code false} otherwise.
     *
     * @return {@code true} if there is an entity present in the message,
     *         {@code false} otherwise.
     */
    public boolean hasEntity();

    /**
     * Get the entity input stream. The runtime is responsible for
     * closing the input stream.
     *
     * @return entity input stream.
     */
    public InputStream getEntityStream();

    /**
     * Set a new entity input stream. The runtime is responsible for
     * closing the input stream.
     *
     * @param input new entity input stream.
     */
    public void setEntityStream(InputStream input);
}
