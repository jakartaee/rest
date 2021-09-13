/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.provider.standardwithxmlbinding;

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
    setContextRoot("/jaxrs_spec_provider_standardwithxmlbinding_web/resource");
  }

  private void setPropertyAndInvoke(String resourceMethod, MediaType md)
      throws Fault {
    String ct = buildContentType(md);
    setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
    if (!MediaType.WILDCARD_TYPE.equals(md))
      setProperty(Property.EXPECTED_HEADERS, ct);
    setProperty(Property.REQUEST_HEADERS, ct);
    setProperty(Property.REQUEST_HEADERS, buildAccept(md));
    invoke();
  }

  private void setPropertyAndInvokeXml(String resourceMethod, MediaType md)
      throws Fault {
    setProperty(Property.CONTENT, "<tag>" + resourceMethod + "</tag>");
    setProperty(Property.SEARCH_STRING, resourceMethod);
    setPropertyAndInvoke(resourceMethod, md);
  }

  private void setPropertyAndInvokeXml(String method) throws Fault {
    setPropertyAndInvokeXml(method, MediaType.TEXT_XML_TYPE);
    setPropertyAndInvokeXml(method, MediaType.APPLICATION_XML_TYPE);
    setPropertyAndInvokeXml(method, MediaType.APPLICATION_ATOM_XML_TYPE);
    setPropertyAndInvokeXml(method, MediaType.APPLICATION_SVG_XML_TYPE);
    setPropertyAndInvokeXml(method, new MediaType("application", "*+xml"));
  }

  protected String getAbsoluteUrl() {
    StringBuilder sb = new StringBuilder();
    sb.append("http://").append(_hostname).append(":").append(_port)
        .append(getContextRoot());
    return sb.toString();
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
   * @testName: jaxbElementProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.8;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   * jakarta.xml.bind.JAXBElement and application-supplied JAXB classes XML media
   * types (text/xml, application/xml and application/*+xml)
   */
  public void jaxbElementProviderTest() throws Fault {
    setPropertyAndInvokeXml("jaxb");
  }

}
