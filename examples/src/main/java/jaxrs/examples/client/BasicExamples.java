/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import javax.net.ssl.SSLContext;
import javax.xml.bind.annotation.XmlRootElement;

import jaxrs.examples.client.custom.ThrottledClient;
import static javax.ws.rs.client.Entity.form;
import static javax.ws.rs.client.Entity.text;
import static javax.ws.rs.client.Entity.xml;

/**
 * Basic client-side examples.
 *
 * @author Bill Burke
 * @author Marek Potociar
 */
public class BasicExamples {

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

    @SuppressWarnings("UnusedDeclaration")
    public void clientBootstrapping() {
        // Default client instantiation using default configuration
        Client defaultClient = ClientBuilder.newClient();
        assert defaultClient != null;
        defaultClient.property("CUSTOM_PROPERTY", "CUSTOM_VALUE");

        // Default client instantiation using custom configuration

        Client defaultConfiguredClient = ClientBuilder.newClient(defaultClient.getConfiguration());
        assert defaultConfiguredClient != null;

        ///////////////////////////////////////////////////////////

        // Custom client instantiation examples
        ThrottledClient myClient = new ThrottledClient();
        ThrottledClient myConfiguredClient = new ThrottledClient(10);
    }

    public void creatingResourceAndSubResourceUris() {
        // Target( http://jaxrs.examples.org/jaxrsApplication/customers/ )
        WebTarget customersUri = ClientBuilder.newClient()
                                              .target("http://jaxrs.examples.org/jaxrsApplication/customers");
        // Target( http://jaxrs.examples.org/jaxrsApplication/customers/{id}/ )
        WebTarget anyCustomerUri = customersUri.path("{id}");
        // Target( http://jaxrs.examples.org/jaxrsApplication/customers/123/ )
        WebTarget customer123 = anyCustomerUri.resolveTemplate("id", 123);

        assert customer123 != null;
    }

    public void creatingClientRequestsAndInvocations() {
        final Client client = ClientBuilder.newClient();

        Response response = client.target("http://jaxrs.examples.org/jaxrsApplication/customers")
                                  .request(MediaType.APPLICATION_XML).header("Foo", "Bar").get();
        assert response.getStatus() == 200;
    }

    public void autoCloseableResponse() {
        final Client client = ClientBuilder.newClient();

        try (Response response =
                     client.target("http://jaxrs.examples.org/jaxrsApplication/customers")
                           .request(MediaType.APPLICATION_XML)
                           .header("Foo", "Bar")
                           .get();
        ) {
            assert response.getStatus() == 200;
        }
    }

    public void creatingClientInstanceUsingClientBuilder() {
        final SSLContext sslContext = null;
        // ...initialize SSL context...
        final Client client = ClientBuilder.newBuilder()
                                           .sslContext(sslContext)
                                           .build();

        assert client != null;
    }

    public void creatingResourceUriRequestsAndInvocations() {
        final Client client = ClientBuilder.newClient();
        final WebTarget customersUri = client.target("http://jaxrs.examples.org/jaxrsApplication/customers");

        // Create target request, customize it and invoke using newClient
        Response response = customersUri.request(MediaType.APPLICATION_XML).header("Foo", "Bar").get();
        assert response.getStatus() == 200;
    }

    public void defaultResponse() {
        Customer customer;
        Response response;

        final WebTarget customersUri = ClientBuilder.newClient()
                                                    .target("http://jaxrs.examples.org/jaxrsApplication/customers");

        response = customersUri.path("{id}").resolveTemplate("id", 123).request().get();
        customer = response.readEntity(Customer.class);
        assert customer != null;

        response = customersUri.request().post(xml(new Customer("Marek")));
        assert response.getStatus() == 201;
    }

    public void typedResponse() {
        Customer customer = ClientBuilder.newClient()
                                         .target("http://jaxrs.examples.org/jaxrsApplication/customers/{id}")
                                         .resolveTemplate("id", 123).request().get(Customer.class);
        assert customer != null;
    }

    public void typedGenericResponse() {
        List<Customer> customers = ClientBuilder.newClient()
                                                .target("http://jaxrs.examples.org/jaxrsApplication/customers")
                                                .request().get(new GenericType<List<Customer>>() {
                });
        assert customers != null;
    }

    public void responseUsingSubResourceClient() {
        WebTarget customersUri = ClientBuilder.newClient()
                                              .target("http://jaxrs.examples.org/jaxrsApplication/customers");
        WebTarget customer = customersUri.path("{id}");

        // Create a customer
        Response response = customersUri.request().post(xml(new Customer("Bill")));
        assert response.getStatus() == 201;

        Customer favorite;
        // view a customer
        favorite = customer.resolveTemplate("id", 123).request().get(Customer.class);
        assert favorite != null;

        // view a customer (alternative)
        favorite = customer
                .resolveTemplate("id", 123) // Target ("http://jaxrs.examples.org/jaxrsApplication/customers/123/")
                .request().get(Customer.class);
        assert favorite != null;
    }

    public void asyncResponse() throws Exception {
        Future<Response> future = ClientBuilder.newClient()
                                               .target("http://jaxrs.examples.org/jaxrsApplication/customers/{id}")
                                               .resolveTemplate("id", 123).request().async().get();

        Response response = future.get();
        Customer customer = response.readEntity(Customer.class);
        assert customer != null;
    }

    public void typedAsyncResponse() throws Exception {
        Future<Customer> customer = ClientBuilder.newClient()
                                                 .target("http://jaxrs.examples.org/jaxrsApplication/customers/{id}")
                                                 .resolveTemplate("id", 123).request().async().get(Customer.class);
        assert customer.get() != null;
    }

    public void asyncCallback() {
        final Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://jaxrs.examples.org/jaxrsApplication/customers/{id}");
        target.resolveTemplate("id", 123).request().async().get(new InvocationCallback<Customer>() {

            @Override
            public void completed(Customer customer) {
                // Do something
            }

            @Override
            public void failed(Throwable error) {
                // process error
            }
        });

        // invoke another request in background
        Future<?> handle = target.resolveTemplate("id", 456).request().async().get(new InvocationCallback<Response>() {

            @Override
            public void completed(Response response) {
                // do something
            }

            @Override
            public void failed(Throwable error) {
                // process error
            }
        });
        handle.cancel(true);
    }

    public void asyncCallbackUsingSubResourceClient() throws Exception {
        final Client client = ClientBuilder.newClient();
        WebTarget anyCustomerUri = client.target("http://jaxrs.examples.org/jaxrsApplication/customers/{id}");

        // invoke a request in background
        Future<Customer> handle = anyCustomerUri.resolveTemplate("id", 123) // Target
                                                .request().async().get(new InvocationCallback<Customer>() {
                    @Override
                    public void completed(Customer customer) {
                        // do something
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        // do something
                    }
                });
        handle.cancel(true);

        // invoke another request in background
        anyCustomerUri.resolveTemplate("id", 456) // Target
                      .request().async().get(new InvocationCallback<Response>() {

            @Override
            public void completed(Response customer) {
                // do something
            }

            @Override
            public void failed(Throwable throwable) {
                // do something
            }
        });

        // invoke one more request using newClient
        Future<Response> response = anyCustomerUri.resolveTemplate("id", 789)
                                                  .request().cookie(new Cookie("fooName", "XYZ")).async().get();
        assert response.get() != null;
    }

    public static class TestFeature implements Feature {

        @Override
        public boolean configure(FeatureContext context) {
            // do nothing
            return true;
        }
    }

    public void commonFluentUseCases() {
        Client client = ClientBuilder.newClient();

        // Invocation
        client.target("http://examples.jaxrs.com/");

        client.target("http://examples.jaxrs.com/").request("text/plain").get();
        client.target("http://examples.jaxrs.com/").request("text/plain").async().get();
        client.target("http://examples.jaxrs.com/").request().buildPut(text("Hi")).invoke();
        client.target("http://examples.jaxrs.com/").request("text/plain").buildGet().submit();


        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").get();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").async().get();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").buildGet().invoke();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").buildGet().submit();

        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").get();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").async().get();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").buildGet().invoke();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain").buildGet().submit();

        client.target("http://examples.jaxrs.com/").path("123").request("text/plain")
              .header("custom-name", "custom_value").get();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain")
              .header("custom-name", "custom_value").async().get();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain")
              .header("custom-name", "custom_value").buildGet().invoke();
        client.target("http://examples.jaxrs.com/").path("123").request("text/plain")
              .header("custom-name", "custom_value").buildGet().submit();

        // POSTing Forms
        client.target("http://examples.jaxrs.com/").path("123").request(MediaType.APPLICATION_JSON)
              .post(form(new Form("param1", "a").param("param2", "b")));

        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("param1", "a");
        formData.add("param2", "b");
        client.target("http://examples.jaxrs.com/").path("123").request(MediaType.APPLICATION_JSON)
              .post(form(formData));

        // Configuration
        TestFeature testFeature = new TestFeature();
        client.register(testFeature);
        client.target("http://examples.jaxrs.com/").register(testFeature);
        client.target("http://examples.jaxrs.com/").request("text/plain").property("foo", "bar");
        client.target("http://examples.jaxrs.com/").request("text/plain").buildGet().property("foo", "bar");
    }

    public void invocationFlexibility() {
        // For users who really need it...
        Invocation i = ClientBuilder.newClient()
                                    .target("http://examples.jaxrs.com/greeting")
                                    .request("text/plain")
                                    .header("custom-name", "custom_value")
                                    .buildPut(text("Hi"));

        i.invoke();                                              // Ok, now I can send the updated request
    }

    public static class MyProvider implements ReaderInterceptor, WriterInterceptor, ContainerRequestFilter {

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
            // TODO: implement method.
        }

        @Override
        public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
            return null;  // TODO: implement method.
        }

        @Override
        public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
            // TODO: implement method.
        }
    }

    public void customContractRegistration() {
        Configurable<?> configurable = ClientBuilder.newClient();

        configurable.register(MyProvider.class, ContainerRequestContext.class);
        configurable.register(MyProvider.class, ContainerRequestContext.class, ReaderInterceptor.class);
        configurable.register(new MyProvider(), WriterInterceptor.class);
        configurable.register(new MyProvider(), WriterInterceptor.class, ReaderInterceptor.class);
    }

    public void customExecutor() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(256);

        Client client = ClientBuilder.newBuilder()
                                     .executorService(executorService)
                                     .scheduledExecutorService(scheduledExecutorService)
                                     .build();
    }

    public void responseStatus20() {
        Client client = ClientBuilder.newClient();

        Response response = client.target("https://github.com/jax-rs/").request().get();

        switch (response.getStatus()) {
            case 200: return;
            case 201: return;
            case 404: return;
            case 500:
        }
    }

    public void responseStatus21() {
        Client client = ClientBuilder.newClient();

        Response response = client.target("https://github.com/jax-rs/").request().get();

        switch (response.getStatusInfo().toEnum()) {
            case OK: return;
            case CREATED: return;
            case NOT_FOUND: return;
            case INTERNAL_SERVER_ERROR:
        }
    }
}
