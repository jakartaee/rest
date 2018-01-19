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

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Example of WebTarget extension to support WebDAV.
 *
 * @author Marek Potociar
 */
public class WebDavTarget implements WebTarget {

    private final WebTarget target;

    public WebDavTarget(WebTarget target) {
        this.target = target;
    }

    @Override
    public URI getUri() {
        return target.getUri();
    }

    @Override
    public UriBuilder getUriBuilder() {
        return target.getUriBuilder();
    }

    @Override
    public WebDavTarget path(String path) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget resolveTemplate(String name, Object value) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget resolveTemplate(String name, Object value, boolean encodeSlashInPath) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget resolveTemplateFromEncoded(String name, Object value) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget resolveTemplates(Map<String, Object> templateValues) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget resolveTemplatesFromEncoded(Map<String, Object> templateValues) throws NullPointerException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget resolveTemplates(Map<String, Object> parameters, boolean encodeSlashInPath) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget matrixParam(String name, Object... values) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget queryParam(String name, Object... values) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTargetedBuilder request() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTargetedBuilder request(String... mediaTypes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTargetedBuilder request(MediaType... mediaTypes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget property(String name, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Class<?> componentClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Class<?> componentClass, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Class<?> componentClass, Class<?>... contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Class<?> providerClass, Map<Class<?>, Integer> contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Object component) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Object component, int priority) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Object component, Class<?>... contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WebDavTarget register(Object provider, Map<Class<?>, Integer> contracts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Configuration getConfiguration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
