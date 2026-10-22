/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation
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

package ee.jakarta.tck.ws.rs.spec.context.cdi.request;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

/**
 * An {@code @ApplicationScoped} provider which echoes the request, as seen through its injected context objects, in
 * the response headers.
 */
@Provider
public class EchoFilter implements ContainerResponseFilter {

    public static final String PATH = "echo-path";
    public static final String HEADER = "echo-header";
    public static final String METHOD = "echo-method";

    @Inject
    UriInfo uriInfo;

    @Inject
    HttpHeaders httpHeaders;

    @Inject
    ResourceInfo resourceInfo;

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        responseContext.getHeaders().putSingle(PATH, uriInfo.getPath());
        responseContext.getHeaders().putSingle(HEADER, httpHeaders.getHeaderString("test-header"));
        responseContext.getHeaders().putSingle(METHOD, resourceInfo.getResourceMethod().getName());
    }
}
