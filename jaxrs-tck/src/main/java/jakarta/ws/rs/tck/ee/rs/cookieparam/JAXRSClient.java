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

/*
 * $Id$
 */
package jakarta.ws.rs.tck.ee.rs.cookieparam;

import jakarta.ws.rs.tck.common.util.JaxrsUtil;
import jakarta.ws.rs.tck.ee.rs.JaxrsParamClient;

import jakarta.ws.rs.core.Response.Status;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsParamClient {
  private static final long serialVersionUID = 1L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_ee_rs_cookieparam_web/CookieParamTest");
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
   * @testName: cookieParamTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:JAVADOC:145; JAXRS:JAVADOC:2;
   * 
   * @test_Strategy: Client invokes GET on root resource at /CookieParamTest;
   * Resource will respond with a cookie; Client verify the cookie is received;
   * Client send request again with the cookie; Verify that the cookie is
   * received using CookieParam in the resource; Verify on the client side from
   * response.
   */
  public void cookieParamTest() throws Fault {
    StringBuffer sb = new StringBuffer();
    boolean pass = true;

    setProperty(Property.REQUEST, buildRequest("setcookie"));
    setProperty(Property.SEARCH_STRING, "setCookie=done");
    setProperty(Property.EXPECTED_HEADERS, "Set-Cookie:name1=value1");
    setProperty(Property.SAVE_STATE, "true");
    try {
      invoke();
    } catch (Exception ex) {
      pass = false;
      sb.append("Test failed with: " + ex.getMessage());
    }

    setProperty(Property.REQUEST, buildRequest("verifycookie"));
    setProperty(Property.SEARCH_STRING, "name1=value1|verifyCookie=done");
    setProperty(Property.USE_SAVED_STATE, "true");
    try {
      invoke();
    } catch (Exception ex) {
      pass = false;
      sb.append("Test failed with: " + ex.getMessage());
    }

    assertFault(pass, "At least one assertion failed:", sb);
  }

  /*
   * @testName: cookieParamEntityWithConstructorTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.2; JAXRS:JAVADOC:12;
   * JAXRS:JAVADOC:12.1;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieParamEntityWithConstructorTest() throws Fault {
    super.paramEntityWithConstructorTest();
  }

  /*
   * @testName: cookieParamEntityWithValueOfTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:JAVADOC:12;
   * JAXRS:JAVADOC:12.1;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieParamEntityWithValueOfTest() throws Fault {
    super.paramEntityWithValueOfTest();
  }

  /*
   * @testName: cookieParamEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:JAVADOC:12;
   * JAXRS:JAVADOC:12.1;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieParamEntityWithFromStringTest() throws Fault {
    super.paramEntityWithFromStringTest();
  }

  /*
   * @testName: cookieParamSetEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:JAVADOC:12;
   * JAXRS:JAVADOC:12.1;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieParamSetEntityWithFromStringTest() throws Fault {
    super.paramCollectionEntityWithFromStringTest(CollectionName.SET);
  }

  /*
   * @testName: cookieParamListEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:JAVADOC:12;
   * JAXRS:JAVADOC:12.1;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieParamListEntityWithFromStringTest() throws Fault {
    super.paramCollectionEntityWithFromStringTest(CollectionName.LIST);
  }

  /*
   * @testName: cookieParamSortedSetEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:JAVADOC:12;
   * JAXRS:JAVADOC:12.1;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieParamSortedSetEntityWithFromStringTest() throws Fault {
    super.paramCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
  }

  /*
   * @testName: cookieFieldParamEntityWithConstructorTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.2; JAXRS:JAVADOC:6;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieFieldParamEntityWithConstructorTest() throws Fault {
    super.fieldEntityWithConstructorTest();
  }

  /*
   * @testName: cookieFieldParamEntityWithValueOfTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieFieldParamEntityWithValueOfTest() throws Fault {
    super.fieldEntityWithValueOfTest();
  }

  /*
   * @testName: cookieFieldParamEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieFieldParamEntityWithFromStringTest() throws Fault {
    super.fieldEntityWithFromStringTest();
  }

  /*
   * @testName: cookieFieldParamSetEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieFieldParamSetEntityWithFromStringTest() throws Fault {
    super.fieldCollectionEntityWithFromStringTest(CollectionName.SET);
  }

  /*
   * @testName: cookieFieldParamListEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieFieldParamListEntityWithFromStringTest() throws Fault {
    super.fieldCollectionEntityWithFromStringTest(CollectionName.LIST);
  }

  /*
   * @testName: cookieFieldSortedSetEntityWithFromStringTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
   * 
   * @test_Strategy: Verify that named CookieParam is handled properly
   */
  public void cookieFieldSortedSetEntityWithFromStringTest() throws Fault {
    super.fieldCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
  }

  /*
   * @testName: cookieParamThrowingWebApplicationExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:12.3;
   * 
   * @test_Strategy: Exceptions thrown during construction of parameter values
   * are treated the same as exceptions thrown during construction of field or
   * bean property values, see section 3.2.
   * 
   */
  public void cookieParamThrowingWebApplicationExceptionTest() throws Fault {
    super.paramThrowingWebApplicationExceptionTest();
  }

  /*
   * @testName: cookieFieldThrowingWebApplicationExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:8;
   * 
   * @test_Strategy: A WebApplicationException thrown during construction of
   * field or property values using 2 or 3 above is processed directly as
   * described in section 3.3.4.
   */
  public void cookieFieldThrowingWebApplicationExceptionTest() throws Fault {
    super.fieldThrowingWebApplicationExceptionTest();
  }

  /*
   * @testName: cookieParamThrowingIllegalArgumentExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:9; JAXRS:SPEC:9.2; JAXRS:SPEC:10;
   * 
   * @test_Strategy: Other exceptions thrown during construction of field or
   * property values using 2 or 3 above are treated as client errors:
   *
   * if the field or property is annotated with @HeaderParam or @CookieParam
   * then an implementation MUST generate a WebApplicationException that wraps
   * the thrown exception with a client error response (400 status) and no
   * entity.
   */
  public void cookieParamThrowingIllegalArgumentExceptionTest() throws Fault {
    super.paramThrowingIllegalArgumentExceptionTest();
  }

  /*
   * @testName: cookieFieldParamThrowingIllegalArgumentExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:12.3;
   * 
   * @test_Strategy: Exceptions thrown during construction of parameter values
   * are treated the same as exceptions thrown during construction of field or
   * bean property values, see section 3.2.
   */
  public void cookieFieldParamThrowingIllegalArgumentExceptionTest()
      throws Fault {
    super.fieldThrowingIllegalArgumentExceptionTest();
  }

  @Override
  protected void paramEntityThrowingAfterRequestSet(String request)
      throws Fault {
    createAndCheckCookie(request);
    // this is for illegal argument exception tests only
    if (request.contains(IllegalArgumentException.class.getSimpleName()))
      setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
    super.paramEntityThrowingAfterRequestSet(request);
  }

  private void createAndCheckCookie(String request) throws Fault {
    // create cookie
    setProperty(Property.REQUEST, buildRequest(request));
    setProperty(Property.SAVE_STATE, "true");
    invoke();
    checkCookie(request);
    // check cookie
    setProperty(Property.USE_SAVED_STATE, "true");
  }

  @Override
  protected void paramEntity(String request) throws Fault {
    createAndCheckCookie(request);
    super.paramEntity(request);
  }

  @Override
  protected String buildRequest(String param) {
    StringBuilder sb = new StringBuilder();
    sb.append(Request.GET).append(" ").append(_contextRoot);
    sb.append("?todo=").append(param.replace("=", "%3d")).append(HTTP11);
    return sb.toString();
  }

  @Override
  protected String getDefaultValueOfParam(String param) {
    StringBuilder sb = new StringBuilder();
    sb.append(param).append("=");
    sb.append(CookieParamTest.class.getSimpleName());
    return sb.toString();
  }

  private void checkCookie(String cookie) throws Fault {
    boolean found = false;
    String lowCookie = stripQuotesSpacesAndLowerCase(cookie);
    String[] headers = getResponseHeaders();
    for (String h : headers) {
      String header = stripQuotesSpacesAndLowerCase(h);
      if (header.startsWith("set-cookie"))
        if (header.contains(lowCookie))
          found = true;
    }
    assertTrue(found, "Could not find cookie", cookie, "in response headers:",
        JaxrsUtil.iterableToString(";", headers));
    logMsg("Found cookie", cookie, "as expected");
  }

  private static String stripQuotesSpacesAndLowerCase(String cookie) {
    return cookie.toLowerCase().replace("\"", "").replace("'", "").replace(" ",
        "");
  }
}
