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

package javax.ws.rs.client;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * A resource target identified by the resource URI.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface WebTarget extends Configurable<WebTarget> {

    /**
     * Get the URI identifying the resource.
     *
     * @return the resource URI.
     */
    public URI getUri();

    /**
     * Get the URI builder initialized with the {@link URI} of the current
     * resource target. The returned URI builder is detached from the target,
     * i.e. any updates in the URI builder MUST NOT have any effects on the
     * URI of the originating target.
     *
     * @return the initialized URI builder.
     */
    public UriBuilder getUriBuilder();

    /**
     * Create a new {@code WebTarget} instance by appending path to the URI of
     * the current target instance.
     * <p>
     * When constructing the final path, a '/' separator will be inserted between
     * the existing path and the supplied path if necessary. Existing '/' characters
     * are preserved thus a single value can represent multiple URI path segments.
     * </p>
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param path the path, may contain URI template parameters.
     * @return a new target instance.
     * @throws NullPointerException if path is {@code null}.
     */
    public WebTarget path(String path);

    /**
     * Create a new {@code WebTarget} instance by resolving a URI template with a given {@code name}
     * in the URI of the current target instance using a supplied value.
     *
     * In case a {@code null} template name or value is entered a {@link NullPointerException}
     * is thrown.
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param name  name of the URI template.
     * @param value value to be used to resolve the template.
     * @return a new target instance.
     * @throws NullPointerException if the resolved template name or value is {@code null}.
     */
    public WebTarget resolveTemplate(String name, Object value);

    /**
     * Create a new {@code WebTarget} instance by resolving a URI template with a given {@code name}
     * in the URI of the current target instance using a supplied value.
     *
     * In case a {@code null} template name or value is entered a {@link NullPointerException}
     * is thrown.
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param name  name of the URI template.
     * @param value value to be used to resolve the template.
     * @param encodeSlashInPath if {@code true}, the slash ({@code '/'}) characters
     *                          in template values will be encoded if the template
     *                          is placed in the URI path component, otherwise the slash
     *                          characters will not be encoded in path templates.
     * @return a new target instance.
     * @throws NullPointerException if the resolved template name or value is {@code null}.
     */
    public WebTarget resolveTemplate(String name, Object value, boolean encodeSlashInPath);

    /**
     * Create a new {@code WebTarget} instance by resolving a URI template with a given {@code name}
     * in the URI of the current target instance using a supplied encoded value.
     *
     * A template with a matching name will be replaced by the supplied value.
     * Value is converted to {@code String} using its {@code toString()} method and is then
     * encoded to match the rules of the URI component to which they pertain.  All % characters in
     * the stringified values that are not followed by two hexadecimal numbers will be encoded.
     *
     * In case a {@code null} template name or value is entered a {@link NullPointerException}
     * is thrown.
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param name  name of the URI template.
     * @param value encoded value to be used to resolve the template.
     * @return a new target instance.
     * @throws NullPointerException if the resolved template name or value is {@code null}.
     */
    public WebTarget resolveTemplateFromEncoded(String name, Object value);

    /**
     * Create a new {@code WebTarget} instance by resolving one or more URI templates
     * in the URI of the current target instance using supplied name-value pairs.
     *
     * A call to the method with an empty parameter map is ignored, i.e. same {@code WebTarget}
     * instance is returned.
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param templateValues a map of URI template names and their values.
     * @return a new target instance or the same target instance in case the input name-value map
     *         is empty.
     * @throws NullPointerException if the name-value map or any of the names or values in the map
     *                              is {@code null}.
     */
    public WebTarget resolveTemplates(Map<String, Object> templateValues);
    /**
     * Create a new {@code WebTarget} instance by resolving one or more URI templates
     * in the URI of the current target instance using supplied name-value pairs.
     *
     * A call to the method with an empty parameter map is ignored, i.e. same {@code WebTarget}
     * instance is returned.
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     *
     * @param templateValues a map of URI template names and their values.
     * @param encodeSlashInPath if {@code true}, the slash ({@code '/'}) characters
     *                          in template values will be encoded if the template
     *                          is placed in the URI path component, otherwise the slash
     *                          characters will not be encoded in path templates.
     * @return a new target instance or the same target instance in case the input name-value map
     *         is empty.
     * @throws NullPointerException if the name-value map or any of the names or values in the map
     *                              is {@code null}.
     */
    public WebTarget resolveTemplates(Map<String, Object> templateValues, boolean encodeSlashInPath);

    /**
     * Create a new {@code WebTarget} instance by resolving one or more URI templates
     * in the URI of the current target instance using supplied name-encoded value pairs.
     *
     * All templates  with their name matching one of the keys in the supplied map will be replaced
     * by the value in the supplied map. Values are converted to {@code String} using
     * their {@code toString()} method and are then encoded to match the
     * rules of the URI component to which they pertain.  All % characters in
     * the stringified values that are not followed by two hexadecimal numbers
     * will be encoded.
     *
     * A call to the method with an empty parameter map is ignored, i.e. same {@code WebTarget}
     * instance is returned.
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param templateValues a map of URI template names and their encoded values.
     * @return a new target instance or the same target instance in case the input name-value map
     *         is empty.
     * @throws NullPointerException if the name-value map or any of the names or encoded values in the map
     *                              is {@code null}.
     */
    public WebTarget resolveTemplatesFromEncoded(Map<String, Object> templateValues);

    /**
     * Create a new {@code WebTarget} instance by appending a matrix parameter to
     * the existing set of matrix parameters of the current final segment of the
     * URI of the current target instance.
     *
     * If multiple values are supplied the parameter will be added once per value. In case a single
     * {@code null} value is entered, all parameters with that name in the current final path segment
     * are removed (if present) from the collection of last segment matrix parameters inherited from
     * the current target.
     * <p>
     * Note that the matrix parameters are tied to a particular path segment; appending
     * a value to an existing matrix parameter name will not affect the position of
     * the matrix parameter in the URI path.
     * </p>
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param name   the matrix parameter name, may contain URI template parameters.
     * @param values the matrix parameter value(s), each object will be converted
     *               to a {@code String} using its {@code toString()} method. Stringified
     *               values may contain URI template parameters.
     * @return a new target instance.
     * @throws NullPointerException if the parameter name is {@code null} or if there are multiple
     *                              values present and any of those values is {@code null}.
     * @see <a href="http://www.w3.org/DesignIssues/MatrixURIs.html">Matrix URIs</a>
     */
    public WebTarget matrixParam(String name, Object... values);

    /**
     * Create a new {@code WebTarget} instance by configuring a query parameter on the URI
     * of the current target instance.
     *
     * If multiple values are supplied the parameter will be added once per value. In case a single
     * {@code null} value is entered, all parameters with that name are removed (if present) from
     * the collection of query parameters inherited from the current target.
     * <p>
     * A snapshot of the present configuration of the current (parent) target
     * instance is taken and is inherited by the newly constructed (child) target
     * instance.
     * </p>
     *
     * @param name   the query parameter name, may contain URI template parameters
     * @param values the query parameter value(s), each object will be converted
     *               to a {@code String} using its {@code toString()} method. Stringified
     *               values may contain URI template parameters.
     * @return a new target instance.
     * @throws NullPointerException if the parameter name is {@code null} or if there are multiple
     *                              values present and any of those values is {@code null}.
     */
    public WebTarget queryParam(String name, Object... values);

    /**
     * Start building a request to the targeted web resource.
     *
     * @return builder for a request targeted at the URI referenced by this target instance.
     */
    public Invocation.Builder request();

    /**
     * Start building a request to the targeted web resource and define the accepted
     * response media types.
     * <p>
     * Invoking this method is identical to:
     * </p>
     * <pre>
     * webTarget.request().accept(types);
     * </pre>
     *
     * @param acceptedResponseTypes accepted response media types.
     * @return builder for a request targeted at the URI referenced by this target instance.
     */
    public Invocation.Builder request(String... acceptedResponseTypes);

    /**
     * Start building a request to the targeted web resource and define the accepted
     * response media types.
     * <p>
     * Invoking this method is identical to:
     * </p>
     * <pre>
     * webTarget.request().accept(types);
     * </pre>
     *
     * @param acceptedResponseTypes accepted response media types.
     * @return builder for a request targeted at the URI referenced by this target instance.
     */
    public Invocation.Builder request(MediaType... acceptedResponseTypes);
}
