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

package javax.ws.rs.client;

import java.net.URI;

import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * Client is the main entry point to the fluent API used to build and execute client
 * requests in order to consume responses returned.
 * <p/>
 * Clients are heavy-weight objects that manage the client-side communication
 * infrastructure. Initialization as well as disposal of a {@code Client} instance
 * may be a rather expensive operation. It is therefore advised to construct only
 * a small number of {@code Client} instances in the application. Client instances
 * must be {@link #close() properly closed} before being disposed to avoid leaking
 * resources.
 *
 * @author Marek Potociar
 * @see javax.ws.rs.core.Configurable
 * @since 2.0
 */
public interface Client extends Configurable<Client> {

    /**
     * Close client instance and all it's associated resources. Subsequent calls
     * have no effect and are ignored. Once the client is closed, invoking any
     * other method on the client instance would result in an {@link IllegalStateException}
     * being thrown.
     * <p/>
     * Calling this method effectively invalidates all {@link WebTarget resource targets}
     * produced by the client instance. Invoking any method on such targets once the client
     * is closed would result in an {@link IllegalStateException} being thrown.
     */
    public void close();

    /**
     * Build a new web resource target.
     *
     * @param uri web resource URI. May contain template parameters. Must not be {@code null}.
     * @return web resource target bound to the provided URI.
     * @throws IllegalArgumentException in case the supplied string is not a valid URI template.
     * @throws NullPointerException     in case the supplied argument is {@code null}.
     */
    public WebTarget target(String uri);

    /**
     * Build a new web resource target.
     *
     * @param uri web resource URI. Must not be {@code null}.
     * @return web resource target bound to the provided URI.
     * @throws NullPointerException in case the supplied argument is {@code null}.
     */
    public WebTarget target(URI uri);

    /**
     * Build a new web resource target.
     *
     * @param uriBuilder web resource URI represented as URI builder. Must not be {@code null}.
     * @return web resource target bound to the provided URI.
     * @throws NullPointerException in case the supplied argument is {@code null}.
     */
    public WebTarget target(UriBuilder uriBuilder);

    /**
     * Build a new web resource target.
     *
     * @param link link to a web resource. Must not be {@code null}.
     * @return web resource target bound to the linked web resource.
     * @throws NullPointerException in case the supplied argument is {@code null}.
     */
    public WebTarget target(Link link);

    /**
     * <p>Build an invocation builder from a link. It uses the URI and the type
     * of the link to initialize the invocation builder. The type is used as the
     * initial value for the HTTP Accept header, if present.</p>
     *
     * @param link link to build invocation from. Must not be {@code null}.
     * @return newly created invocation builder.
     * @throws NullPointerException     in case link is {@code null}.
     */
    public Invocation.Builder invocation(Link link);

    /**
     * Get the SSL context configured to be used with the current client run-time.
     *
     * @return SSL context configured to be used with the current client run-time.
     */
    public SSLContext getSslContext();

    /**
     * Get the hostname verifier configured in the client or {@code null} in case
     * no hostname verifier has been configured.
     *
     * @return client hostname verifier or {@code null} if not set.
     */
    public HostnameVerifier getHostnameVerifier();
}
