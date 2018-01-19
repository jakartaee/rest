/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.webdav;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * Example of Client extension to support WebDAV.
 *
 * @author Marek Potociar
 */
public class WebDavClient implements Client {

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget target(String uri) throws IllegalArgumentException, NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget target(URI uri) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget target(UriBuilder uriBuilder) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget target(Link link) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Invocation.Builder invocation(Link link) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient property(String name, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Class<?> componentClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Class<?> componentClass, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Class<?> componentClass, Class<?>... contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Class<?> providerClass, Map<Class<?>, Integer> contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Object component) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Object component, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Object component, Class<?>... contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavClient register(Object provider, Map<Class<?>, Integer> contracts) {
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
