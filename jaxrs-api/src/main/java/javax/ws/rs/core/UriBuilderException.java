/*
 * Copyright (c) 2010, 2017 Oracle and/or its affiliates. All rights reserved.
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

/**
 * A runtime exception thrown by {@link UriBuilder#build(Object...)} methods when
 * a {@link java.net.URI} cannot be constructed based on the current state of the
 * builder.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @since 1.0
 */
public class UriBuilderException extends java.lang.RuntimeException {

    private static final long serialVersionUID = 956255913370721193L;

    /**
     * Creates a new instance of <code>UriBuilderException</code> without detail message.
     */
    public UriBuilderException() {
    }

    /**
     * Constructs an instance of <code>UriBuilderException</code> with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public UriBuilderException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>UriBuilderException</code> with the specified detail message and cause.
     * <p>Note that the detail message associated with cause is not automatically incorporated in this exception's detail message.
     *
     * @param msg   the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UriBuilderException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of (<code>cause==null ? null : cause.toString()</code>) (which typically contains
     * the class and detail message of cause). This constructor is useful
     * for exceptions that are little more than wrappers for other throwables.
     *
     * @param cause the original exception
     */
    public UriBuilderException(Throwable cause) {
        super(cause);
    }
}
