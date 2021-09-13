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

package com.sun.ts.tests.jaxrs.spec.provider.exceptionmapper;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import jakarta.ws.rs.core.Response.Status;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

public class JAXRSClient extends JAXRSCommonClient {

  private static final long serialVersionUID = -8228843141906281907L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_provider_exceptionmapper_web/resource");
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
   * @testName: throwableTest
   * 
   * @assertion_ids: JAXRS:SPEC:39;
   * 
   * @test_Strategy: When choosing an exception mapping provider to map an
   * exception, an implementation MUST use the provider whose generic type is
   * the nearest superclass of the exception.
   */
  public void throwableTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "throwable"));
    setProperty(Property.SEARCH_STRING, ThrowableMapper.class.getName());
    invoke();
  }

  /*
   * @testName: exceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:39;
   * 
   * @test_Strategy: When choosing an exception mapping provider to map an
   * exception, an implementation MUST use the provider whose generic type is
   * the nearest superclass of the exception.
   */
  public void exceptionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "exception"));
    setProperty(Property.SEARCH_STRING, PlainExceptionMapper.class.getName());
    invoke();
  }

  /*
   * @testName: runtimeExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:39;
   * 
   * @test_Strategy: When choosing an exception mapping provider to map an
   * exception, an implementation MUST use the provider whose generic type is
   * the nearest superclass of the exception.
   */
  public void runtimeExceptionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "runtime"));
    setProperty(Property.SEARCH_STRING, RuntimeExceptionMapper.class.getName());
    invoke();
  }

  /*
   * @testName: webapplicationExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:39;
   * 
   * @test_Strategy: When choosing an exception mapping provider to map an
   * exception, an implementation MUST use the provider whose generic type is
   * the nearest superclass of the exception.
   */
  public void webapplicationExceptionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "webapp"));
    setProperty(Property.SEARCH_STRING, WebAppExceptionMapper.class.getName());
    invoke();
  }

  /*
   * @testName: clientErrorExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:39;
   * 
   * @test_Strategy: When choosing an exception mapping provider to map an
   * exception, an implementation MUST use the provider whose generic type is
   * the nearest superclass of the exception.
   */
  public void clientErrorExceptionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "clienterror"));
    setProperty(Property.SEARCH_STRING,
        ClientErrorExceptionMapper.class.getName());
    invoke();
  }

  /*
   * @testName: filterChainTest
   * 
   * @assertion_ids: JAXRS:SPEC:82;
   * 
   * @test_Strategy: When a resource class or provider method throws an
   * exception for which there is an exception mapping provider, the matching
   * provider is used to obtain a Response instance. The resulting Response is
   * processed as if a web resource method had returned the Response, see
   * Section 3.3.3. In particular, a mapped Response MUST be processed using the
   * ContainerResponse filter chain defined in Chapter 6.
   */
  public void filterChainTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "chain"));
    setProperty(Property.SEARCH_STRING, ResponseFilter.class.getName());
    invoke();
  }

  /*
   * @testName: mappedExceptionTest
   * 
   * @assertion_ids: JAXRS:SPEC:83;
   * 
   * @test_Strategy: To avoid a potentially infinite loop, a single exception
   * mapper must be used during the processing of a request and its
   * corresponding response. JAX-RS implementations MUST NOT attempt to map
   * exceptions thrown while processing a response previously mapped from an
   * exception. Instead, this exception MUST be processed as described in steps
   * 3 and 4 in Section 3.3.4.
   */
  // TODO : Use a servlet filter to verify the exception has been passed
  // to underlying container, JIRA 1613
  public void mappedExceptionTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "mapped"));
    setProperty(Property.UNEXPECTED_RESPONSE_MATCH,
        WebAppExceptionMapper.class.getName());
    setProperty(Property.STATUS_CODE,
        getStatusCode(Status.INTERNAL_SERVER_ERROR));
    invoke();
  }

}
