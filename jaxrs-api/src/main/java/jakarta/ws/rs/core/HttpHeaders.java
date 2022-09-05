/*
 * Copyright (c) 2010, 2020 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.core;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * An injectable interface that provides access to HTTP header information. All methods throw
 * {@link java.lang.IllegalStateException} if called outside the scope of a request (e.g. from a provider constructor).
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see Context
 * @since 1.0
 */
public interface HttpHeaders {

    /**
     * Get the values of a HTTP request header if the header exists on the current request. The returned value will be
     * a read-only List if the specified header exists or {@code null} if it does not. This is a shortcut for
     * {@code getRequestHeaders().get(name)}.
     *
     * @param name the header name, case insensitive.
     * @return a read-only list of header values if the specified header exists, otherwise {@code null}.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public List<String> getRequestHeader(String name);

    /**
     * <p>
     * Get a HTTP header as a single string value.
     * </p>
     * Each single header value is converted to String using a {@link jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one
     * is available via {@link jakarta.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)} for the header value
     * class or using its {@code toString} method if a header delegate is not available.
     *
     * @param name the HTTP header.
     * @return the HTTP header value. If the HTTP header is not present then {@code null} is returned. If the HTTP header is
     * present but has no value then the empty string is returned. If the HTTP header is present more than once then the
     * values of joined together and separated by a ',' character.
     * @see #getRequestHeader(java.lang.String)
     * @since 2.0
     */
    public String getHeaderString(String name);

    /**
     * Checks whether a header with a specific name and value (or item of the comma-separated value list) exists.
     *
     * Each single header value is converted to String using a {@link jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one
     * is available via {@link jakarta.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)} for the header value
     * class or using its {@code toString} method if a header delegate is not available.
     *
     * @param name the message header.
     * @param value the message header value.
     * @param ignoreCase whether to ignore upper/lower case.
     * @return {@code true} if and only if a header with the provided name exists having either the exact value or whose
     * comma-separated header string contains value as a whole word.
     * @see #getRequestHeaders()
     * @see #getHeaderString(String)
     * @since 4.0
     */
    public boolean containsHeaderString(String name, String value, boolean ignoreCase);

    /**
     * Get the values of HTTP request headers. The returned Map is case-insensitive wrt. keys and is read-only. The method
     * never returns {@code null}.
     *
     * @return a read-only map of header names and values.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public MultivaluedMap<String, String> getRequestHeaders();

    /**
     * <p>
     * Get a list of media types that are acceptable for the response.
     * </p>
     * If no acceptable media types are specified, a read-only list containing a single
     * {@link jakarta.ws.rs.core.MediaType#WILDCARD_TYPE wildcard media type} instance is returned.
     *
     * @return a read-only list of requested response media types sorted according to their q-value, with highest preference
     * first.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public List<MediaType> getAcceptableMediaTypes();

    /**
     * <p>
     * Get a list of languages that are acceptable for the response.
     * </p>
     * If no acceptable languages are specified, a read-only list containing a single wildcard {@link java.util.Locale}
     * instance (with language field set to "{@code *}") is returned.
     *
     * @return a read-only list of acceptable languages sorted according to their q-value, with highest preference first.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public List<Locale> getAcceptableLanguages();

    /**
     * Get the media type of the request entity.
     *
     * @return the media type or {@code null} if there is no request entity.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public MediaType getMediaType();

    /**
     * Get the language of the request entity.
     *
     * @return the language of the entity or {@code null} if not specified.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public Locale getLanguage();

    /**
     * Get any cookies that accompanied the request.
     *
     * @return a read-only map of cookie name (String) to Cookie.
     * @throws java.lang.IllegalStateException if called outside the scope of a request
     */
    public Map<String, Cookie> getCookies();

    /**
     * Get message date.
     *
     * @return the message date, otherwise {@code null} if not present.
     * @since 2.0
     */
    public Date getDate();

    /**
     * Get Content-Length value.
     *
     * @return Content-Length as integer if present and valid number. In other cases returns -1.
     * @since 2.0
     */
    public int getLength();

    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1">HTTP/1.1 documentation</a>.
     */
    public static final String ACCEPT = "Accept";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.2">HTTP/1.1 documentation</a>.
     */
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3">HTTP/1.1 documentation</a>.
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4">HTTP/1.1 documentation</a>.
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.7">HTTP/1.1 documentation</a>.
     */
    public static final String ALLOW = "Allow";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.8">HTTP/1.1 documentation</a>.
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">HTTP/1.1 documentation</a>.
     */
    public static final String CACHE_CONTROL = "Cache-Control";
    /**
     * See <a href="http://tools.ietf.org/html/rfc2183">IETF RFC-2183</a>.
     */
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">HTTP/1.1 documentation</a>.
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";
    /**
     * See <a href="http://tools.ietf.org/html/rfc2392">IETF RFC-2392</a>.
     */
    public static final String CONTENT_ID = "Content-ID";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.12">HTTP/1.1 documentation</a>.
     */
    public static final String CONTENT_LANGUAGE = "Content-Language";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13">HTTP/1.1 documentation</a>.
     */
    public static final String CONTENT_LENGTH = "Content-Length";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.14">HTTP/1.1 documentation</a>.
     */
    public static final String CONTENT_LOCATION = "Content-Location";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">HTTP/1.1 documentation</a>.
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.18">HTTP/1.1 documentation</a>.
     */
    public static final String DATE = "Date";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">HTTP/1.1 documentation</a>.
     */
    public static final String ETAG = "ETag";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.20">HTTP/1.1 documentation</a>.
     */
    public static final String EXPECT = "Expect";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21">HTTP/1.1 documentation</a>.
     */
    public static final String EXPIRES = "Expires";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23">HTTP/1.1 documentation</a>.
     */
    public static final String HOST = "Host";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.24">HTTP/1.1 documentation</a>.
     */
    public static final String IF_MATCH = "If-Match";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.25">HTTP/1.1 documentation</a>.
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.26">HTTP/1.1 documentation</a>.
     */
    public static final String IF_NONE_MATCH = "If-None-Match";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.28">HTTP/1.1 documentation</a>.
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29">HTTP/1.1 documentation</a>.
     */
    public static final String LAST_MODIFIED = "Last-Modified";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30">HTTP/1.1 documentation</a>.
     */
    public static final String LOCATION = "Location";
    /**
     * See <a href="http://tools.ietf.org/html/rfc5988#page-6">Web Linking (IETF RFC-5988) documentation</a>.
     */
    public static final String LINK = "Link";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.37">HTTP/1.1 documentation</a>.
     */
    public static final String RETRY_AFTER = "Retry-After";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.43">HTTP/1.1 documentation</a>.
     */
    public static final String USER_AGENT = "User-Agent";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.44">HTTP/1.1 documentation</a>.
     */
    public static final String VARY = "Vary";
    /**
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.47">HTTP/1.1 documentation</a>.
     */
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    /**
     * See <a href="http://www.ietf.org/rfc/rfc2109.txt">IETF RFC 2109</a>.
     */
    public static final String COOKIE = "Cookie";
    /**
     * See <a href="http://www.ietf.org/rfc/rfc2109.txt">IETF RFC 2109</a>.
     */
    public static final String SET_COOKIE = "Set-Cookie";
    /**
     * {@code "Last-Event-ID"} HTTP request header name as defined by
     * <a href="http://www.w3.org/TR/eventsource/#last-event-id">SSE specification</a>.
     *
     * @since 2.1
     */
    public static final String LAST_EVENT_ID_HEADER = "Last-Event-ID";
}
