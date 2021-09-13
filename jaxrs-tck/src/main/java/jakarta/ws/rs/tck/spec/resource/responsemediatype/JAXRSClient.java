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

package com.sun.ts.tests.jaxrs.spec.resource.responsemediatype;

import java.util.List;

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
    setContextRoot("/jaxrs_spec_resource_responsemediatype_web");
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
   * @testName: responseOverrideTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.1;
   * 
   * @test_Strategy: If the method returns an instance of Response whose
   * metadata includes the response media type (Mspecified) then set Mselected =
   * Mspecified, finish.
   */
  public void responseOverrideTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/responseoverride"));
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.APPLICATION_XML_TYPE));
    invoke();
  }

  /*
   * @testName: responseNotAllowedToOverrideTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.1; JAXRS:SPEC:25.6;
   * 
   * @test_Strategy: If the method returns an instance of Response whose
   * metadata includes the response media type (Mspecified) then set Mselected =
   * Mspecified, finish.
   * 
   * There is no way to get method body through Accept/Produces
   */
  public void responseNotAllowedToOverrideTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "resource/responseoverride"));
    setProperty(Property.REQUEST_HEADERS,
        buildAccept(MediaType.APPLICATION_JSON_TYPE));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
    invoke();
  }

  /*
   * @testName: responseOverrideNoProducesTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.1;
   * 
   * @test_Strategy: If the method returns an instance of Response whose
   * metadata includes the response media type (Mspecified) then set Mselected =
   * Mspecified, finish.
   */
  public void responseOverrideNoProducesTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "nomedia/responseoverride"));
    setProperty(Property.REQUEST_HEADERS,
        buildAccept(MediaType.APPLICATION_JSON_TYPE));
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.APPLICATION_XML_TYPE));
    invoke();
  }

  /*
   * @testName: methodProducesTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.2;
   * 
   * @test_Strategy: If the method is annotated with @Produces, set P = V
   * (method) where V (t) represents the values of @Produces on the specified
   * target t.
   */
  public void methodProducesTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/method"));
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.APPLICATION_ATOM_XML_TYPE));
    invoke();
  }

  /*
   * @testName: classProducesTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.3;
   * 
   * @test_Strategy: Else if the class is annotated with @Produces, set P = V
   * (class)..
   */
  public void classProducesTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.HEAD, "resource/class"));
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.TEXT_HTML_TYPE));
    invoke();
  }

  /*
   * @testName: mesageBodyWriterProducesTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.4;
   * 
   * @test_Strategy: Else set P = V (writers) where writers is the set of
   * MessageBodyWriter that support the class of the returned entity object.
   */
  public void mesageBodyWriterProducesTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "nomedia/list"));
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.APPLICATION_SVG_XML_TYPE));
    setProperty(Property.SEARCH_STRING, List.class.getSimpleName());
    invoke();
  }

  /*
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.5; JAXRS:SPEC:26.9;
   * 
   * @test_Strategy: If P = {}, set P = {*\*}. untestable
   */
  public void noProducesTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "nomedia/nothing"));
    setProperty(Property.SEARCH_STRING, "nothing");
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.APPLICATION_OCTET_STREAM_TYPE));
    invoke();
  }

  /*
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.5; JAXRS:SPEC:26.9;
   * 
   * @test_Strategy: If P = {}, set P = {*\*}. untestable
   */
  public void noProducesResponseReturnTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(Request.GET, "nomedia/response"));
    setProperty(Property.SEARCH_STRING, "nothing");
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.APPLICATION_OCTET_STREAM_TYPE));
    invoke();
  }

  // ----------------------------- 26.8 -------------------------------

  /*
   * @testName: noPreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void noPreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    invoke();
  }

  /*
   * @testName: textPreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void textPreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.REQUEST_HEADERS, "Accept: text/*");
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    invoke();
  }

  /*
   * @testName: appPreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void appPreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.REQUEST_HEADERS,
        "Accept: application/*;q=0.9, application/xml;q=0.1");
    setProperty(Property.SEARCH_STRING, MediaType.APPLICATION_XML);
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.APPLICATION_XML_TYPE));
    invoke();
  }

  /*
   * @testName: imagePreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void imagePreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.REQUEST_HEADERS, "Accept: image/*");
    setProperty(Property.SEARCH_STRING, "image/png");
    setProperty(Property.EXPECTED_HEADERS, "Content-Type: image/png");
    invoke();
  }

  /*
   * @testName: clientImagePreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void clientImagePreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.REQUEST_HEADERS,
        "Accept: image/something;q=0.1, image/*;q=0.9");
    setProperty(Property.SEARCH_STRING, "image/png");
    setProperty(Property.EXPECTED_HEADERS, "Content-Type: image/png");
    invoke();
  }

  /*
   * @testName: clientXmlHtmlPreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void clientXmlHtmlPreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.REQUEST_HEADERS,
        "Accept: text/html;q=0.2,text/xml;q=0.9");
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_XML);
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.TEXT_XML_TYPE));
    invoke();
  }

  /*
   * @testName: clientXmlHtmlPreferenceNoWeightOnServerTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void clientXmlHtmlPreferenceNoWeightOnServerTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "resource"));
    setProperty(Property.REQUEST_HEADERS,
        "Accept: text/html;q=0.2,text/xml;q=0.9");
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_XML);
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.TEXT_XML_TYPE));
    invoke();
  }

  /*
   * @testName: clientHtmlXmlPreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void clientHtmlXmlPreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.REQUEST_HEADERS,
        "Accept: text/xml;q=0.3, text/html;q=0.9");
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_HTML);
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.TEXT_HTML_TYPE));
    invoke();
  }

  /*
   * @testName: clientAnyPreferenceTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.8;
   * 
   * @test_Strategy: Sort M in descending order, with a primary key of
   * specificity (n/m > n\* > *\*), a secondary key of q-value and a tertiary
   * key of qs-value.
   */
  public void clientAnyPreferenceTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "weight"));
    setProperty(Property.REQUEST_HEADERS, "Accept: */*;q=0.8, text/xml;q=0.3");
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
    setProperty(Property.EXPECTED_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    invoke();
  }

  // ----------------------------- 26.10 -------------------------------
  // JIRA: JERSEY-1054
  /*
   * @testName: defaultErrorTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.10;
   * 
   * @test_Strategy: Generate a WebApplicationException with a not acceptable
   * response (HTTP 406 status) and no entity. The exception MUST be processed
   * as described in section 3.3.4. Finish.
   */
  public void defaultErrorTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "error"));
    setProperty(Property.REQUEST_HEADERS, "Accept: text/*");
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
    invoke();
  }

  /*
   * @testName: defaultResponseErrorTest
   * 
   * @assertion_ids: JAXRS:SPEC:26; JAXRS:SPEC:26.10;
   * 
   * @test_Strategy: Generate a WebApplicationException with a not acceptable
   * response (HTTP 406 status) and no entity. The exception MUST be processed
   * as described in section 3.3.4. Finish.
   */
  public void defaultResponseErrorTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "error"));
    setProperty(Property.CONTENT, "anything");
    setProperty(Property.REQUEST_HEADERS, "Accept: text/*");
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
    invoke();
  }
}
