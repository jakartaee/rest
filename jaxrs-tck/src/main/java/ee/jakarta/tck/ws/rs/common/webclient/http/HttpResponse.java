/*
 * Copyright (c) 2006, 2021 Oracle and/or its affiliates. All rights reserved.
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ProtocolVersion;

import ee.jakarta.tck.ws.rs.common.webclient.Util;

/**
 * This class represents an HTTP response from the server.
 */

public class HttpResponse {

  /**
   * Default encoding based on Servlet Specification
   */
  private static final String DEFAULT_ENCODING = "ISO-8859-1";

  /**
   * Content-Type header
   */
  private static final String CONTENT_TYPE = "Content-Type";

  /**
   * HttpState obtained after execution of request
   */
  private HttpClientContext _state = null;

  /**
   * Charset encoding returned in the response
   */
  private String _encoding = DEFAULT_ENCODING;

  /**
   * The response body. Initialized after first call to one of the
   * getResponseBody methods and cached for subsequent calls.
   */
  private String _responseBody = null;

  /**
   * Host name used for processing request
   */
  private String _host = null;

  /**
   * Port number used for processing request
   */
  private int _port;

  /**
   * Issecure
   */
  private boolean _isSecure;

  private String _path;

  private ProtocolVersion _version;

  private int _statusCode;

  private String _reasonPhrase;

  private Header[] _headers;

  private byte[] _rawResponseBody;

  /** Creates new HttpResponse */
  public HttpResponse(String host, int port, boolean isSecure, String path,
      ProtocolVersion version, int statusCode, String reasonPhrase,
      Header[] headers, byte[] rawResponseBody, HttpClientContext state) {

    _host = host;
    _port = port;
    _isSecure = isSecure;
    _path = path;
    _version = version;
    _statusCode = statusCode;
    _reasonPhrase = reasonPhrase;
    _headers = headers;
    _rawResponseBody = rawResponseBody == null ? new byte[0] : rawResponseBody;
    _state = state;
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * Returns the HTTP status code returned by the server
   *
   * @return HTTP status code
   */
  public String getStatusCode() {
    return Integer.toString(_statusCode);
  }

  /**
   * Returns the HTTP reason-phrase returned by the server
   *
   * @return HTTP reason-phrase
   */
  public String getReasonPhrase() {
    return _reasonPhrase;
  }

  /**
   * Returns the headers received in the response from the server.
   *
   * @return response headers
   */
  public Header[] getResponseHeaders() {
    return _headers;
  }

  /**
   * Returns the headers designated by the name provided.
   *
   * @return response headers
   */
  public Header[] getResponseHeaders(String headerName) {
    int count = 0;
    for (Header header : _headers) {
      if (header.getName().equalsIgnoreCase(headerName)) {
        count++;
      }
    }

    Header[] filtered = new Header[count];
    int idx = 0;
    for (Header header : _headers) {
      if (header.getName().equalsIgnoreCase(headerName)) {
        filtered[idx++] = header;
      }
    }
    return filtered;
  }

  /**
   * Returns the response header designated by the name provided.
   *
   * @return a specfic response header or null if the specified header doesn't
   *         exist.
   */
  public Header getResponseHeader(String headerName) {
    for (Header header : _headers) {
      if (header.getName().equalsIgnoreCase(headerName)) {
        return header;
      }
    }
    return null;
  }

  /**
   * Returns the response body as a byte array using the charset specified in
   * the server's response.
   *
   * @return response body as an array of bytes.
   */
  public byte[] getResponseBodyAsBytes() throws IOException {
    return getEncodedResponse().getBytes();
  }

  /**
   * Returns the response as bytes (no encoding is performed by client.
   *
   * @return the raw response bytes
   * @throws IOException
   *           if an error occurs reading from server
   */
  public byte[] getResponseBodyAsRawBytes() throws IOException {
    return _rawResponseBody;
  }

  /**
   * Returns the response body as a string using the charset specified in the
   * server's response.
   *
   * @return response body as a String
   */
  public String getResponseBodyAsString() throws IOException {
    return getEncodedResponse();
  }

  /**
   * Returns the response body of the server without being encoding by the
   * client.
   *
   * @return an unecoded String representation of the response
   * @throws IOException
   *           if an error occurs reading from the server
   */
  public String getResponseBodyAsRawString() throws IOException {
    return new String(_rawResponseBody, StandardCharsets.ISO_8859_1);
  }

  /**
   * Returns the response body as an InputStream using the encoding specified in
   * the server's response.
   *
   * @return response body as an InputStream
   */
  public InputStream getResponseBodyAsStream() throws IOException {
    return new ByteArrayInputStream(getEncodedResponse().getBytes());
  }

  /**
   * Returns the response body as an InputStream without any encoding applied by
   * the client.
   *
   * @return an InputStream to read the response
   * @throws IOException
   *           if an error occurs reading from the server
   */
  public InputStream getResponseBodyAsRawStream() throws IOException {
    return new ByteArrayInputStream(_rawResponseBody);
  }

  /**
   * Returns the charset encoding for this response.
   *
   * @return charset encoding
   */
  public String getResponseEncoding() {
    Header content = getResponseHeader(CONTENT_TYPE);
    if (content != null) {
      String headerVal = content.getValue();
      int idx = headerVal.toLowerCase().indexOf(";charset=");
      if (idx > -1) {
        // content encoding included in response
        _encoding = headerVal.substring(idx + 9);
      }
    }
    return _encoding;
  }

  /**
   * Returns the post-request state.
   *
   * @return an HttpState object
   */
  public HttpClientContext getState() {
    return _state;
  }

  /**
   * Displays a String representation of the response.
   *
   * @return string representation of response
   */
  public String toString() {
    StringBuffer sb = new StringBuffer(255);

    sb.append("[RESPONSE STATUS LINE] -> ");
    sb.append(_version == null ? "HTTP/1.1" : _version).append(' ');
    sb.append(_statusCode).append(' ');
    sb.append(_reasonPhrase).append('\n');
    if (_headers != null && _headers.length != 0) {
      for (Header header : _headers) {
        sb.append("       [RESPONSE HEADER] -> ");
        sb.append(header.getName()).append(": ").append(header.getValue())
            .append('\n');
      }
    }

    String resBody;
    try {
      resBody = getResponseBodyAsRawString();
    } catch (IOException ioe) {
      resBody = "UNEXECTED EXCEPTION: " + ioe.toString();
    }
    if (resBody != null && resBody.length() != 0) {
      sb.append("------ [RESPONSE BODY] ------\n");
      sb.append(resBody);
      sb.append("\n-----------------------------\n\n");
    }
    return sb.toString();
  }

  /*
   * Eventually they need to come from _method
   */

  public String getHost() {
    return _host;
  }

  public int getPort() {
    return _port;
  }

  public String getProtocol() {
    return _isSecure ? "https" : "http";
  }

  public String getPath() {
    return _path;
  }

  /*
   * Private Methods
   * ==========================================================================
   */

  /**
   * Returns the response body using the encoding returned in the response.
   *
   * @return encoded response String.
   */
  private String getEncodedResponse() throws IOException {
    if (_responseBody == null) {
      _responseBody = Util.getEncodedStringFromStream(
          new ByteArrayInputStream(_rawResponseBody), getResponseEncoding());
    }
    return _responseBody;
  }
}
