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

/**
 * Contract for a provider that supplies context information to resource
 * classes and other providers.
 *
 * A {@code ContextResolver} implementation may be annotated
 * with {@link javax.ws.rs.Produces} to restrict the media types for
 * which it will be considered suitable.
 * <p>
 * Providers implementing {@code ContextResolver} contract must be either programmatically
 * registered in a JAX-RS runtime or must be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} annotation to be automatically discovered
 * by the JAX-RS runtime during a provider scanning phase.
 * </p>
 *
 * @param <T> type of the context
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see javax.ws.rs.core.Context
 * @see Providers#getContextResolver(Class, javax.ws.rs.core.MediaType)
 * @see Provider
 * @see javax.ws.rs.Produces
 * @since 1.0
 */
public interface ContextResolver<T> {

    /**
     * Get a context of type {@code T} that is applicable to the supplied
     * type.
     *
     * @param type the class of object for which a context is desired
     * @return a context for the supplied type or {@code null} if a
     *         context for the supplied type is not available from this provider.
     */
    T getContext(Class<?> type);
}
