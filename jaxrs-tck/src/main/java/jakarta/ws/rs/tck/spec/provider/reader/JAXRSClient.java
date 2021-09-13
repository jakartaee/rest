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

package com.sun.ts.tests.jaxrs.spec.provider.reader;

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
    setContextRoot("/jaxrs_spec_provider_reader_web/resource");
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
   * @testName: noEntityProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:31; JAXRS:SPEC:62.5;
   * 
   * @test_Strategy: An implementation MUST NOT use an entity provider for a
   * media type that is not supported by that provider.
   */
  public void noEntityProviderTest() throws Fault {
    enableAppJava(false);
    String ct = buildContentType(AbstractReader.NO_PROVIDER_MEDIATYPE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.REQUEST_HEADERS, ct);
    setProperty(Property.CONTENT, "meadiaTypeContentTypeTest");
    setProperty(Property.STATUS_CODE,
        getStatusCode(Status.UNSUPPORTED_MEDIA_TYPE));
    invoke();
  }

  /*
   * @testName: meadiaTypeContentTypeTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.1;
   * 
   * @test_Strategy: Obtain the media type of the request. If the request does
   * not contain a Content-Type header then use application/octet-stream
   */
  public void meadiaTypeContentTypeTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_HTML_TYPE));
    setProperty(Property.CONTENT, "meadiaTypeContentTypeTest");
    setProperty(Property.SEARCH_STRING, "meadiaTypeContentTypeTest");
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_HTML);
    invoke();
  }

  /*
   * @testName: meadiaTypeDefaultTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.1;
   * 
   * @test_Strategy: Obtain the media type of the request. If the request does
   * not contain a Content-Type header then use application/octet-stream
   */
  public void meadiaTypeDefaultTest() throws Fault {
    enableAppJava(false);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.UNORDERED_SEARCH_STRING, "meadiaTypeDefaultTest");
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppOctetReader.class.getSimpleName().toUpperCase());
    setProperty(Property.CONTENT, "meadiaTypeDefaultTest");
    invoke();
  }

  /*
   * @testName: iterateAllAppJavaReadersTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.2; JAXRS:SPEC:62.3;
   * JAXRS:SPEC:62.4; JAXRS:SPEC:62.5;
   * 
   * @test_Strategy:
   */
  public void iterateAllAppJavaReadersTest() throws Fault {
    enableAppJava(false);
    MediaType mt = new MediaType("application", "java");
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.CONTENT, "some content");
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppJavaReader.class.getSimpleName());
    setProperty(Property.UNORDERED_SEARCH_STRING,
        WildCardReader.class.getSimpleName());
    setProperty(Property.UNORDERED_SEARCH_STRING,
        WildCardReader.class.getSimpleName().toUpperCase());
    invoke();
  }

  /*
   * @testName: iterateFirstAppJavaReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.2; JAXRS:SPEC:62.3;
   * JAXRS:SPEC:62.4; JAXRS:SPEC:62.5;
   * 
   * @test_Strategy:
   */
  public void iterateFirstAppJavaReaderTest() throws Fault {
    enableAppJava(true);
    MediaType mt = new MediaType("application", "java");
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.CONTENT, "some content");
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppJavaReader.class.getSimpleName());
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppJavaReader.class.getSimpleName().toUpperCase());
    setProperty(Property.UNORDERED_SEARCH_STRING, mt.toString());
    invoke();
  }

  // /////////////////////////////////////////////////////////////////////////

  private void enableAppJava(boolean enable) throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "appjava"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    setProperty(Property.CONTENT, String.valueOf(enable));
    invoke();
  }

}
