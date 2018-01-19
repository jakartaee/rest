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

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;

/**
 * A type that may be used as a resource method return value or as the entity
 * in a {@link Response} when the application wishes to stream the output.
 * This is a lightweight alternative to a
 * {@link javax.ws.rs.ext.MessageBodyWriter}.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see javax.ws.rs.ext.MessageBodyWriter
 * @see javax.ws.rs.core.Response
 * @since 1.0
 */
public interface StreamingOutput {

    /**
     * Called to write the message body.
     *
     * @param output the OutputStream to write to.
     * @throws java.io.IOException if an IO error is encountered
     * @throws javax.ws.rs.WebApplicationException
     *                             if a specific
     *                             HTTP error response needs to be produced. Only effective if thrown prior
     *                             to any bytes being written to output.
     */
    void write(OutputStream output) throws IOException, WebApplicationException;
}
