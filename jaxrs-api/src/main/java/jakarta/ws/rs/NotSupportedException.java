/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs;

import jakarta.ws.rs.core.Response;

/**
 * A runtime exception indicating that the client request entity media type is
 * {@link jakarta.ws.rs.core.Response.Status#UNSUPPORTED_MEDIA_TYPE not supported}.
 *
 * @author Sergey Beryozkin
 * @author Marek Potociar
 * @since 2.0
 */
public class NotSupportedException extends ClientErrorException {

    private static final long serialVersionUID = -8286622745725405656L;

    /**
     * Construct a new unsupported media type exception.
     */
    public NotSupportedException() {
        super(Response.Status.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Construct a new unsupported media type exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public NotSupportedException(final String message) {
        super(message, Response.Status.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Construct a new unsupported media type exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 415}.
     */
    public NotSupportedException(final Response response) {
        super(validate(response, Response.Status.UNSUPPORTED_MEDIA_TYPE));
    }

    /**
     * Construct a new unsupported media type exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 415}.
     */
    public NotSupportedException(final String message, final Response response) {
        super(message, validate(response, Response.Status.UNSUPPORTED_MEDIA_TYPE));
    }

    /**
     * Construct a new unsupported media type exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public NotSupportedException(final Throwable cause) {
        super(Response.Status.UNSUPPORTED_MEDIA_TYPE, cause);
    }

    /**
     * Construct a new unsupported media type exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the underlying cause of the exception.
     */
    public NotSupportedException(final String message, final Throwable cause) {
        super(message, Response.Status.UNSUPPORTED_MEDIA_TYPE, cause);
    }

    /**
     * Construct a new unsupported media type exception.
     *
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 415}.
     */
    public NotSupportedException(final Response response, final Throwable cause) {
        super(validate(response, Response.Status.UNSUPPORTED_MEDIA_TYPE), cause);
    }

    /**
     * Construct a new unsupported media type exception.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response is not HTTP {@code 415}.
     */
    public NotSupportedException(final String message, final Response response, final Throwable cause) {
        super(message, validate(response, Response.Status.UNSUPPORTED_MEDIA_TYPE), cause);
    }
}
