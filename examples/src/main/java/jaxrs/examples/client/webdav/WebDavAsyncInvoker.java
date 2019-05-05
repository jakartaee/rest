/*
 * Copyright (c) 2011, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.webdav;

import java.util.concurrent.Future;

import jakarta.ws.rs.client.AsyncInvoker;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;

/**
 * @author Marek Potociar
 */
public interface WebDavAsyncInvoker extends AsyncInvoker {

    public Future<Response> search(Entity<?> entity);

    // other WebDAV method definitions go here
}
