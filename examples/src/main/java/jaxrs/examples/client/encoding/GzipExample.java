/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.encoding;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import static javax.ws.rs.client.Entity.text;

import jaxrs.examples.filter.compression.GzipEntityInterceptor;

/**
 * @author Bill Burke
 * @author Marek Potociar
 */
public class GzipExample {

    public void gzipExample() {
        WebTarget target = ClientBuilder.newClient().target("http://example.com/foo/bar.txt");
        target.register(GzipEntityInterceptor.class);

        // getting a gzip encoded body
        String body = target.request("text/plain").get(String.class);

        // send a gzip encoded body
        target.request().header("Content-Encoding", "gzip").post(text(body));
    }
}
