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
 * A runtime exception indicating that the server, while acting as a gateway or proxy,
 * received an invalid response from the upstream server it accessed in attempting to fulfill the request.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 2.2
 */
public class GatewayTimeoutException extends ServerErrorException {

    private static final long serialVersionUID = 6523870074504974712L;

    /**
     * Construct a new gateway timeout exception.
     */
    public GatewayTimeoutException() {
        super(Response.Status.GATEWAY_TIMEOUT);
    }

    /**
     * Construct a new gateway timeout exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public GatewayTimeoutException(final String message) {
        super(message, Response.Status.GATEWAY_TIMEOUT);
    }

    /**
     * Construct a new gateway timeout exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 504}.
     */
    public GatewayTimeoutException(final Response response) {
        super(validate(response, Response.Status.GATEWAY_TIMEOUT));
    }

    /**
     * Construct a new gateway timeout exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 504}.
     */
    public GatewayTimeoutException(final String message, final Response response) {
        super(message, validate(response, Response.Status.GATEWAY_TIMEOUT));
    }

    /**
     * Construct a new gateway timeout exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public GatewayTimeoutException(final Throwable cause) {
        super(Response.Status.GATEWAY_TIMEOUT, cause);
    }

    /**
     * Construct a new gateway timeout exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public GatewayTimeoutException(final String message, final Throwable cause) {
        super(message, Response.Status.GATEWAY_TIMEOUT, cause);
    }

    /**
     * Construct a new gateway timeout exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 504}.
     */
    public GatewayTimeoutException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.GATEWAY_TIMEOUT), cause);
    }

    /**
     * Construct a new gateway timeout exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 504}.
     */
    public GatewayTimeoutException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.GATEWAY_TIMEOUT), cause);
    }
}
