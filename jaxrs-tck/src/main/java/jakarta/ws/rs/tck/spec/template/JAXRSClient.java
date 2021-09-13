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

package com.sun.ts.tests.jaxrs.spec.template;

import java.io.PrintWriter;

import com.sun.javatest.Status;
import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

public class JAXRSClient extends JAXRSCommonClient {

  public void JAXRSClient() {
    setContextRoot("/jaxrs_spec_templateTest_web");
  }

  /**
   * Entry point for different-VM execution. It should delegate to method
   * run(String[], PrintWriter, PrintWriter), and this method should not contain
   * any test configuration.
   */
  public static void main(String[] args) {
    JAXRSClient theTests = new JAXRSClient();
    Status s = theTests.run(args, new PrintWriter(System.out),
        new PrintWriter(System.err));
    s.exit();
  }

  /**
   * Entry point for same-VM execution. In different-VM execution, the main
   * method delegates to this method.
   */
  public Status run(String[] args, PrintWriter out, PrintWriter err) {
    return super.run(args, out, err);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: Test1
   *
   * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:20.1; JAXRS:SPEC:60;
   *
   * @test_Strategy: Client sends a request on a resource at /TemplateTest/{id},
   * Verify that correct resource method invoked through use of URI Template
   */
  public void Test1() throws Fault {
    setProperty(REQUEST,
        "GET " + "/jaxrs_spec_templateTest_web/TemplateTest/xyz HTTP/1.1");
    setProperty(SEARCH_STRING, "id1=xyz");
    invoke();
  }

  /*
   * @testName: Test2
   *
   * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:20.1; JAXRS:SPEC:57;
   * JAXRS:SPEC:60;
   *
   * @test_Strategy:Client sends a request on a resource at /TemplateTest/{id],
   * Verify that correct resource method invoked through use of URI Template
   */
  public void Test2() throws Fault {
    setProperty(REQUEST,
        "GET " + "/jaxrs_spec_templateTest_web/TemplateTest/xyz/abc HTTP/1.1");
    setProperty(SEARCH_STRING, "id3=abc");
    invoke();
  }

  /*
   * @testName: Test3
   *
   * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:20.1; JAXRS:SPEC:57;
   * JAXRS:SPEC:60;
   *
   * @test_Strategy:Client sends a request on a resource at /TemplateTest/{id],
   * Verify that correct resource method invoked through use of URI Template
   */
  public void Test3() throws Fault {
    setProperty(REQUEST, "GET "
        + "/jaxrs_spec_templateTest_web/TemplateTest/xyz/abc/def HTTP/1.1");
    setProperty(SEARCH_STRING, "id3=abc/def");
    invoke();
  }

  /*
   * @testName: Test4
   *
   * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:20.1; JAXRS:SPEC:57;
   * JAXRS:SPEC:60;
   *
   * @test_Strategy:Client sends a request on a resource at /TemplateTest/{id],
   * Verify that correct resource method invoked through use of URI Template
   */
  public void Test4() throws Fault {
    setProperty(REQUEST, "GET "
        + "/jaxrs_spec_templateTest_web/TemplateTest/xy/abc/def HTTP/1.1");
    setProperty(SEARCH_STRING, "id1=xy/abc/def");
    invoke();
  }

  /*
   * @testName: Test5
   *
   * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:20.1; JAXRS:SPEC:57;
   * JAXRS:SPEC:60;
   *
   * @test_Strategy:Client sends a request on a resource at /TemplateTest/{id],
   * Verify that correct resource method invoked through use of URI Template
   */
  public void Test5() throws Fault {
    setProperty(REQUEST, "GET "
        + "/jaxrs_spec_templateTest_web/TemplateTest/abc/test.html HTTP/1.1");
    setProperty(SEARCH_STRING, "id4=abc|name=test");
    invoke();
  }

  /*
   * @testName: Test6
   *
   * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:20.1; JAXRS:SPEC:57;
   * JAXRS:SPEC:60;
   *
   * @test_Strategy:Client sends a request on a resource at /TemplateTest/{id],
   * Verify that correct resource method invoked through use of URI Template
   */
  public void Test6() throws Fault {
    setProperty(REQUEST, "GET "
        + "/jaxrs_spec_templateTest_web/TemplateTest/abc/def/test.xml HTTP/1.1");
    setProperty(SEARCH_STRING, "id5=abc/def|name=test");
    invoke();
  }
}
