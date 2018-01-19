/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.links;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Marek Potociar
 */
public class LinkUsageExample {

    public void testCLientSideLinks() {
        Client client = ClientBuilder.newClient();

        Response current = client.target("http://examples.jax-rs-spec.com/current")
                .request(MediaType.APPLICATION_XML).get();

        Response next = client.target(current.getLink("next"))
                .request(MediaType.APPLICATION_XML).get();
    }
}
