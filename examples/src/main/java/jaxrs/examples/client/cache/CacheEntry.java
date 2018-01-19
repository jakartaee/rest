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

import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Bill Burke
 * @author Marek Potociar
 */
public class CacheEntry {

    private int status;
    private MultivaluedMap<String, String> headers;
    private byte[] body;

    public CacheEntry(int status, MultivaluedMap<String, String> headers, byte[] body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public MultivaluedMap<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
