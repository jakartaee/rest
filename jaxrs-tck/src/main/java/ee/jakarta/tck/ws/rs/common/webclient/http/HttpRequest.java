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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.Credentials;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import ee.jakarta.tck.ws.rs.common.webclient.Util;
import ee.jakarta.tck.ws.rs.lib.util.TestUtil;

/**
 * Represents an HTTP client Request
 */

public class HttpRequest {

  /**
   * Default HTTP port.
   */
  public static int DEFAULT_HTTP_PORT = 80;

  /**
   * Default HTTP SSL port.
   */
  public static final int DEFAULT_SSL_PORT = 443;

  /**
   * No authentication
   */
  public static final int NO_AUTHENTICATION = 0;

  /**
   * Basic authentication
   */
  public static final int BASIC_AUTHENTICATION = 1;

  /**
   * Digest authenctication
   */
  public static final int DIGEST_AUTHENTICATION = 2;

  /**
   * Method representation of request.
   */
  private HttpUriRequestBase _method = null;

  /**
   * Target web container host
   */
  private String _host = null;

  /**
   * Target web container port
   */
  private int _port = DEFAULT_HTTP_PORT;

  /**
   * Is the request going over SSL
   */
  private boolean _isSecure = false;

  /**
   * HTTP state
   */
  private HttpClientContext _state = null;

  /**
   * Original request line for this request.
   */
  private String _requestLine = null;

  /**
   * Authentication type for current request
   */
  private int _authType = NO_AUTHENTICATION;

  /**
   * Flag to determine if session tracking will be used or not.
   */
  private boolean _useCookies = false;

  /**
   * FollowRedirects
   */
  private boolean _redirect = false;

  Header[] _headers = null;

  /**
   * Creates new HttpRequest based of the passed request line. The request line
   * provied must be in the form of:<br>
   *
   * <pre>
   *     METHOD PATH HTTP-VERSION
   *     Ex.  GET /index.html HTTP/1.0
   * </pre>
   */
  public HttpRequest(String requestLine, String host, int port) {
    _method = createMethod(requestLine);
    _host = host;
    _port = port;

    if (port == DEFAULT_SSL_PORT) {
      _isSecure = true;
    }

    _requestLine = requestLine;
  }

  protected HttpUriRequestBase createMethod(String requestLine) {
    return MethodFactory.getInstance(requestLine);
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * <code>getRequestPath</code> returns the request path for this particular
   * request.
   *
   * @return String request path
   */
  public String getRequestPath() {
    return _method.getPath();
  }

  /**
   * <code>getRequestMethod</code> returns the request type, i.e., GET, POST,
   * etc.
   *
   * @return String request type
   */
  public String getRequestMethod() {
    return _method.getMethod();
  }

  /**
   * <code>isSecureConnection()</code> indicates if the Request is secure or
   * not.
   *
   * @return boolean whether Request is using SSL or not.
   */
  public boolean isSecureRequest() {
    return _isSecure;
  }

  /**
   * <code>setSecureRequest</code> configures this request to use SSL.
   *
   * @param secure
   *          - whether the Request uses SSL or not.
   */
  public void setSecureRequest(boolean secure) {
    _isSecure = secure;
  }

  /**
   * <code>setContent</code> will set the body for this request. Note, this is
   * only valid for POST and PUT operations, however, if called and the request
   * represents some other HTTP method, it will be no-op'd.
   *
   * @param content
   *          request content
   */
  public void setContent(String content) {
    if (isEntityEnclosingMethod()) {
      _method.setEntity(new StringEntity(content, StandardCharsets.ISO_8859_1));
    }
  }

  /**
   * <code>setAuthenticationCredentials configures the request to
   * perform authentication.
   *
   * <p><code>username</code> and <code>password</code> cannot be null.
   * </p>
   *
   * <p>
   * It is legal for <code>realm</code> to be null.
   * </p>
   *
   * @param username
   *          the user
   * @param password
   *          the user's password
   * @param authType
   *          authentication type
   * @param realm
   *          authentication realm
   */
  public void setAuthenticationCredentials(String username, String password,
      int authType, String realm) {
    if (username == null) {
      throw new IllegalArgumentException("Username cannot be null");
    }

    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }

    UsernamePasswordCredentials cred = new UsernamePasswordCredentials(username,
        password.toCharArray());
    AuthScope scope = new AuthScope(_host, null, _port, realm, null);
    ((BasicCredentialsProvider) getState().getCredentialsProvider())
        .setCredentials(scope, cred);
    TestUtil.logTrace("[HttpRequest] Added credentials for '" + username
        + "' with password '" + password + "' in realm '" + realm + "'");

    _authType = authType;
  }

  /**
   * <code>addRequestHeader</code> adds a request header to this request. If a
   * request header of the same name already exists, the new value, will be
   * added to the set of already existing values.
   *
   * <strong>NOTE:</strong> that header names are not case-sensitive.
   *
   * @param headerName
   *          request header name
   * @param headerValue
   *          request header value
   */
  public void addRequestHeader(String headerName, String headerValue) {
    _method.addHeader(headerName, headerValue);
    TestUtil.logTrace("[HttpRequest] Added request header: "
        + formatHeader(_method.getFirstHeader(headerName)));
  }

  public void addRequestHeader(String header) {
    StringTokenizer st = new StringTokenizer(header, "|");
    while (st.hasMoreTokens()) {
      String h = st.nextToken();
      if (h.toLowerCase().startsWith("cookie")) {
        createCookie(h);
        continue;
      }
      int col = h.indexOf(':');
      addRequestHeader(h.substring(0, col).trim(), h.substring(col + 1).trim());
    }
  }

  /**
   * <code>setRequestHeader</code> sets a request header for this request
   * overwritting any previously existing header/values with the same name.
   *
   * <strong>NOTE:</strong> Header names are not case-sensitive.
   *
   * @param headerName
   *          request header name
   * @param headerValue
   *          request header value
   */
  public void setRequestHeader(String headerName, String headerValue) {
    _method.setHeader(headerName, headerValue);
    TestUtil.logTrace("[HttpRequest] Set request header: "
        + formatHeader(_method.getFirstHeader(headerName)));

  }

  /**
   * <code>setFollowRedirects</code> indicates whether HTTP redirects are
   * followed. By default, redirects are not followed.
   */
  public void setFollowRedirects(boolean followRedirects) {
    _redirect = followRedirects;
  }

  /**
   * <code>getFollowRedirects</code> indicates whether HTTP redirects are
   * followed.
   */
  public boolean getFollowRedirects() {
    return _redirect;
  }

  /**
   * <code>setState</code> will set the HTTP state for the current request (i.e.
   * session tracking). This has the side affect
   */
  public void setState(HttpClientContext state) {
    _state = state;
    _useCookies = true;
  }

  /**
   * <code>execute</code> will dispatch the current request to the target
   * server.
   *
   * @return HttpResponse the server's response.
   * @throws IOException
   *           if an I/O error occurs during dispatch.
   */
  public HttpResponse execute() throws IOException {
    String method = _isSecure ? "https" : "http";
    HttpHost target = new HttpHost(method, _host, _port);

    TestUtil.logMsg("[HttpRequest] Dispatching request: '" + _requestLine
        + "' to target server at '" + _host + ":" + _port + "'");

    addSupportHeaders();
    _headers = _method.getHeaders();

    TestUtil.logTrace(
        "########## The real value set: " + getFollowRedirects());

    RequestConfig config = RequestConfig.custom()
        .setRedirectsEnabled(getFollowRedirects()).build();

    try (CloseableHttpClient client = HttpClients.custom()
        .setDefaultRequestConfig(config).build();
        CloseableHttpResponse response = client.execute(target, _method,
            getState())) {

      byte[] responseBody = response.getEntity() == null ? new byte[0]
          : EntityUtils.toByteArray(response.getEntity());

      return new HttpResponse(_host, _port, _isSecure, _method.getPath(),
          response.getVersion(), response.getCode(), response.getReasonPhrase(),
          response.getHeaders(), responseBody, getState());
    }
  }

  /**
   * Returns the current state for this request.
   *
   * @return HttpState current state
   */
  public HttpClientContext getState() {
    if (_state == null) {
      _state = HttpClientContext.create();
      _state.setCredentialsProvider(new BasicCredentialsProvider());
      _state.setCookieStore(new BasicCookieStore());
    }
    return _state;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer(255);
    sb.append("[REQUEST LINE] -> ").append(_requestLine).append('\n');

    if (_headers != null && _headers.length != 0) {

      for (Header _header : _headers) {
        sb.append("       [REQUEST HEADER] -> ");
        sb.append(formatHeader(_header)).append('\n');
      }
    }

    return sb.toString();

  }

  /*
   * private methods
   * ========================================================================
   */

  private void createCookie(String cookieHeader) {
    String cookieLine = cookieHeader.substring(cookieHeader.indexOf(':') + 1)
        .trim();
    StringTokenizer st = new StringTokenizer(cookieLine, " ;");
    BasicClientCookie cookie = null;
    while (st.hasMoreTokens()) {
      String token = st.nextToken();

      if (token.charAt(0) != '$' && !token.startsWith("Domain")
          && !token.startsWith("Path")) {
        String name = token.substring(0, token.indexOf('='));
        String value = token.substring(token.indexOf('=') + 1);
        cookie = new BasicClientCookie(name, value);
      } else if (cookie != null && token.indexOf("Domain") > -1) {
        cookie.setDomain(token.substring(token.indexOf('=') + 1));
      } else if (cookie != null && token.indexOf("Path") > -1) {
        cookie.setPath(token.substring(token.indexOf('=') + 1));
      }
    }

    if (cookie != null) {
      getState().getCookieStore().addCookie(cookie);
      _useCookies = true;
    }
  }

  /**
   * Adds any support request headers necessary for this request. These headers
   * will be added based on the state of the request.
   */
  private void addSupportHeaders() {

    // Authentication headers
    // NOTE: Possibly move logic to generic method
    switch (_authType) {
    case NO_AUTHENTICATION:
      break;
    case BASIC_AUTHENTICATION:
      setBasicAuthorizationHeader();
      break;
    case DIGEST_AUTHENTICATION:
      throw new UnsupportedOperationException(
          "Digest Authentication is not currently " + "supported");
    }

    // A Host header will be added to each request to handle
    // cases where virtual hosts are used, or there is no DNS
    // available on the system where the container is running.
    setHostHeader();

    // Cookies
    setCookieHeader();
  }

  /**
   * Sets a basic authentication header in the request is Request is configured
   * to use basic authentication
   */
  private void setBasicAuthorizationHeader() {
    Credentials cred = getState().getCredentialsProvider()
        .getCredentials(new AuthScope(_host, _port), null);
    if (cred instanceof UsernamePasswordCredentials) {
      UsernamePasswordCredentials upCred = (UsernamePasswordCredentials) cred;
      String authString = "Basic " + Util.getBase64EncodedString(
          upCred.getUserName() + ":" + String.valueOf(upCred.getUserPassword()));
      _method.setHeader("Authorization", authString);
    } else {
      TestUtil.logTrace("[HttpRequest] NULL CREDENTIALS");
    }
  }

  /**
   * Sets a host header in the request. If the configured host value is an IP
   * address, the Host header will be sent, but without any value.
   *
   * If we adhered to the HTTP/1.1 spec, the Host header must be empty of the
   * target server is identified via IP address. However, no user agents I've
   * tested follow this. And if a custom client library does this, it may not
   * work properly with the target server. For now, the Host request-header will
   * always have a value.
   */
  private void setHostHeader() {
    if (_port == DEFAULT_HTTP_PORT || _port == DEFAULT_SSL_PORT) {
      _method.setHeader("Host", _host);
    } else {
      _method.setHeader("Host", _host + ":" + _port);
    }
  }

  /**
   * Sets a Cookie header if this request is using cookies.
   */
  private void setCookieHeader() {
    if (_useCookies) {
      List<Cookie> cookies = getState().getCookieStore().getCookies();
      if (cookies != null && !cookies.isEmpty()) {
        StringBuilder cookieHeader = new StringBuilder();
        for (Cookie cookie : cookies) {
          if (cookieHeader.length() > 0) {
            cookieHeader.append("; ");
          }
          cookieHeader.append(cookie.getName()).append('=')
              .append(cookie.getValue());
        }
        if (cookieHeader.length() > 0) {
          _method.setHeader("Cookie", cookieHeader.toString());
        }
      }
    }
  }

  private boolean isEntityEnclosingMethod() {
    String requestMethod = _method.getMethod();
    return "POST".equals(requestMethod) || "PUT".equals(requestMethod)
        || "PATCH".equals(requestMethod);
  }

  private String formatHeader(Header header) {
    return header == null ? "null" : header.getName() + ": " + header.getValue();
  }
}
