/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.spec;

import java.util.concurrent.Future;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static javax.ws.rs.client.Entity.entity;

import javax.xml.bind.annotation.XmlRootElement;

import jaxrs.examples.client.custom.ThrottledClient;

/**
 * @author Bill Burke
 * @author Marek Potociar
 */
public class SpecExamples {

    @XmlRootElement
    public static class Customer {

        private final String name;

        public Customer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public void clientBootstrapping() {
        // Default client instantiation using default configuration
        Client defaultClient = ClientBuilder.newClient();
        defaultClient.property("CUSTOM_PROPERTY", "CUSTOM_VALUE");
        assert defaultClient != null;

        // Default client instantiation using custom configuration

        Client defaultConfiguredClient = ClientBuilder.newClient(defaultClient.getConfiguration());
        assert defaultConfiguredClient != null;

        ///////////////////////////////////////////////////////////

        // Custom client instantiation examples
        ThrottledClient myClient = new ThrottledClient();
        assert myClient != null;

        ThrottledClient myConfiguredClient = new ThrottledClient(10);
        assert myConfiguredClient != null;
    }

    public void fluentMethodChaining() {
        Client client = ClientBuilder.newClient();
        Response res = client.target("http://example.org/hello")
                .request("text/plain").get();

        Response res2 = client.target("http://example.org/hello")
                .queryParam("MyParam", "...")
                .request("text/plain")
                .header("MyHeader", "...")
                .get();
    }

    public void typeRelationships() {
        Client client = ClientBuilder.newClient();
        WebTarget uri = client.target("");
        Invocation.Builder builder = uri.request("text/plain");

        SyncInvoker syncInvoker = builder;
        AsyncInvoker asyncInvoker = builder.async();
        Invocation inv = builder.buildGet();

        Response r1 = builder.get();
        Response r2 = syncInvoker.get();
        Response r3 = inv.invoke();

        Future<Response> fr1 = asyncInvoker.get();
        Future<Response> fr2 = inv.submit();
    }

    public void benefitsOfResourceUri() {
        Client client = ClientBuilder.newClient();
        WebTarget base = client.target("http://example.org/");
        WebTarget hello = base.path("hello").path("{whom}");
        final WebTarget whomToGreet = hello.resolveTemplate("whom", "world");
        Response res = whomToGreet.request().get();
    }

    public void gettingAndPostingCustomers() {
        Client client = ClientBuilder.newClient();
        Customer c = client.target("http://examples.org/customers/123")
                .request("application/xml").get(Customer.class);
        Response res = client.target("http://examples.org/premium-customers/")
                .request().post(entity(c, "application/xml"));
    }

    public void asyncSamples() throws Exception {
        Client client = ClientBuilder.newClient();
        Future<Customer> fc = client.target("http://examples.org/customers/123")
                .request("application/xml").async().get(Customer.class);
        Customer c = fc.get();
    }
}
