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

/**
 * Interface for message body reader interceptors that wrap around calls
 * to {@link javax.ws.rs.ext.MessageBodyReader#readFrom}.
 * <p>
 * Providers implementing {@code ReaderInterceptor} contract must be either programmatically
 * registered in an API runtime or must be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} annotation to be automatically discovered
 * by the runtime during a provider scanning phase.
 * Message body interceptor instances may also be discovered and
 * bound {@link javax.ws.rs.container.DynamicFeature dynamically} to particular resource methods.
 * </p>
 *
 * @author Santiago Pericas-Geertsen
 * @author Bill Burke
 * @author Marek Potociar
 * @see MessageBodyReader
 * @since 2.0
 */
public interface ReaderInterceptor {

    /**
     * Interceptor method wrapping calls to {@link MessageBodyReader#readFrom} method.
     *
     * The parameters of the wrapped method called are available from {@code context}.
     * Implementations of this method SHOULD explicitly call {@link ReaderInterceptorContext#proceed}
     * to invoke the next interceptor in the chain, and ultimately the wrapped
     * {@link MessageBodyReader#readFrom} method.
     *
     * @param context invocation context.
     * @return result of next interceptor invoked or the wrapped method if last interceptor in chain.
     * @throws java.io.IOException if an IO error arises or is thrown by the wrapped
     *                             {@code MessageBodyReader.readFrom} method.
     * @throws javax.ws.rs.WebApplicationException
     *                             thrown by the wrapped {@code MessageBodyReader.readFrom} method.
     */
    public Object aroundReadFrom(ReaderInterceptorContext context)
            throws java.io.IOException, javax.ws.rs.WebApplicationException;
}
