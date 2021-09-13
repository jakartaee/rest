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

package com.sun.ts.tests.jaxrs.spec.provider.standard;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

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
    setContextRoot("/jaxrs_spec_provider_standard_web/resource");
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

  private void setEntityAndPropertyAndInvoke(String resourceMethod,
      MediaType md) throws Fault {
    setProperty(Property.CONTENT, resourceMethod);
    setProperty(Property.SEARCH_STRING, resourceMethod);
    setPropertyAndInvoke(resourceMethod, md);
  }

  private void setPropertyAndInvoke(String resourceMethod) throws Fault {
    setEntityAndPropertyAndInvoke(resourceMethod,
        MediaType.APPLICATION_SVG_XML_TYPE);
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

  /**
   * MediaType should either be an enum or have the values method It's neither
   * so this method uses reflection to acquire public static fields of given
   * class, either MediaType or String.
   * 
   * @param clazz
   *          Class of the public static Field
   * @return array of the Fields of a given class
   * @throws Fault
   */
  @SuppressWarnings("unchecked")
  protected <T> T[] getMediaTypes(Class<T> clazz) throws Fault {
    MediaType type = MediaType.WILDCARD_TYPE;
    List<T> list = new LinkedList<T>();
    for (Field field : MediaType.class.getFields())
      if (Modifier.isStatic(field.getModifiers())
          && Modifier.isPublic(field.getModifiers())
          && field.getType().equals(clazz))
        try {
          T value = (T) field.get(type);
          if (value.toString().contains("/"))
            list.add(value);
        } catch (Exception e) {
          throw new Fault(e);
        }
    T[] array = (T[]) Array.newInstance(clazz, 0);
    return list.toArray(array);
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

  String[] methodsAll = { "bytearray", "string", "inputstream", "file",
      "datasource", "streamingoutput" };

  /* Run test */

  /*
   * @testName: byteArrayProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.1;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   */
  public void byteArrayProviderTest() throws Fault {
    for (MediaType media : getMediaTypes(MediaType.class))
      setEntityAndPropertyAndInvoke(methodsAll[0], media); // All media
    setPropertyAndInvoke(methodsAll[0] + "svg"); // just the one
  }

  /*
   * @testName: stringProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.2;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   */
  public void stringProviderTest() throws Fault {
    for (MediaType media : getMediaTypes(MediaType.class))
      setEntityAndPropertyAndInvoke(methodsAll[1], media); // All media
    setPropertyAndInvoke(methodsAll[1] + "svg"); // just the one
  }

  /*
   * @testName: inputStreamProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.3;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   */
  public void inputStreamProviderTest() throws Fault {
    for (MediaType media : getMediaTypes(MediaType.class))
      setEntityAndPropertyAndInvoke(methodsAll[2], media); // All media
    setPropertyAndInvoke(methodsAll[2] + "svg"); // just the one
  }

  /*
   * @testName: readerProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.4;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   * java.io.Reader All media types (*\*)
   * 
   * JIRA:1078
   */
  public void readerProviderTest() throws Fault {
    for (MediaType media : getMediaTypes(MediaType.class))
      setEntityAndPropertyAndInvoke("reader", media);
    setPropertyAndInvoke("readersvg");
  }

  /*
   * @testName: fileProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.5;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   */
  public void fileProviderTest() throws Fault {
    for (MediaType media : getMediaTypes(MediaType.class))
      setEntityAndPropertyAndInvoke(methodsAll[3], media); // All media
    setPropertyAndInvoke(methodsAll[3] + "svg"); // just the one
  }

  /*
   * @testName: dataSourceProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.6;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   */
  public void dataSourceProviderTest() throws Fault {
    for (MediaType media : getMediaTypes(MediaType.class))
      setEntityAndPropertyAndInvoke(methodsAll[4], media); // All media
    setPropertyAndInvoke(methodsAll[4] + "svg"); // just the one
  }

  /*
   * @testName: sourceProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.7;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   * javax.xml.transform.Source XML types (text/xml, application/xml and
   * application/*+xml)
   * 
   */
  public void sourceProviderTest() throws Fault {
    setPropertyAndInvokeXml("source");
  }

  /*
   * @testName: streamingOutputProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.10;
   * 
   * @test_Strategy: An implementation MUST include pre-packaged
   * MessageBodyReader and MessageBodyWriter implementations for the following
   * Java and media type combinations
   * 
   */
  public void streamingOutputProviderTest() throws Fault {
    for (MediaType media : getMediaTypes(MediaType.class))
      setEntityAndPropertyAndInvoke(methodsAll[5], media); // All media
    setPropertyAndInvoke(methodsAll[5] + "svg"); // just the one
  }
  /*
   * JAXRS:SPEC:36
   * 
   * @test_Strategy: MessageBodyReader providers always operate on the decoded
   * HTTP entity body rather than directly on the HTTP message body.
   * 
   * This is not possible with JAXRS Client.
   * 
   * This depends on a container, works in TC, not in GF
   * 
   * public void encodedEntityTest() throws Fault { for (String method :
   * methodsAll) setPropertyAndInvokeEncoded(method); }
   */

}
