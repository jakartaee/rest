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
 * A runtime exception indicating the server refuses to accept the request without a defined Content-Length.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 2.2
 */
public class LenghtRequiredException extends ClientErrorException {

    private static final long serialVersionUID = 1216486825751034780L;

    /**
     * Construct a new lenght required request exception.
     */
    public LenghtRequiredException() {
        super(Response.Status.LENGTH_REQUIRED);
    }

    /**
     * Construct a new lenght required request exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public LenghtRequiredException(final String message) {
        super(message, Response.Status.LENGTH_REQUIRED);
    }

    /**
     * Construct a new lenght required request exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 411}.
     */
    public LenghtRequiredException(final Response response) {
        super(validate(response, Response.Status.LENGTH_REQUIRED));
    }

    /**
     * Construct a new lenght required request exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 411}.
     */
    public LenghtRequiredException(final String message, final Response response) {
        super(message, validate(response, Response.Status.LENGTH_REQUIRED));
    }

    /**
     * Construct a new lenght required request exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public LenghtRequiredException(final Throwable cause) {
        super(Response.Status.LENGTH_REQUIRED, cause);
    }

    /**
     * Construct a new lenght required request exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public LenghtRequiredException(final String message, final Throwable cause) {
        super(message, Response.Status.LENGTH_REQUIRED, cause);
    }

    /**
     * Construct a new lenght required request exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 411}.
     */
    public LenghtRequiredException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.LENGTH_REQUIRED), cause);
    }

    /**
     * Construct a new lenght required request exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 411}.
     */
    public LenghtRequiredException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.LENGTH_REQUIRED), cause);
    }


}
