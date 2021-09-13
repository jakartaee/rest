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

package com.sun.ts.tests.jaxrs.spec.client.typedentities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.impl.SinglevaluedMap;
import com.sun.ts.tests.jaxrs.common.impl.StringDataSource;
import com.sun.ts.tests.jaxrs.common.impl.StringStreamingOutput;
import com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity;

import jakarta.activation.DataSource;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.StreamingOutput;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = 1339633069677106930L;

  private static final String entity = Resource.class.getName();

  public JAXRSClient() {
    setContextRoot("/jaxrs_spec_client_typedentities_web/resource");
  }

  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /* Run test */
  /*
   * @testName: clientAnyReaderUsageTest
   * 
   * @assertion_ids: JAXRS:SPEC:69;
   * 
   * @test_Strategy: JAX-RS implementations are REQUIRED to use entity providers
   */
  public void clientAnyReaderUsageTest() throws Fault {
    addProvider(new EntityMessageReader());
    setProperty(Property.REQUEST, buildRequest(Request.GET, "readerprovider"));
    setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
    setProperty(Property.SEARCH_STRING, Resource.class.getName());
    bufferEntity(true);
    invoke();

    ReadableWritableEntity entity = getResponse()
        .readEntity(ReadableWritableEntity.class);
    assertFault(entity != null, "Returned Entity is null!");
    assertFault(entity.toString().equals(Resource.class.getName()),
        "Returned Entity", entity.toString(), "is unexpected");
  }

  /*
   * @testName: clientAnyWriterUsageTest
   * 
   * @assertion_ids: JAXRS:SPEC:69;
   * 
   * @test_Strategy: JAX-RS implementations are REQUIRED to use entity providers
   */
  public void clientAnyWriterUsageTest() throws Fault {
    ReadableWritableEntity entity = new ReadableWritableEntity(
        String.valueOf(serialVersionUID));
    addProvider(new EntityMessageWriter());
    setProperty(Property.REQUEST, buildRequest(Request.POST, "writerprovider"));
    setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
    setRequestContentEntity(entity);
    setProperty(Property.SEARCH_STRING, entity.toXmlString());
    invoke();
  }

  // ///////////////////////////////////////////////////////////////////////
  // Standard readers

  /*
   * @testName: clientByteArrayReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientByteArrayReaderTest() throws Fault {
    standardReaderInvocation(MediaType.WILDCARD_TYPE);
    toStringTest(byte[].class);
  }

  /*
   * @testName: clientStringReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientStringReaderTest() throws Fault {
    standardReaderInvocation(MediaType.WILDCARD_TYPE);
    toStringTest(String.class);
  }

  /*
   * @testName: clientInputStreamReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientInputStreamReaderTest() throws Fault {
    standardReaderInvocation(MediaType.WILDCARD_TYPE);
    InputStream responseEntity = getResponse().readEntity(InputStream.class);
    assertFault(responseEntity != null, "Returned Entity is null!");
    InputStreamReader reader = new InputStreamReader(responseEntity);
    readerTest(reader);
  }

  /*
   * @testName: clientReaderReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientReaderReaderTest() throws Fault {
    standardReaderInvocation(MediaType.WILDCARD_TYPE);
    Reader responseEntity = getResponse().readEntity(Reader.class);
    assertFault(responseEntity != null, "Returned Entity is null!");
    readerTest(responseEntity);
  }

  /*
   * @testName: clientFileReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientFileReaderTest() throws Fault {
    standardReaderInvocation(MediaType.WILDCARD_TYPE);
    File responseEntity = getResponse().readEntity(File.class);
    assertFault(responseEntity != null, "Returned Entity is null!");
    FileReader fr;
    try {
      fr = new FileReader(responseEntity);
    } catch (FileNotFoundException e) {
      throw new Fault(e);
    }
    readerTest(fr);
    responseEntity.deleteOnExit();
  }

  /*
   * @testName: clientDataSourceReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientDataSourceReaderTest() throws Fault {
    standardReaderInvocation(MediaType.WILDCARD_TYPE);
    DataSource responseEntity = getResponse().readEntity(DataSource.class);
    assertFault(responseEntity != null, "Returned Entity is null!");
    InputStreamReader reader;
    try {
      reader = new InputStreamReader(responseEntity.getInputStream());
    } catch (IOException e) {
      throw new Fault(e);
    }
    readerTest(reader);
  }

  /*
   * @testName: clientSourceReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientSourceReaderTest() throws Fault {
    standardReaderInvocation(MediaType.TEXT_XML_TYPE);
    Source responseEntity = getResponse().readEntity(Source.class);
    assertFault(responseEntity != null, "Returned Entity is null!");

    standardReaderInvocation(MediaType.APPLICATION_XML_TYPE);
    responseEntity = getResponse().readEntity(Source.class);
    assertFault(responseEntity != null, "Returned Entity is null!");

    standardReaderInvocation(MediaType.APPLICATION_ATOM_XML_TYPE);
    responseEntity = getResponse().readEntity(Source.class);
    assertFault(responseEntity != null, "Returned Entity is null!");
  }

  /*
   * @testName: clientMultivaluedMapReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientMultivaluedMapReaderTest() throws Fault {
    standardReaderInvocation(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
    @SuppressWarnings("unchecked")
    MultivaluedMap<String, String> responseEntity = getResponse()
        .readEntity(MultivaluedMap.class);
    assertFault(responseEntity != null, "Returned Entity is null!");
    boolean ok = responseEntity.containsKey(entity)
        || responseEntity.containsValue(entity);
    assertFault(ok, "Returned Entity", responseEntity,
        "does not contains supposed value");
  }

  // ///////////////////////////////////////////////////////////////////////
  // Writer test

  /*
   * @testName: clientByteArrayWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientByteArrayWriterTest() throws Fault {
    standardWriterInvocation(entity.getBytes());
  }

  /*
   * @testName: clientStringWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientStringWriterTest() throws Fault {
    standardWriterInvocation(entity);
  }

  /*
   * @testName: clientInputStreamWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientInputStreamWriterTest() throws Fault {
    ByteArrayInputStream bais = new ByteArrayInputStream(entity.getBytes());
    standardWriterInvocation(bais);
    close(bais);
  }

  /*
   * @testName: clientReaderWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientReaderWriterTest() throws Fault {
    ByteArrayInputStream bais = new ByteArrayInputStream(entity.getBytes());
    Reader reader = new InputStreamReader(bais);
    standardWriterInvocation(reader);
    close(reader);
  }

  /*
   * @testName: clientFileWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientFileWriterTest() throws Fault {
    File file = createFileEntity(entity);
    standardWriterInvocation(file);
    file.deleteOnExit();
  }

  /*
   * @testName: clientDataSourceWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientDataSourceWriterTest() throws Fault {
    DataSource ds = new StringDataSource(entity, MediaType.WILDCARD_TYPE);
    standardWriterInvocation(ds);
  }

  /*
   * @testName: clientSourceWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientSourceWriterTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.APPLICATION_XML_TYPE));
    File file = createFileEntity("<xml>" + entity + "</xml>");
    Source source = new StreamSource(file);
    standardWriterInvocation(source);
    file.deleteOnExit();
  }

  /*
   * @testName: clientMultivaluedMapWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientMultivaluedMapWriterTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE));
    MultivaluedMap<String, String> map = new SinglevaluedMap<String, String>();
    map.add(entity, entity);
    standardWriterInvocation(map);
  }

  /*
   * @testName: clientStreamingOutputWriterTest
   * 
   * @assertion_ids: JAXRS:SPEC:70;
   * 
   * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
   * be supported by all JAX-RS implementations
   */
  public void clientStreamingOutputWriterTest() throws Fault {
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE));
    StreamingOutput output = new StringStreamingOutput(entity);
    standardWriterInvocation(output);
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

  void readerTest(Reader reader) throws Fault {
    BufferedReader bf = new BufferedReader(reader);
    String s = null;
    try {
      s = bf.readLine();
    } catch (IOException e) {
      throw new Fault(e);
    }
    assertEquals(s, entity, "Returned Entity", s, "is unexpected");
    close(reader);
  }

  void close(Closeable closable) throws Fault {
    try {
      closable.close();
    } catch (IOException e) {
      throw new Fault(e);
    }
  }

  File createFileEntity(String entity) throws Fault {
    File file;
    FileWriter fr = null;
    try {
      file = File.createTempFile("tckjaxrs", ".tmp");
      assertFault(file.canWrite(), "file is not for writing");
      fr = new FileWriter(file);
      fr.write(entity);
      fr.flush();
      fr.close();
    } catch (IOException e) {
      try {
        fr.close();
      } catch (IOException ee) {
      }
      throw new Fault(e);
    }
    return file;
  }

}
