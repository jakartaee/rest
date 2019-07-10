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
 * An extension interface implemented by container response filters.
 * <p>
 * By default, i.e. if no {@link javax.ws.rs.NameBinding name binding} is applied
 * to the filter implementation class, the filter instance is applied globally to
 * any outgoing response.
 * If there is a {@code @NameBinding} annotation applied to the filter, the filter
 * will only be executed for a response for which the request has been matched to
 * a {@link javax.ws.rs.HttpMethod resource or sub-resource method} AND the method
 * or the whole custom {@link javax.ws.rs.core.Application Application} class
 * is bound to the same name-binding annotation.
 * </p>
 * <p>
 * Implement a name-bound response filter in cases when you want limit the filter
 * functionality to a matched resource or resource method. In other cases,
 * when the filter should be applied globally to any outgoing response, implement an
 * unbound, global response filter.
 * </p>
 * <p>
 * Filters implementing this interface must be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} to be discovered by the
 * runtime. Container response filter instances may also be discovered and
 * bound {@link DynamicFeature dynamically} to particular resource methods.
 * </p>
 *
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 * @see javax.ws.rs.container.ContainerRequestFilter
 * @since 2.0
 */
public interface ContainerResponseFilter {

    /**
     * Filter method called after a response has been provided for a request
     * (either by a {@link ContainerRequestFilter request filter} or by a
     * matched resource method.
     * <p>
     * Filters in the filter chain are ordered according to their {@code javax.annotation.Priority}
     * class-level annotation value.
     * </p>
     *
     * @param requestContext  request context.
     * @param responseContext response context.
     * @throws IOException if an I/O exception occurs.
     */
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException;
}
