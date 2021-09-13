/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.context.server;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = -8615109992706004114L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_context_server_web/resource");
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
   * @testName: serverWriterInjectionTest
   * 
   * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
   * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
   * JAXRS:SPEC:100;
   * 
   * @test_Strategy: @Context available to providers
   * 
   * An instance can be injected into a class field or method parameter using
   * the @Context annotation.
   */
  public void serverWriterInjectionTest() throws Fault {
    setRequestContentEntity("");
    setProperty(Property.REQUEST, buildRequest(Request.POST, "writer"));
    invoke();
    assertInjection("@Context injection did not work properly:");
  }

  /*
   * @testName: serverReaderInjectionTest
   * 
   * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
   * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
   * JAXRS:SPEC:100;
   * 
   * @test_Strategy: @Context available to providers
   * 
   * An instance can be injected into a class field or method parameter using
   * the @Context annotation.
   */
  public void serverReaderInjectionTest() throws Fault {
    setRequestContentEntity("");
    setProperty(Property.REQUEST, buildRequest(Request.POST, "reader"));
    invoke();
    assertInjection("@Context injection did not work properly:");
  }

  /*
   * @testName: resourceInjectionTest
   * 
   * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
   * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
   * JAXRS:SPEC:100;
   * 
   * @test_Strategy: @Context available to providers
   * 
   * An instance can be injected into a class field or method parameter using
   * the @Context annotation.
   */
  public void resourceInjectionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "instance"));
    invoke();
    assertInjection("@Context injection did not work properly:");
  }

  /*
   * @testName: applicationInjectionTest
   * 
   * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
   * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
   * JAXRS:SPEC:100;
   * 
   * @test_Strategy: @Context available to providers
   * 
   * An instance can be injected into a class field or method parameter using
   * the @Context annotation.
   */
  public void applicationInjectionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "application"));
    invoke();
    assertInjection("@Context injection did not work properly:");
  }

  /*
   * @testName: methodArgumentsInjectionTest
   * 
   * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
   * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
   * JAXRS:SPEC:100;
   * 
   * @test_Strategy: @Context available to providers
   * 
   * An instance can be injected into a class field or method parameter using
   * the @Context annotation.
   */
  public void methodArgumentsInjectionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "method"));
    invoke();
    assertInjection("@Context injection did not work properly:");
  }

  // ////////////////////////////////////////////////////////////////////
  private void assertInjection(String body, Object failMessage) throws Fault {
    String notInjected = StringBeanEntityProviderWithInjectables
        .notInjected(body);
    assertEquals("111111111", body, failMessage, notInjected,
        "has not been injected");
    logMsg("@Context injected as expected");
  }

  private void assertInjection(Object failMessage) throws Fault {
    String body = getResponseBody();
    assertInjection(body, failMessage);
  }
}
