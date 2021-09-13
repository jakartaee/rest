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

package com.sun.ts.tests.jaxrs.spec.provider.overridestandard;

import java.util.zip.Deflater;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import jakarta.ws.rs.core.MediaType;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

public class JAXRSClient extends JAXRSCommonClient {

  private static final long serialVersionUID = 1L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_provider_overridestandard_web/resource");
  }

  private void setPropertyAndInvoke(String resourceMethod, MediaType md)
      throws Fault {
    String ct = buildContentType(md);
    setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
    setProperty(Property.REQUEST_HEADERS, ct);
    setProperty(Property.REQUEST_HEADERS, buildAccept(md));
    setProperty(Property.CONTENT, resourceMethod);
    setProperty(Property.SEARCH_STRING_IGNORE_CASE,
        "Tck" + resourceMethod + "Reader");
    setProperty(Property.SEARCH_STRING_IGNORE_CASE,
        "Tck" + resourceMethod + "Writer");
    invoke();
  }

  void setPropertyAndInvokeEncoded(String resourceMethod) throws Fault {
    byte[] buffer = new byte[20];
    java.util.zip.Deflater deflater = new Deflater();
    deflater.setInput(resourceMethod.getBytes());
    deflater.finish();
    deflater.deflate(buffer);

    setProperty(Property.REQUEST_HEADERS, "Transfer-Encoding: deflate");
    setProperty(Property.REQUEST_HEADERS, "Transfer-Encoding: chunked");
    setProperty(Property.CONTENT, new String(buffer));
    setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
    setProperty(Property.STATUS_CODE, "501");
    invoke();
  }

  String[] methodsAll = { "bytearray", "string", "inputstream", "reader",
      "file", "datasource", "source", "jaxb", "streamingoutput" };

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
   * @testName: readWriteByteArrayProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteByteArrayProviderTest() throws Fault {
    setPropertyAndInvoke("bytearray", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteStringProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteStringProviderTest() throws Fault {
    setPropertyAndInvoke("string", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteInputStreamProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteInputStreamProviderTest() throws Fault {
    setPropertyAndInvoke("inputstream", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteReaderProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteReaderProviderTest() throws Fault {
    setPropertyAndInvoke("reader", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteFileProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteFileProviderTest() throws Fault {
    setPropertyAndInvoke("file", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteDataSourceProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteDataSourceProviderTest() throws Fault {
    setPropertyAndInvoke("datasource", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteSourceProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteSourceProviderTest() throws Fault {
    setPropertyAndInvoke("source", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteJaxbProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteJaxbProviderTest() throws Fault {
    setPropertyAndInvoke("jaxb", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteStreamingOutputProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteStreamingOutputProviderTest() throws Fault {
    setPropertyAndInvoke("streamingoutput", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteMapProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteMapProviderTest() throws Fault {
    setPropertyAndInvoke("map", MediaType.APPLICATION_FORM_URLENCODED_TYPE);
  }

  /*
   * @testName: readWriteBooleanProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteBooleanProviderTest() throws Fault {
    MediaType mt = MediaType.TEXT_PLAIN_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, "boolean"));
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.REQUEST_HEADERS, buildAccept(mt));
    setProperty(Property.CONTENT, "false");
    setProperty(Property.SEARCH_STRING_IGNORE_CASE, "true");
    invoke();
  }

  /*
   * @testName: readWriteCharacterProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteCharacterProviderTest() throws Fault {
    MediaType mt = MediaType.TEXT_PLAIN_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, "character"));
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.REQUEST_HEADERS, buildAccept(mt));
    setProperty(Property.CONTENT, "a");
    setProperty(Property.SEARCH_STRING_IGNORE_CASE, "c");
    invoke();
  }

  /*
   * @testName: readWriteIntegerProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request.
   * 
   */
  public void readWriteIntegerProviderTest() throws Fault {
    MediaType mt = MediaType.TEXT_PLAIN_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, "number"));
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.REQUEST_HEADERS, buildAccept(mt));
    setProperty(Property.CONTENT, "0");
    setProperty(Property.SEARCH_STRING_IGNORE_CASE, "2");
    invoke();
  }

  ////////////////////////////////////////////////////////////////////////
  /*
   * @assertion_ids: JAXRS:SPEC:36
   * 
   * @test_Strategy: MessageBodyReader providers always operate on the decoded
   * HTTP entity body rather than directly on the HTTP message body.
   *
   * This depends on the container public void encodedEntityTest() throws Fault
   * { for (String method : methodsAll) setPropertyAndInvokeEncoded(method); }
   */

}
