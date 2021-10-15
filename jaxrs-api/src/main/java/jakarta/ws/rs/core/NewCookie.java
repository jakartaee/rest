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

import jakarta.ws.rs.ext.RuntimeDelegate;
import jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

/**
 * Used to create a new HTTP cookie, transferred in a response.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see <a href="http://www.ietf.org/rfc/rfc2109.txt">IETF RFC 2109</a>
 * @since 1.0
 */
public class NewCookie extends Cookie {

    /**
     * Specifies that the cookie expires with the current application/browser session.
     */
    public static final int DEFAULT_MAX_AGE = -1;

    /**
     * @deprecated This field will be removed in a future version. See https://github.com/eclipse-ee4j/jaxrs-api/issues/607
     */
    @Deprecated
    private static final HeaderDelegate<NewCookie> DELEGATE = RuntimeDelegate.getInstance().createHeaderDelegate(NewCookie.class);

    private final String comment;
    private final int maxAge;
    private final Date expiry;
    private final boolean secure;
    private final boolean httpOnly;
    private final SameSite sameSite;

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie.
     * @param value the value of the cookie.
     * @throws IllegalArgumentException if name is {@code null}.
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final String name, final String value) {
        this(name, value, null, null, DEFAULT_VERSION, null, DEFAULT_MAX_AGE, null, false, false, null);
    }

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie.
     * @param value the value of the cookie.
     * @param path the URI path for which the cookie is valid.
     * @param domain the host domain for which the cookie is valid.
     * @param comment the comment.
     * @param maxAge the maximum age of the cookie in seconds.
     * @param secure specifies whether the cookie will only be sent over a secure connection.
     * @throws IllegalArgumentException if name is {@code null}.
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final String name,
            final String value,
            final String path,
            final String domain,
            final String comment,
            final int maxAge,
            final boolean secure) {
        this(name, value, path, domain, DEFAULT_VERSION, comment, maxAge, null, secure, false, null);
    }

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie.
     * @param value the value of the cookie.
     * @param path the URI path for which the cookie is valid.
     * @param domain the host domain for which the cookie is valid.
     * @param comment the comment.
     * @param maxAge the maximum age of the cookie in seconds.
     * @param secure specifies whether the cookie will only be sent over a secure connection.
     * @param httpOnly if {@code true} make the cookie HTTP only, i.e. only visible as part of an HTTP request.
     * @throws IllegalArgumentException if name is {@code null}.
     * @since 2.0
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final String name,
            final String value,
            final String path,
            final String domain,
            final String comment,
            final int maxAge,
            final boolean secure,
            final boolean httpOnly) {
        this(name, value, path, domain, DEFAULT_VERSION, comment, maxAge, null, secure, httpOnly, null);
    }

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the URI path for which the cookie is valid
     * @param domain the host domain for which the cookie is valid
     * @param version the version of the specification to which the cookie complies
     * @param comment the comment
     * @param maxAge the maximum age of the cookie in seconds
     * @param secure specifies whether the cookie will only be sent over a secure connection
     * @throws IllegalArgumentException if name is {@code null}.
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final String name,
            final String value,
            final String path,
            final String domain,
            final int version,
            final String comment,
            final int maxAge,
            final boolean secure) {
        this(name, value, path, domain, version, comment, maxAge, null, secure, false, null);
    }

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the URI path for which the cookie is valid
     * @param domain the host domain for which the cookie is valid
     * @param version the version of the specification to which the cookie complies
     * @param comment the comment
     * @param maxAge the maximum age of the cookie in seconds
     * @param expiry the cookie expiry date.
     * @param secure specifies whether the cookie will only be sent over a secure connection
     * @param httpOnly if {@code true} make the cookie HTTP only, i.e. only visible as part of an HTTP request.
     * @throws IllegalArgumentException if name is {@code null}.
     * @since 2.0
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final String name,
            final String value,
            final String path,
            final String domain,
            final int version,
            final String comment,
            final int maxAge,
            final Date expiry,
            final boolean secure,
            final boolean httpOnly) {
        this(name, value, path, domain, version, comment, maxAge, expiry, secure, httpOnly, null);
    }

    /**
     * Create a new instance.
     *
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the URI path for which the cookie is valid
     * @param domain the host domain for which the cookie is valid
     * @param version the version of the specification to which the cookie complies
     * @param comment the comment
     * @param maxAge the maximum age of the cookie in seconds
     * @param expiry the cookie expiry date.
     * @param secure specifies whether the cookie will only be sent over a secure connection
     * @param httpOnly if {@code true} make the cookie HTTP only, i.e. only visible as part of an HTTP request.
     * @param sameSite specifies the value of the {@code SameSite} cookie attribute
     * @throws IllegalArgumentException if name is {@code null}.
     * @since 3.1
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final String name,
            final String value,
            final String path,
            final String domain,
            final int version,
            final String comment,
            final int maxAge,
            final Date expiry,
            final boolean secure,
            final boolean httpOnly,
            final SameSite sameSite) {
        super(name, value, path, domain, version);
        this.comment = comment;
        this.maxAge = maxAge;
        this.expiry = expiry;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.sameSite = sameSite;
    }

    /**
     * Create a new instance copying the information in the supplied cookie.
     *
     * @param cookie the cookie to clone.
     * @throws IllegalArgumentException if cookie is {@code null}.
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final Cookie cookie) {
        this(cookie, null, DEFAULT_MAX_AGE, null, false, false, null);
    }

    /**
     * Create a new instance supplementing the information in the supplied cookie.
     *
     * @param cookie the cookie to clone.
     * @param comment the comment.
     * @param maxAge the maximum age of the cookie in seconds.
     * @param secure specifies whether the cookie will only be sent over a secure connection.
     * @throws IllegalArgumentException if cookie is {@code null}.
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final Cookie cookie, final String comment, final int maxAge, final boolean secure) {
        this(cookie, comment, maxAge, null, secure, false, null);
    }

    /**
     * Create a new instance supplementing the information in the supplied cookie.
     *
     * @param cookie the cookie to clone.
     * @param comment the comment.
     * @param maxAge the maximum age of the cookie in seconds.
     * @param expiry the cookie expiry date.
     * @param secure specifies whether the cookie will only be sent over a secure connection.
     * @param httpOnly if {@code true} make the cookie HTTP only, i.e. only visible as part of an HTTP request.
     * @throws IllegalArgumentException if cookie is {@code null}.
     * @since 2.0
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final Cookie cookie, final String comment, final int maxAge, final Date expiry, final boolean secure, final boolean httpOnly) {
        this(cookie, comment, maxAge, expiry, secure, httpOnly, null);
    }


    /**
     * Create a new instance supplementing the information in the supplied cookie.
     *
     * @param cookie the cookie to clone.
     * @param comment the comment.
     * @param maxAge the maximum age of the cookie in seconds.
     * @param expiry the cookie expiry date.
     * @param secure specifies whether the cookie will only be sent over a secure connection.
     * @param httpOnly if {@code true} make the cookie HTTP only, i.e. only visible as part of an HTTP request.
     * @param sameSite specifies the value of the {@code SameSite} cookie attribute
     * @throws IllegalArgumentException if cookie is {@code null}.
     * @since 3.1
     * @deprecated This constructor will be removed in a future version. Please use {@link NewCookie.Builder} instead.
     */
    @Deprecated
    public NewCookie(final Cookie cookie, final String comment, final int maxAge, final Date expiry, final boolean secure, final boolean httpOnly,
            final SameSite sameSite) {
        super(cookie == null ? null : cookie.getName(),
                cookie == null ? null : cookie.getValue(),
                cookie == null ? null : cookie.getPath(),
                cookie == null ? null : cookie.getDomain(),
                cookie == null ? Cookie.DEFAULT_VERSION : cookie.getVersion());
        this.comment = comment;
        this.maxAge = maxAge;
        this.expiry = expiry;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.sameSite = sameSite;
    }

    /**
     * Create a new instance from the supplied {@link AbstractNewCookieBuilder} instance.
     *
     * @param builder the builder.
     * @throws IllegalArgumentException if {@code builder.name} is {@code null}.
     * @since 3.1
     */
    protected NewCookie(AbstractNewCookieBuilder<?> builder) {
        super(builder);
        this.comment = builder.comment;
        this.maxAge = builder.maxAge;
        this.expiry = builder.expiry;
        this.secure = builder.secure;
        this.httpOnly = builder.httpOnly;
        this.sameSite = builder.sameSite;
    }

    /**
     * Creates a new instance of NewCookie by parsing the supplied string.
     *
     * @param value the cookie string.
     * @return the newly created {@code NewCookie}.
     * @throws IllegalArgumentException if the supplied string cannot be parsed or is {@code null}.
     * @deprecated This method will be removed in a future version. Please use
     * RuntimeDelegate.getInstance().createHeaderDelegate(NewCookie.class).fromString(value) instead.
     */
    @Deprecated
    public static NewCookie valueOf(final String value) {
        return DELEGATE.fromString(value);
    }

    /**
     * Get the comment associated with the cookie.
     *
     * @return the comment or null if none set
     */
    public String getComment() {
        return comment;
    }

    /**
     * Get the maximum age of the the cookie in seconds. Cookies older than the maximum age are discarded. A cookie can be
     * unset by sending a new cookie with maximum age of 0 since it will overwrite any existing cookie and then be
     * immediately discarded. The default value of {@code -1} indicates that the cookie will be discarded at the end of the
     * browser/application session.
     * <p>
     * Note that it is recommended to use {@code Max-Age} to control cookie expiration, however some browsers do not
     * understand {@code Max-Age}, in which case setting {@link #getExpiry()} Expires} parameter may be necessary.
     * </p>
     *
     * @return the maximum age in seconds.
     * @see #getExpiry()
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Get the cookie expiry date. Cookies whose expiry date has passed are discarded. A cookie can be unset by setting a
     * new cookie with an expiry date in the past, typically the lowest possible date that can be set.
     * <p>
     * Note that it is recommended to use {@link #getMaxAge() Max-Age} to control cookie expiration, however some browsers
     * do not understand {@code Max-Age}, in which case setting {@code Expires} parameter may be necessary.
     * </p>
     *
     * @return cookie expiry date or {@code null} if no expiry date was set.
     * @see #getMaxAge()
     * @since 2.0
     */
    public Date getExpiry() {
        return expiry;
    }

    /**
     * Whether the cookie will only be sent over a secure connection. Defaults to {@code false}.
     *
     * @return {@code true} if the cookie will only be sent over a secure connection, {@code false} otherwise.
     */
    public boolean isSecure() {
        return secure;
    }

    /**
     * Returns {@code true} if this cookie contains the {@code HttpOnly} attribute. This means that the cookie should not be
     * accessible to scripting engines, like javascript.
     *
     * @return {@code true} if this cookie should be considered http only, {@code false} otherwise.
     * @since 2.0
     */
    public boolean isHttpOnly() {
        return httpOnly;
    }

    /**
     * Returns the value of the {@code SameSite} attribute for this cookie or {@code null} if the attribute is not set.
     * This attributes controls whether the cookie is sent with cross-origin requests, providing protection against
     * cross-site request forgery.
     *
     * @return the value of the {@code SameSite} cookie attribute or {@code null}.
     * @since 3.1
     */
    public SameSite getSameSite() {
        return sameSite;
    }

    /**
     * Obtain a new instance of a {@link Cookie} with the same name, value, path, domain and version as this
     * {@code NewCookie}. This method can be used to obtain an object that can be compared for equality with another
     * {@code Cookie}; since a {@code Cookie} will never compare equal to a {@code NewCookie}.
     *
     * @return a {@link Cookie}
     */
    public Cookie toCookie() {
        return new Cookie(this.getName(), this.getValue(), this.getPath(),
                this.getDomain(), this.getVersion());
    }

    /**
     * Convert the cookie to a string suitable for use as the value of the corresponding HTTP header.
     *
     * @return a stringified cookie.
     * @deprecated The format of the toString() method is subject to change in a future version. Please use
     * RuntimeDelegate.getInstance().createHeaderDelegate(NewCookie.class).toString(value) instead if you rely on the format
     * of this method.
     */
    @Override
    @Deprecated
    public String toString() {
        return DELEGATE.toString(this);
    }

    /**
     * Generate a hash code by hashing all of the properties.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 59 * hash + (this.comment != null ? this.comment.hashCode() : 0);
        hash = 59 * hash + this.maxAge;
        hash = 59 + hash + (this.expiry != null ? this.expiry.hashCode() : 0);
        hash = 59 * hash + (this.secure ? 1 : 0);
        hash = 59 * hash + (this.httpOnly ? 1 : 0);
        hash = 59 * hash + this.sameSite.ordinal();
        return hash;
    }

    /**
     * Compare for equality. Use {@link #toCookie()} to compare a {@code NewCookie} to a {@code Cookie} considering only the
     * common properties.
     *
     * @param obj the object to compare to
     * @return true if the object is a {@code NewCookie} with the same value for all properties, false otherwise.
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
        final NewCookie other = (NewCookie) obj;
        if (this.getName() != other.getName() && (this.getName() == null || !this.getName().equals(other.getName()))) {
            return false;
        }
        if (this.getValue() != other.getValue() && (this.getValue() == null || !this.getValue().equals(other.getValue()))) {
            return false;
        }
        if (this.getVersion() != other.getVersion()) {
            return false;
        }
        if (this.getPath() != other.getPath() && (this.getPath() == null || !this.getPath().equals(other.getPath()))) {
            return false;
        }
        if (this.getDomain() != other.getDomain() && (this.getDomain() == null || !this.getDomain().equals(other.getDomain()))) {
            return false;
        }
        if (this.comment != other.comment && (this.comment == null || !this.comment.equals(other.comment))) {
            return false;
        }
        if (this.maxAge != other.maxAge) {
            return false;
        }

        if (this.expiry != other.expiry && (this.expiry == null || !this.expiry.equals(other.expiry))) {
            return false;
        }

        if (this.secure != other.secure) {
            return false;
        }
        if (this.httpOnly != other.httpOnly) {
            return false;
        }
        if (this.sameSite != other.sameSite) {
            return false;
        }
        return true;
    }

    /**
     * The available values for the {@code SameSite} cookie attribute.
     *
     * @since 3.1
     */
    public enum SameSite {

        /**
         * The {@code None} mode disables protection provided by the {@code SameSite} cookie attribute.
         */
        NONE,

        /**
         * The {@code Lax} mode only allows to send cookies for cross-site top level navigation requests.
         */
        LAX,

        /**
         * The {@code Strict} mode prevents clients from sending cookies with any cross-site request.
         */
        STRICT

    }

    /**
     * JAX-RS {@link NewCookie} builder class.
     * <p>
     * New Cookie builder provides methods that let you conveniently configure and subsequently build a new
     * {@code NewCookie} instance.
     * </p>
     * For example:
     *
     * <pre>
     * NewCookie cookie = new NewCookie.Builder("name")
     *         .path("/")
     *         .domain("domain.com")
     *         .sameSite(SameSite.LAX)
     *         .build();
     * </pre>
     *
     * @since 3.1
     */
    public static class Builder extends AbstractNewCookieBuilder<Builder> {

        /**
         * Create a new instance.
         *
         * @param name the name of the cookie.
         */
        public Builder(String name) {
            super(name);
        }

        /**
         * Create a new instance supplementing the information in the supplied cookie.
         *
         * @param cookie the cookie to clone.
         */
        public Builder(Cookie cookie) {
            super(cookie);
        }

        @Override
        public NewCookie build() {
            return new NewCookie(this);
        }

    }

    /**
     * JAX-RS abstract {@link NewCookie} builder class.
     *
     * @since 3.1
     */
    public static abstract class AbstractNewCookieBuilder<SELF extends AbstractNewCookieBuilder<SELF>> extends AbstractCookieBuilder<AbstractNewCookieBuilder<SELF>> {

        private String comment;
        private int maxAge = DEFAULT_MAX_AGE;
        private Date expiry;
        private boolean secure;
        private boolean httpOnly;
        private SameSite sameSite;

        /**
         * Create a new instance.
         *
         * @param name the name of the cookie.
         */
        public AbstractNewCookieBuilder(String name) {
            super(name);
        }

        /**
         * Create a new instance supplementing the information in the supplied cookie.
         *
         * @param cookie the cookie to clone.
         */
        public AbstractNewCookieBuilder(Cookie cookie) {
            super(cookie == null ? null : cookie.getName());
            if (cookie != null) {
                value(cookie.getValue());
                path(cookie.getPath());
                domain(cookie.getDomain());
                version(cookie.getVersion());
            }
        }

        /**
         * Set the comment associated with the cookie.
         *
         * @param comment the comment.
         * @return the updated builder instance.
         */
        public SELF comment(String comment) {
            this.comment = comment;
            return self();
        }

        /**
         * Set the maximum age of the the cookie in seconds. Cookies older than the maximum age are discarded. A cookie can be
         * unset by sending a new cookie with maximum age of 0 since it will overwrite any existing cookie and then be
         * immediately discarded. The default value of {@code -1} indicates that the cookie will be discarded at the end of the
         * browser/application session.
         *
         * @param maxAge the maximum age in seconds.
         * @return the updated builder instance.
         * @see #expiry(Date)
         */
        public SELF maxAge(int maxAge) {
            this.maxAge = maxAge;
            return self();
        }

        /**
         * Set the cookie expiry date. Cookies whose expiry date has passed are discarded. A cookie can be unset by setting a
         * new cookie with an expiry date in the past, typically the lowest possible date that can be set.
         * <p>
         * Note that it is recommended to use {@link #maxAge(int) Max-Age} to control cookie expiration, however some browsers
         * do not understand {@code Max-Age}, in which case setting {@code Expires} parameter may be necessary.
         * </p>
         *
         * @param expiry the cookie expiry date
         * @return the updated builder instance.
         * @see #maxAge(int)
         */
        public SELF expiry(Date expiry) {
            this.expiry = expiry;
            return self();
        }

        /**
         * Whether the cookie will only be sent over a secure connection. Defaults to {@code false}.
         *
         * @param secure specifies whether the cookie will only be sent over a secure connection.
         * @return the updated builder instance.
         */
        public SELF secure(boolean secure) {
            this.secure = secure;
            return self();
        }

        /**
         * Whether the cookie will only be visible as part of an HTTP request. Defaults to {@code false}.
         *
         * @param httpOnly if {@code true} make the cookie HTTP only, i.e. only visible as part of an HTTP request.
         * @return the updated builder instance.
         */
        public SELF httpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
            return self();
        }

        /**
         * Set the attribute that controls whether the cookie is sent with cross-origin requests, providing protection against
         * cross-site request forgery.
         *
         * @param sameSite specifies the value of the {@code SameSite} cookie attribute.
         * @return the updated builder instance.
         */
        public SELF sameSite(SameSite sameSite) {
            this.sameSite = sameSite;
            return self();
        }

        @SuppressWarnings("unchecked")
        private SELF self() {
            return (SELF) this;
        }

        /**
         * Build a new {@link NewCookie} instance using all the configuration previously specified in this builder.
         *
         * @return a new {@link NewCookie} instance.
         * @throws IllegalArgumentException if name is {@code null}.
         */
        @Override
        public abstract NewCookie build();

    }

}
