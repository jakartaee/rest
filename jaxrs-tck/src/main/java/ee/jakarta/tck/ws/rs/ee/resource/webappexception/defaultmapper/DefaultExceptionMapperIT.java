/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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

package ee.jakarta.tck.ws.rs.ee.resource.webappexception.defaultmapper;

import java.io.IOException;
import java.io.InputStream;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import ee.jakarta.tck.ws.rs.common.JAXRSCommonClient;
import ee.jakarta.tck.ws.rs.lib.util.TestUtil;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@ExtendWith(ArquillianExtension.class)
public class DefaultExceptionMapperIT extends JAXRSCommonClient {

  public DefaultExceptionMapperIT() {
    setContextRoot("/jaxrs_resource_webappexception_defaultmapper_web/resource");
  }

  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException {

    InputStream inStream = DefaultExceptionMapperIT.class.getClassLoader().getResourceAsStream("ee/jakarta/tck/ws/rs/ee/resource/webappexception/defaultmapper/web.xml.template");
    // Replace the servlet_adaptor in web.xml.template with the System variable set as servlet adaptor
    String webXml = editWebXmlString(inStream);

    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_resource_webappexception_defaultmapper_web.war");
    archive.addClasses(
        TSAppConfig.class, 
        Resource.class, 
        OverriddenDefaultExceptionMapper.class
    );
    archive.setWebXML(new StringAsset(webXml));
    
    return archive;
  }

  @BeforeEach
  void logStartTest(TestInfo testInfo) {
    super.setup();
    TestUtil.logMsg("STARTING TEST : "+testInfo.getDisplayName());
  }

  @AfterEach
  void logFinishTest(TestInfo testInfo) {
    TestUtil.logMsg("FINISHED TEST : "+testInfo.getDisplayName());
  }

  /*
   * @testName: overrideDefaultExceptionMapperTest
   * 
   * @test_Strategy: The default ExceptionMapper must be able to be overridden.
   */
  @Test
  public void overrideDefaultExceptionMapperTest() throws Fault {
    setProperty(REQUEST, buildRequest(GET, "throwable"));
    setProperty(STATUS_CODE, "512");
    invoke();
  }
}