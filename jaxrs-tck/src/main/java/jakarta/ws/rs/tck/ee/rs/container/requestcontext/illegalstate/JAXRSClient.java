/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.container.requestcontext.illegalstate;

import jakarta.ws.rs.tck.common.client.JaxrsCommonClient;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = -8112756483664393579L;

  public JAXRSClient() {
    setContextRoot(
        "/jaxrs_ee_rs_container_requestcontext_illegalstate_web/resource");
    setPrintEntity(true);
  }

  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /*
   * @testName: setMethodTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:669; JAXRS:SPEC:85;
   * 
   * @test_Strategy: Throws IllegalStateException - in case the method is not
   * invoked from a pre-matching request filter.
   */
  public void setMethodTest() throws Fault {
    setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
    invokeRequestAndCheckResponse(ContextOperation.SETMETHOD);
  }

  /*
   * @testName: setRequestUriOneUriTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:672; JAXRS:SPEC:85;
   * 
   * @test_Strategy: Trying to invoke the method in a filter bound to a resource
   * method results in an IllegalStateException being thrown.
   * 
   * ContainerRequestContext.abortWith
   */
  public void setRequestUriOneUriTest() throws Fault {
    setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
    invokeRequestAndCheckResponse(ContextOperation.SETREQUESTURI1);
  }

  /*
   * @testName: setRequestUriTwoUrisTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:674; JAXRS:SPEC:85;
   * 
   * @test_Strategy: Trying to invoke the method in a filter bound to a resource
   * method results in an IllegalStateException being thrown.
   * 
   * ContainerRequestContext.abortWith
   */
  public void setRequestUriTwoUrisTest() throws Fault {
    setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
    invokeRequestAndCheckResponse(ContextOperation.SETREQUESTURI2);
  }

  /*
   * @testName: abortWithTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:649;
   * 
   * @test_Strategy: throws IllegalStateException in case the method is invoked
   * from a response filter.
   */
  public void abortWithTest() throws Fault {
    setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
    invokeRequestAndCheckResponse(ContextOperation.ABORTWITH);
  }

  /*
   * @testName: setEntityStreamTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:668;
   * 
   * @test_Strategy: throws IllegalStateException in case the method is invoked
   * from a response filter.
   */
  public void setEntityStreamTest() throws Fault {
    setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
    invokeRequestAndCheckResponse(ContextOperation.SETENTITYSTREAM);
  }

  /*
   * @testName: setSecurityContextTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:676;
   * 
   * @test_Strategy: throws IllegalStateException in case the method is invoked
   * from a response filter.
   */
  public void setSecurityContextTest() throws Fault {
    setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
    invokeRequestAndCheckResponse(ContextOperation.SETSECURITYCONTEXT);
  }

  // ////////////////////////////////////////////////////////////////////////////

  protected void invokeRequestAndCheckResponse(ContextOperation operation)
      throws Fault {
    String op = operation.name();
    String request = buildRequest(Request.GET, op.toLowerCase());
    String header = RequestFilter.OPERATION + ":" + op;
    setProperty(Property.REQUEST, request);
    setProperty(Property.REQUEST_HEADERS, header);
    invoke();
  }
}
