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

package com.sun.ts.tests.jaxrs.spec.filter.namebinding;

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

  private static final long serialVersionUID = -1559382208933998735L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_filter_namebinding_web/resource");
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
   * @testName: noInterceptorBoundTest
   * 
   * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
   * 
   * @test_Strategy: Similarly, a resource method can be decorated with multiple
   * binding annotations. Each binding annotation instance in a resource method
   * denotes a set of filters and interceptors whose class definitions are
   * decorated with that annotation (possibly among others). The final set of
   * (static) filters and interceptors is the union of all these sets
   * 
   * returning filters or interceptors from the methods getClasses or
   * getSingletons in an application subclass will bind them globally only if
   * they are not decorated with a name binding annotation.
   */
  public void noInterceptorBoundTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "one"));
    setProperty(Property.SEARCH_STRING, "1");
    invoke();
    logMsg("No interceptor has been bound as expected");
  }

  /*
   * @testName: singleInterceptorBoundTest
   * 
   * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
   * 
   * @test_Strategy: Similarly, a resource method can be decorated with multiple
   * binding annotations. Each binding annotation instance in a resource method
   * denotes a set of filters and interceptors whose class definitions are
   * decorated with that annotation (possibly among others). The final set of
   * (static) filters and interceptors is the union of all these sets
   * 
   * returning filters or interceptors from the methods getClasses or
   * getSingletons in an application subclass will bind them globally only if
   * they are not decorated with a name binding annotation.
   */
  public void singleInterceptorBoundTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "ten"));
    setProperty(Property.SEARCH_STRING, "11");
    invoke();
    logMsg("Interceptor has been bound as expected");
  }

  /*
   * @testName: onlyPartOfUnionOfInterceptorsBoundTest
   * 
   * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
   * 
   * @test_Strategy: Similarly, a resource method can be decorated with multiple
   * binding annotations. Each binding annotation instance in a resource method
   * denotes a set of filters and interceptors whose class definitions are
   * decorated with that annotation (possibly among others). The final set of
   * (static) filters and interceptors is the union of all these sets
   * 
   * returning filters or interceptors from the methods getClasses or
   * getSingletons in an application subclass will bind them globally only if
   * they are not decorated with a name binding annotation.
   */
  public void onlyPartOfUnionOfInterceptorsBoundTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "complement"));
    setProperty(Property.SEARCH_STRING, "10000");
    invoke();
    logMsg("No interceptor has been bound as expected");
  }

  /*
   * @testName: readerWriterInterceptorBoundTest
   * 
   * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
   * 
   * @test_Strategy: Similarly, a resource method can be decorated with multiple
   * binding annotations. Each binding annotation instance in a resource method
   * denotes a set of filters and interceptors whose class definitions are
   * decorated with that annotation (possibly among others). The final set of
   * (static) filters and interceptors is the union of all these sets
   * 
   * returning filters or interceptors from the methods getClasses or
   * getSingletons in an application subclass will bind them globally only if
   * they are not decorated with a name binding annotation.
   */
  public void readerWriterInterceptorBoundTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
    setRequestContentEntity("1111");
    setProperty(Property.SEARCH_STRING, "1113");
    invoke();
    logMsg("Reader and Writer interceptor has been bound as expected");
  }

  /*
   * @testName: resourceAnnotatedFirstMethodInterceptedTest
   * 
   * @assertion_ids: JAXRS:SPEC:87; JAXRS:SPEC:88; JAXRS:SPEC:89;
   * 
   * @test_Strategy: Binding annotations that decorate resource classes apply to
   * all the resource methods defined in them. A filter or interceptor class can
   * be decorated with multiple binding annotations.
   * 
   * Similarly, a resource method can be decorated with multiple binding
   * annotations. Each binding annotation instance in a resource method denotes
   * a set of filters and interceptors whose class definitions are decorated
   * with that annotation (possibly among others). The final set of (static)
   * filters and interceptors is the union of all these sets
   * 
   * returning filters or interceptors from the methods getClasses or
   * getSingletons in an application subclass will bind them globally only if
   * they are not decorated with a name binding annotation.
   */
  public void resourceAnnotatedFirstMethodInterceptedTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "all/hundred"));
    setProperty(Property.SEARCH_STRING, "101");
    invoke();
    logMsg("Both name bound interceptors has been bound as expected");
  }

  /*
   * @testName: resourceAnnotatedSecondMethodInterceptedTest
   * 
   * @assertion_ids: JAXRS:SPEC:87; JAXRS:SPEC:88; JAXRS:SPEC:89;
   * 
   * @test_Strategy: Binding annotations that decorate resource classes apply to
   * all the resource methods defined in them. A filter or interceptor class can
   * be decorated with multiple binding annotations.
   * 
   * Similarly, a resource method can be decorated with multiple binding
   * annotations. Each binding annotation instance in a resource method denotes
   * a set of filters and interceptors whose class definitions are decorated
   * with that annotation (possibly among others). The final set of (static)
   * filters and interceptors is the union of all these sets
   * 
   * returning filters or interceptors from the methods getClasses or
   * getSingletons in an application subclass will bind them globally only if
   * they are not decorated with a name binding annotation.
   */
  public void resourceAnnotatedSecondMethodInterceptedTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "all/thousand"));
    setProperty(Property.SEARCH_STRING, "1011");
    invoke();
    logMsg("Both name bound interceptors has been bound as expected");
  }
}
