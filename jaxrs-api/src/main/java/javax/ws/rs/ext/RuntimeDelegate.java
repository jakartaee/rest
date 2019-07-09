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

package javax.ws.rs.ext;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant.VariantListBuilder;
import java.lang.reflect.ReflectPermission;
import java.net.URL;

/**
 * Implementations of this API provide a concrete subclass of RuntimeDelegate and
 * various API methods defer to methods of RuntimeDelegate for their
 * functionality. Regular users of the API are not expected to use this class
 * directly and overriding an implementation of this class with a user supplied
 * subclass may cause unexpected behavior.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @since 1.0
 */
public abstract class RuntimeDelegate {

    /**
     * Name of the property identifying the {@link RuntimeDelegate} implementation
     * to be returned from {@link RuntimeDelegate#getInstance()}.
     */
    public static final String JAXRS_RUNTIME_DELEGATE_PROPERTY = "javax.ws.rs.ext.RuntimeDelegate";
    private static final String JAXRS_DEFAULT_RUNTIME_DELEGATE = "org.glassfish.jersey.internal.RuntimeDelegateImpl";
    private static final Object RD_LOCK = new Object();
    private static ReflectPermission suppressAccessChecksPermission = new ReflectPermission("suppressAccessChecks");
    private static volatile RuntimeDelegate cachedDelegate;

    /**
     * Allows custom implementations to extend the {@code RuntimeDelegate} class.
     */
    protected RuntimeDelegate() {
    }

    /**
     * Obtain a {@code RuntimeDelegate} instance. If an instance had not already been
     * created and set via {@link #setInstance(RuntimeDelegate)}, the first
     * invocation will create an instance which will then be cached for future use.
     *
     * <p>
     * The algorithm used to locate the RuntimeDelegate subclass to use consists
     * of the following steps:
     * </p>
     * <ul>
     * <li>
     * If a resource with the name of {@code META-INF/services/javax.ws.rs.ext.RuntimeDelegate}
     * exists, then its first line, if present, is used as the UTF-8 encoded
     * name of the implementation class.
     * </li>
     * <li>
     * If the $java.home/lib/jaxrs.properties file exists and it is readable by
     * the {@code java.util.Properties.load(InputStream)} method and it contains
     * an entry whose key is {@code javax.ws.rs.ext.RuntimeDelegate}, then the value of
     * that entry is used as the name of the implementation class.
     * </li>
     * <li>
     * If a system property with the name {@code javax.ws.rs.ext.RuntimeDelegate}
     * is defined, then its value is used as the name of the implementation class.
     * </li>
     * <li>
     * Finally, a default implementation class name is used.
     * </li>
     * </ul>
     *
     * @return an instance of {@code RuntimeDelegate}.
     */
    public static RuntimeDelegate getInstance() {
        // Double-check idiom for lazy initialization of fields.
        // Local variable is used to limit the number of more expensive accesses to a volatile field.
        RuntimeDelegate result = cachedDelegate;
        if (result == null) { // First check (no locking)
            synchronized (RD_LOCK) {
                result = cachedDelegate;
                if (result == null) { // Second check (with locking)
                    cachedDelegate = result = findDelegate();
                }
            }
        }
        return result;
    }

    /**
     * Obtain a {@code RuntimeDelegate} instance using the method described in
     * {@link #getInstance}.
     *
     * @return an instance of {@code RuntimeDelegate}.
     */
    private static RuntimeDelegate findDelegate() {
        try {
            Object delegate = FactoryFinder.find(
                    JAXRS_RUNTIME_DELEGATE_PROPERTY,
                    JAXRS_DEFAULT_RUNTIME_DELEGATE,
                    RuntimeDelegate.class);
            if (!(delegate instanceof RuntimeDelegate)) {
                Class pClass = RuntimeDelegate.class;
                String classnameAsResource = pClass.getName().replace('.', '/') + ".class";
                ClassLoader loader = pClass.getClassLoader();
                if (loader == null) {
                    loader = ClassLoader.getSystemClassLoader();
                }
                URL targetTypeURL = loader.getResource(classnameAsResource);
                throw new LinkageError("ClassCastException: attempting to cast"
                        + delegate.getClass().getClassLoader().getResource(classnameAsResource)
                        + " to " + targetTypeURL);
            }
            return (RuntimeDelegate) delegate;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Set the runtime delegate that will be used by classes. If this method
     * is not called prior to {@link #getInstance} then an implementation will
     * be sought as described in {@link #getInstance}.
     *
     * @param rd the runtime delegate instance
     * @throws SecurityException if there is a security manager and the permission
     *                           ReflectPermission("suppressAccessChecks") has not been granted.
     */
    public static void setInstance(final RuntimeDelegate rd) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(suppressAccessChecksPermission);
        }
        synchronized (RD_LOCK) {
            RuntimeDelegate.cachedDelegate = rd;
        }
    }

    /**
     * Create a new instance of a {@link javax.ws.rs.core.UriBuilder}.
     *
     * @return new {@code UriBuilder} instance.
     * @see javax.ws.rs.core.UriBuilder
     */
    public abstract UriBuilder createUriBuilder();

    /**
     * Create a new instance of a {@link javax.ws.rs.core.Response.ResponseBuilder}.
     *
     * @return new {@code ResponseBuilder} instance.
     * @see javax.ws.rs.core.Response.ResponseBuilder
     */
    public abstract ResponseBuilder createResponseBuilder();

    /**
     * Create a new instance of a {@link javax.ws.rs.core.Variant.VariantListBuilder}.
     *
     * @return new {@code VariantListBuilder} instance.
     * @see javax.ws.rs.core.Variant.VariantListBuilder
     */
    public abstract VariantListBuilder createVariantListBuilder();

    /**
     * Create a configured instance of the supplied endpoint type. How the
     * returned endpoint instance is published is dependent on the type of
     * endpoint.
     *
     * @param <T>          endpoint type.
     * @param application  the application configuration.
     * @param endpointType the type of endpoint instance to be created.
     * @return a configured instance of the requested type.
     * @throws java.lang.IllegalArgumentException
     *          if application is null or the requested endpoint type is
     *          not supported.
     * @throws java.lang.UnsupportedOperationException
     *          if the implementation supports no endpoint types.
     */
    public abstract <T> T createEndpoint(Application application, Class<T> endpointType)
            throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * Obtain an instance of a {@link HeaderDelegate} for the supplied class. An
     * implementation is required to support the following values for type:
     * {@link javax.ws.rs.core.CacheControl}, {@link javax.ws.rs.core.Cookie},
     * {@link javax.ws.rs.core.EntityTag}, {@link javax.ws.rs.core.Link},
     * {@link javax.ws.rs.core.NewCookie}, {@link javax.ws.rs.core.MediaType}
     * and {@code java.util.Date}.
     *
     * @param <T>  header type.
     * @param type the class of the header.
     * @return an instance of {@code HeaderDelegate} for the supplied type.
     * @throws java.lang.IllegalArgumentException
     *          if type is {@code null}.
     * @see javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate
     */
    public abstract <T> HeaderDelegate<T> createHeaderDelegate(Class<T> type)
            throws IllegalArgumentException;

    /**
     * Defines the contract for a delegate that is responsible for
     * converting between the String form of a HTTP header and
     * the corresponding type {@code T}.
     *
     * @param <T> an type that corresponds to the value of a HTTP header.
     */
    public static interface HeaderDelegate<T> {

        /**
         * Parse the supplied value and create an instance of {@code T}.
         *
         * @param value the string value.
         * @return the newly created instance of {@code T}.
         * @throws IllegalArgumentException if the supplied string cannot be
         *                                  parsed or is {@code null}.
         */
        public T fromString(String value);

        /**
         * Convert the supplied value to a String.
         *
         * @param value the value of type {@code T}.
         * @return a String representation of the value.
         * @throws IllegalArgumentException if the supplied object cannot be
         *                                  serialized or is {@code null}.
         */
        public String toString(T value);
    }

    /**
     * Create a new instance of a {@link javax.ws.rs.core.Link.Builder}.
     *
     * @return new {@code Link.Builder} instance.
     * @see javax.ws.rs.core.Link.Builder
     */
    public abstract Link.Builder createLinkBuilder();
}
