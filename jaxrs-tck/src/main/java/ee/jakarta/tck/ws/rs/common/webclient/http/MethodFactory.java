/*
 * Copyright (c) 2007, 2021 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */

package ee.jakarta.tck.ws.rs.common.webclient.http;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.classic.methods.HttpOptions;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.core5.http.HttpVersion;

import ee.jakarta.tck.ws.rs.lib.porting.TSURL;

/**
 * Simple factory class which returns HttpMethod implementations based on a
 * request line.
 * <p>
 * For example, a request line of <tt>GET /index.jsp HTTP/1.0</tt> would return
 * an HttpMethod implementation that handles GET requests using HTTP/1.0.
 * </p>
 */

public class MethodFactory {

  /**
   * HTTP GET
   */
  private static final String GET_METHOD = "GET";

  /**
   * HTTP POST
   */
  private static final String POST_METHOD = "POST";

  /**
   * HTTP HEAD
   */
  private static final String HEAD_METHOD = "HEAD";

  /**
   * HTTP PUT
   */
  private static final String PUT_METHOD = "PUT";

  /**
   * HTTP DELETE
   */
  private static final String DELETE_METHOD = "DELETE";

  /**
   * HTTP OPTIONS
   */
  private static final String OPTIONS_METHOD = "OPTIONS";

  private static final Map<String, Class<? extends HttpUriRequestBase>> METHOD_MAP = new HashMap<String, Class<? extends HttpUriRequestBase>>();

  static {
    METHOD_MAP.put(GET_METHOD, HttpGet.class);
    METHOD_MAP.put(POST_METHOD, HttpPost.class);
    METHOD_MAP.put(PUT_METHOD, HttpPut.class);
    METHOD_MAP.put(DELETE_METHOD, HttpDelete.class);
    METHOD_MAP.put(HEAD_METHOD, HttpHead.class);
    METHOD_MAP.put(OPTIONS_METHOD, HttpOptions.class);
  }

  /**
   * TSURL implementation
   */
  private static final TSURL TS_URL = new TSURL();

  /**
   * Private constructor as all interaction with this class is through the
   * getInstance() method.
   */
  private MethodFactory() {
  }

  public static Map<String, Class<? extends HttpUriRequestBase>> getMethodMap() {
    return METHOD_MAP;
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * Returns the approriate request method based on the provided request string.
   * The request must be in the format of METHOD URI_PATH HTTP_VERSION, i.e. GET
   * /index.jsp HTTP/1.1.
   *
   * @return HttpMethod based in request.
   */
  public static HttpUriRequestBase getInstance(String request) {
    StringTokenizer st = new StringTokenizer(request);
    String method;
    String uri;
    String version;
    try {
      method = st.nextToken();
      uri = TS_URL.getRequest(st.nextToken());
      version = st.nextToken();
    } catch (NoSuchElementException nsee) {
      throw new IllegalArgumentException(
          "Request provided: " + request + " is malformed.");
    }

    HttpUriRequestBase req;
    Class<? extends HttpUriRequestBase> methodClass = METHOD_MAP.get(method);
    if (methodClass == null) {
      throw new IllegalArgumentException("Invalid method: " + method);
    }

    try {
      Constructor<? extends HttpUriRequestBase> constructor = methodClass
          .getDeclaredConstructor(String.class);
      req = constructor.newInstance(uri);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }

    setHttpVersion(version, req);
    return req;
  }

  /*
   * private methods
   * ========================================================================
   */

  /**
   * Sets the HTTP version for the method in question.
   *
   * @param version
   *          HTTP version to use for this request
   * @param method
   *          method to adjust HTTP version
   */
  private static void setHttpVersion(String version, HttpUriRequestBase method) {
    final String oneOne = "HTTP/1.1";
    method.setVersion(
        (version.equals(oneOne) ? HttpVersion.HTTP_1_1 : HttpVersion.HTTP_1_0));
  }
}
