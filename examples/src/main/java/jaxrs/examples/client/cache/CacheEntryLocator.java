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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Response;

/**
 * @author Bill Burke
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 */
public class CacheEntryLocator implements ClientRequestFilter {

    private Map<String, CacheEntry> cache;

    public CacheEntryLocator(Map<String, CacheEntry> cache) {
        this.cache = cache;
    }

    @Override
    public void filter(ClientRequestContext request) throws IOException {
        load(request);
    }

    private void load(ClientRequestContext request) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            CacheEntry cacheEntry = cache.get(request.getUri().toString());

            if (cacheEntry != null) {
                Response.ResponseBuilder responseBuilder =
                        Response.status(cacheEntry.getStatus()).entity(new ByteArrayInputStream(cacheEntry.getBody()));

                for (Map.Entry<String, List<String>> mapEntry : cacheEntry.getHeaders().entrySet()) {
                    for (String value : mapEntry.getValue()) {
                        responseBuilder.header(mapEntry.getKey(), value);
                    }
                }

                // stops filter chain & returns response
                request.abortWith(responseBuilder.build());
            }
        }
    }
}
