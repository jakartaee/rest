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
 * A runtime exception indicating that the request entity is larger than the server is willing or able to process.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 2.2
 */
public class RequestEntityTooLargeException extends ClientErrorException {

    private static final long serialVersionUID = 757954278321450800L;


    /**
     * Construct a new request entity too large exception.
     */
    public RequestEntityTooLargeException() {
        super(Response.Status.REQUEST_ENTITY_TOO_LARGE);
    }

    /**
     * Construct a new request entity too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public RequestEntityTooLargeException(final String message) {
        super(message, Response.Status.REQUEST_ENTITY_TOO_LARGE);
    }

    /**
     * Construct a new request entity too large exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 413}.
     */
    public RequestEntityTooLargeException(final Response response) {
        super(validate(response, Response.Status.REQUEST_ENTITY_TOO_LARGE));
    }

    /**
     * Construct a new request entity too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 413}.
     */
    public RequestEntityTooLargeException(final String message, final Response response) {
        super(message, validate(response, Response.Status.REQUEST_ENTITY_TOO_LARGE));
    }

    /**
     * Construct a new request entity too large exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public RequestEntityTooLargeException(final Throwable cause) {
        super(Response.Status.REQUEST_ENTITY_TOO_LARGE, cause);
    }

    /**
     * Construct a new request entity too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public RequestEntityTooLargeException(final String message, final Throwable cause) {
        super(message, Response.Status.REQUEST_ENTITY_TOO_LARGE, cause);
    }

    /**
     * Construct a new request entity too large exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 413}.
     */
    public RequestEntityTooLargeException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.REQUEST_ENTITY_TOO_LARGE), cause);
    }

    /**
     * Construct a new request entity too large exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 413}.
     */
    public RequestEntityTooLargeException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.REQUEST_ENTITY_TOO_LARGE), cause);
    }

}
