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

package javax.ws.rs.ext;

import javax.ws.rs.core.Response;

/**
 * Contract for a provider that maps Java exceptions to {@link javax.ws.rs.core.Response}.
 * <p>
 * Providers implementing {@code ExceptionMapper} contract must be either programmatically
 * registered in a JAX-RS runtime or must be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} annotation to be automatically discovered
 * by the JAX-RS runtime during a provider scanning phase.
 *
 * @param <E> exception type supported by the provider.
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see Provider
 * @see javax.ws.rs.core.Response
 * @since 1.0
 */
public interface ExceptionMapper<E extends Throwable> {

    /**
     * Map an exception to a {@link javax.ws.rs.core.Response}. Returning
     * {@code null} results in a {@link javax.ws.rs.core.Response.Status#NO_CONTENT}
     * response. Throwing a runtime exception results in a
     * {@link javax.ws.rs.core.Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    Response toResponse(E exception);
}
