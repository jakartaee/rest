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
 * A runtime exception indicating that the client must first authenticate itself with the proxy.
 *
 * @author Jeyvison Nascimento (jeynoronha@gmail.com)
 * @since 2.2
 */
public class ProxyAutheticationRequiredException extends ClientErrorException {

	private static final long serialVersionUID = 3053792820894492489L;

	/**
	 * Construct a new proxy authentication required exception.
	 */
	public ProxyAutheticationRequiredException() {
		super(Response.Status.PROXY_AUTHENTICATION_REQUIRED);
	}

	/**
	 * Construct a new proxy authentication required exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 */
	public ProxyAutheticationRequiredException(final String message) {
		super(message, Response.Status.PROXY_AUTHENTICATION_REQUIRED);
	}

	/**
	 * Construct a new proxy authentication required exception.
	 *
	 * @param response error response.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 407}.
	 */
	public ProxyAutheticationRequiredException(final Response response) {
		super(validate(response, Response.Status.PROXY_AUTHENTICATION_REQUIRED));
	}

	/**
	 * Construct a new proxy authentication required exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param response error response.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 407}.
	 */
	public ProxyAutheticationRequiredException(final String message, final Response response) {
		super(message, validate(response, Response.Status.PROXY_AUTHENTICATION_REQUIRED));
	}

	/**
	 * Construct a new proxy authentication required exception.
	 *
	 * @param cause the underlying cause of the exception.
	 */
	public ProxyAutheticationRequiredException(final Throwable cause) {
		super(Response.Status.PROXY_AUTHENTICATION_REQUIRED, cause);
	}

	/**
	 * Construct a new proxy authentication required exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause the underlying cause of the exception.
	 */
	public ProxyAutheticationRequiredException(final String message, final Throwable cause) {
		super(message, Response.Status.PROXY_AUTHENTICATION_REQUIRED, cause);
	}

	/**
	 * Construct a new proxy authentication required exception.
	 *
	 * @param response error response.
	 * @param cause the underlying cause of the exception.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 407}.
	 */
	public ProxyAutheticationRequiredException(final Response response, final Throwable cause) {
		super(validate(response, Response.Status.PROXY_AUTHENTICATION_REQUIRED), cause);
	}

	/**
	 * Construct a new proxy authentication required exception.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param response error response.
	 * @param cause the underlying cause of the exception.
	 * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 407}.
	 */
	public ProxyAutheticationRequiredException(final String message, final Response response, final Throwable cause) {
		super(message, validate(response, Response.Status.PROXY_AUTHENTICATION_REQUIRED), cause);
	}


}
