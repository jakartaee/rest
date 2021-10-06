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

package jakarta.ws.rs.tck.ee.rs.container.requestcontext.security;

import java.util.Properties;

import jakarta.ws.rs.tck.common.client.JaxrsCommonClient;

import jakarta.ws.rs.core.Response.Status;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 *                     user;
 *                     password;                     
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = -3020219607348263568L;

  protected String user;

  protected String password;

  public JAXRSClient() {
    setContextRoot(
        "/jaxrs_ee_rs_container_requestcontext_security_web/resource");
  }

  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  public void setup(String[] args, Properties p) throws Fault {
    user = p.getProperty("user");
    password = p.getProperty("password");
    assertFault(!isNullOrEmpty(user), "user was not in build.proerties");
    assertFault(!isNullOrEmpty(password),
        "password was not in build.proerties");
    super.setup(args, p);
  }

  /*
   * @testName: getSecurityContextTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:664;
   * 
   * @test_Strategy: Get the injectable security context information for the
   * current request, the user is authenticated.
   */
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
  public void noSecurityTest() throws Fault {
    String request = buildRequest(Request.POST, "");
    setProperty(Property.REQUEST, request);
    setProperty(Property.STATUS_CODE, getStatusCode(Status.UNAUTHORIZED));
    invoke();
  }

  // ////////////////////////////////////////////////////////////////////////////

}
