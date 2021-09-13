/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.client.invocations;

import java.net.URI;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.client.JdkLoggingFilter;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = -3347907896017729013L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_client_invocations_web/resource");
  }

  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /* Run test */
  /*
   * @testName: synchronousTest
   * 
   * @assertion_ids: JAXRS:SPEC:71;
   * 
   * @test_Strategy: The mapping calls Invocation.invoke() to execute the
   * invocation synchronously; asynchronous execution is also supported by
   * calling Invocation.submit().
   */
  public void synchronousTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "call"));
    setProperty(Property.SEARCH_STRING, Resource.class.getName());
    invoke();
  }

  /*
   * @testName: asynchronousTest
   * 
   * @assertion_ids: JAXRS:SPEC:71;
   * 
   * @test_Strategy: The mapping calls Invocation.invoke() to execute the
   * invocation synchronously; asynchronous execution is also supported by
   * calling Invocation.submit().
   */
  public void asynchronousTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "call"));
    setProperty(Property.SEARCH_STRING, Resource.class.getName());
    setAsynchronousProcessing();
    invoke();
  }

  /*
   * @testName: invocationFromLinkTextXmlMediaTypeTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:411; JAXRS:JAVADOC:788;
   * 
   * @test_Strategy: Build an invocation builder from a link. It uses the URI
   * and the type of the link to initialize the invocation builder. The type is
   * used as the initial value for the HTTP Accept header, if present.
   * 
   * 
   * Build an invocation from a link. The method and URI are obtained from the
   * link. The HTTP Accept header is initialized to the value of the "produces"
   * parameter in the link. If the operation requires an entity, use the
   * overloaded form of this method. This method will throw an
   * java.lang.IllegalArgumentException if there is not enough information to
   * build an invocation (e.g. no HTTP method or entity when required).
   * 
   * Create a new link instance initialized from an existing URI.
   */
  public void invocationFromLinkTextXmlMediaTypeTest() throws Fault {
    Response r = invocationFromLinkWithMediaType(MediaType.TEXT_XML);
    checkResposeForMessage(MediaType.TEXT_XML, r);
  }

  /*
   * @testName: invocationFromLinkApplicationJsonMediaTypeTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:411; JAXRS:JAVADOC:788;
   * 
   * @test_Strategy: Build an invocation from a link. The method and URI are
   * obtained from the link. The HTTP Accept header is initialized to the value
   * of the "produces" parameter in the link. If the operation requires an
   * entity, use the overloaded form of this method.
   * 
   * Create a new link instance initialized from an existing URI.
   */
  public void invocationFromLinkApplicationJsonMediaTypeTest() throws Fault {
    Response r = invocationFromLinkWithMediaType(MediaType.APPLICATION_JSON);
    checkResposeForMessage(MediaType.APPLICATION_JSON, r);
  }

  /*
   * @testName: invocationFromLinkTwoMediaTypesTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:411; JAXRS:JAVADOC:788;
   * 
   * @test_Strategy: Build an invocation from a link. The method and URI are
   * obtained from the link. The HTTP Accept header is initialized to the value
   * of the "produces" parameter in the link. If the operation requires an
   * entity, use the overloaded form of this method.
   * 
   * Create a new link instance initialized from an existing URI.
   */
  public void invocationFromLinkTwoMediaTypesTest() throws Fault {
    String type1 = MediaType.APPLICATION_ATOM_XML;
    String type2 = MediaType.TEXT_HTML;
    Response r = invocationFromLinkWithMediaType(type1 + "," + type2);
    r.bufferEntity();
    checkResposeForMessage(type1, r);
    checkResposeForMessage(type2, r);
  }

  // /////////////////////////////////////////////////////////////////////////
  protected Response invocationFromLinkWithMediaType(String mediaType)
      throws Fault {
    String url = "mediatype";
    Client client = ClientBuilder.newClient();
    client.register(new JdkLoggingFilter(false));
    URI uri = UriBuilder.fromPath(getUrl(url)).build();
    Link link = Link.fromUri(uri).type(mediaType).build();
    Invocation i = client.invocation(link).buildGet();
    Response response = i.invoke();
    return response;
  }

  protected void checkResposeForMessage(String message, Response response)
      throws Fault {
    String body = response.readEntity(String.class);
    boolean containsMediaType = body.contains(message);
    assertFault(containsMediaType == true,
        "The HTTP Accept header does not contain", message);
  }

  protected String getUrl(String method) {
    StringBuilder url = new StringBuilder();
    url.append("http://").append(_hostname).append(":").append(_port);
    url.append(getContextRoot()).append("/").append(method);
    return url.toString();
  }
}
