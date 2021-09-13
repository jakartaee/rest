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

package com.sun.ts.tests.jaxrs.spec.context.client;

import static com.sun.ts.tests.jaxrs.spec.context.server.StringBeanEntityProviderWithInjectables.notInjected;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.provider.StringBean;
import com.sun.ts.tests.jaxrs.spec.context.server.StringBeanEntityProviderWithInjectables;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = -2921113736906329195L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_context_client_web/resource");
  }

  /**
   * Entry point for different-VM execution. It should delegate to method
   * run(String[], PrintWriter, PrintWriter), and this method should not contain
   * any test configuration.
   */
  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /* Run test */
  /*
   * @testName: clientWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
   * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
   * JAXRS:SPEC:100; JAXRS:JAVADOC:754;
   * 
   * @test_Strategy: @Context available to providers
   * 
   * An instance can be injected into a class field or method parameter using
   * the @Context annotation.
   * 
   * The lifecycle of components registered using this class-based register(...)
   * method is fully managed by the JAX-RS implementation or any underlying IoC
   * container supported by the implementation.
   */
  public void clientWriterTest() throws Fault {
    addProvider(StringBeanEntityProviderWithInjectables.class);
    setRequestContentEntity(new StringBean("stringbean"));
    setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
    invoke();
    assertInjection("@Context injection did not work properly:");
  }

  /*
   * @testName: clientReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
   * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
   * JAXRS:SPEC:100; JAXRS:JAVADOC:754;
   * 
   * @test_Strategy: @Context available to providers
   * 
   * An instance can be injected into a class field or method parameter using
   * the @Context annotation.
   * 
   * The lifecycle of components registered using this class-based register(...)
   * method is fully managed by the JAX-RS implementation or any underlying IoC
   * container supported by the implementation.
   */
  public void clientReaderTest() throws Fault {
    addProvider(StringBeanEntityProviderWithInjectables.class);
    setRequestContentEntity("stringbean");
    setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
    invoke();
    StringBean content = getResponseBody(StringBean.class);
    assertNotNull(content, "response body is null");
    logMsg("Injectables are", content.get());
    assertInjection(content.get(), "@Context injection did not work properly:");
  }

  // ////////////////////////////////////////////////////////////////////
  private void assertInjection(String body, Object failMessage) throws Fault {
    assertEquals('1', body.charAt(6), failMessage, notInjected(body, 6),
        "has not been injected"); // Providers
    assertEquals('1', body.charAt(8), failMessage, notInjected(body, 8),
        "has not been injected"); // Configuration
    logMsg("@Context injected as expected");
  }

  private void assertInjection(Object failMessage) throws Fault {
    String body = getResponseBody();
    assertInjection(body, failMessage);
  }
}
