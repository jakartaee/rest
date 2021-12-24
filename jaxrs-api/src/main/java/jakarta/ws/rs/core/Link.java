/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
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

import javax.xml.namespace.QName;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import jakarta.ws.rs.ext.RuntimeDelegate;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>
 * Class representing hypermedia links. A hypermedia link may include additional parameters beyond its underlying URI.
 * Parameters such as {@code rel} or {@code type} provide additional meta-data. Links in responses can be
 * <em>followed</em> by creating an {@link jakarta.ws.rs.client.Invocation.Builder} or a
 * {@link jakarta.ws.rs.client.WebTarget}.
 * </p>
 *
 * <p>
 * The methods {@link #toString} and {@link #valueOf} can be used to serialize and de-serialize a link into a link
 * header (RFC 5988).
 * </p>
 *
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 * @see jakarta.ws.rs.client.Client#invocation
 * @see jakarta.ws.rs.client.Client#target(jakarta.ws.rs.core.Link)
 * @see jakarta.ws.rs.core.Response#getLink(java.lang.String)
 * @since 2.0
 */
public abstract class Link {

    /**
     * Title link param from RFC 5988.
     */
    public static final String TITLE = "title";

    /**
     * Rel link param from RFC 5988.
     */
    public static final String REL = "rel";

    /**
     * Type link param from RFC 5988.
     */
    public static final String TYPE = "type";

    /**
     * Returns the underlying URI associated with this link.
     *
     * @return underlying URI.
     */
    public abstract URI getUri();

    /**
     * Convenience method that returns a {@link jakarta.ws.rs.core.UriBuilder} initialized with this link's underlying URI.
     *
     * @return UriBuilder initialized using underlying URI.
     */
    public abstract UriBuilder getUriBuilder();

    /**
     * Returns the value associated with the link {@code rel} param, or {@code null} if this param is not specified.
     *
     * @return relation types as string or {@code null}.
     */
    public abstract String getRel();

    /**
     * Returns the value associated with the link {@code rel} param as a list of strings or the empty list if {@code rel} is
     * not defined.
     *
     * @return relation types as list of strings or empty list.
     */
    public abstract List<String> getRels();

    /**
     * Returns the value associated with the link {@code title} param, or {@code null} if this param is not specified.
     *
     * @return value of title parameter or {@code null}.
     */
    public abstract String getTitle();

    /**
     * Returns the value associated with the link {@code type} param, or {@code null} if this param is not specified.
     *
     * @return value of type parameter or {@code null}.
     */
    public abstract String getType();

    /**
     * Returns an immutable map that includes all the link parameters defined on this link. If defined, this map will
     * include entries for {@code rel}, {@code title} and {@code type}.
     *
     * @return immutable map of link parameters.
     */
    public abstract Map<String, String> getParams();

    /**
     * Returns a string representation as a link header (RFC 5988). All link params are serialized as link-param="value"
     * where value is a quoted-string. For example,
     *
     * &lt;http://foo.bar/employee/john&gt;; title="employee"; rel="manager friend"
     *
     * @return string link header representation for this link.
     */
    @Override
    public abstract String toString();

    /**
     * Simple parser to convert link header string representations into a link.
     *
     * <pre>
     * link ::= '&lt;' uri 'gt;' (';' link-param)*
     * link-param ::= name '=' quoted-string
     * </pre>
     *
     * See <a href="http://tools.ietf.org/html/rfc5988">RFC 5988</a> for more information.
     *
     * @param value String representation.
     * @return newly parsed link.
     * @throws IllegalArgumentException if a syntax error is found.
     * @see Link.Builder#link(java.lang.String)
     */
    public static Link valueOf(final String value) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.link(value);
        return b.build();
    }

    /**
     * Create a new builder instance initialized from an existing URI.
     *
     * @param uri a URI that will be used to initialize the builder.
     * @return a new builder.
     * @throws IllegalArgumentException if uri is {@code null}.
     */
    public static Builder fromUri(final URI uri) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.uri(uri);
        return b;
    }

    /**
     * Create a new builder instance initialized from an existing URI represented as a string.
     *
     * @param uri a URI that will be used to initialize the builder.
     * @return a new builder.
     * @throws IllegalArgumentException if uri is {@code null}.
     */
    public static Builder fromUri(final String uri) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.uri(uri);
        return b;
    }

    /**
     * Create a new builder instance initialized from a URI builder.
     *
     * @param uriBuilder instance of URI builder.
     * @return a new builder.
     */
    public static Builder fromUriBuilder(final UriBuilder uriBuilder) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.uriBuilder(uriBuilder);
        return b;
    }

    /**
     * Create a new builder instance initialized from another link.
     *
     * @param link other link used for initialization.
     * @return a new builder.
     */
    public static Builder fromLink(final Link link) {
        Builder b = RuntimeDelegate.getInstance().createLinkBuilder();
        b.link(link);
        return b;
    }

    /**
     * Convenience method to build a link from a path. Equivalent to {@code fromUriBuilder(UriBuilder.fromPath(path))}.
     *
     * @param path a URI path that will be used to initialize the Link, may contain URI template parameters.
     * @return a new Link.Builder.
     * @throws IllegalArgumentException if path is {@code null}.
     */
    public static Builder fromPath(final String path) {
        return fromUriBuilder(UriBuilder.fromPath(path));
    }

    /**
     * Convenience method to build a link from a resource. Equivalent to
     * {@code Link.fromUriBuilder({@link UriBuilder#fromResource UriBuilder.fromResource(resource)})}. Note that the link
     * URI passed to the {@code Link.Builder} instance returned by this method is relative. Should the link be built as
     * absolute, a {@link Link.Builder#baseUri(URI) base URI} has to be specified in the builder prior to building the new
     * link instance. For example, on a server side a {@link UriInfo#getBaseUri()} may be typically used to define the base
     * URI of a link created using this method.
     *
     * @param resource a root resource whose {@link jakarta.ws.rs.Path} value will be used to initialize the builder.
     * @return a new {@link Link.Builder link builder} instance.
     * @throws IllegalArgumentException if resource is not annotated with {@link jakarta.ws.rs.Path} or resource is
     * {@code null}.
     * @see UriInfo#getBaseUri()
     */
    public static Builder fromResource(final Class<?> resource) {
        return fromUriBuilder(UriBuilder.fromResource(resource));
    }

    /**
     * Convenience method to build a link from a resource. Equivalent to
     * {@code Link.fromUriBuilder({@link UriBuilder#fromMethod(Class, String) UriBuilder.fromMethod(resource, method)})}.
     * Note that the link URI passed to the {@code Link.Builder} instance returned by this method is relative. Should the
     * link be built as absolute, a {@link Link.Builder#baseUri(URI) base URI} has to be specified in the builder prior to
     * building the new link instance. For example, on a server side a {@link UriInfo#getBaseUri()} may be typically used to
     * define the base URI of a link created using this method.
     *
     * @param resource the resource containing the method.
     * @param method the name of the method whose {@link jakarta.ws.rs.Path} value will be used to obtain the path to append.
     * @return the updated Link.Builder.
     * @throws IllegalArgumentException if resource or method is {@code null}, or there is more than or less than one
     * variant of the method annotated with {@link jakarta.ws.rs.Path}.
     * @see UriInfo#getBaseUri()
     */
    public static Builder fromMethod(final Class<?> resource, final String method) {
        return fromUriBuilder(UriBuilder.fromMethod(resource, method));
    }

    /**
     * Builder class for hypermedia links.
     *
     * @see Link
     * @since 2.0
     */
    public interface Builder {

        /**
         * Initialize builder using another link. Sets underlying URI and copies all parameters.
         *
         * @param link other link from which to initialize.
         * @return the updated builder.
         */
        public Builder link(Link link);

        /**
         * Initialize builder using another link represented as a string. Uses simple parser to convert string representation
         * into a link.
         *
         * <pre>
         * link ::= '&lt;' uri 'gt;' (';' link-param)*
         * link-param ::= name '=' quoted-string
         * </pre>
         *
         * See <a href="http://tools.ietf.org/html/rfc5988">RFC 5988</a> for more information.
         *
         * @param link other link in string representation.
         * @return the updated builder.
         * @throws IllegalArgumentException if string representation of URI is invalid.
         */
        public Builder link(String link);

        /**
         * Set underlying URI template for the link being constructed.
         *
         * @param uri underlying URI for link
         * @return the updated builder.
         */
        public Builder uri(URI uri);

        /**
         * Set underlying string representing URI template for the link being constructed.
         *
         * @param uri underlying URI for link.
         * @return the updated builder.
         * @throws IllegalArgumentException if string representation of URI is invalid.
         */
        public Builder uri(String uri);

        /**
         * Set the base URI for resolution of relative URIs. If the underlying URI is already absolute, the base URI is ignored.
         *
         * @param uri base URI for relative links.
         * @return the updated builder.
         * @see Link#fromPath(java.lang.String)
         * @see Link#fromResource(java.lang.Class)
         * @see Link#fromMethod(java.lang.Class, java.lang.String)
         */
        public Builder baseUri(URI uri);

        /**
         * Set the base URI as a string for resolution of relative URIs. If the underlying URI is already absolute, the base URI
         * is ignored.
         *
         * @param uri base URI for relative links.
         * @return the updated builder.
         * @throws IllegalArgumentException if string representation of URI is invalid.
         * @see Link#fromPath(java.lang.String)
         * @see Link#fromResource(java.lang.Class)
         * @see Link#fromMethod(java.lang.Class, java.lang.String)
         */
        public Builder baseUri(String uri);

        /**
         * Set underlying URI builder representing the URI template for the link being constructed.
         *
         * @param uriBuilder underlying URI builder.
         * @return the updated builder.
         */
        public Builder uriBuilder(UriBuilder uriBuilder);

        /**
         * Convenience method to set a link relation. More than one {@code rel} value can be specified by using one or more
         * whitespace characters as delimiters according to RFC 5988. The effect of calling this method is cumulative; relations
         * are appended using a single space character as separator.
         *
         * @param rel relation name.
         * @return the updated builder.
         * @throws IllegalArgumentException if the name is {@code null}.
         */
        public Builder rel(String rel);

        /**
         * Convenience method to set a {@code title} on this link.
         *
         * @param title title parameter of this link.
         * @return the updated builder.
         * @throws IllegalArgumentException if the title is {@code null}.
         */
        public Builder title(String title);

        /**
         * Convenience method to set a {@code type} on this link.
         *
         * @param type type parameter of this link.
         * @return the updated builder.
         * @throws IllegalArgumentException if the type is {@code null}.
         */
        public Builder type(String type);

        /**
         * Set an arbitrary parameter on this link. Note that link parameters are those defined in RFC 5988 and should not be
         * confused with URI parameters which can be specified when calling {@link #build(Object...)}.
         *
         * @param name the name of the parameter.
         * @param value the value set for the parameter.
         * @return the updated builder.
         * @throws IllegalArgumentException if either the name or value are {@code null}.
         */
        public Builder param(String name, String value);

        /**
         * Finish building this link using the supplied values as URI parameters.
         *
         * The state of the builder is unaffected; this method may be called multiple times on the same builder instance.
         *
         * @param values parameters used to build underlying URI.
         * @return newly built link.
         * @throws IllegalArgumentException if there are any URI template parameters without a supplied value, or if a value is
         * {@code null}.
         * @throws UriBuilderException if a URI cannot be constructed based on the current state of the underlying URI builder.
         */
        public Link build(Object... values);

        /**
         * Finish building this link using the supplied values as URI parameters and relativize the result with respect to the
         * supplied URI.
         *
         * If the underlying link is already relative or if it is absolute but does not share a prefix with the supplied URI,
         * this method is equivalent to calling {@link Link.Builder#build(java.lang.Object[])}. Note that a base URI can be set
         * on a relative link using {@link Link.Builder#baseUri(java.net.URI)}. The state of the builder is unaffected; this
         * method may be called multiple times on the same builder instance.
         *
         * @param uri URI used for relativization.
         * @param values parameters used to build underlying URI.
         * @return newly built link.
         * @throws IllegalArgumentException if there are any URI template parameters without a supplied value, or if a value is
         * {@code null}.
         * @throws UriBuilderException if a URI cannot be constructed based on the current state of the underlying URI builder.
         * @see #baseUri(java.lang.String)
         * @see #baseUri(java.net.URI)
         */
        public Link buildRelativized(URI uri, Object... values);
    }

    /**
     * Value type for {@link jakarta.ws.rs.core.Link} that can be marshalled and
     * unmarshalled by JAXB.
     *
     * Note that usage of this class requires the Jakarta XML Binding API and an implementation. The Jakarta RESTful Web
     * Services implementation is not required to provide these dependencies.
     *
     * @see jakarta.ws.rs.core.Link.JaxbAdapter
     * @since 2.0
     * @deprecated
     */
    @Deprecated
    public static class JaxbLink {

        private URI uri;
        private Map<QName, Object> params;

        /**
         * Default constructor needed during unmarshalling.
         */
        public JaxbLink() {
        }

        /**
         * Construct an instance from a URI and no parameters.
         *
         * @param uri underlying URI.
         */
        public JaxbLink(final URI uri) {
            this.uri = uri;
        }

        /**
         * Construct an instance from a URI and some parameters.
         *
         * @param uri underlying URI.
         * @param params parameters of this link.
         */
        public JaxbLink(final URI uri, final Map<QName, Object> params) {
            this.uri = uri;
            this.params = params;
        }

        /**
         * Get the underlying URI for this link.
         *
         * @return underlying URI.
         */
        @XmlAttribute(name = "href")
        public URI getUri() {
            return uri;
        }

        /**
         * Get the parameter map for this link.
         *
         * @return parameter map.
         */
        @XmlAnyAttribute
        public Map<QName, Object> getParams() {
            if (params == null) {
                params = new HashMap<QName, Object>();
            }
            return params;
        }

        /**
         * Set the underlying URI for this link.
         *
         * This setter is needed for JAXB unmarshalling.
         */
        void setUri(final URI uri) {
            this.uri = uri;
        }

        /**
         * Set the parameter map for this link.
         *
         * This setter is needed for JAXB unmarshalling.
         */
        void setParams(final Map<QName, Object> params) {
            this.params = params;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof JaxbLink)) {
                return false;
            }

            JaxbLink jaxbLink = (JaxbLink) o;

            if (uri != null ? !uri.equals(jaxbLink.uri) : jaxbLink.uri != null) {
                return false;
            }

            if (params == jaxbLink.params) {
                return true;
            }
            if (params == null) {
                // if this.params is 'null', consider other.params equal to empty
                return jaxbLink.params.isEmpty();
            }
            if (jaxbLink.params == null) {
                // if other.params is 'null', consider this.params equal to empty
                return params.isEmpty();
            }

            return params.equals(jaxbLink.params);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uri, params);
        }

    }

    /**
     * An implementation of JAXB {@link jakarta.xml.bind.annotation.adapters.XmlAdapter} that maps the JAX-RS
     * {@link jakarta.ws.rs.core.Link} type to a value that can be marshalled and unmarshalled by JAXB. The following example
     * shows how to use this adapter on a JAXB bean class:
     *
     * <pre>
     * &#64;XmlRootElement
     * public class MyModel {
     *
     *   private Link link;
     *
     *   &#64;XmlElement(name="link")
     *   &#64;XmlJavaTypeAdapter(JaxbAdapter.class)
     *   public Link getLink() {
     *     return link;
     *   }
     *   ...
     * }
     * </pre>
     *
     * Note that usage of this class requires the Jakarta XML Binding API and an implementation. The Jakarta RESTful Web
     * Services implementation is not required to provide these dependencies.
     *
     * @see jakarta.ws.rs.core.Link.JaxbLink
     * @since 2.0
     * @deprecated
     */
    @Deprecated
    public static class JaxbAdapter extends XmlAdapter<JaxbLink, Link> {

        /**
         * Convert a {@link JaxbLink} into a {@link Link}.
         *
         * @param v instance of type {@link JaxbLink}.
         * @return mapped instance of type {@link JaxbLink}
         */
        @Override
        public Link unmarshal(final JaxbLink v) {
            Link.Builder lb = Link.fromUri(v.getUri());
            for (Entry<QName, Object> e : v.getParams().entrySet()) {
                lb.param(e.getKey().getLocalPart(), e.getValue().toString());
            }
            return lb.build();
        }

        /**
         * Convert a {@link Link} into a {@link JaxbLink}.
         *
         * @param v instance of type {@link Link}.
         * @return mapped instance of type {@link JaxbLink}.
         */
        @Override
        public JaxbLink marshal(final Link v) {
            JaxbLink jl = new JaxbLink(v.getUri());
            for (Entry<String, String> e : v.getParams().entrySet()) {
                final String name = e.getKey();
                jl.getParams().put(new QName("", name), e.getValue());
            }
            return jl;
        }
    }
}
