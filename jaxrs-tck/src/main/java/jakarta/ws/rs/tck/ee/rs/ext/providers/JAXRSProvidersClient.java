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

package com.sun.ts.tests.jaxrs.ee.rs.ext.providers;

import com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.EnumProvider;
import com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSProvidersClient
    extends com.sun.ts.tests.jaxrs.ee.rs.core.application.JAXRSClient {

  private static final long serialVersionUID = -935293219512493643L;

  public JAXRSProvidersClient() {
    TSAppConfig cfg = new TSAppConfig();
    setContextRoot("/jaxrs_ee_ext_providers_web/ProvidersServlet");
    expectedClasses = cfg.getClasses().size();
    expectedSingletons = cfg.getSingletons().size();
  }

  /**
   * Entry point for different-VM execution. It should delegate to method
   * run(String[], PrintWriter, PrintWriter), and this method should not contain
   * any test configuration.
   */
  public static void main(String[] args) {
    JAXRSProvidersClient theTests = new JAXRSProvidersClient();
    theTests.run(args);
  }

  /* Run test */

  /*
   * @testName: getSingletonsTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:23
   * 
   * @test_Strategy: Check that the implementation returns set of
   * TSAppConfig.CLASSLIST
   */
  public void getSingletonsTest() throws Fault {
    super.getSingletonsTest();
  }

  /*
   * @testName: getClassesTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:22
   * 
   * @test_Strategy: Check the implementation injects TSAppConfig
   */
  public void getClassesTest() throws Fault {
    super.getClassesTest();
  }

  /*
   * @testName: isRegisteredTextPlainContextResolverTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:269; JAXRS:JAVADOC:280; JAXRS:JAVADOC:299;
   * JAXRS:SPEC:40; JAXRS:SPEC:80; JAXRS:SPEC:81;
   * 
   * @test_Strategy: Register ContextResolver and try to get proper Provider
   * 
   * When injecting an instance of one of the types listed in section 9.2, the
   * instance supplied MUST be capable of selecting the correct context for a
   * particular request.
   * 
   * Context providers MAY return null from the getContext method if they do not
   * wish to provide their context for a particular Java type.
   * 
   * Context provider implementations MAY restrict the media types they support
   * using the @Produces annotation.
   */
  public void isRegisteredTextPlainContextResolverTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredTextPlainContextResolver"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: isRegisteredAppJsonContextResolverTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:269; JAXRS:JAVADOC:280; JAXRS:JAVADOC:299;
   * JAXRS:SPEC:40; JAXRS:SPEC:80; JAXRS:SPEC:81;
   * 
   * @test_Strategy: Register ContextResolver and try to get proper Provider
   * 
   * When injecting an instance of one of the types listed in section 9.2, the
   * instance supplied MUST be capable of selecting the correct context for a
   * particular request.
   * 
   * Context providers MAY return null from the getContext method if they do not
   * wish to provide their context for a particular Java type.
   * 
   * Context provider implementations MAY restrict the media types they support
   * using the @Produces annotation. Absence implies that any media type is
   * supported.
   */
  public void isRegisteredAppJsonContextResolverTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredAppJsonContextResolver"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: isRegisteredExceptionMapperRuntimeExceptionTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:270; JAXRS:JAVADOC:281; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Try to get proper ExceptionMapper
   */
  public void isRegisteredExceptionMapperRuntimeExceptionTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredExceptionMapperRuntimeEx"));
    setProperty(Property.STATUS_CODE,
        getStatusCode(Status.INTERNAL_SERVER_ERROR));
    invoke();
  }

  /*
   * @testName: isRegisteredExceptionMapperNullExceptionTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:281;
   * 
   * @test_Strategy: Try to get proper ExceptionMapper
   */
  public void isRegisteredExceptionMapperNullExceptionTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredExceptionMapperNullEx"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
    invoke();
  }

  /*
   * @testName: isRegisteredRuntimeExceptionExceptionMapperTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:281; JAXRS:JAVADOC:300; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Try to get RuntimeExceptionExceptionMapper but there is
   * none
   */
  public void isRegisteredRuntimeExceptionExceptionMapperTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredRuntimeExceptionMapper"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: isRegisteredIOExceptionExceptionMapperTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:281;
   * 
   * @test_Strategy: Try to get IOExceptionExceptionMapper
   */
  public void isRegisteredIOExceptionExceptionMapperTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredIOExceptionMapper"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
    invoke();
  }

  /*
   * @testName: isRegisteredMessageBodyWriterWildcardTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:283;
   * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Check what is returned for wildcard is for text/plain
   */
  public void isRegisteredMessageBodyWriterWildcardTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredWriterWildcard"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: isRegisteredMessageBodyWriterXmlTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:283;
   * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Check BodyWriter is returned for text/xml
   */
  public void isRegisteredMessageBodyWriterXmlTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredMessageWriterXml"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: isRegisteredMessageBodyReaderWildcardTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:282;
   * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Check what is returned for wildcard is for text/plain
   */
  public void isRegisteredMessageBodyReaderWildcardTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredMessageReaderWildCard"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: isRegisteredMessageBodReaderXmlTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:282;
   * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Check BodyReader is returned for text/xml
   */
  public void isRegisteredMessageBodReaderXmlTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "isRegisteredMessageReaderXml"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: writeBodyEntityUsingWriterTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:283;
   * JAXRS:JAVADOC:132; JAXRS:JAVADOC:275; JAXRS:JAVADOC:276; JAXRS:JAVADOC:304;
   * 
   * @test_Strategy: Check BodyWriter is used for text/xml to write entity
   */
  public void writeBodyEntityUsingWriterTest() throws Fault {
    String ename = EnumProvider.JAXRS.name();
    String search = new ReadableWritableEntity(ename).toXmlString();
    setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST,
        buildRequest(GET, "writeBodyEntityUsingWriter"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    setProperty(Property.SEARCH_STRING, search);
    invoke();
  }

  /*
   * @testName: writeHeaderEntityUsingWriterTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:132;
   * JAXRS:JAVADOC:275; JAXRS:JAVADOC:277; JAXRS:JAVADOC:304;
   * 
   * @test_Strategy: Check HeaderWriter is used for text/xml to write entity
   */
  public void writeHeaderEntityUsingWriterTest() throws Fault {
    String ename = EnumProvider.JAXRS.name();
    String search = new ReadableWritableEntity(ename).toXmlString();
    setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST,
        buildRequest(GET, "writeHeaderEntityUsingWriter"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    setProperty(Property.EXPECTED_HEADERS,
        ReadableWritableEntity.NAME + ":" + search);
    invoke();
  }

  /*
   * @testName: writeIOExceptionUsingWriterTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:281; JAXRS:JAVADOC:304; JAXRS:JAVADOC:87;
   * JAXRS:JAVADOC:132; JAXRS:JAVADOC:277; JAXRS:JAVADOC:278;
   * 
   * @test_Strategy: Check EntityWriter is used and IOException is written using
   * mapper
   */
  public void writeIOExceptionUsingWriterTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST,
        buildRequest(GET, "writeIOExceptionUsingWriter"));
    // Depending whether the response has been committed
    setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
    setProperty(Property.STATUS_CODE,
        getStatusCode(Status.INTERNAL_SERVER_ERROR));
    invoke();
  }

  /*
   * @testName: writeIOExceptionWithoutWriterTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:304; JAXRS:JAVADOC:281; JAXRS:SPEC:16.2;
   * 
   * @test_Strategy: Check IOExceptionExceptionMapper is chosen
   */
  public void writeIOExceptionWithoutWriterTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST,
        buildRequest(GET, "writeIOExceptionWithoutWriter"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
    invoke();
  }

  /*
   * @testName: readEntityFromHeaderTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:JAVADOC:138;
   * JAXRS:JAVADOC:304; JAXRS:JAVADOC:282;
   * 
   * @test_Strategy: Put entity to header and read it using reader
   */
  public void readEntityFromHeaderTest() throws Fault {
    ReadableWritableEntity entity;
    entity = new ReadableWritableEntity(EnumProvider.JAXRS.name());
    String header = ReadableWritableEntity.NAME + ":" + entity.toXmlString();
    setProperty(Property.REQUEST_HEADERS,
        "Content-Type: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST_HEADERS, header);
    setProperty(Property.REQUEST, buildRequest("POST", "readEntityFromHeader"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: readEntityFromBodyTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:JAVADOC:138;
   * JAXRS:JAVADOC:304; JAXRS:JAVADOC:282;
   * 
   * @test_Strategy: Put entity to body and read it using reader
   */
  public void readEntityFromBodyTest() throws Fault {
    ReadableWritableEntity entity;
    entity = new ReadableWritableEntity(EnumProvider.JAXRS.name());
    setProperty(Property.REQUEST_HEADERS,
        "Content-Type: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST, buildRequest("POST", "readEntityFromBody"));
    setProperty(Property.CONTENT, entity.toXmlString());
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: readEntityIOExceptionTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:273; JAXRS:JAVADOC:138; JAXRS:JAVADOC:304;
   * JAXRS:JAVADOC:282; JAXRS:JAVADOC:271; JAXRS:JAVADOC:272;
   * 
   * @test_Strategy: Put entity to body and read it using reader
   */
  public void readEntityIOExceptionTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS,
        "Content-Type: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST,
        buildRequest("POST", "readEntityIOException"));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
    invoke();
  }

  /*
   * @testName: readEntityWebException400Test
   * 
   * @assertion_ids: JAXRS:JAVADOC:274; JAXRS:JAVADOC:138; JAXRS:JAVADOC:304;
   * JAXRS:JAVADOC:282; JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:SPEC:16.2;
   * 
   * @test_Strategy: Put entity to body and read it using reader
   */
  public void readEntityWebException400Test() throws Fault {
    String code = ReadableWritableEntity.NAME + ":" + Status.BAD_REQUEST.name();
    setProperty(Property.REQUEST_HEADERS,
        "Content-Type: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST,
        buildRequest("POST", "readEntityWebException"));
    setProperty(Property.REQUEST_HEADERS, code);
    setProperty(Property.STATUS_CODE, getStatusCode(Status.BAD_REQUEST));
    invoke();
  }

  /*
   * @testName: readEntityWebException410Test
   * 
   * @assertion_ids: JAXRS:JAVADOC:274; JAXRS:JAVADOC:138; JAXRS:JAVADOC:304;
   * JAXRS:JAVADOC:282; JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:SPEC:16.2;
   * 
   * @test_Strategy: Put entity to body and read it using reader
   */
  public void readEntityWebException410Test() throws Fault {
    String code = ReadableWritableEntity.NAME + ":" + Status.GONE.name();
    setProperty(Property.REQUEST_HEADERS,
        "Content-Type: " + MediaType.TEXT_XML);
    setProperty(Property.REQUEST,
        buildRequest("POST", "readEntityWebException"));
    setProperty(Property.REQUEST_HEADERS, code);
    setProperty(Property.STATUS_CODE, getStatusCode(Status.GONE));
    invoke();
  }

}
