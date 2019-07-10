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

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;

/**
 * Context class used by {@link javax.ws.rs.ext.ReaderInterceptor}
 * to intercept calls to (@link javax.ws.rs.ext.MessageBodyReader#readFrom}.
 * The getters and setters in this context class correspond to the
 * parameters of the intercepted method.
 *
 * @author Santiago Pericas-Geertsen
 * @author Bill Burke
 * @see ReaderInterceptor
 * @see MessageBodyReader
 * @since 2.0
 */
public interface ReaderInterceptorContext extends InterceptorContext {

    /**
     * Proceed to the next interceptor in the chain. Return the result of the
     * next interceptor invoked. Interceptors MUST explicitly call this method
     * to continue the execution chain; the call to this method in the
     * last interceptor of the chain will invoke the wrapped
     * {@link javax.ws.rs.ext.MessageBodyReader#readFrom}.
     *
     * @return result of next interceptor invoked.
     * @throws IOException if an IO error arises or is
     *                     thrown by the wrapped {@code MessageBodyReader.readFrom} method.
     * @throws javax.ws.rs.WebApplicationException
     *                     thrown by the wrapped {@code MessageBodyReader.readFrom} method.
     */
    public Object proceed() throws IOException, WebApplicationException;

    /**
     * Get the input stream of the object to be read. The runtime is responsible
     * for closing the input stream.
     *
     * @return input stream of the object to be read.
     */
    public InputStream getInputStream();

    /**
     * Set the input stream of the object to be read. For example, by wrapping
     * it with another input stream. The runtime is responsible for closing
     * the input stream that is set.
     *
     * @param is new input stream.
     */
    public void setInputStream(InputStream is);

    /**
     * Get mutable map of HTTP headers.
     * <p>
     * Note that while the headers are mutable, a {@link ReaderInterceptor reader interceptor}
     * should typically roll-back any header modifications once the call to {@link #proceed()
     * context.proceed()} returns, to avoid externally visible side-effects of the interceptor
     * invocation.
     * </p>
     *
     * @return map of HTTP headers.
     */
    public MultivaluedMap<String, String> getHeaders();
}
