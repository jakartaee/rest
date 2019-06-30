/********************************************************************************
 * Copyright (c) 2019 Jeyvison Nascimento
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
 * A runtime exception indicating tha the client has sent {@link jakarta.ws.rs.core.Response.Status#TOO_MANY_REQUESTS}
 * in a given amount of time ("rate limiting").
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 2.2
 */
public class TooManyRequestsException extends ClientErrorException {

    private static final long serialVersionUID = -700876868356530832L;

    /**
     * Construct a new too many requests exception.
     */
    public TooManyRequestsException() {
        super(Response.Status.TOO_MANY_REQUESTS);
    }

    /**
     * Construct a new too many requests exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public TooManyRequestsException(final String message) {
        super(message, Response.Status.TOO_MANY_REQUESTS);
    }

    /**
     * Construct a new too many requests exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 429}.
     */
    public TooManyRequestsException(final Response response) {
        super(validate(response, Response.Status.TOO_MANY_REQUESTS));
    }

    /**
     * Construct a new too many requests exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 429}.
     */
    public TooManyRequestsException(final String message, final Response response) {
        super(message, validate(response, Response.Status.TOO_MANY_REQUESTS));
    }

    /**
     * Construct a new too many requests exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public TooManyRequestsException(final Throwable cause) {
        super(Response.Status.TOO_MANY_REQUESTS, cause);
    }

    /**
     * Construct a new too many requests exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public TooManyRequestsException(final String message, final Throwable cause) {
        super(message, Response.Status.TOO_MANY_REQUESTS, cause);
    }

    /**
     * Construct a new too many requests exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 429}.
     */
    public TooManyRequestsException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.TOO_MANY_REQUESTS), cause);
    }

    /**
     * Construct a new too many requests exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 429}.
     */
    public TooManyRequestsException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.TOO_MANY_REQUESTS), cause);
    }
}
