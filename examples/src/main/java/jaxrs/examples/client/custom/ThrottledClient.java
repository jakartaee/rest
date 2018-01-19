/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.custom;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * A custom "throttled" client example.
 *
 * @author Marek Potociar
 */
public final class ThrottledClient implements Client {

    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"})
    private final BlockingQueue<ClientRequestContext> requestQueue;

    public ThrottledClient() {
        this(10);
    }

    public ThrottledClient(int queueCapacity) {
        this.requestQueue = new ArrayBlockingQueue<ClientRequestContext>(queueCapacity);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebTarget target(String uri) throws IllegalArgumentException, NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebTarget target(URI uri) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebTarget target(UriBuilder uriBuilder) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebTarget target(Link link) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Invocation.Builder invocation(Link link) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient property(String name, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Class<?> componentClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Class<?> componentClass, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Class<?> componentClass, Class<?>... contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Class<?> providerClass, Map<Class<?>, Integer> contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Object component) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Object component, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Object component, Class<?>... contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThrottledClient register(Object provider, Map<Class<?>, Integer> contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Configuration getConfiguration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SSLContext getSslContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
