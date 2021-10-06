/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.container.resourceinfo;

import jakarta.ws.rs.tck.common.client.JaxrsCommonClient;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 *
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = -2900337741491627385L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_ee_rs_container_resourceinfo_web/resource");
    setPrintEntity(true);
  }

  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /*
   * @testName: getResourceClassTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:721;
   * 
   * @test_Strategy: Get the resource class that is the target of a request
   */
  public void getResourceClassTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "clazz"));
    setProperty(Property.SEARCH_STRING, Resource.class.getName());
    invoke();
    logMsg("Found expected resource class name");
  }

  /*
   * @testName: getResourceMethodTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:722;
   * 
   * @test_Strategy: Get the resource method that is the target of a request
   */
  public void getResourceMethodTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "method"));
    setProperty(Property.SEARCH_STRING, "getResourceMethod");
    invoke();
    logMsg("Found expected resource method name");
  }
}
