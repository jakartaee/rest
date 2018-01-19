/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs;

import javax.ws.rs.core.Response;

/**
 * A runtime exception indicating a {@link javax.ws.rs.core.Response.Status#BAD_REQUEST
 * bad client request}.
 *
 * @author Sergey Beryozkin
 * @author Marek Potociar
 * @since 2.0
 */
public class BadRequestException extends ClientErrorException {

    private static final long serialVersionUID = 7264647684649480265L;

    /**
     * Construct a new bad client request exception.
     */
    public BadRequestException() {
        super(Response.Status.BAD_REQUEST);
    }

    /**
     * Construct a new bad client request exception.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     */
    public BadRequestException(String message) {
        super(message, Response.Status.BAD_REQUEST);
    }

    /**
     * Construct a new bad client request exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 400}.
     */
    public BadRequestException(Response response) {
        super(validate(response, Response.Status.BAD_REQUEST));
    }

    /**
     * Construct a new bad client request exception.
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 400}.
     */
    public BadRequestException(String message, Response response) {
        super(message, validate(response, Response.Status.BAD_REQUEST));
    }

    /**
     * Construct a new bad client request exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public BadRequestException(Throwable cause) {
        super(Response.Status.BAD_REQUEST, cause);
    }

    /**
     * Construct a new bad client request exception.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the underlying cause of the exception.
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, Response.Status.BAD_REQUEST, cause);
    }

    /**
     * Construct a new bad client request exception.
     *
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 400}.
     */
    public BadRequestException(Response response, Throwable cause) {
        super(validate(response, Response.Status.BAD_REQUEST), cause);
    }

    /**
     * Construct a new bad client request exception.
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 400}.
     */
    public BadRequestException(String message, Response response, Throwable cause) {
        super(message, validate(response, Response.Status.BAD_REQUEST), cause);
    }
}
