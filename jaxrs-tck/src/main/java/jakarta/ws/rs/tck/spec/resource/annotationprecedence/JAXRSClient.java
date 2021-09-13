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

package com.sun.ts.tests.jaxrs.spec.resource.annotationprecedence;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JAXRSCommonClient {

  private static final long serialVersionUID = 1L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_resource_annotationprecedence_web/resource");
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
   * @testName: correctTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface.
   */
  public void correctTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post"));
    invoke();
  }

  /*
   * @testName: incorrectPathOnClassTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (@Path)
   */
  public void incorrectPathOnClassTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post")
        .replace("/resource", "/interfaceresource"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_FOUND));
    invoke();
  }

  /*
   * @testName: incorrectPathOnMethodTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (@Path)
   */
  public void incorrectPathOnMethodTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "get"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_FOUND));
    invoke();
  }

  /*
   * @testName: correctRequestTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (@GET)
   */
  public void correctRequestTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "post"));
    setProperty(Property.STATUS_CODE, "!" + getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: incorrectConsumesTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (Content-Type)
   */
  public void incorrectConsumesTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    setProperty(Property.STATUS_CODE,
        getStatusCode(Status.UNSUPPORTED_MEDIA_TYPE));
    invoke();
  }

  /*
   * @testName: incorrectProdecesTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (Accept)
   */
  public void incorrectProdecesTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post"));
    setProperty(Property.REQUEST_HEADERS,
        buildAccept(MediaType.TEXT_PLAIN_TYPE));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
    invoke();
  }

  /*
   * @testName: incorrectProducesConsumesTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (Accept, Content-type)
   */
  public void incorrectProducesConsumesTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post"));
    setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_XML_TYPE));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: formParamTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (formparam=pqr)
   */
  public void formParamTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post"));
    setProperty(Property.CONTENT, "pqr=hello");
    setProperty(Property.SEARCH_STRING, "default");
    setProperty(Property.UNEXPECTED_RESPONSE_MATCH, "hello");
    invoke();
  }

  /*
   * @testName: queryParamXyzTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (queryParam=xyz)
   */
  public void queryParamXyzTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post?xyz=hello"));
    setProperty(Property.SEARCH_STRING, "default");
    setProperty(Property.UNEXPECTED_RESPONSE_MATCH, "hello");
    invoke();
  }

  /*
   * @testName: queryParamPqrTest
   * 
   * @assertion_ids: JAXRS:SPEC:23;
   * 
   * @test_Strategy: Annotations on a super-class take precedence over those on
   * an implemented interface. (queryParam=pqr)
   */
  public void queryParamPqrTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post?pqr=hello"));
    setProperty(Property.SEARCH_STRING, "hello");
    invoke();
  }
}
