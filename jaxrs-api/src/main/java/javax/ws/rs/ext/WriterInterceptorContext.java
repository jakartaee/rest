/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.ext;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Context class used by {@link javax.ws.rs.ext.WriterInterceptor}
 * to intercept calls to {@link javax.ws.rs.ext.MessageBodyWriter#writeTo}.
 * The getters and setters in this context class correspond to the
 * parameters of the intercepted method.
 *
 * @author Santiago Pericas-Geertsen
 * @author Bill Burke
 * @see WriterInterceptor
 * @see MessageBodyWriter
 * @since 2.0
 */
public interface WriterInterceptorContext extends InterceptorContext {

    /**
     * Proceed to the next interceptor in the chain.
     *
     * Interceptors MUST explicitly call this method to continue the execution chain;
     * the call to this method in the last interceptor of the chain will invoke
     * the wrapped {@link javax.ws.rs.ext.MessageBodyWriter#writeTo} method.
     *
     * @throws java.io.IOException if an IO error arises or is thrown by the wrapped
     *                             {@code MessageBodyWriter.writeTo} method.
     * @throws javax.ws.rs.WebApplicationException
     *                             thrown by the wrapped {@code MessageBodyWriter.writeTo} method.
     */
    void proceed() throws IOException, WebApplicationException;

    /**
     * Get object to be written as HTTP entity.
     *
     * @return object to be written as HTTP entity.
     */
    Object getEntity();

    /**
     * Update object to be written as HTTP entity.
     *
     * @param entity new object to be written.
     */
    void setEntity(Object entity);

    /**
     * Get the output stream for the object to be written. The JAX-RS runtime
     * is responsible for closing the output stream.
     *
     * @return output stream for the object to be written.
     */
    OutputStream getOutputStream();

    /**
     * Set a new output stream for the object to be written. For example, by wrapping
     * it with another output stream. The JAX-RS runtime is responsible for closing
     * the output stream that is set.
     *
     * @param os new output stream for the object to be written.
     */
    public void setOutputStream(OutputStream os);

    /**
     * Get mutable map of HTTP headers.
     *
     * @return map of HTTP headers.
     */
    MultivaluedMap<String, Object> getHeaders();
}
