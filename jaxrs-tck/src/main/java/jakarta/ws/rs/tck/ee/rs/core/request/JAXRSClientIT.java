/*
 * Copyright (c) 2011, 2021 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.core.request;

import org.apache.commons.httpclient.Header;
import java.util.Properties;
import java.io.File;
import jakarta.ws.rs.tck.common.webclient.http.HttpResponse;
import jakarta.ws.rs.tck.common.JAXRSCommonClient;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.ws.rs.core.Response.Status;

@ExtendWith(ArquillianExtension.class)
public class JAXRSClientIT extends JAXRSCommonClient {

  private static final long serialVersionUID = 1L;

  private static final String IF_MODIFIED_SINCE = "If-Modified-Since: Sat, 29 Oct 1994 19:43:31 GMT";

  private static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since: Sat, 29 Oct 1994 19:43:31 GMT";

  private static final String IF_NONE_MATCH = "If-None-Match: \"AAA\"";

  private static final String servletAdaptor = System.getProperty("servlet_adaptor", "org.glassfish.jersey.servlet.ServletContainer");

  public JAXRSClientIT() {
    setup();
    setContextRoot("/jaxrs_ee_core_request_web/RequestTest");
  }


  @Deployment(testable = false)
  public static WebArchive createDeployment() {    

    //TODO: use web.xml files 
    String webXml = " <web-app version=\"5.0\" xmlns=\"https://jakarta.ee/xml/ns/jakartaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd\">" +
    "<servlet>"+
     "<servlet-name>CTSJAX-RSCOREREQUEST</servlet-name>"+
        "<servlet-class>"+ servletAdaptor + "</servlet-class>"+
        "<init-param>"+
         "   <param-name>jakarta.ws.rs.Application</param-name>"+
          "  <param-value>jakarta.ws.rs.tck.ee.rs.core.request.TSAppConfig</param-value>"+
        "</init-param>"+
        "<load-on-startup>1</load-on-startup>"+
    "</servlet>"+
    "<servlet-mapping>"+
      "  <servlet-name>CTSJAX-RSCOREREQUEST</servlet-name>"+
      "  <url-pattern>/*</url-pattern>"+
    "</servlet-mapping>"+
    "<session-config>"+
     "   <session-timeout>30</session-timeout>"+
    "</session-config>"+
    "</web-app>";


    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_ee_core_request_web.war").addClass(RequestTest.class).addClass(TSAppConfig.class).setWebXML(new StringAsset(webXml));
    //archive.as(ZipExporter.class).exportTo(new File("/temp/jaxrs_ee.war"), true); //check archive deployed locally
    return archive;

  }

  /*
   * @testName: getMethodGetRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:118; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.getMethod works.
   */
  @Test
  public void getMethodGetRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "GetMethodGetTest"));
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: getMethodPutRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:118;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.getMethod works.
   */
  @Test
  public void getMethodPutRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest("PUT", "GetMethodPutTest"));
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: getMethodPostRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:118;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.getMethod works.
   */
  @Test
  public void getMethodPostRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest("POST", "GetMethodPostTest"));
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: getMethodDeleteRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:118;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.getMethod works.
   */
  @Test
  public void getMethodDeleteRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest("DELETE", "GetMethodDeleteTest"));
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: getMethodHeadRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:118;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.getMethod works.
   */
  @Test
  public void getMethodHeadRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest("HEAD", "GetMethodHeadTest"));
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: selectVariantGetRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:119;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.selectVariantTest(List vs) works when vs is null.
   */
  @Test
  public void selectVariantGetRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "SelectVariantTestGet"));
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: selectVariantPutRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:119;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.selectVariantTest(List vs) works when vs is null.
   */
  @Test
  public void selectVariantPutRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest("PUT", "SelectVariantTestPut"));
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: selectVariantPostRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:119;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.selectVariantTest(List vs) works when vs is null.
   */
  @Test
  public void selectVariantPostRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest("POST", "SelectVariantTestPost"));
    setProperty(Property.CONTENT, "POST");
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: selectVariantDeleteRequestTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:119;
   * 
   * @test_Strategy: Client send request to a resource, verify that
   * Request.selectVariantTest(List vs) works when vs is null.
   */
  @Test
  public void selectVariantDeleteRequestTest() throws Fault {
    setProperty(REQUEST, buildRequest("DELETE", "SelectVariantTestDelete"));
    setProperty(SEARCH_STRING, "PASSED");
    invoke();
  }

  /*
   * @testName: selectVariantResponseVaryTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:119; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Check if the response contains VARY
   */
  @Test
  public void selectVariantResponseVaryTest() throws Fault {
    setProperty(Property.REQUEST,
        buildRequest(GET, "SelectVariantTestResponse"));
    setProperty(Property.REQUEST_HEADERS, "Accept: application/json");
    setProperty(Property.REQUEST_HEADERS, "Accept-Encoding: *");
    setProperty(Property.REQUEST_HEADERS, "Accept-Language: *");
    setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
    invoke();

    HttpResponse response = _testCase.getResponse();
    Header[] headers = response.getResponseHeaders("Vary");
    assertTrue(headers.length != 0, "Expected at least 1 Vary response header");

    boolean accept = false, lang = false, encoding = false;
    for (Header header : headers)
      for (String vary : header.getValue().split(",")) {
        lang |= vary.contains("Accept-Language");
        encoding |= vary.contains("Accept-Encoding");
        accept |= (vary.contains("Accept") && !vary.contains("Accept-"));
      }
    assertTrue(lang, "Vary should contain Accept-Language");
    assertTrue(encoding, "Vary should contain Accept-Encoding");
    assertTrue(accept, "Vary should contain Accept");
  }

  /*
   * @testName: evaluatePreconditionsTagNullAndSimpleGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Verify null and Simple Tag for GET
   * 
   */
  @Test
  public void evaluatePreconditionsTagNullAndSimpleGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsSimpleGet"));
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsEntityTagIfMatchAAAGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Match: AAA Tag for GET
   */
  @Test
  public void evaluatePreconditionsEntityTagIfMatchAAAGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsAAAGet"));
    setProperty(REQUEST_HEADERS, "If-Match: \"AAA\"");
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsEntityTagIfMatchBBBGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Match: BBB Tag for GET
   */
  @Test
  public void evaluatePreconditionsEntityTagIfMatchBBBGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsAAAGet"));
    setProperty(REQUEST_HEADERS, "If-Match: \"BBB\"");
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsEntityTagIfMatchAAAPutTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Match: AAA Tag for PUT
   */
  @Test
  public void evaluatePreconditionsEntityTagIfMatchAAAPutTest() throws Fault {
    setProperty(REQUEST, buildRequest("PUT", "preconditionsAAAPut"));
    setProperty(REQUEST_HEADERS, "If-Match: \"AAA\"");
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsEntityTagIfMatchBBBPutTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Match: BBB Tag for PUT
   */
  @Test
  public void evaluatePreconditionsEntityTagIfMatchBBBPutTest() throws Fault {
    setProperty(REQUEST, buildRequest("PUT", "preconditionsAAAPut"));
    setProperty(REQUEST_HEADERS, "If-Match: \"BBB\"");
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfNonMatchAAAGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-None-Match: AAA Tag for
   * GET
   */
  @Test
  public void evaluatePreconditionsTagIfNonMatchAAAGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsAAAGet"));
    setProperty(REQUEST_HEADERS, IF_NONE_MATCH);
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfNonMatchAAAPutTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-None-Match: AAA Tag for
   * PUT
   */
  @Test
  public void evaluatePreconditionsTagIfNonMatchAAAPutTest() throws Fault {
    setProperty(REQUEST, buildRequest("PUT", "preconditionsAAAPut"));
    setProperty(REQUEST_HEADERS, IF_NONE_MATCH);
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfNonMatchGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:298;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-None-Match No Tag for
   * GET
   */
  @Test
  public void evaluatePreconditionsTagIfNonMatchGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsGet"));
    setProperty(REQUEST_HEADERS, IF_NONE_MATCH);
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfNonMatchAAAHeadTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:115;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-None-Match: AAA Tag for
   * HEAD
   */
  @Test
  public void evaluatePreconditionsTagIfNonMatchAAAHeadTest() throws Fault {
    setProperty(REQUEST, buildRequest("HEAD", "preconditionsAAAHead"));
    setProperty(REQUEST_HEADERS, IF_NONE_MATCH);
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsIfNonMatchHeadTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:298;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-None-Match No Tag for
   * HEAD
   */
  @Test
  public void evaluatePreconditionsIfNonMatchHeadTest() throws Fault {
    setProperty(REQUEST, buildRequest("HEAD", "preconditionsHead"));
    setProperty(REQUEST_HEADERS, IF_NONE_MATCH);
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsIfModSinceAgesAgoGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:116;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Modified-Since, but was
   * not modified for GET
   */
  @Test
  public void evaluatePreconditionsIfModSinceAgesAgoGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsAgesAgoGet"));
    setProperty(REQUEST_HEADERS, IF_MODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsIfUnmodSinceAgesAgoGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:116;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Unmodified-Since, but
   * was not modified for GET
   */
  @Test
  public void evaluatePreconditionsIfUnmodSinceAgesAgoGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsAgesAgoGet"));
    setProperty(REQUEST_HEADERS, IF_UNMODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsIfModSinceNowGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:116;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Modified-Since for GET,
   * was modified
   */
  @Test
  public void evaluatePreconditionsIfModSinceNowGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsNowGet"));
    setProperty(REQUEST_HEADERS, IF_MODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsIfUnmodSinceNowGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:116;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Modified-Since for GET,
   * was modified
   */
  @Test
  public void evaluatePreconditionsIfUnmodSinceNowGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsNowGet"));
    setProperty(REQUEST_HEADERS, IF_UNMODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfModAAASinceGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:117;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Modified-Since, but was
   * not modified for GET
   */
  @Test
  public void evaluatePreconditionsTagIfModAAASinceGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsAAAAgesAgoGet"));
    setProperty(REQUEST_HEADERS, IF_MODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfUnmodSinceAAAGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:117;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Unmodified-Since, but
   * was not modified for GET
   */
  @Test
  public void evaluatePreconditionsTagIfUnmodSinceAAAGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsAAAAgesAgoGet"));
    setProperty(REQUEST_HEADERS, IF_UNMODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfModSinceNowAAAGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:117;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Modified-Since for GET,
   * was modified
   */
  @Test
  public void evaluatePreconditionsTagIfModSinceNowAAAGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsNowAAAGet"));
    setProperty(REQUEST_HEADERS, IF_MODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.OK));
    invoke();
  }

  /*
   * @testName: evaluatePreconditionsTagIfUnmodSinceNowAAAGetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:117;
   * 
   * @test_Strategy: Verify evaluatePreconditions for If-Modified-Since for GET,
   * was modified
   */
  @Test
  public void evaluatePreconditionsTagIfUnmodSinceNowAAAGetTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "preconditionsNowAAAGet"));
    setProperty(REQUEST_HEADERS, IF_UNMODIFIED_SINCE);
    setProperty(STATUS_CODE, getStatusCode(Status.PRECONDITION_FAILED));
    invoke();
  }

}