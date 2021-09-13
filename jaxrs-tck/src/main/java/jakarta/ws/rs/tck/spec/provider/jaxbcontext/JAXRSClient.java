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

package com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext;

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
    setContextRoot("/jaxrs_spec_provider_jaxbcontext_web/resource");
  }

  private void setPropertyAndInvoke(String resourceMethod) throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.APPLICATION_XML_TYPE));
    setProperty(Property.SEARCH_STRING, SomeUnmarshaller.class.getSimpleName());
    setProperty(Property.SEARCH_STRING, SomeMarshaller.class.getSimpleName());
    setProperty(Property.CONTENT, "anything");
    invoke();
  }

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
   * @testName: readWriteProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:34
   * 
   * @test_Strategy: The implementation-supplied entity provider(s) for
   * jakarta.xml.bind.JAXBElement and application supplied JAXB classes MUST use
   * JAXBContext instances provided by application-supplied context resolvers,
   * see Section 4.3.
   */
  public void readWriteProviderTest() throws Fault {
    setPropertyAndInvoke("jaxb");
  }

}
