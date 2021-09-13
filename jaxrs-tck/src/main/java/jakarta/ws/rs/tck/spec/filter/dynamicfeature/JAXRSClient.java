/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.filter.dynamicfeature;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
/**
 * Test the interceptor is called when any entity provider is called
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = 1177743379402950754L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_filter_dynamicfeature_web/resource");
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
   * @testName: noBindingTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:987;
   * 
   * @test_Strategy: When a filter or interceptor method throws an exception,
   * the JAX-RS runtime will attempt to map the exception
   * 
   * If a web resource had been matched before the exception was thrown, then
   * all the filters in the ContainerResponse chain for that resource MUST be
   * invoked;
   * 
   */
  public void noBindingTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "nobinding"));
    setProperty(Property.CONTENT, "0");
    setProperty(Property.SEARCH_STRING, "0");
    invoke();
    logMsg(
        "Dynamic Bynding did not bind any filter or interceptor as expected");
  }

  /*
   * @testName: dynamicBindingTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:987;
   * 
   * @test_Strategy: When a filter or interceptor method throws an exception,
   * the JAX-RS runtime will attempt to map the exception
   * 
   * If a web resource had been matched before the exception was thrown, then
   * all the filters in the ContainerResponse chain for that resource MUST be
   * invoked;
   * 
   */
  public void dynamicBindingTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "dynamic"));
    setProperty(Property.CONTENT, "0");
    setProperty(Property.SEARCH_STRING, "12");
    invoke();
    logMsg("Dynamic feature bound filter and interceptor as expected");
  }
}
