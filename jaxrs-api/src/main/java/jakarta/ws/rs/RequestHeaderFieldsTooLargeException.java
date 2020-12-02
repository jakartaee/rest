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
 * A runtime exception indicating that the server is unwilling to process
 * the request because its header fields are too large.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 3.1
 */
public class RequestHeaderFieldsTooLargeException extends ClientErrorException {

    private static final long serialVersionUID = 6970394315592858601L;

    /**
     * Construct a new request header fields too large exception.
     */
    public RequestHeaderFieldsTooLargeException() {
        super(Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    /**
     * Construct a new request header fields too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public RequestHeaderFieldsTooLargeException(final String message) {
        super(message, Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    /**
     * Construct a new request header fields too large exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 431}.
     */
    public RequestHeaderFieldsTooLargeException(final Response response) {
        super(validate(response, Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE));
    }

    /**
     * Construct a new request header fields too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 431}.
     */
    public RequestHeaderFieldsTooLargeException(final String message, final Response response) {
        super(message, validate(response, Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE));
    }

    /**
     * Construct a new request header fields too large exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public RequestHeaderFieldsTooLargeException(final Throwable cause) {
        super(Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE, cause);
    }

    /**
     * Construct a new request header fields too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public RequestHeaderFieldsTooLargeException(final String message, final Throwable cause) {
        super(message, Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE, cause);
    }

    /**
     * Construct a new request header fields too large exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 431}.
     */
    public RequestHeaderFieldsTooLargeException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE), cause);
    }

    /**
     * Construct a new request header fields too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 431}.
     */
    public RequestHeaderFieldsTooLargeException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE), cause);
    }
}
