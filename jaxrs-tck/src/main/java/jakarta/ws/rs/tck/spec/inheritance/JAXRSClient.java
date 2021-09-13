/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.inheritance;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import jakarta.ws.rs.core.MediaType;

public class JAXRSClient extends JAXRSCommonClient {

  private static final long serialVersionUID = 4535321107880833833L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_inheritance_web");
  }

  /**
   * Entry point for different-VM execution. It should delegate to method
   * run(String[], PrintWriter, PrintWriter), and this method should not contain
   * any test configuration.
   */
  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: test1
   * 
   * @assertion_ids: JAXRS:SPEC:23; JAXRS:SPEC:57; JAXRS:SPEC:60;
   * 
   * @test_Strategy: Client sends a request on a resource at /InheritanceTest,
   * Verify that inheritance works.
   */
  public void test1() throws Fault {
    setProperty(REQUEST_HEADERS, buildAccept(MediaType.TEXT_PLAIN_TYPE));
    setProperty(REQUEST, buildRequest(Request.GET, "InheritanceTest"));
    setProperty(SEARCH_STRING, "First");
    invoke();
  }

  /*
   * @testName: test2
   * 
   * @assertion_ids: JAXRS:SPEC:24; JAXRS:SPEC:57; JAXRS:SPEC:60;
   * 
   * @test_Strategy: Client sends a request on a resource at /InheritanceTest1,
   * Verify that inheritance works.
   */
  public void test2() throws Fault {
    setProperty(REQUEST_HEADERS, buildAccept(MediaType.TEXT_HTML_TYPE));
    setProperty(REQUEST, buildRequest(Request.GET, "InheritanceTest1"));
    setProperty(SEARCH_STRING, "Second");
    invoke();
  }
}
