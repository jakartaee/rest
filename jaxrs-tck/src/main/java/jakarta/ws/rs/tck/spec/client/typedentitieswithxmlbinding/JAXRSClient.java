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

package com.sun.ts.tests.jaxrs.spec.client.typedentitieswithxmlbinding;

import javax.xml.namespace.QName;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.JAXBElement;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = 1339633069677106930L;

  private static final String entity = Resource.class.getName();

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_client_typedentitieswithxmlbinding_web/resource");
  }

  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /*
   * @testName: clientJaxbElementReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientJaxbElementReaderTest() throws Fault {
    GenericType<JAXBElement<String>> type = new GenericType<JAXBElement<String>>() {
    };

    standardReaderInvocation(MediaType.TEXT_XML_TYPE);
    JAXBElement<?> responseEntity = getResponse().readEntity(type);
    assertFault(responseEntity != null, "Returned Entity is null!");

    standardReaderInvocation(MediaType.APPLICATION_XML_TYPE);
    responseEntity = getResponse().readEntity(type);
    assertFault(responseEntity != null, "Returned Entity is null!");

    standardReaderInvocation(MediaType.APPLICATION_ATOM_XML_TYPE);
    responseEntity = getResponse().readEntity(type);
    assertFault(responseEntity != null, "Returned Entity is null!");

    String s = responseEntity.getValue().toString();
    assertFault(s.equals(entity), "Returned Entity", s, "is unexpected");
  }

  /*
   * @testName: clientJaxbElementWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientJaxbElementWriterTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.APPLICATION_XML_TYPE));
    JAXBElement<String> element = new JAXBElement<String>(new QName(""),
        String.class, entity);
    standardWriterInvocation(element);
  }


  // ///////////////////////////////////////////////////////////////////////
  // Helper methods

  protected void standardReaderInvocation(MediaType mediaType) throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.GET, "standardreader"));
    setProperty(Property.SEARCH_STRING, entity);
    setProperty(Property.REQUEST_HEADERS, buildAccept(mediaType));
    bufferEntity(true);
    invoke();
  }

  protected void standardWriterInvocation(Object objectEntity) throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "standardwriter"));
    setRequestContentEntity(objectEntity);
    setProperty(Property.SEARCH_STRING, entity);
    invoke();
  }


  protected <T> void toStringTest(Class<T> clazz) throws Fault {
    T responseEntity = getResponse().readEntity(clazz);
    assertFault(responseEntity != null, "Returned Entity is null!");
    String s = responseEntity.toString();
    if (s.startsWith("[B"))
      s = new String((byte[]) responseEntity);
    assertFault(s.equals(entity), "Was expected returned entity", entity, "got",
        s);
  }
}
