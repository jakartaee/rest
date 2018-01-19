/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.cache;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author Bill Burke
 * @author Marek Potociar
 */
public class CacheExample {

    public void cacheExample() {
        Client client = ClientBuilder.newClient();
        client.register(CachingFeature.class);

        WebTarget resource = client.target("http://example.com/foo/bar.txt");

        String text = resource.request("text/plain").get(String.class);
        String second = resource.request("text/plain").get(String.class);

        System.out.println(text);
        System.out.println(second);
    }
}
