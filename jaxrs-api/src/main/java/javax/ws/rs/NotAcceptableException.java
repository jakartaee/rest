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
 * A runtime exception indicating that a client request is
 * {@link javax.ws.rs.core.Response.Status#NOT_ACCEPTABLE not acceptable}
 * by the server.
 *
 * @author Sergey Beryozkin
 * @author Marek Potociar
 * @since 2.0
 */
public class NotAcceptableException extends ClientErrorException {

    private static final long serialVersionUID = -1476163816796529078L;

    /**
     * Construct a new "request not acceptable" exception.
     */
    public NotAcceptableException() {
        super(Response.Status.NOT_ACCEPTABLE);
    }

    /**
     * Construct a new "request not acceptable" exception.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     */
    public NotAcceptableException(String message) {
        super(message, Response.Status.NOT_ACCEPTABLE);
    }

    /**
     * Construct a new "request not acceptable" exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 406}.
     */
    public NotAcceptableException(Response response) {
        super(validate(response, Response.Status.NOT_ACCEPTABLE));
    }

    /**
     * Construct a new "request not acceptable" exception.
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 406}.
     */
    public NotAcceptableException(String message, Response response) {
        super(message, validate(response, Response.Status.NOT_ACCEPTABLE));
    }

    /**
     * Construct a new "request not acceptable" exception.
     *
     * @param cause the underlying cause of the exception.
     */
    public NotAcceptableException(Throwable cause) {
        super(Response.Status.NOT_ACCEPTABLE, cause);
    }

    /**
     * Construct a new "request not acceptable" exception.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the underlying cause of the exception.
     */
    public NotAcceptableException(String message, Throwable cause) {
        super(message, Response.Status.NOT_ACCEPTABLE, cause);
    }

    /**
     * Construct a new "request not acceptable" exception.
     *
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 406}.
     */
    public NotAcceptableException(Response response, Throwable cause) {
        super(validate(response, Response.Status.NOT_ACCEPTABLE), cause);
    }

    /**
     * Construct a new "request not acceptable" exception.
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 406}.
     */
    public NotAcceptableException(String message, Response response, Throwable cause) {
        super(message, validate(response, Response.Status.NOT_ACCEPTABLE), cause);
    }
}
