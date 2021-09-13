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

package com.sun.ts.tests.jaxrs.spec.resource.valueofandfromstring;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = 6626213314312507899L;

  private static final String DATA = "ASDFGHJKLQWERTYUIOPPPPPPP";

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_resource_valueofandfromstring_web");
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
   * @testName: enumHeaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an enum in which case fromString MUST be used.
   */
  public void enumHeaderTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS, "param:" + DATA);
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/enumheader"));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.FROMSTRING.name());
    invoke();
  }

  /*
   * @testName: enumCookieTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an enum in which case fromString MUST be used.
   */
  public void enumCookieTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS, "Cookie: param=" + DATA);
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/enumcookie"));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.FROMSTRING.name());
    invoke();
  }

  /*
   * @testName: enumMaxtrixTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an enum in which case fromString MUST be used.
   */
  public void enumMaxtrixTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/enummatrix;param=" + DATA));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.FROMSTRING.name());
    invoke();
  }

  /*
   * @testName: enumQueryTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an enum in which case fromString MUST be used.
   */
  public void enumQueryTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/enumquery?param=" + DATA));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.FROMSTRING.name());
    invoke();
  }

  /*
   * @testName: enumPathTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an enum in which case fromString MUST be used.
   */
  public void enumPathTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/enumpath/" + DATA));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.FROMSTRING.name());
    invoke();
  }

  /*
   * @testName: entityHeaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an entity in which case fromString MUST be used.
   */
  public void entityHeaderTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS, "param:" + DATA);
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/entityheader"));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.VALUEOF.name());
    invoke();
  }

  /*
   * @testName: entityCookieTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an entity in which case fromString MUST be used.
   */
  public void entityCookieTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS, "Cookie: param=" + DATA);
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/entitycookie"));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.VALUEOF.name());
    invoke();
  }

  /*
   * @testName: entityMaxtrixTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an entity in which case fromString MUST be used.
   */
  public void entityMaxtrixTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/entitymatrix;param=" + DATA));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.VALUEOF.name());
    invoke();
  }

  /*
   * @testName: entityQueryTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an entity in which case fromString MUST be used.
   */
  public void entityQueryTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/entityquery?param=" + DATA));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.VALUEOF.name());
    invoke();
  }

  /*
   * @testName: entityPathTest
   * 
   * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
   * 
   * @test_Strategy: If both methods are present then valueOf MUST be used
   * unless the type is an entity in which case fromString MUST be used.
   */
  public void entityPathTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/entitypath/" + DATA));
    setProperty(Property.SEARCH_STRING,
        EnumWithFromStringAndValueOf.VALUEOF.name());
    invoke();
  }
}
