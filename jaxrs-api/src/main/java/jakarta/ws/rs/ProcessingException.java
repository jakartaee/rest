/*
 * Copyright (c) 2011, 2019 Oracle and/or its affiliates. All rights reserved.
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

/**
 * A base JAX-RS runtime processing exception.
 *
 * The exception of this type is thrown during HTTP request or response processing, to signal a runtime processing
 * failure. Typical classes of failures covered by {@code ProcessingException} include
 * <ul>
 * <li>failures in filter or interceptor chain execution,</li>
 * <li>errors caused by missing message body readers or writers for the particular Java type and media type
 * combinations,</li>
 * <li>propagated {@link java.io.IOException IO exceptions} thrown by entity {@link jakarta.ws.rs.ext.MessageBodyReader
 * readers} and {@link jakarta.ws.rs.ext.MessageBodyWriter writers} during entity serialization and de-serialization.</li>
 * </ul>
 * as well as any other JAX-RS runtime processing errors. The exception message or nested {@link Throwable} cause SHOULD
 * contain additional information about the reason of the processing failure.
 * <p>
 * Note that the exception is used to indicate (internal) JAX-RS processing errors. It is not used to indicate HTTP
 * error response states. A HTTP error response is represented by a {@link jakarta.ws.rs.WebApplicationException} class or
 * one of its sub-classes.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public class ProcessingException extends RuntimeException {

    private static final long serialVersionUID = -4232431597816056514L;

    /**
     * Constructs a new JAX-RS runtime processing exception with the specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of
     * {@code cause}). This constructor is useful for runtime exceptions that are little more than wrappers for other
     * throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null} value
     * is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ProcessingException(final Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructs a new JAX-RS runtime processing exception with the specified detail message and cause.
     * </p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this runtime
     * exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null} value
     * is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new JAX-RS runtime processing exception with the specified detail message. The cause is not initialized,
     * and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public ProcessingException(final String message) {
        super(message);
    }
}
