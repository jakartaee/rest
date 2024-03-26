/*
 * Copyright (c) 2010, 2024 Oracle and/or its affiliates. All rights reserved.
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

import java.net.URI;
import java.util.List;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.ApplicationPath;

/**
 * An injectable interface that provides access to application and request URI information. Relative URIs are relative
 * to the base URI of the application, see {@link #getBaseUri}.
 *
 * <p>
 * All methods throw {@code java.lang.IllegalStateException} if called outside the scope of a request (e.g. from a
 * provider constructor).
 * </p>
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see Context
 * @since 1.0
 */
public interface UriInfo {

    /**
     * Get the path of the current request relative to the base URI as a string. All sequences of escaped octets are
     * decoded, equivalent to {@link #getPath(boolean) getPath(true)}.
     *
     * @return the relative URI path.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public String getPath();

    /**
     * Get the path of the current request relative to the base URI as a string.
     *
     * @param decode controls whether sequences of escaped octets are decoded ({@code true}) or not ({@code false}).
     * @return the relative URI path
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public String getPath(boolean decode);

    /**
     * Get the path of the current request relative to the base URI as a list of {@link PathSegment}. This method is useful
     * when the path needs to be parsed, particularly when matrix parameters may be present in the path. All sequences of
     * escaped octets in path segments and matrix parameter values are decoded, equivalent to {@code getPathSegments(true)}.
     *
     * @return an unmodifiable list of {@link PathSegment}. The matrix parameter map of each path segment is also
     * unmodifiable.
     * @throws IllegalStateException if called outside the scope of a request
     * @see PathSegment
     * @see <a href="http://www.w3.org/DesignIssues/MatrixURIs.html">Matrix URIs</a>
     */
    public List<PathSegment> getPathSegments();

    /**
     * Get the path of the current request relative to the base URI as a list of {@link PathSegment}. This method is useful
     * when the path needs to be parsed, particularly when matrix parameters may be present in the path.
     *
     * @param decode controls whether sequences of escaped octets in path segments and matrix parameter values are decoded
     * ({@code true}) or not ({@code false}).
     * @return an unmodifiable list of {@link PathSegment}. The matrix parameter map of each path segment is also
     * unmodifiable.
     * @throws java.lang.IllegalStateException if called outside the scope of a request
     * @see PathSegment
     * @see <a href="http://www.w3.org/DesignIssues/MatrixURIs.html">Matrix URIs</a>
     */
    public List<PathSegment> getPathSegments(boolean decode);

    /**
     * Get the absolute request URI including any query parameters.
     *
     * @return the absolute request URI
     * @throws java.lang.IllegalStateException if called outside the scope of a request
     */
    public URI getRequestUri();

    /**
     * Get the absolute request URI in the form of a UriBuilder.
     *
     * @return a UriBuilder initialized with the absolute request URI.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public UriBuilder getRequestUriBuilder();

    /**
     * Get the absolute path of the request. This includes everything preceding the path (host, port etc) but excludes query
     * parameters. This is a shortcut for {@code uriInfo.getBaseUri().resolve(uriInfo.getPath(false))}.
     *
     * @return the absolute path of the request.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public URI getAbsolutePath();

    /**
     * Get the absolute path of the request in the form of a UriBuilder. This includes everything preceding the path (host,
     * port etc) but excludes query parameters.
     *
     * @return a UriBuilder initialized with the absolute path of the request.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public UriBuilder getAbsolutePathBuilder();

    /**
     * Get the base URI of the application. URIs of root resource classes are all relative to this base URI.
     *
     * @return the base URI of the application.
     */
    public URI getBaseUri();

    /**
     * Get the base URI of the application in the form of a UriBuilder.
     *
     * @return a UriBuilder initialized with the base URI of the application.
     */
    public UriBuilder getBaseUriBuilder();

    /**
     * Get the values of any embedded URI template parameters. All sequences of escaped octets are decoded, equivalent to
     * {@link #getPathParameters(boolean) getPathParameters(true)}.
     *
     * @return an unmodifiable map of parameter names and values.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     * @see jakarta.ws.rs.Path
     * @see jakarta.ws.rs.PathParam
     */
    public MultivaluedMap<String, String> getPathParameters();

    /**
     * Get the values of any embedded URI template parameters.
     *
     * @param decode controls whether sequences of escaped octets are decoded ({@code true}) or not ({@code false}).
     * @return an unmodifiable map of parameter names and values
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     * @see jakarta.ws.rs.Path
     * @see jakarta.ws.rs.PathParam
     */
    public MultivaluedMap<String, String> getPathParameters(boolean decode);

    /**
     * Get the URI query parameters of the current request. The map keys are the names of the query parameters with any
     * escaped characters decoded. All sequences of escaped octets in parameter names and values are decoded, equivalent to
     * {@link #getQueryParameters(boolean) getQueryParameters(true)}.
     *
     * @return an unmodifiable map of query parameter names and values.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public MultivaluedMap<String, String> getQueryParameters();

    /**
     * Get the URI query parameters of the current request. The map keys are the names of the query parameters with any
     * escaped characters decoded.
     *
     * @param decode controls whether sequences of escaped octets in parameter names and values are decoded ({@code true})
     * or not ({@code false}).
     * @return an unmodifiable map of query parameter names and values.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    public MultivaluedMap<String, String> getQueryParameters(boolean decode);

    /**
     * Get a read-only list of URIs for matched resources.
     *
     * Each entry is a relative URI that matched a resource class, a sub-resource method or a sub-resource locator. All
     * sequences of escaped octets are decoded, equivalent to {@code getMatchedURIs(true)}. Entries do not include query
     * parameters but do include matrix parameters if present in the request URI. Entries are ordered in reverse request URI
     * matching order, with the current resource URI first. E.g. given the following resource classes:
     *
     * <pre>
     * &#064;Path("foo")
     * public class FooResource {
     *  &#064;GET
     *  public String getFoo() {...}
     *
     *  &#064;Path("bar")
     *  public BarResource getBarResource() {...}
     * }
     *
     * public class BarResource {
     *  &#064;GET
     *  public String getBar() {...}
     * }
     * </pre>
     *
     * <p>
     * The values returned by this method based on request uri and where the method is called from are:
     * </p>
     *
     * <table border="1">
     * <caption>Matched URIs from requests</caption>
     * <tr>
     * <th>Request</th>
     * <th>Called from</th>
     * <th>Value(s)</th>
     * </tr>
     * <tr>
     * <td>GET /foo</td>
     * <td>FooResource.getFoo</td>
     * <td>foo</td>
     * </tr>
     * <tr>
     * <td>GET /foo/bar</td>
     * <td>FooResource.getBarResource</td>
     * <td>foo/bar, foo</td>
     * </tr>
     * <tr>
     * <td>GET /foo/bar</td>
     * <td>BarResource.getBar</td>
     * <td>foo/bar, foo</td>
     * </tr>
     * </table>
     *
     * In case the method is invoked prior to the request matching (e.g. from a pre-matching filter), the method returns an
     * empty list.
     *
     * @return a read-only list of URI paths for matched resources.
     */
    public List<String> getMatchedURIs();

    /**
     * <p>
     * Get a URI template that includes all {@link Path Paths} (including {@link ApplicationPath})
     * matched by the current request's URI.
     * </p>
     * <p>
     * Each {@link Path} value used to match a resource class, a sub-resource method or a sub-resource locator is concatenated
     * into a single {@code String} value. The template does not include query parameters but does include matrix parameters
     * if present in the request URI. The concatenation is ordered in the request URI matching order, with the
     * {@link ApplicationPath} value first and current resource URI last. E.g. given the following resource classes:
     * </p>
     *
     * <pre>
     * &#064;Path("foo")
     * public class FooResource {
     *  &#064;GET
     *  &#064;Path("{foo:[f-z][a-z]*}")
     *  public String getFoo() {...}
     *
     *  &#064;Path("{bar:[b-e][a-z]*}")
     *  public BarResource getBarResource() {...}
     * }
     *
     * public class BarResource {
     *  &#064;GET
     *  &#064;Path("{id}{id:[0-9]}")
     *  public String getBar() {...}
     * }
     * </pre>
     *
     * <p>
     * The values returned by this method based on request uri and where the method is called from are:
     * </p>
     *
     * <table border="1">
     * <caption>Matched URIs from requests</caption>
     * <tr>
     * <th>Request</th>
     * <th>Called from</th>
     * <th>Value(s)</th>
     * </tr>
     * <tr>
     * <td>GET /foo</td>
     * <td>FooResource.getFoo</td>
     * <td>/foo/{foo:[f-z][a-z]*}</td>
     * </tr>
     * <tr>
     * <td>GET /foo/bar</td>
     * <td>FooResource.getBarResource</td>
     * <td>/foo/{bar:[b-e][a-z]*}</td>
     * </tr>
     * <tr>
     * <td>GET /foo/bar/id0</td>
     * <td>BarResource.getBar</td>
     * <td>/foo/{bar:[b-e][a-z]*}/{id}{id:[0-9]}</td>
     * </tr>
     * </table>
     *
     * In case the method is invoked prior to the request matching (e.g. from a pre-matching filter), the method returns an
     * empty string.
     *
     * @return A concatenated string of {@link Path} templates.
     */
    public String getMatchedResourceTemplate();

    /**
     * Get a read-only list of URIs for matched resources.
     *
     * Each entry is a relative URI that matched a resource class, a sub-resource method or a sub-resource locator. Entries
     * do not include query parameters but do include matrix parameters if present in the request URI. Entries are ordered
     * in reverse request URI matching order, with the current resource URI first. See {@link #getMatchedURIs()} for an
     * example.
     *
     * In case the method is invoked prior to the request matching (e.g. from a pre-matching filter), the method returns an
     * empty list.
     *
     * @param decode controls whether sequences of escaped octets are decoded ({@code true}) or not ({@code false}).
     * @return a read-only list of URI paths for matched resources.
     */
    public List<String> getMatchedURIs(boolean decode);

    /**
     * Get a read-only list of the currently matched resource class instances.
     *
     * Each entry is a resource class instance that matched the request URI either directly or via a sub-resource method or
     * a sub-resource locator. Entries are ordered according to reverse request URI matching order, with the current
     * resource first. E.g. given the following resource classes:
     *
     * <pre>
     * &#064;Path("foo")
     * public class FooResource {
     *  &#064;GET
     *  public String getFoo() {...}
     *
     *  &#064;Path("bar")
     *  public BarResource getBarResource() {...}
     * }
     *
     * public class BarResource {
     *  &#064;GET
     *  public String getBar() {...}
     * }
     * </pre>
     *
     * <p>
     * The values returned by this method based on request uri and where the method is called from are:
     * </p>
     *
     * <table border="1">
     * <caption>Matched resources from requests</caption>
     * <tr>
     * <th>Request</th>
     * <th>Called from</th>
     * <th>Value(s)</th>
     * </tr>
     * <tr>
     * <td>GET /foo</td>
     * <td>FooResource.getFoo</td>
     * <td>FooResource</td>
     * </tr>
     * <tr>
     * <td>GET /foo/bar</td>
     * <td>FooResource.getBarResource</td>
     * <td>FooResource</td>
     * </tr>
     * <tr>
     * <td>GET /foo/bar</td>
     * <td>BarResource.getBar</td>
     * <td>BarResource, FooResource</td>
     * </tr>
     * </table>
     *
     * In case the method is invoked prior to the request matching (e.g. from a pre-matching filter), the method returns an
     * empty list.
     *
     * @return a read-only list of matched resource class instances.
     */
    public List<Object> getMatchedResources();

    /**
     * Resolve a relative URI with respect to the base URI of the application. The resolved URI returned by this method is
     * normalized. If the supplied URI is already resolved, it is just returned.
     *
     * @param uri URI to resolve against the base URI of the application.
     * @return newly resolved URI or supplied URI if already resolved.
     * @since 2.0
     */
    public URI resolve(URI uri);

    /**
     * Relativize a URI with respect to the current request URI. Relativization works as follows:
     * <ol>
     * <li>If the URI to relativize is already relative, it is first resolved using {@link #resolve(java.net.URI)}.</li>
     * <li>The resulting URI is relativized with respect to the current request URI. If the two URIs do not share a prefix,
     * the URI computed in step 1 is returned.</li>
     * </ol>
     *
     * <p>
     * Examples (for base URI {@code http://example.com:8080/app/root/}): <br>
     * <br>
     * <b>Request URI:</b> {@code http://example.com:8080/app/root/a/b/c/resource.html} <br>
     * <b>Supplied URI:</b> {@code a/b/c/d/file.txt} <br>
     * <b>Returned URI:</b> {@code d/file.txt} <br>
     * <br>
     * <b>Request URI:</b> {@code http://example.com:8080/app/root/a/b/c/resource.html} <br>
     * <b>Supplied URI:</b> {@code http://example2.com:9090/app2/root2/a/d/file.txt} <br>
     * <b>Returned URI:</b> {@code http://example2.com:9090/app2/root2/a/d/file.txt}
     * </p>
     *
     * <p>
     * In the second example, the supplied URI is returned given that it is absolute and there is no common prefix between
     * it and the request URI.
     * </p>
     *
     * @param uri URI to relativize against the request URI.
     * @return newly relativized URI.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     * @since 2.0
     */
    public URI relativize(URI uri);

}
