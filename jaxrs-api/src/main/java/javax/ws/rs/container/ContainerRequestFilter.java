/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.container;

import java.io.IOException;

/**
 * An extension interface implemented by container request filters.
 * <p>
 * By default, i.e. if no {@link javax.ws.rs.NameBinding name binding} is applied
 * to the filter implementation class, the filter instance is applied globally,
 * however only after the incoming request has been matched to a particular resource
 * by JAX-RS runtime.
 * If there is a {@link javax.ws.rs.NameBinding &#64;NameBinding} annotation
 * applied to the filter, the filter will also be executed at the <i>post-match</i>
 * request extension point, but only in case the matched {@link javax.ws.rs.HttpMethod
 * resource or sub-resource method} is bound to the same name-binding annotation.
 * </p>
 * <p>
 * In case the filter should be applied at the <i>pre-match</i> extension point,
 * i.e. before any request matching has been performed by JAX-RS runtime, the
 * filter MUST be annotated with a {@link PreMatching &#64;PreMatching} annotation.
 * </p>
 * <p>
 * Use a pre-match request filter to update the input to the JAX-RS matching algorithm,
 * e.g., the HTTP method, Accept header, return cached responses etc. Otherwise,
 * the use of a request filter invoked at the <i>post-match</i> request extension point
 * (after a successful resource method matching) is recommended.
 * </p>
 * <p>
 * Filters implementing this interface must be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} to be discovered by the JAX-RS
 * runtime. Container request filter instances may also be discovered and
 * bound {@link DynamicFeature dynamically} to particular resource methods.
 * </p>
 *
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 * @see PreMatching
 * @see javax.ws.rs.container.ContainerResponseFilter
 * @since 2.0
 */
public interface ContainerRequestFilter {

    /**
     * Filter method called before a request has been dispatched to a resource.
     *
     * <p>
     * Filters in the filter chain are ordered according to their {@code javax.annotation.Priority}
     * class-level annotation value.
     * If a request filter produces a response by calling {@link ContainerRequestContext#abortWith}
     * method, the execution of the (either pre-match or post-match) request filter
     * chain is stopped and the response is passed to the corresponding response
     * filter chain (either pre-match or post-match). For example, a pre-match
     * caching filter may produce a response in this way, which would effectively
     * skip any post-match request filters as well as post-match response filters.
     * Note however that a responses produced in this manner would still be processed
     * by the pre-match response filter chain.
     * </p>
     *
     * @param requestContext request context.
     * @throws IOException if an I/O exception occurs.
     * @see PreMatching
     */
    public void filter(ContainerRequestContext requestContext) throws IOException;
}
