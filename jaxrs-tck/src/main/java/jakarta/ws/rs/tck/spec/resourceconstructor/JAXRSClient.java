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

package com.sun.ts.tests.jaxrs.spec.resourceconstructor;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

import jakarta.ws.rs.core.Response.Status;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = 1L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_resourceconstructor_web/resource");
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
   * @testName: visibleTest
   * 
   * @assertion_ids: JAXRS:SPEC:1; JAXRS:SPEC:2; JAXRS:SPEC:77; JAXRS:SPEC:77.1;
   * 
   * @test_Strategy: verify the PUBLIC constructor of a resource with most
   * attributes is used
   * 
   * A public constructor MAY include parameters annotated with one of the
   * following: @Context
   */
  public void visibleTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "mostAttributes"));
    invoke();
  }

  /*
   * @testName: packageVisibilityTest
   * 
   * @assertion_ids: JAXRS:SPEC:11;
   * 
   * @test_Strategy: Only public methods may be exposed as resource methods.
   */
  public void packageVisibilityTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "packageVisibility"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_FOUND));
    invoke();
  }

  /*
   * @testName: protectedVisibilityTest
   * 
   * @assertion_ids: JAXRS:SPEC:11;
   * 
   * @test_Strategy: Only public methods may be exposed as resource methods.
   */
  public void protectedVisibilityTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "protectedVisibility"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_FOUND));
    invoke();
  }

  /*
   * @testName: privateVisibilityTest
   * 
   * @assertion_ids: JAXRS:SPEC:11;
   * 
   * @test_Strategy: Only public methods may be exposed as resource methods.
   */
  public void privateVisibilityTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "privateVisibility"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_FOUND));
    invoke();
  }

  /*
   * @testName: constructorWithHeaderParamUsedTest
   * 
   * @assertion_ids: JAXRS:SPEC:77; JAXRS:SPEC:77.2;
   * 
   * @test_Strategy: A public constructor MAY include parameters annotated with
   * one of the following: @HeaderParam
   */
  public void constructorWithHeaderParamUsedTest() throws Fault {
    String param = "ABCDEFGH";
    setProperty(Property.REQUEST_HEADERS, "param:" + param);
    setProperty(Property.REQUEST, buildRequest(Request.GET, "header"));
    setProperty(Property.SEARCH_STRING, param);
    invoke();
  }

  /*
   * @testName: constructorWithCookieParamUsedTest
   * 
   * @assertion_ids: JAXRS:SPEC:77; JAXRS:SPEC:77.3;
   * 
   * @test_Strategy: A public constructor MAY include parameters annotated with
   * one of the following: @HeaderParam
   */
  public void constructorWithCookieParamUsedTest() throws Fault {
    String param = "ABCDEFGH";
    setProperty(Property.REQUEST_HEADERS, "Cookie: param=" + param);
    setProperty(Property.REQUEST, buildRequest(Request.GET, "cookie"));
    setProperty(Property.SEARCH_STRING, param);
    invoke();
  }

  /*
   * @testName: constructorWithMatrixParamUsedTest
   * 
   * @assertion_ids: JAXRS:SPEC:77; JAXRS:SPEC:77.4;
   * 
   * @test_Strategy: A public constructor MAY include parameters annotated with
   * one of the following: @MatrixParam
   */
  public void constructorWithMatrixParamUsedTest() throws Fault {
    String param = "ABCDEFGH";
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "matrix;param=" + param));
    setProperty(Property.SEARCH_STRING, param);
    invoke();
  }

  /*
   * @testName: constructorWithQueryParamUsedTest
   * 
   * @assertion_ids: JAXRS:SPEC:77; JAXRS:SPEC:77.5;
   * 
   * @test_Strategy: A public constructor MAY include parameters annotated with
   * one of the following: @QueryParam
   */
  public void constructorWithQueryParamUsedTest() throws Fault {
    String param = "ABCDEFGH";
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "query?param=" + param));
    setProperty(Property.SEARCH_STRING, param);
    invoke();
  }

  /*
   * @testName: constructorWithPathParamUsedTest
   * 
   * @assertion_ids: JAXRS:SPEC:77; JAXRS:SPEC:77.6;
   * 
   * @test_Strategy: A public constructor MAY include parameters annotated with
   * one of the following: @PathParam
   */
  public void constructorWithPathParamUsedTest() throws Fault {
    String param = "ABCDEFGH";
    setProperty(Property.REQUEST, buildRequest(Request.GET, "path/" + param));
    setProperty(Property.SEARCH_STRING, param);
    invoke();
  }
}
