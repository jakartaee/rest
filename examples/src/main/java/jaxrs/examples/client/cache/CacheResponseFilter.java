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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 * @author Bill Burke
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 */
public class CacheResponseFilter implements ClientResponseFilter {

    private Map<String, CacheEntry> cacheStore;

    public CacheResponseFilter(Map<String, CacheEntry> store) {
        this.cacheStore = store;
    }

    @Override
    public void filter(ClientRequestContext request, ClientResponseContext response) throws IOException {
        store(request, response);
    }

    private void store(ClientRequestContext request, ClientResponseContext response) {
        if (request.getMethod().equalsIgnoreCase("GET")) {

            final byte[] body = readFromStream(1024, response.getEntityStream());

            CacheEntry cacheEntry = new CacheEntry(
                    response.getStatus(),
                    new MultivaluedHashMap<String, String>(response.getHeaders()),
                    body);
            cacheStore.put(request.getUri().toString(), cacheEntry);

            response.setEntityStream(new ByteArrayInputStream(cacheEntry.getBody()));
        }
    }

    private static byte[] readFromStream(int bufferSize, InputStream entityStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[bufferSize];
        int wasRead = 0;
        do {
            try {
                wasRead = entityStream.read(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (wasRead > 0) {
                baos.write(buffer, 0, wasRead);
            }
        } while (wasRead > -1);
        return baos.toByteArray();
    }
}
