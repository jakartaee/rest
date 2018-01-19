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

package javax.ws.rs.core;

import java.io.IOException;

/**
 * An I/O exception thrown by {@link javax.ws.rs.ext.MessageBodyReader} implementations
 * when reading a zero-length message content to indicate that the message body reader
 * is not able to produce an instance representing an zero-length message content.
 * <p>
 * This exception, when thrown while reading a server request entity, is automatically
 * translated by JAX-RS server runtime into a {@link javax.ws.rs.BadRequestException}
 * wrapping the original {@code NoContentException} and rethrown for a standard processing by
 * the registered {@link javax.ws.rs.ext.ExceptionMapper exception mappers}.
 * </p>
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 * @since 2.0
 */
public class NoContentException extends IOException {
    private static final long serialVersionUID = -3082577759787473245L;

    /**
     * Construct a new {@code NoContentException} instance.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     */
    public NoContentException(String message) {
        super(message);
    }

    /**
     * Construct a new {@code NoContentException} instance.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the underlying cause of the exception.
     */
    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a new {@code NoContentException} instance.
     *
     * @param cause the underlying cause of the exception.
     */
    public NoContentException(Throwable cause) {
        super(cause);
    }
}
