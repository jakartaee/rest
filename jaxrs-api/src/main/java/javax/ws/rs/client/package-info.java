/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/**
 * <h1>The JAX-RS client API</h1>
 *
 * The JAX-RS client API is a Java based API used to access Web resources.
 * It is not restricted to resources implemented using JAX-RS.
 * It provides a higher-level abstraction compared to a {@link java.net.HttpURLConnection
 * plain HTTP communication API} as well as integration with the JAX-RS extension
 * providers, in order to enable concise and efficient implementation of
 * reusable client-side solutions that leverage existing and well
 * established client-side implementations of HTTP-based communication.
 * <p />
 * The JAX-RS Client API encapsulates the Uniform Interface Constraint &ndash;
 * a key constraint of the REST architectural style &ndash; and associated data
 * elements as client-side Java artifacts and supports a pluggable architecture
 * by defining multiple extension points.
 *
 * <h2>Client API Bootstrapping and Configuration</h2>
 * The main entry point to the API is a {@link javax.ws.rs.client.ClientBuilder}
 * that is used to bootstrap {@link javax.ws.rs.client.Client} instances -
 * {@link javax.ws.rs.core.Configurable configurable}, heavy-weight objects
 * that manage the underlying communication infrastructure and serve as the root
 * objects for accessing any Web resource. The following example illustrates the
 * bootstrapping and configuration of a {@code Client} instance:
 * <pre>
 *   Client client = ClientBuilder.newClient();
 *
 *   client.property("MyProperty", "MyValue")
 *         .register(MyProvider.class)
 *         .register(MyFeature.class);
 * </pre>
 *
 * <h2>Accessing Web Resources</h2>
 * A Web resource can be accessed using a fluent API in which method invocations
 * are chained to configure and ultimately submit an HTTP request. The following
 * example gets a {@code text/plain} representation of the resource identified by
 * {@code "http://example.org/hello"}:
 * <pre>
 *   Client client = ClientBuilder.newClient();
 *   Response res = client.target("http://example.org/hello").request("text/plain").get();
 * </pre>
 * Conceptually, the steps required to submit a request are the following:
 * <ol>
 *   <li>obtain an {@link javax.ws.rs.client.Client} instance</li>
 *   <li>create a {@link javax.ws.rs.client.WebTarget WebTarget} pointing at a Web resource</li>
 *   <li>{@link javax.ws.rs.client.Invocation.Builder build} a request</li>
 *   <li>submit a request to directly retrieve a response or get a prepared
 *       {@link javax.ws.rs.client.Invocation} for later submission</li>
 * </ol>
 *
 * As illustrated above, individual Web resources are in the JAX-RS Client API
 * represented as resource targets. Each {@code WebTarget} instance is bound to a
 * concrete URI, e.g. {@code "http://example.org/messages/123"},
 * or a URI template, e.g. {@code "http://example.org/messages/{id}"}.
 * That way a single target can either point at a particular resource or represent
 * a larger group of resources (that e.g. share a common configuration) from which
 * concrete resources can be later derived:
 * <pre>
 *   // Parent target for all messages
 *   WebTarget messages = client.target("http://example.org/messages/{id}");
 *
 *   // New target for http://example.org/messages/123
 *   WebTarget msg123 = messages.resolveTemplate("id", 123);
 *
 *   // New target for http://example.org/messages/456
 *   WebTarget msg456 = messages.resolveTemplate("id", 456);
 * </pre>
 *
 *<h2>Generic Invocations</h2>
 * An {@link javax.ws.rs.client.Invocation} is a request that has been prepared
 * and is ready for execution.
 * Invocations provide a generic interface that enables a separation of concerns
 * between the creator and the submitter. In particular, the submitter does not
 * need to know how the invocation was prepared, but only whether it should be
 * executed synchronously or asynchronously.
 * <pre>
 *   Invocation inv1 = client.target("http://example.org/atm/balance")
 *       .queryParam("card", "111122223333").queryParam("pin", "9876")
 *       .request("text/plain").buildGet();
 *   Invocation inv2 = client.target("http://example.org/atm/withdrawal")
 *       .queryParam("card", "111122223333").queryParam("pin", "9876")
 *       .request().buildPost(text("50.0")));
 *
 *   Collection<Invocation> invs = Arrays.asList(inv1, inv2);
 *   // Executed by the submitter
 *   Collection<Response> ress = Collections.transform(invs, new F<Invocation, Response>() {
 *      public Response apply(Invocation inv) {return inv.invoke(); }
 *   });
 * </pre>
 */
package javax.ws.rs.client;
