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

package com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.basic;

import com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.TestServlet;
import com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.TestServlet.Scheme;

import jakarta.ws.rs.core.Response;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 *                     user;
 *                     password;
 *                     authuser;
 *                     authpassword;
 */
public class JAXRSBasicClient
    extends com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.JAXRSClient {

  private static final long serialVersionUID = 340277879725875946L;

  public JAXRSBasicClient() {
    setContextRoot("/jaxrs_ee_core_securitycontext_basic_web/Servlet");
  }

  /**
   * Entry point for different-VM execution. It should delegate to method
   * run(String[], PrintWriter, PrintWriter), and this method should not contain
   * any test configuration.
   */
  public static void main(String[] args) {
    JAXRSBasicClient theTests = new JAXRSBasicClient();
    theTests.run(args);
  }

  /* Run test */

  /*
   * @testName: noAuthorizationTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Send no authorization, make sure of 401 response
   */
  public void noAuthorizationTest() throws Fault {
    super.noAuthorizationTest();
  }

  /*
   * @testName: basicAuthorizationAdminTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:169; JAXRS:JAVADOC:170; JAXRS:JAVADOC:171;
   * JAXRS:JAVADOC:172; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Send basic authorization, check security context
   */
  public void basicAuthorizationAdminTest() throws Fault {
    setProperty(Property.STATUS_CODE, getStatusCode(Response.Status.OK));
    setProperty(Property.BASIC_AUTH_USER, user);
    setProperty(Property.BASIC_AUTH_PASSWD, password);

    setProperty(Property.SEARCH_STRING, TestServlet.Security.UNSECURED.name());
    setProperty(Property.SEARCH_STRING, TestServlet.Role.DIRECTOR.name());
    setProperty(Property.SEARCH_STRING, user);
    setProperty(Property.SEARCH_STRING, TestServlet.Scheme.BASIC.name());
    invokeRequest();
  }

  /*
   * @testName: basicAuthorizationIncorrectUserTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Send basic authorization, check security context
   */
  public void basicAuthorizationIncorrectUserTest() throws Fault {
    setProperty(Property.STATUS_CODE,
        getStatusCode(Response.Status.UNAUTHORIZED));
    setProperty(Property.BASIC_AUTH_USER, Scheme.NOSCHEME.name());
    setProperty(Property.BASIC_AUTH_PASSWD, password);
    invokeRequest();
  }

  /*
   * @testName: basicAuthorizationIncorrectPasswordTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Send basic authorization, check security context
   */
  public void basicAuthorizationIncorrectPasswordTest() throws Fault {
    setProperty(Property.STATUS_CODE,
        getStatusCode(Response.Status.UNAUTHORIZED));
    setProperty(Property.BASIC_AUTH_USER, authuser);
    setProperty(Property.BASIC_AUTH_PASSWD, password);
    invokeRequest();
  }

  /*
   * @testName: basicAuthorizationStandardUserTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:169; JAXRS:JAVADOC:170; JAXRS:JAVADOC:171;
   * JAXRS:JAVADOC:172; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Send basic authorization with made up Realm, check security
   * context
   */
  public void basicAuthorizationStandardUserTest() throws Fault {
    setProperty(Property.STATUS_CODE, getStatusCode(Response.Status.OK));
    setProperty(Property.BASIC_AUTH_USER, authuser);
    setProperty(Property.BASIC_AUTH_PASSWD, authpassword);

    setProperty(Property.SEARCH_STRING, TestServlet.Security.UNSECURED.name());
    setProperty(Property.SEARCH_STRING, TestServlet.Role.OTHERROLE.name());
    setProperty(Property.SEARCH_STRING, authuser);
    setProperty(Property.SEARCH_STRING, TestServlet.Scheme.BASIC.name());
    invokeRequest();
  }
}
