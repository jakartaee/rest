/*
 * Copyright (c) 2024 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.ws.rs.jaxrs40.ee.rs.core.uriinfo;

import ee.jakarta.tck.ws.rs.common.JAXRSCommonClient;
import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 */
@ExtendWith(ArquillianExtension.class)
public class JAXRSClientIT extends JAXRSCommonClient {

  private static final long serialVersionUID = 40L;

  protected static final String ROOT = "jaxrs40_ee_core_uriinfo_web";

  protected static final String RESOURCE = "app/resource";

  public JAXRSClientIT() {
    setup();
    setContextRoot("/" + ROOT + "/" + RESOURCE);
  }


  @BeforeEach
  void logStartTest(TestInfo testInfo) {
    TestUtil.logMsg("STARTING TEST : " + testInfo.getDisplayName());
  }

  @AfterEach
  void logFinishTest(TestInfo testInfo) {
    TestUtil.logMsg("FINISHED TEST : " + testInfo.getDisplayName());
  }

  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException{
    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs40_ee_core_uriinfo_web.war");
    archive.addClasses(TSAppConfig.class, UriInfoTestResource.class);
    return archive;
  }

  /* Run test */

  /**
   * @testName: getMatchedResourceTemplateOneTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:97;
   *
   * @test_Strategy: Check the template containing {@link UriInfoTestResource#ONE_POST}
   */
  @Test
  public void getMatchedResourceTemplateOneTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "one/azazaz00"));
    setProperty(Property.SEARCH_STRING, "/app/resource/one/" + UriInfoTestResource.ONE_POST);
    invoke();
  }

  /**
   * @testName: getMatchedResourceTemplateTwoGetTest
   *
   * @assertion_ids: JAXRS:JAVADOC:97;
   *
   * @test_Strategy: Check the template containing {@link UriInfoTestResource#TWO_GET}
   */
  @Test
  public void getMatchedResourceTemplateTwoGetTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "two/P/abc/MyNumber"));
    setProperty(Property.SEARCH_STRING, "/app/resource/two/" + UriInfoTestResource.TWO_GET);
    invoke();
  }

  /**
   * @testName: getMatchedResourceTemplateTwoPostTest
   *
   * @assertion_ids: JAXRS:JAVADOC:97;
   *
   * @test_Strategy: Check the template containing {@link UriInfoTestResource#TWO_POST}
   */
  @Test
  public void getMatchedResourceTemplateTwoPostTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "two/P/abc/MyNumber"));
    setProperty(Property.SEARCH_STRING, "/app/resource/two/" + UriInfoTestResource.TWO_POST);
    invoke();
  }

  /**
   * @testName: getMatchedResourceTemplateSubTest
   *
   * @assertion_ids: JAXRS:JAVADOC:97;
   *
   * @test_Strategy: Check the template including subresource containing {@link UriInfoTestResource#THREE_SUB}
   */
  @Test
  public void getMatchedResourceTemplateSubTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.PUT, "three/a"));
    setProperty(Property.SEARCH_STRING, "/app/resource/three/" + UriInfoTestResource.THREE_SUB);
    invoke();
  }
}
