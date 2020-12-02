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
 * A runtime exception indicating that the server cannot serve the requested ranges.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 3.1
 */
public class RequestedRangeNotSatisfiableException extends ClientErrorException {

    private static final long serialVersionUID = 7771468695802646780L;

    /**
     * Construct a new requested range not satisfiable exception.
     */
    public RequestedRangeNotSatisfiableException() {
        super(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    /**
     * Construct a new requested range not satisfiable exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public RequestedRangeNotSatisfiableException(final String message) {
        super(message, Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    /**
     * Construct a new requested range not satisfiable exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 416}.
     */
    public RequestedRangeNotSatisfiableException(final Response response) {
        super(validate(response, Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE));
    }

    /**
     * Construct a new requested range not satisfiable exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 416}.
     */
    public RequestedRangeNotSatisfiableException(final String message, final Response response) {
        super(message, validate(response, Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE));
    }

    /**
     * Construct a new requested range not satisfiable exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public RequestedRangeNotSatisfiableException(final Throwable cause) {
        super(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE, cause);
    }

    /**
     * Construct a new requested range not satisfiable exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public RequestedRangeNotSatisfiableException(final String message, final Throwable cause) {
        super(message, Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE, cause);
    }

    /**
     * Construct a new requested range not satisfiable exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 416}.
     */
    public RequestedRangeNotSatisfiableException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE), cause);
    }

    /**
     * Construct a new requested range not satisfiable exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 416}.
     */
    public RequestedRangeNotSatisfiableException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE), cause);
    }
}
