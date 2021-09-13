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

package com.sun.ts.tests.jaxrs.spec.provider.sort;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import jakarta.ws.rs.core.MediaType;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

public class JAXRSClient extends JAXRSCommonClient {

  private static final long serialVersionUID = -8228843141906281907L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_provider_sort_web/resource");
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
   * @testName: contentTypeApplicationGotWildCardTest
   * 
   * @assertion_ids: JAXRS:SPEC:30;
   * 
   * @test_Strategy: The absence of these annotations is equivalent to their
   * inclusion with media type (
   *//*
      * ), i.e. absence implies that any media type is supported.
      *
      * Unexpected providers add "text/" to content
      */
  public void contentTypeApplicationGotWildCardTest() throws Fault {
    MediaType type = new MediaType("application", "plain");
    setProperty(Property.REQUEST, buildRequest(Request.POST, ""));
    setProperty(Property.REQUEST_HEADERS, buildContentType(type));
    setProperty(Property.REQUEST_HEADERS, buildAccept(type));
    setProperty(Property.CONTENT, "test");
    setProperty(Property.SEARCH_STRING, "test");
    setProperty(Property.UNEXPECTED_RESPONSE_MATCH, "text");
    invoke();
  }

  /*
   * @testName: contentTypeTextHmtlGotTextWildCardTest
   * 
   * @assertion_ids: JAXRS:SPEC:32;
   * 
   * @test_Strategy: When choosing an entity provider an implementation sorts
   * the available providers according to the media types they declare support
   * for. Sorting of media types follows the general rule: x/y < x/* <
   *//*
      * , i.e. a provider that explicitly lists a media types is sorted before a
      * provider that lists
      *//*
         * .
         */
  public void contentTypeTextHmtlGotTextWildCardTest() throws Fault {
    MediaType type = MediaType.TEXT_HTML_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, ""));
    setProperty(Property.REQUEST_HEADERS, buildContentType(type));
    setProperty(Property.REQUEST_HEADERS, buildAccept(type));
    setProperty(Property.CONTENT, "test");
    setProperty(Property.SEARCH_STRING, "test");
    setProperty(Property.SEARCH_STRING, "text/*");
    invoke();
  }

  /*
   * @testName: contentTypeTextXmlGotTextWildCardTest
   * 
   * @assertion_ids: JAXRS:SPEC:32; JAXRS:SPEC:38;
   * 
   * @test_Strategy: When choosing an entity provider an implementation sorts
   * the available providers according to the media types they declare support
   * for. Sorting of media types follows the general rule: x/y < x/* <
   *//*
      * , i.e. a provider that explicitly lists a media types is sorted before a
      * provider that lists
      *//*
         * .
         */
  public void contentTypeTextXmlGotTextWildCardTest() throws Fault {
    MediaType type = MediaType.TEXT_XML_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, ""));
    setProperty(Property.REQUEST_HEADERS, buildContentType(type));
    setProperty(Property.REQUEST_HEADERS, buildAccept(type));
    setProperty(Property.CONTENT, "test");
    setProperty(Property.SEARCH_STRING, "test");
    setProperty(Property.SEARCH_STRING, "text/*");
    invoke();
  }

  /*
   * @testName: contentTypeTextPlainGotTextPlainTest
   * 
   * @assertion_ids: JAXRS:SPEC:32; JAXRS:SPEC:38;
   * 
   * @test_Strategy: When choosing an entity provider an implementation sorts
   * the available providers according to the media types they declare support
   * for. Sorting of media types follows the general rule: x/y < x/* <
   *//*
      * , i.e. a provider that explicitly lists a media types is sorted before a
      * provider that lists
      *//*
         * .
         */
  public void contentTypeTextPlainGotTextPlainTest() throws Fault {
    MediaType type = MediaType.TEXT_PLAIN_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, ""));
    setProperty(Property.REQUEST_HEADERS, buildContentType(type));
    setProperty(Property.REQUEST_HEADERS, buildAccept(type));
    setProperty(Property.CONTENT, "test");
    setProperty(Property.SEARCH_STRING, "test");
    setProperty(Property.SEARCH_STRING, "text/plain");
    invoke();
  }
}
