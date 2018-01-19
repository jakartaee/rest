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

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * Test of WebDAV Client API Extension.
 *
 * @author Marek Potociar
 */
public class WebDavClientTest {

    private WebDavClient createClient() {
        return new WebDavClient();
    }

    static class TestFeature implements Feature {

        @Override
        public boolean configure(FeatureContext context) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public void fluentUseCases() {
        WebDavClient client = createClient();

        TestFeature testFeature = new TestFeature();
        client.register(testFeature);

        client.target("http://examples.jaxrs.com/webdav/");
        client.target("http://examples.jaxrs.com/webdav/").register(testFeature);
        client.target("http://examples.jaxrs.com/webdav/").request().property("foo", "bar");
        client.target("http://examples.jaxrs.com/webdav/").request().buildGet().property("foo", "bar");

        // HTTP
        client.target("http://examples.jaxrs.com/webdav/").request().get();
        client.target("http://examples.jaxrs.com/webdav/").request().async().get();
        client.target("http://examples.jaxrs.com/webdav/").request().buildGet().invoke();
        client.target("http://examples.jaxrs.com/webdav/").request().buildGet().submit();
        // WebDAV
        client.target("http://examples.jaxrs.com/webdav/").request().search(null);
        client.target("http://examples.jaxrs.com/webdav/").request().async().search(null);
        client.target("http://examples.jaxrs.com/webdav/").request().buildSearch(null).invoke();
        client.target("http://examples.jaxrs.com/webdav/").request().buildSearch(null).submit();

        // HTTP
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().get();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().async().get();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().buildGet().invoke();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().buildGet().submit();
        // WebDAV
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().search(null);
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().async().search(null);
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().buildSearch(null).invoke();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request().buildSearch(null).submit();

        // HTTP
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").get();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").async().get();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").buildGet().invoke();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").buildGet().submit();
        // WebDAV
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").search(null);
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").async().search(null);
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").buildSearch(null).invoke();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").buildSearch(null).submit();

        // HTTP
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").get();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").async().get();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").buildGet().invoke();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").buildGet().submit();
        // WebDAV
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").search(null);
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").async().search(null);
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").buildSearch(null).invoke();
        client.target("http://examples.jaxrs.com/webdav/").path("123").request("text/plain").header("custom-name", "custom_value").buildSearch(null).submit();
    }
}
