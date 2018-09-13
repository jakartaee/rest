/*
 * Copyright (c) 2018 IBM and Contributors to the Eclipse Foundation
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

import java.security.KeyStore;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;

/**
 * {@link javax.ws.rs.client.ClientBuilderListener} unit tests.
 *
 * @author Andy McCright
 * @since 2.2
 */
public class ClientBuilderStub extends ClientBuilder {

    ConfigurationStub config = new ConfigurationStub();
    /* (non-Javadoc)
    * @see javax.ws.rs.core.Configurable#getConfiguration()
    */
    @Override
    public Configuration getConfiguration() {
        return config;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#property(java.lang.String, java.lang.Object)
     */
    @Override
    public ClientBuilder property(String key, Object value) {
        config.properties.put(key, value);
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Class)
     */
    @Override
    public ClientBuilder register(Class<?> clazz) {
        config.providerClasses.put(clazz, 5000);
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Object)
     */
    @Override
    public ClientBuilder register(Object o) {
        if (o instanceof Feature) {
            config.features.add((Feature)o);
        } else {
            config.providerInstances.put(o, 5000);
        }
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Class, int)
     */
    @Override
    public ClientBuilder register(Class<?> arg0, int arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Class, java.lang.Class[])
     */
    @Override
    public ClientBuilder register(Class<?> arg0, Class<?>... arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Class, java.util.Map)
     */
    @Override
    public ClientBuilder register(Class<?> arg0, Map<Class<?>, Integer> arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Object, int)
     */
    @Override
    public ClientBuilder register(Object arg0, int arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Object, java.lang.Class[])
     */
    @Override
    public ClientBuilder register(Object arg0, Class<?>... arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configurable#register(java.lang.Object, java.util.Map)
     */
    @Override
    public ClientBuilder register(Object arg0, Map<Class<?>, Integer> arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#build()
     */
    @Override
    public Client build() {
        // unimplemented
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#connectTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public ClientBuilder connectTimeout(long arg0, TimeUnit arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#executorService(java.util.concurrent.ExecutorService)
     */
    @Override
    public ClientBuilder executorService(ExecutorService arg0) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#hostnameVerifier(javax.net.ssl.HostnameVerifier)
     */
    @Override
    public ClientBuilder hostnameVerifier(HostnameVerifier arg0) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#keyStore(java.security.KeyStore, char[])
     */
    @Override
    public ClientBuilder keyStore(KeyStore arg0, char[] arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#readTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public ClientBuilder readTimeout(long arg0, TimeUnit arg1) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#scheduledExecutorService(java.util.concurrent.ScheduledExecutorService)
     */
    @Override
    public ClientBuilder scheduledExecutorService(ScheduledExecutorService arg0) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#sslContext(javax.net.ssl.SSLContext)
     */
    @Override
    public ClientBuilder sslContext(SSLContext arg0) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#trustStore(java.security.KeyStore)
     */
    @Override
    public ClientBuilder trustStore(KeyStore arg0) {
        // unimplemented
        return this;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.client.ClientBuilder#withConfig(javax.ws.rs.core.Configuration)
     */
    @Override
    public ClientBuilder withConfig(Configuration arg0) {
        // unimplemented
        return this;
    }

 }
