/********************************************************************************
 * Copyright (c) 2020 Jeyvison Nascimento
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 ********************************************************************************/

package jakarta.ws.rs;

import jakarta.ws.rs.core.Response;

/**
 * A runtime exception indicating that The server does not support, or refuses to support,
 * the HTTP protocol version that was used in the request message.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 3.1
 */
public class HttpVersionNotSupportedException extends ServerErrorException {

    private static final long serialVersionUID = -2162737983746617138L;

    /**
     * Construct a new http version not supported exception.
     */
    public HttpVersionNotSupportedException() {
        super(Response.Status.HTTP_VERSION_NOT_SUPPORTED);
    }

    /**
     * Construct a new http version not supported exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public HttpVersionNotSupportedException(final String message) {
        super(message, Response.Status.HTTP_VERSION_NOT_SUPPORTED);
    }

    /**
     * Construct a new http version not supported exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 505}.
     */
    public HttpVersionNotSupportedException(final Response response) {
        super(validate(response, Response.Status.HTTP_VERSION_NOT_SUPPORTED));
    }

    /**
     * Construct a new http version not supported exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 505}.
     */
    public HttpVersionNotSupportedException(final String message, final Response response) {
        super(message, validate(response, Response.Status.HTTP_VERSION_NOT_SUPPORTED));
    }

    /**
     * Construct a new http version not supported exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public HttpVersionNotSupportedException(final Throwable cause) {
        super(Response.Status.HTTP_VERSION_NOT_SUPPORTED, cause);
    }

    /**
     * Construct a new http version not supported exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public HttpVersionNotSupportedException(final String message, final Throwable cause) {
        super(message, Response.Status.HTTP_VERSION_NOT_SUPPORTED, cause);
    }

    /**
     * Construct a new http version not supported exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 505}.
     */
    public HttpVersionNotSupportedException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.HTTP_VERSION_NOT_SUPPORTED), cause);
    }

    /**
     * Construct a new http version not supported exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 505}.
     */
    public HttpVersionNotSupportedException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.HTTP_VERSION_NOT_SUPPORTED), cause);
    }
}
