/*
 * Copyright (c) 2014, 2020 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.constrainedto;

import jakarta.ws.rs.tck.common.client.JaxrsCommonClient;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

/**
 * test_strategy_common: If such a annotation is present, the JAX-RS runtime
 * will enforce the specified usage restriction. - Not optional
 * 
 * It is a configuration error to constraint a JAX-RS provider implementation to
 * a run-time context in which the provider cannot be applied. In such case a
 * JAX-RS runtime SHOULD inform a user about the issue and ignore the provider
 * implementation in further processing - Should not throw exception, just
 * ignore
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = 3343257931794865470L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_ee_rs_constrainedto_web/resource");
  }

  /**
   * Entry point for different-VM execution. It should delegate to method
   * run(String[], PrintWriter, PrintWriter), and this method should not contain
   * any test configuration.
   */
  public static void main(String[] args) {
    JAXRSClient theTests = new JAXRSClient();
    theTests.run(args);
  }

  /* Run test */
  /*
   * @testName: serverSideReaderIsUsedOnServerTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used
   */
  public void serverSideReaderIsUsedOnServerTest() throws Fault {
    setProperty(Property.CONTENT, "Anything");
    setProperty(Property.SEARCH_STRING, ServerSideReader.FAKE_MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(ServerSideReader.MEDIA_TYPE));
    invoke();
  }

  /*
   * @testName: clientSideReaderIsNotUsedOnServerTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used
   */
  public void clientSideReaderIsNotUsedOnServerTest() throws Fault {
    setProperty(Property.CONTENT, Resource.MESSAGE);
    setProperty(Property.SEARCH_STRING, Resource.MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(ClientSideReader.MEDIA_TYPE));
    invoke();
  }

  /*
   * @testName: serverSideWriterIsUsedOnServerTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used
   */
  public void serverSideWriterIsUsedOnServerTest() throws Fault {
    setProperty(Property.CONTENT, Resource.MESSAGE);
    setProperty(Property.SEARCH_STRING, ServerSideWriter.FAKE_MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(ServerSideWriter.MEDIA_TYPE));
    invoke();
  }

  /*
   * @testName: clientSideWriterIsNotUsedOnServerTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used
   */
  public void clientSideWriterIsNotUsedOnServerTest() throws Fault {
    setPrintEntity(true);
    setProperty(Property.CONTENT, Resource.MESSAGE);
    setProperty(Property.SEARCH_STRING, Resource.MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(ClientSideWriter.MEDIA_TYPE));
    invoke();
  }

  /*
   * @testName: serverSideReaderIsNotUsedOnClientTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used
   */
  public void serverSideReaderIsNotUsedOnClientTest() throws Fault {
    addProviders();
    setProperty(Property.CONTENT, ServerSideReader.MEDIA_TYPE.toString());
    setProperty(Property.SEARCH_STRING, Resource.MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "media"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    invoke();
  }

  /*
   * @testName: clientSideReaderIsUsedOnClientTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used
   */
  public void clientSideReaderIsUsedOnClientTest() throws Fault {
    addProviders();
    setProperty(Property.CONTENT, ClientSideReader.MEDIA_TYPE.toString());
    setProperty(Property.SEARCH_STRING, ClientSideReader.FAKE_MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "media"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    invoke();
  }

  /*
   * @testName: serverSideWriterIsNotUsedOnClientTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used
   */
  public void serverSideWriterIsNotUsedOnClientTest() throws Fault {
    addProviders();
    setProperty(Property.CONTENT, ServerSideWriter.MEDIA_TYPE.toString());
    setProperty(Property.SEARCH_STRING, Resource.MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "media"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    invoke();
  }

  /*
   * @testName: clientSideWriterIsUsedOnClientTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:969;
   * 
   * @test_Strategy: jakarta.ws.rs.ConstrainedTo.value is used this goes to
   * special resource method with response 204 to check the ClientSideWriter
   * worked on client rather then let it (wrongly) work on server and falsely
   * pass
   */
  public void clientSideWriterIsUsedOnClientTest() throws Fault {
    addProviders();
    setProperty(Property.CONTENT, Resource.MESSAGE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "clientwriter"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(ClientSideWriter.MEDIA_TYPE));
    setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
    invoke();
  }

  // /////////////////////////////////////////////////////////////////////
  protected void addProviders() {
    addProvider(ServerSideReader.class);
    addProvider(ClientSideReader.class);
    addProvider(ServerSideWriter.class);
    addProvider(ClientSideWriter.class);
  }
}
