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

package jakarta.ws.rs.core;

import jakarta.ws.rs.ext.RuntimeDelegate;
import jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

/**
 * Represents the value of a HTTP cookie, transferred in a request. RFC 2109 specifies the legal characters for name,
 * value, path and domain. The default version of 1 corresponds to RFC 2109.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see <a href="http://www.ietf.org/rfc/rfc2109.txt">IETF RFC 2109</a>
 * @since 1.0
 */
public class Cookie {

    /**
     * Cookies using the default version correspond to RFC 2109.
     */
    public static final int DEFAULT_VERSION = 1;
    /**
     * @deprecated This field will be removed in a future version. See https://github.com/eclipse-ee4j/jaxrs-api/issues/607
     */
    @Deprecated
    private static final HeaderDelegate<Cookie> HEADER_DELEGATE = RuntimeDelegate.getInstance().createHeaderDelegate(Cookie.class);
    private final String name;
    private final String value;
    private final int version;
    private final String path;
    private final String domain;

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie.
     * @param value the value of the cookie.
     * @param path the URI path for which the cookie is valid.
     * @param domain the host domain for which the cookie is valid.
     * @param version the version of the specification to which the cookie complies.
     * @throws IllegalArgumentException if name is {@code null}.
     */
    public Cookie(final String name, final String value, final String path, final String domain, final int version)
            throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("name==null");
        }
        this.name = name;
        this.value = value;
        this.version = version;
        this.domain = domain;
        this.path = path;
    }

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie.
     * @param value the value of the cookie.
     * @param path the URI path for which the cookie is valid.
     * @param domain the host domain for which the cookie is valid.
     * @throws IllegalArgumentException if name is {@code null}.
     */
    public Cookie(final String name, final String value, final String path, final String domain)
            throws IllegalArgumentException {
        this(name, value, path, domain, DEFAULT_VERSION);
    }

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie.
     * @param value the value of the cookie.
     * @throws IllegalArgumentException if name is {@code null}.
     */
    public Cookie(final String name, final String value)
            throws IllegalArgumentException {
        this(name, value, null, null);
    }

    /**
     * Create a new instance from the supplied {@link AbstractCookieBuilder} instance.
     *
     * @param builder the builder.
     * @throws IllegalArgumentException if {@code builder.name} is {@code null}.
     * @since 3.1
     */
    protected Cookie(AbstractCookieBuilder<?> builder) throws IllegalArgumentException {
        if (builder.name == null) {
            throw new IllegalArgumentException("name==null");
        }
        this.name = builder.name;
        this.value = builder.value;
        this.version = builder.version;
        this.domain = builder.domain;
        this.path = builder.path;
    }

    /**
     * Creates a new instance of {@code Cookie} by parsing the supplied string.
     *
     * @param value the cookie string.
     * @return the newly created {@code Cookie}.
     * @throws IllegalArgumentException if the supplied string cannot be parsed or is {@code null}.
     * @deprecated This method will be removed in a future version. Please use
     * RuntimeDelegate.getInstance().createHeaderDelegate(Cookie.class).fromString(value) instead.
     */
    @Deprecated
    public static Cookie valueOf(final String value) {
        return HEADER_DELEGATE.fromString(value);
    }

    /**
     * Get the name of the cookie.
     *
     * @return the cookie name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value of the cookie.
     *
     * @return the cookie value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the version of the cookie.
     *
     * @return the cookie version.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Get the domain of the cookie.
     *
     * @return the cookie domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Get the path of the cookie.
     *
     * @return the cookie path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Convert the cookie to a string suitable for use as the value of the corresponding HTTP header.
     *
     * @return a stringified cookie.
     * @deprecated The format of the toString() method is subject to change in a future version. Please use
     * RuntimeDelegate.getInstance().createHeaderDelegate(Cookie.class).toString(value) instead if you rely on the format of
     * this method.
     */
    @Override
    @Deprecated
    public String toString() {
        return HEADER_DELEGATE.toString(this);
    }

    /**
     * Generate a hash code by hashing all of the cookies properties.
     *
     * @return the cookie hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 97 * hash + this.version;
        hash = 97 * hash + (this.path != null ? this.path.hashCode() : 0);
        hash = 97 * hash + (this.domain != null ? this.domain.hashCode() : 0);
        return hash;
    }

    /**
     * Compare for equality.
     *
     * @param obj the object to compare to.
     * @return {@code true}, if the object is a {@code Cookie} with the same value for all properties, {@code false}
     * otherwise.
     */
    @SuppressWarnings({ "StringEquality", "RedundantIfStatement" })
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cookie other = (Cookie) obj;
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.path != other.path && (this.path == null || !this.path.equals(other.path))) {
            return false;
        }
        if (this.domain != other.domain && (this.domain == null || !this.domain.equals(other.domain))) {
            return false;
        }
        return true;
    }

    /**
     * JAX-RS {@link Cookie} builder class.
     *
     * @since 3.1
     */
    public static class Builder extends AbstractCookieBuilder<Builder> {

        public Builder(String name) {
            super(name);
        }

        @Override
        public Cookie build() {
            return new Cookie(this);
        }

    }

    /**
     * JAX-RS abstract {@link Cookie} builder class.
     *
     * @since 3.1
     */
    public static abstract class AbstractCookieBuilder<T extends AbstractCookieBuilder<T>> {

        private final String name;

        private String value;
        private int version = DEFAULT_VERSION;
        private String path;
        private String domain;

        /**
         * Create a new instance.
         *
         * @param name the name of the cookie.
         */
        public AbstractCookieBuilder(String name) {
            this.name = name;
        }

        /**
         * Set the value of the cookie.
         *
         * @param value the value of the cookie.
         * @return the updated builder instance.
         */
        @SuppressWarnings("unchecked")
        public T value(String value) {
            this.value = value;
            return (T) this;
        }

        /**
         * Set the version of the cookie. Defaults to {@link Cookie#DEFAULT_VERSION}
         *
         * @param version the version of the specification to which the cookie complies.
         * @return the updated builder instance.
         */
        @SuppressWarnings("unchecked")
        public T version(int version) {
            this.version = version;
            return (T) this;
        }

        /**
         * Set the path of the cookie.
         *
         * @param path the URI path for which the cookie is valid.
         * @return the updated builder instance.
         */
        @SuppressWarnings("unchecked")
        public T path(String path) {
            this.path = path;
            return (T) this;
        }

        /**
         * Set the domain of the cookie.
         *
         * @param domain the host domain for which the cookie is valid.
         * @return the updated builder instance.
         */
        @SuppressWarnings("unchecked")
        public T domain(String domain) {
            this.domain = domain;
            return (T) this;
        }

        /**
         * Build a {@link Cookie} instance.
         *
         * @return new {@link Cookie} instance.
         * @throws IllegalArgumentException if name is {@code null}.
         */
        public abstract Cookie build();

    }

}
