/*
 * Copyright (c) 2013, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.client;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

/**
 * JAX-RS client-side runtime processing exception thrown to indicate that
 * response processing has failed (e.g. in a filter chain or during message
 * entity de-serialization). The exception contains the nested {@link Response}
 * instance for which the runtime response processing failed.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 * @since 2.0
 */
public class ResponseProcessingException extends ProcessingException {

    private static final long serialVersionUID = -4923161617935731839L;

    private final Response response;

    /**
     * Constructs a new JAX-RS runtime response processing exception
     * for a specific {@link Response response} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for runtime exceptions that are little more
     * than wrappers for other throwables.
     *
     * @param response the response instance for which the processing failed.
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method). (A {@code null} value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     */
    public ResponseProcessingException(Response response, Throwable cause) {
        super(cause);
        this.response = response;
    }

    /**
     * Constructs a new JAX-RS runtime response processing exception with the specified detail
     * message and cause.
     * <p/>
     * Note that the detail message associated with {@code cause} is <i>not</i>
     * automatically incorporated in this runtime exception's detail message.
     *
     * @param response the response instance for which the processing failed.
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method). (A {@code null} value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     */
    public ResponseProcessingException(Response response, String message, Throwable cause) {
        super(message, cause);
        this.response = response;
    }

    /**
     * Constructs a new JAX-RS runtime processing exception with the specified detail
     * message. The cause is not initialized, and may subsequently be initialized
     * by a call to {@link #initCause}.
     *
     * @param response the response instance for which the processing failed.
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     */
    public ResponseProcessingException(Response response, String message) {
        super(message);
        this.response = response;
    }

    /**
     * Get the HTTP response for which the processing has failed.
     *
     * @return the HTTP response.
     */
    public Response getResponse() {
        return response;
    }
}
