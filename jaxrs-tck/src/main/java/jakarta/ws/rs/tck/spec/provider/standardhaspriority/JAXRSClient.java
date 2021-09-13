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

package com.sun.ts.tests.jaxrs.spec.provider.standardhaspriority;

import javax.xml.namespace.QName;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.xml.bind.JAXBElement;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = 1L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_provider_standardhaspriority_web/resource");
  }

  private void setPropertyAndInvoke(String resourceMethod, MediaType md)
      throws Fault {
    String ct = buildContentType(md);
    setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
    setProperty(Property.REQUEST_HEADERS, ct);
    setProperty(Property.REQUEST_HEADERS, buildAccept(md));
    setProperty(Property.UNEXPECTED_RESPONSE_MATCH,
        "Tck" + resourceMethod + "Reader");
    setProperty(Property.UNEXPECTED_RESPONSE_MATCH,
        "Tck" + resourceMethod + "Writer");
    setProperty(Property.SEARCH_STRING, resourceMethod);
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
   * @testName: readWriteJaxbProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request. More precisely, step 4
   * in Section 4.2.1 and step 5 in Section 4.2.2 MUST prefer
   * application-provided over pre-packaged entity providers. i.e. When have the
   * same mediaType
   */
  public void readWriteJaxbProviderTest() throws Fault {
    JAXBElement<String> element = new JAXBElement<String>(new QName("jaxb"),
        String.class, "jaxb");
    setRequestContentEntity(element);
    setPropertyAndInvoke("jaxb", MediaType.APPLICATION_XML_TYPE);
  }

  /*
   * @testName: readWriteMapProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request. More precisely, step 4
   * in Section 4.2.1 and step 5 in Section 4.2.2 MUST prefer
   * application-provided over pre-packaged entity providers. i.e. When have the
   * same mediaType
   */
  public void readWriteMapProviderTest() throws Fault {
    MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
    map.add("map", "map");
    setRequestContentEntity(map);
    setPropertyAndInvoke("map", MediaType.APPLICATION_FORM_URLENCODED_TYPE);
  }

  /*
   * @testName: readWriteBooleanProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request. More precisely, step 4
   * in Section 4.2.1 and step 5 in Section 4.2.2 MUST prefer
   * application-provided over pre-packaged entity providers. i.e. When have the
   * same mediaType
   */
  public void readWriteBooleanProviderTest() throws Fault {
    MediaType mt = MediaType.TEXT_PLAIN_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, "boolean"));
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.REQUEST_HEADERS, buildAccept(mt));
    setProperty(Property.CONTENT, "false");
    setProperty(Property.SEARCH_STRING, "false");
    invoke();
  }

  /*
   * @testName: readWriteCharacterProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request. More precisely, step 4
   * in Section 4.2.1 and step 5 in Section 4.2.2 MUST prefer
   * application-provided over pre-packaged entity providers. i.e. When have the
   * same mediaType
   */
  public void readWriteCharacterProviderTest() throws Fault {
    MediaType mt = MediaType.TEXT_PLAIN_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, "character"));
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.REQUEST_HEADERS, buildAccept(mt));
    setProperty(Property.CONTENT, "a");
    setProperty(Property.SEARCH_STRING, "a");
    invoke();
  }

  /*
   * @testName: readWriteIntegerProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:35
   * 
   * @test_Strategy: An implementation MUST support application-provided entity
   * providers and MUST use those in preference to its own pre-packaged
   * providers when either could handle the same request. More precisely, step 4
   * in Section 4.2.1 and step 5 in Section 4.2.2 MUST prefer
   * application-provided over pre-packaged entity providers. i.e. When have the
   * same mediaType
   */
  public void readWriteIntegerProviderTest() throws Fault {
    MediaType mt = MediaType.TEXT_PLAIN_TYPE;
    setProperty(Property.REQUEST, buildRequest(Request.POST, "number"));
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.REQUEST_HEADERS, buildAccept(mt));
    setProperty(Property.CONTENT, "0");
    setProperty(Property.SEARCH_STRING, "0");
    invoke();
  }

}
