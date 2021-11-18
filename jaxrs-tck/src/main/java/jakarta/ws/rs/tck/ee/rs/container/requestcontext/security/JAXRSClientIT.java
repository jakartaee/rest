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

package jakarta.ws.rs.tck.ee.rs.container.requestcontext.security;

import java.io.InputStream;
import java.io.IOException;
import jakarta.ws.rs.tck.lib.util.TestUtil;

import jakarta.ws.rs.tck.common.client.JaxrsCommonClient;

import jakarta.ws.rs.core.Response.Status;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 *                     user;
 *                     password;                     
 */
@ExtendWith(ArquillianExtension.class)
public class JAXRSClientIT extends JaxrsCommonClient {

  private static final long serialVersionUID = -3020219607348263568L;

  protected String user;

  protected String password;

  public JAXRSClientIT() {
    usersetup();
    setContextRoot("/jaxrs_ee_rs_container_requestcontext_security_web/resource");
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

    InputStream inStream = JAXRSClientIT.class.getClassLoader().getResourceAsStream("jakarta/ws/rs/tck/ee/rs/container/requestcontext/security/web.xml.template");
    String webXml = editWebXmlString(inStream);

    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_ee_rs_container_requestcontext_security_web.war");
    archive.addClasses(TSAppConfig.class, Resource.class, RequestFilter.class);
    archive.addAsWebInfResource("jakarta/ws/rs/tck/ee/rs/container/requestcontext/security/jaxrs_ee_rs_container_requestcontext_security_web.war.sun-web.xml", "sun-web.xml");
    archive.setWebXML(new StringAsset(webXml));
    return archive;

  }

  public void usersetup() {
    user = System.getProperty("user");
    password = System.getProperty("password");
    assertTrue(!isNullOrEmpty(user), "user not set");
    assertTrue(!isNullOrEmpty(password),
        "password not set");
    super.setup();
  }

  /*
   * @testName: getSecurityContextTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:664;
   * 
   * @test_Strategy: Get the injectable security context information for the
   * current request, the user is authenticated.
   */
  @Test
  public void getSecurityContextTest() throws Fault {
    setProperty(Property.BASIC_AUTH_USER, user);
    setProperty(Property.BASIC_AUTH_PASSWD, password);
    setProperty(Property.SEARCH_STRING, user);
    String request = buildRequest(Request.POST, "");
    setProperty(Property.REQUEST, request);
    invoke();
  }

  /*
   * @testName: noSecurityTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:664;
   * 
   * @test_Strategy: Make sure the authorization is needed
   */
  @Test
  public void noSecurityTest() throws Fault {
    String request = buildRequest(Request.POST, "");
    setProperty(Property.REQUEST, request);
    setProperty(Property.STATUS_CODE, getStatusCode(Status.UNAUTHORIZED));
    invoke();
  }

  // ////////////////////////////////////////////////////////////////////////////

}
