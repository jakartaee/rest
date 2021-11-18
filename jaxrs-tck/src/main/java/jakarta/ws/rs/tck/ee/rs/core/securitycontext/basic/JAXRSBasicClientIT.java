/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.core.securitycontext.basic;

import java.io.InputStream;
import java.io.IOException;
import jakarta.ws.rs.tck.lib.util.TestUtil;
import jakarta.ws.rs.tck.ee.rs.core.securitycontext.TestServlet;
import jakarta.ws.rs.tck.ee.rs.core.securitycontext.TestServlet.Scheme;

import jakarta.ws.rs.core.Response;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     user;
 *                     password;
 *                     authuser;
 *                     authpassword;
 */
@ExtendWith(ArquillianExtension.class)
public class JAXRSBasicClientIT
    extends jakarta.ws.rs.tck.ee.rs.core.securitycontext.JAXRSClient {

  private static final long serialVersionUID = 340277879725875946L;

  public JAXRSBasicClientIT() {
    setup();
    setContextRoot("/jaxrs_ee_core_securitycontext_basic_web/Servlet");
  }

  @BeforeEach
  void logStartTest(TestInfo testInfo) {
    TestUtil.logMsg("STARTING TEST : "+testInfo.getDisplayName());
  }

  @AfterEach
  void logFinishTest(TestInfo testInfo) {
    TestUtil.logMsg("FINISHED TEST : "+testInfo.getDisplayName());
  }

  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException {

    InputStream inStream = JAXRSBasicClientIT.class.getClassLoader().getResourceAsStream("jakarta/ws/rs/tck/ee/rs/core/securitycontext/basic/web.xml.template");
    String webXml = editWebXmlString(inStream);

    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_ee_core_securitycontext_basic_web.war");
    archive.addClasses(jakarta.ws.rs.tck.ee.rs.core.securitycontext.TSAppConfig.class, 
      jakarta.ws.rs.tck.ee.rs.core.securitycontext.TestServlet.class,
      jakarta.ws.rs.tck.ee.rs.core.securitycontext.TestServlet.Security.class,
      jakarta.ws.rs.tck.ee.rs.core.securitycontext.TestServlet.Scheme.class,
      jakarta.ws.rs.tck.ee.rs.core.securitycontext.TestServlet.Role.class);
    archive.setWebXML(new StringAsset(webXml));
    archive.addAsWebInfResource("jakarta/ws/rs/tck/ee/rs/core/securitycontext/basic/jaxrs_ee_core_securitycontext_basic_web.war.sun-web.xml", "sun-web.xml");
    return archive;

  }

  /* Run test */

  /*
   * @testName: noAuthorizationTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Send no authorization, make sure of 401 response
   */
  @Test
  public void noAuthorizationTest() throws Fault {
    super.noAuthorizationTest();
  }

  /*
   * @testName: basicAuthorizationAdminTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:169; JAXRS:JAVADOC:170; JAXRS:JAVADOC:171;
   * JAXRS:JAVADOC:172; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Send basic authorization, check security context
   */
  @Test
  public void basicAuthorizationAdminTest() throws Fault {
    setProperty(Property.STATUS_CODE, getStatusCode(Response.Status.OK));
    setProperty(Property.BASIC_AUTH_USER, user);
    setProperty(Property.BASIC_AUTH_PASSWD, password);

    setProperty(Property.SEARCH_STRING, TestServlet.Security.UNSECURED.name());
    setProperty(Property.SEARCH_STRING, TestServlet.Role.DIRECTOR.name());
    setProperty(Property.SEARCH_STRING, user);
    setProperty(Property.SEARCH_STRING, TestServlet.Scheme.BASIC.name());
    invokeRequest();
  }

  /*
   * @testName: basicAuthorizationIncorrectUserTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Send basic authorization, check security context
   */
  @Test
  public void basicAuthorizationIncorrectUserTest() throws Fault {
    setProperty(Property.STATUS_CODE,
        getStatusCode(Response.Status.UNAUTHORIZED));
    setProperty(Property.BASIC_AUTH_USER, Scheme.NOSCHEME.name());
    setProperty(Property.BASIC_AUTH_PASSWD, password);
    invokeRequest();
  }

  /*
   * @testName: basicAuthorizationIncorrectPasswordTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Send basic authorization, check security context
   */
  @Test
  public void basicAuthorizationIncorrectPasswordTest() throws Fault {
    setProperty(Property.STATUS_CODE,
        getStatusCode(Response.Status.UNAUTHORIZED));
    setProperty(Property.BASIC_AUTH_USER, authuser);
    setProperty(Property.BASIC_AUTH_PASSWD, password);
    invokeRequest();
  }

  /*
   * @testName: basicAuthorizationStandardUserTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:169; JAXRS:JAVADOC:170; JAXRS:JAVADOC:171;
   * JAXRS:JAVADOC:172; JAXRS:SPEC:40;
   * 
   * @test_Strategy: Send basic authorization with made up Realm, check security
   * context
   */
  @Test
  public void basicAuthorizationStandardUserTest() throws Fault {
    setProperty(Property.STATUS_CODE, getStatusCode(Response.Status.OK));
    setProperty(Property.BASIC_AUTH_USER, authuser);
    setProperty(Property.BASIC_AUTH_PASSWD, authpassword);

    setProperty(Property.SEARCH_STRING, TestServlet.Security.UNSECURED.name());
    setProperty(Property.SEARCH_STRING, TestServlet.Role.OTHERROLE.name());
    setProperty(Property.SEARCH_STRING, authuser);
    setProperty(Property.SEARCH_STRING, TestServlet.Scheme.BASIC.name());
    invokeRequest();
  }
}
