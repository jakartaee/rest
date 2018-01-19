/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.link.clusterservice;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

/**
 * ClientTest class.
 *
 * @author Santiago.Pericas-Geertsen@oracle.com
 */
public class ClientTest {

    public void test() {
        Client client = ClientBuilder.newClient();

        // Get cluster representation -- entry point
        Response rc = client.target("/cluster").request("application/json").get();

        // Ensure cluster is online
        if (rc.hasLink("onliner")) {
            client.invocation(rc.getLink("onliner")).buildPost(null).invoke();
        }

        // Start all machines in cluster
        Cluster c = rc.readEntity(Cluster.class);
        for (Machine m : c.getMachines()) {
            // Machine name is need for URI template in link
            Link l = rc.getLinkBuilder("item").build(m.getName());

            // Create invocation from link and call invoke()
            Response rm = client.invocation(l).buildGet().invoke();

            // Start machine if not started already
            if (rm.hasLink("starter")) {
                client.invocation(rm.getLink("starter")).buildPost(null).invoke();
            }
        }
    }
}
