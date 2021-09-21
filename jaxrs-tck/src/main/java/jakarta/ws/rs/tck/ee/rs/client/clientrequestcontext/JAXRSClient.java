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

package com.sun.ts.tests.jaxrs.ee.rs.client.clientrequestcontext;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.jaxrs.api.client.clientrequestcontext.ContextProvider;
import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.impl.ReplacingOutputStream;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.core.MultivaluedMap;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends JaxrsCommonClient {

  private static final long serialVersionUID = -3234850442044177095L;

  public JAXRSClient() {
    setContextRoot("/jaxrs_ee_rs_client_clientrequestcontext_web/resource");
  }

  public static void main(String[] args) {
    new JAXRSClient().run(args);
  }

  /* Run test */
  /*
   * @testName: getEntityStreamTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:437; JAXRS:JAVADOC:451; JAXRS:JAVADOC:455;
   * JAXRS:JAVADOC:456;
   * 
   * @test_Strategy: Get the entity output stream. Set a new entity output
   * stream.
   */
  public void getEntityStreamTest() throws Fault {
    final String entityStreamWorks = "ENTITY_STREAM_WORKS";
    ContextProvider provider = new ContextProvider() {
      @Override
      protected void checkFilterContext(ClientRequestContext context)
          throws Fault {
        OutputStream stream = context.getEntityStream();
        ReplacingOutputStream wrapper = new ReplacingOutputStream(stream, 'X',
            'T');
        context.setEntityStream(wrapper);
      }
    };
    ByteArrayInputStream entity = new ByteArrayInputStream(
        entityStreamWorks.replace('T', 'X').getBytes());
    addProvider(provider);
    setRequestContentEntity(entity);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "post"));
    invoke();

    String body = getResponseBody();
    assertContains(body, entityStreamWorks);
  }

  /*
   * @testName: getHeadersIsMutableTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:439; JAXRS:JAVADOC:455; JAXRS:JAVADOC:456;
   * 
   * @test_Strategy: Get the generic entity type information.
   */
  public void getHeadersIsMutableTest() throws Fault {
    ContextProvider provider = new ContextProvider() {
      @Override
      protected void checkFilterContext(ClientRequestContext context)
          throws Fault {
        MultivaluedMap<String, Object> headers = context.getHeaders();
        headers.add("Accept-Language", "en_gb");
        headers.add("Date", "Tue, 15 Nov 1994 08:12:31 GMT");
        headers.add("tck", "cts");
      }
    };
    addProvider(provider);
    setProperty(Property.REQUEST, buildRequest(Request.GET, "headers"));
    invoke();

    String body = getResponseBody().toLowerCase();
    assertContains(body, "accept-language");
    assertContains(body, "date");
    assertContains(body, "tck");
  }

  /*
   * @testName: setMethodTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:452; JAXRS:JAVADOC:455; JAXRS:JAVADOC:456;
   * 
   * @test_Strategy: Set the request method.
   */
  public void setMethodTest() throws Fault {
    String entity = "ENTITY";
    ContextProvider provider = new ContextProvider() {
      @Override
      protected void checkFilterContext(ClientRequestContext context)
          throws Fault {
        context.setMethod("PUT");
      }
    };
    addProvider(provider);
    setRequestContentEntity(entity);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "put"));
    invoke();

    String body = getResponseBody();
    assertContains(body, entity);
  }

  /*
   * @testName: setUriTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:454; JAXRS:JAVADOC:447; JAXRS:JAVADOC:455;
   * JAXRS:JAVADOC:456;
   * 
   * @test_Strategy: Set a new request URI. Get the request URI.
   */
  public void setUriTest() throws Fault {
    String entity = "ENTITY";
    ContextProvider provider = new ContextProvider() {
      @Override
      protected void checkFilterContext(ClientRequestContext context)
          throws Fault {
        URI uri = context.getUri();
        try {
          uri = new URI(uri.toASCIIString().replace("qwerty", "post"));
        } catch (URISyntaxException e) {
          throw new Fault(e);
        }
        context.setUri(uri);
      }
    };
    addProvider(provider);
    setRequestContentEntity(entity);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "qwerty"));
    invoke();

    String body = getResponseBody();
    assertContains(body, entity);
  }

  // ////////////////////////////////////////////////////////////////////
  protected static void assertContains(String string, String substring)
      throws Fault {
    assertFault(string.contains(substring), string, "does NOT contain",
        substring);
    TestUtil.logMsg("Found expected substring: " + substring);
  }

}
