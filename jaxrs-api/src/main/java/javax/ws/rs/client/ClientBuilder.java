/*
 * Copyright (c) 2011, 2019 Oracle and/or its affiliates. All rights reserved.
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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Configuration;
import java.net.URL;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Main entry point to the client API used to bootstrap {@link javax.ws.rs.client.Client}
 * instances.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public abstract class ClientBuilder implements Configurable<ClientBuilder> {

    /**
     * Name of the property identifying the {@link ClientBuilder} implementation
     * to be returned from {@link ClientBuilder#newBuilder()}.
     */
    public static final String JAXRS_DEFAULT_CLIENT_BUILDER_PROPERTY =
            "javax.ws.rs.client.ClientBuilder";
    /**
     * Default client builder implementation class name.
     */
    private static final String JAXRS_DEFAULT_CLIENT_BUILDER =
            "org.glassfish.jersey.client.JerseyClientBuilder";

    /**
     * Allows custom implementations to extend the {@code ClientBuilder} class.
     */
    protected ClientBuilder() {
    }

    /**
     * Create a new {@code ClientBuilder} instance using the default client builder
     * implementation class provided by the implementation provider.
     *
     * @return new client builder instance.
     */
    public static ClientBuilder newBuilder() {
        try {
            Object delegate =
                    FactoryFinder.find(JAXRS_DEFAULT_CLIENT_BUILDER_PROPERTY,
                            JAXRS_DEFAULT_CLIENT_BUILDER, ClientBuilder.class);
            if (!(delegate instanceof ClientBuilder)) {
                Class pClass = ClientBuilder.class;
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
            return (ClientBuilder) delegate;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Create a new {@link Client} instance using the default client builder implementation
     * class provided by the implementation provider.
     *
     * @return new client instance.
     */
    public static Client newClient() {
        return newBuilder().build();
    }

    /**
     * Create a new custom-configured {@link Client} instance using the default client builder
     * implementation class provided by the implementation provider.
     *
     * @param configuration data used to provide initial configuration for the new
     *                      client instance.
     * @return new configured client instance.
     */
    public static Client newClient(final Configuration configuration) {
        return newBuilder().withConfig(configuration).build();
    }

    /**
     * Set the internal configuration state to an externally provided configuration state.
     *
     * @param config external configuration state to replace the configuration of this configurable
     *               instance.
     * @return the updated client builder instance.
     */
    public abstract ClientBuilder withConfig(Configuration config);

    /**
     * Set the SSL context that will be used when creating secured transport connections
     * to server endpoints from {@link WebTarget web targets} created by the client
     * instance that is using this SSL context. The SSL context is expected to have all the
     * security infrastructure initialized, including the key and trust managers.
     * <p>
     * Setting a SSL context instance resets any {@link #keyStore(java.security.KeyStore, char[])
     * key store} or {@link #trustStore(java.security.KeyStore) trust store} values previously
     * specified.
     * </p>
     *
     * @param sslContext secure socket protocol implementation which acts as a factory
     *                   for secure socket factories or {@link javax.net.ssl.SSLEngine
     *                   SSL engines}. Must not be {@code null}.
     * @return an updated client builder instance.
     * @throws NullPointerException in case the {@code sslContext} parameter is {@code null}.
     * @see #keyStore(java.security.KeyStore, char[])
     * @see #keyStore(java.security.KeyStore, String)
     * @see #trustStore
     */
    public abstract ClientBuilder sslContext(final SSLContext sslContext);

    /**
     * Set the client-side key store. Key store contains client's private keys, and the certificates with their
     * corresponding public keys.
     * <p>
     * Setting a key store instance resets any {@link #sslContext(javax.net.ssl.SSLContext) SSL context instance}
     * value previously specified.
     * </p>
     * <p>
     * Note that a custom key store is only required if you want to enable a custom setup of a 2-way SSL connections
     * (client certificate authentication).
     * </p>
     *
     * @param keyStore client-side key store. Must not be {@code null}.
     * @param password client key password. Must not be {@code null}.
     * @return an updated client builder instance.
     * @throws NullPointerException in case any of the supplied parameters is {@code null}.
     * @see #sslContext
     * @see #keyStore(java.security.KeyStore, String)
     * @see #trustStore
     */
    public abstract ClientBuilder keyStore(final KeyStore keyStore, final char[] password);

    /**
     * Set the client-side key store. Key store contains client's private keys, and the certificates with their
     * corresponding public keys.
     * <p>
     * Setting a key store instance resets any {@link #sslContext(javax.net.ssl.SSLContext) SSL context instance}
     * value previously specified.
     * </p>
     * <p>
     * Note that for improved security of working with password data and avoid storing passwords in Java string
     * objects, the {@link #keyStore(java.security.KeyStore, char[])} version of the method can be utilized.
     * Also note that a custom key store is only required if you want to enable a custom setup of a 2-way SSL
     * connections (client certificate authentication).
     * </p>
     *
     * @param keyStore client-side key store. Must not be {@code null}.
     * @param password client key password. Must not be {@code null}.
     * @return an updated client builder instance.
     * @throws NullPointerException in case any of the supplied parameters is {@code null}.
     * @see #sslContext
     * @see #keyStore(java.security.KeyStore, char[])
     * @see #trustStore
     */
    public ClientBuilder keyStore(final KeyStore keyStore, final String password) {
        return keyStore(keyStore, password.toCharArray());
    }

    /**
     * Set the client-side trust store. Trust store is expected to contain certificates from other parties
     * the client is you expect to communicate with, or from Certificate Authorities that are trusted to
     * identify other parties.
     * <p>
     * Setting a trust store instance resets any {@link #sslContext(javax.net.ssl.SSLContext) SSL context instance}
     * value previously specified.
     * </p>
     * <p>
     * In case a custom trust store or custom SSL context is not specified, the trust management will be
     * configured to use the default Java runtime settings.
     * </p>
     *
     * @param trustStore client-side trust store. Must not be {@code null}.
     * @return an updated client builder instance.
     * @throws NullPointerException in case the supplied trust store parameter is {@code null}.
     * @see #sslContext
     * @see #keyStore(java.security.KeyStore, char[])
     * @see #keyStore(java.security.KeyStore, String)
     */
    public abstract ClientBuilder trustStore(final KeyStore trustStore);

    /**
     * Set the hostname verifier to be used by the client to verify the endpoint's hostname against it's
     * identification information.
     *
     * @param verifier hostname verifier.
     * @return an updated client builder instance.
     */
    public abstract ClientBuilder hostnameVerifier(final HostnameVerifier verifier);

    /**
     * Set the client-side {@link ExecutorService}.
     * <p>
     * Provided executor service will be used for executing asynchronous tasks.
     * <p>
     * When running in a Jakarta EE container, implementations are required to use the container-managed
     * executor service by default.  In Java SE, the default is implementation-specific.  In either
     * case, calling this method will override the default.
     *
     * @param executorService executor service to be used for async invocations.
     * @return an updated client builder instance.
     * @see Invocation.Builder#async()
     * @see Invocation.Builder#rx()
     * @see RxInvokerProvider#getRxInvoker(SyncInvoker, ExecutorService)
     * @since 2.1
     */
    public abstract ClientBuilder executorService(final ExecutorService executorService);

    /**
     * Set the client-side {@link ScheduledExecutorService}.
     * <p>
     * Provided executor service will be used for executing scheduled asynchronous tasks.
     * <p>
     * When running in a Jakarta EE container, implementations are required to use the container-managed
     * scheduled executor service by default.  In Java SE the default is implementation-specific.  In 
     * either case, calling this method will override the default.
     *
     * @param scheduledExecutorService executor service to be used for scheduled async invocations.
     * @return an updated client builder instance.
     * @see javax.ws.rs.sse.SseEventSource.Builder#reconnectingEvery(long, TimeUnit)
     * @since 2.1
     */
    public abstract ClientBuilder scheduledExecutorService(final ScheduledExecutorService scheduledExecutorService);

    /**
     * Set the connect timeout.
     * <p>
     * Value {@code 0} represents infinity. Negative values are not allowed.
     *
     * @param timeout the maximum time to wait.
     * @param unit    the time unit of the timeout argument.
     * @return an updated client builder instance.
     * @throws IllegalArgumentException when the value is negative.
     * @since 2.1
     */
    public abstract ClientBuilder connectTimeout(long timeout, TimeUnit unit);

    /**
     * Set the read timeout.
     * <p>
     * The value is the timeout to read a response. If the server doesn't respond within the defined timeframe,
     * {@link ProcessingException} is thrown with {@link TimeoutException} as a cause.
     * <p>
     * Value {@code 0} represents infinity. Negative values are not allowed.
     *
     * @param timeout the maximum time to wait.
     * @param unit    the time unit of the timeout argument.
     * @return an updated client builder instance.
     * @throws IllegalArgumentException when the value is negative.
     * @since 2.1
     */
    public abstract ClientBuilder readTimeout(long timeout, TimeUnit unit);

    /**
     * Build a new client instance using all the configuration previously specified
     * in this client builder.
     *
     * @return a new client instance.
     */
    public abstract Client build();
}
