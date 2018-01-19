/*
 * Copyright (c) 2010, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ext.RuntimeDelegate;
import javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

/**
 * An abstraction for the value of a HTTP Cache-Control response header.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">HTTP/1.1 section 14.9</a>
 * @since 1.0
 */
public class CacheControl {

    private static final HeaderDelegate<CacheControl> HEADER_DELEGATE =
            RuntimeDelegate.getInstance().createHeaderDelegate(CacheControl.class);
    private List<String> privateFields;
    private List<String> noCacheFields;
    private Map<String, String> cacheExtension;

    private boolean privateFlag;
    private boolean noCache;
    private boolean noStore;
    private boolean noTransform;
    private boolean mustRevalidate;
    private boolean proxyRevalidate;
    private int maxAge = -1;
    private int sMaxAge = -1;

    /**
     * Create a new instance of CacheControl. The new instance will have the
     * following default settings:
     *
     * <ul>
     * <li>private = false</li>
     * <li>noCache = false</li>
     * <li>noStore = false</li>
     * <li>noTransform = true</li>
     * <li>mustRevalidate = false</li>
     * <li>proxyRevalidate = false</li>
     * <li>An empty list of private fields</li>
     * <li>An empty list of no-cache fields</li>
     * <li>An empty map of cache extensions</li>
     * </ul>
     */
    public CacheControl() {
        privateFlag = false;
        noCache = false;
        noStore = false;
        noTransform = true;
        mustRevalidate = false;
        proxyRevalidate = false;
    }

    /**
     * Creates a new instance of CacheControl by parsing the supplied string.
     *
     * @param value the cache control string
     * @return the newly created CacheControl
     *
     * @throws IllegalArgumentException if the supplied string cannot be parsed
     *                                  or is null
     */
    public static CacheControl valueOf(final String value) {
        return HEADER_DELEGATE.fromString(value);
    }

    /**
     * Corresponds to the must-revalidate cache control directive.
     *
     * @return true if the must-revalidate cache control directive will be included in the
     * response, false otherwise.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4">HTTP/1.1 section 14.9.4</a>
     */
    public boolean isMustRevalidate() {
        return mustRevalidate;
    }

    /**
     * Corresponds to the must-revalidate cache control directive.
     *
     * @param mustRevalidate true if the must-revalidate cache control directive should be included in the
     *                       response, false otherwise.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4">HTTP/1.1 section 14.9.4</a>
     */
    public void setMustRevalidate(final boolean mustRevalidate) {
        this.mustRevalidate = mustRevalidate;
    }

    /**
     * Corresponds to the proxy-revalidate cache control directive.
     *
     * @return true if the proxy-revalidate cache control directive will be included in the
     * response, false otherwise.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4">HTTP/1.1 section 14.9.4</a>
     */
    public boolean isProxyRevalidate() {
        return proxyRevalidate;
    }

    /**
     * Corresponds to the must-revalidate cache control directive.
     *
     * @param proxyRevalidate true if the proxy-revalidate cache control directive should be included in the
     *                        response, false otherwise.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4">HTTP/1.1 section 14.9.4</a>
     */
    public void setProxyRevalidate(final boolean proxyRevalidate) {
        this.proxyRevalidate = proxyRevalidate;
    }

    /**
     * Corresponds to the max-age cache control directive.
     *
     * @return the value of the max-age cache control directive, -1 if the directive is disabled.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">HTTP/1.1 section 14.9.3</a>
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Corresponds to the max-age cache control directive.
     *
     * @param maxAge the value of the max-age cache control directive, a value of -1 will disable the directive.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">HTTP/1.1 section 14.9.3</a>
     */
    public void setMaxAge(final int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Corresponds to the s-maxage cache control directive.
     *
     * @return the value of the s-maxage cache control directive, -1 if the directive is disabled.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">HTTP/1.1 section 14.9.3</a>
     */
    public int getSMaxAge() {
        return sMaxAge;
    }

    /**
     * Corresponds to the s-maxage cache control directive.
     *
     * @param sMaxAge the value of the s-maxage cache control directive, a value of -1 will disable the directive.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">HTTP/1.1 section 14.9.3</a>
     */
    public void setSMaxAge(final int sMaxAge) {
        this.sMaxAge = sMaxAge;
    }

    /**
     * Corresponds to the value of the no-cache cache control directive.
     *
     * @return a mutable list of field-names that will form the value of the no-cache cache control directive.
     * An empty list results in a bare no-cache directive.
     *
     * @see #isNoCache()
     * @see #setNoCache(boolean)
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">HTTP/1.1 section 14.9.1</a>
     */
    public List<String> getNoCacheFields() {
        if (noCacheFields == null) {
            noCacheFields = new ArrayList<String>();
        }
        return noCacheFields;
    }

    /**
     * Corresponds to the no-cache cache control directive.
     *
     * @param noCache true if the no-cache cache control directive should be included in the
     *                response, false otherwise.
     * @see #getNoCacheFields()
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">HTTP/1.1 section 14.9.1</a>
     */
    public void setNoCache(final boolean noCache) {
        this.noCache = noCache;
    }

    /**
     * Corresponds to the no-cache cache control directive.
     *
     * @return true if the no-cache cache control directive will be included in the
     * response, false otherwise.
     *
     * @see #getNoCacheFields()
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">HTTP/1.1 section 14.9.1</a>
     */
    public boolean isNoCache() {
        return noCache;
    }

    /**
     * Corresponds to the private cache control directive.
     *
     * @return true if the private cache control directive will be included in the
     * response, false otherwise.
     *
     * @see #getPrivateFields()
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">HTTP/1.1 section 14.9.1</a>
     */
    public boolean isPrivate() {
        return privateFlag;
    }

    /**
     * Corresponds to the value of the private cache control directive.
     *
     * @return a mutable list of field-names that will form the value of the private cache control directive.
     * An empty list results in a bare no-cache directive.
     *
     * @see #isPrivate()
     * @see #setPrivate(boolean)
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">HTTP/1.1 section 14.9.1</a>
     */
    public List<String> getPrivateFields() {
        if (privateFields == null) {
            privateFields = new ArrayList<String>();
        }
        return privateFields;
    }

    /**
     * Corresponds to the private cache control directive.
     *
     * @param flag true if the private cache control directive should be included in the
     *             response, false otherwise.
     * @see #getPrivateFields()
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">HTTP/1.1 section 14.9.1</a>
     */
    public void setPrivate(final boolean flag) {
        this.privateFlag = flag;
    }

    /**
     * Corresponds to the no-transform cache control directive.
     *
     * @return true if the no-transform cache control directive will be included in the
     * response, false otherwise.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.5">HTTP/1.1 section 14.9.5</a>
     */
    public boolean isNoTransform() {
        return noTransform;
    }

    /**
     * Corresponds to the no-transform cache control directive.
     *
     * @param noTransform true if the no-transform cache control directive should be included in the
     *                    response, false otherwise.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.5">HTTP/1.1 section 14.9.5</a>
     */
    public void setNoTransform(final boolean noTransform) {
        this.noTransform = noTransform;
    }

    /**
     * Corresponds to the no-store cache control directive.
     *
     * @return true if the no-store cache control directive will be included in the
     * response, false otherwise.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.2">HTTP/1.1 section 14.9.2</a>
     */
    public boolean isNoStore() {
        return noStore;
    }

    /**
     * Corresponds to the no-store cache control directive.
     *
     * @param noStore true if the no-store cache control directive should be included in the
     *                response, false otherwise.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.2">HTTP/1.1 section 14.9.2</a>
     */
    public void setNoStore(final boolean noStore) {
        this.noStore = noStore;
    }

    /**
     * Corresponds to a set of extension cache control directives.
     *
     * @return a mutable map of cache control extension names and their values.
     * If a key has a null value, it will appear as a bare directive. If a key has
     * a value that contains no whitespace then the directive will appear as
     * a simple name=value pair. If a key has a value that contains whitespace
     * then the directive will appear as a quoted name="value" pair.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.6">HTTP/1.1 section 14.9.6</a>
     */
    public Map<String, String> getCacheExtension() {
        if (cacheExtension == null) {
            cacheExtension = new HashMap<String, String>();
        }
        return cacheExtension;
    }

    /**
     * Convert the cache control to a string suitable for use as the value of the
     * corresponding HTTP header.
     *
     * @return a stringified cache control
     */
    @Override
    public String toString() {
        return HEADER_DELEGATE.toString(this);
    }

    /**
     * Generate hash code from cache control properties.
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.privateFlag ? 1 : 0);
        hash = 41 * hash + (this.noCache ? 1 : 0);
        hash = 41 * hash + (this.noStore ? 1 : 0);
        hash = 41 * hash + (this.noTransform ? 1 : 0);
        hash = 41 * hash + (this.mustRevalidate ? 1 : 0);
        hash = 41 * hash + (this.proxyRevalidate ? 1 : 0);
        hash = 41 * hash + this.maxAge;
        hash = 41 * hash + this.sMaxAge;
        hash = 41 * hash + hashCodeOf(this.privateFields);
        hash = 41 * hash + hashCodeOf(this.noCacheFields);
        hash = 41 * hash + hashCodeOf(this.cacheExtension);
        return hash;
    }

    /**
     * Compares object argument to this cache control to see if they are the same
     * considering all property values.
     *
     * @param obj the object to compare to
     * @return true if the two cache controls are the same, false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CacheControl other = (CacheControl) obj;
        if (this.privateFlag != other.privateFlag) {
            return false;
        }
        if (this.noCache != other.noCache) {
            return false;
        }
        if (this.noStore != other.noStore) {
            return false;
        }
        if (this.noTransform != other.noTransform) {
            return false;
        }
        if (this.mustRevalidate != other.mustRevalidate) {
            return false;
        }
        if (this.proxyRevalidate != other.proxyRevalidate) {
            return false;
        }
        if (this.maxAge != other.maxAge) {
            return false;
        }
        if (this.sMaxAge != other.sMaxAge) {
            return false;
        }
        if (notEqual(this.privateFields, other.privateFields)) {
            return false;
        }
        if (notEqual(this.noCacheFields, other.noCacheFields)) {
            return false;
        }
        if (notEqual(this.cacheExtension, other.cacheExtension)) {
            return false;
        }
        return true;
    }

    /**
     * Check if two collections are not equal.
     *
     * If one of the collections is {@code null} and the other is empty, consider the collections to be equal.
     *
     * @param first  first collection. May be {@code null}.
     * @param second second collection. May be {@code null}.
     * @return {@code true} if the two collections are not equal, {@code false} if the two collections are equal
     * (or one of them is {@code null} and the other one is empty).
     */
    private static boolean notEqual(Collection<?> first, Collection<?> second) {
        if (first == second) {
            return false;
        }
        if (first == null) {
            // if first is 'null', consider equal to empty
            return !second.isEmpty();
        }
        if (second == null) {
            // if second is 'null', consider equal to empty
            return !first.isEmpty();
        }

        return !first.equals(second);
    }

    /**
     * Check if two maps are not equal.
     *
     * If one of the maps is {@code null} and the other is empty, consider the maps to be equal.
     *
     * @param first  first collection. May be {@code null}.
     * @param second second collection. May be {@code null}.
     * @return {@code true} if the two maps are not equal, {@code false} if the two maps are equal
     * (or one of them is {@code null} and the other one is empty).
     */
    private static boolean notEqual(Map<?, ?> first, Map<?, ?> second) {
        if (first == second) {
            return false;
        }
        if (first == null) {
            // if first is 'null', consider equal to empty
            return !second.isEmpty();
        }
        if (second == null) {
            // if second is 'null', consider equal to empty
            return !first.isEmpty();
        }

        return !first.equals(second);
    }

    /**
     * Compute a {@link Object#hashCode} of a collection.
     *
     * If the collection is {@code null} or empty, the returned hash code is {@code 0} (zero). Otherwise, the collection's
     * {@code hashCode()} method is called to compute the hash code.
     *
     * @param instance collection, may be {@code null}.
     * @return hash code for the collection, if {@code null} or empty, the returned hash code is {@code 0} (zero).
     */
    private static int hashCodeOf(Collection<?> instance) {
        return (instance == null || instance.isEmpty()) ? 0 : instance.hashCode();
    }

    /**
     * Compute a {@link Object#hashCode} of a map.
     *
     * If the map is {@code null} or empty, the returned hash code is {@code 0} (zero). Otherwise, the map's
     * {@code hashCode()} method is called to compute the hash code.
     *
     * @param instance map, may be {@code null}.
     * @return hash code for the map, if {@code null} or empty, the returned hash code is {@code 0} (zero).
     */
    private static int hashCodeOf(Map<?, ?> instance) {
        return (instance == null || instance.isEmpty()) ? 0 : instance.hashCode();
    }
}
