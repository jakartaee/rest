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
 * A runtime exception indicating that a client request has timed out.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 3.1
 */
public class RequestTimeoutException extends ClientErrorException {

	private static final long serialVersionUID = -5009709317708795655L;

	/**
	 * Construct a new request timeout exception.
	 */
	public RequestTimeoutException() {
		super(Response.Status.REQUEST_TIMEOUT);
	}

	/**
	 * Construct a new request timeout exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 */
	public RequestTimeoutException(final String message) {
		super(message, Response.Status.REQUEST_TIMEOUT);
	}

	/**
	 * Construct a new request timeout exception.
	 *
	 * @param response error response.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 408}.
	 */
	public RequestTimeoutException(final Response response) {
		super(validate(response, Response.Status.REQUEST_TIMEOUT));
	}

	/**
	 * Construct a new request timeout exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param response error response.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 408}.
	 */
	public RequestTimeoutException(final String message, final Response response) {
		super(message, validate(response, Response.Status.REQUEST_TIMEOUT));
	}

	/**
	 * Construct a new request timeout exception.
	 *
	 * @param cause the underlying cause of the exception.
	 */
	public RequestTimeoutException(final Throwable cause) {
		super(Response.Status.REQUEST_TIMEOUT, cause);
	}

	/**
	 * Construct a new request timeout exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause the underlying cause of the exception.
	 */
	public RequestTimeoutException(final String message, final Throwable cause) {
		super(message, Response.Status.REQUEST_TIMEOUT, cause);
	}

	/**
	 * Construct a new request timeout exception.
	 *
	 * @param response error response.
	 * @param cause the underlying cause of the exception.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 408}.
	 */
	public RequestTimeoutException(final Response response, final Throwable cause) {
		super(validate(response, Response.Status.REQUEST_TIMEOUT), cause);
	}

	/**
	 * Construct a new request timeout exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param response error response.
	 * @param cause the underlying cause of the exception.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 408}.
	 */
	public RequestTimeoutException(final String message, final Response response, final Throwable cause) {
		super(message, validate(response, Response.Status.REQUEST_TIMEOUT), cause);
	}



}
