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

package ee.jakarta.tck.ws.rs.spec.context.cdi;

import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Providers;
import jakarta.ws.rs.sse.Sse;

public record ContextInjectionDescriptor(
        String application,
        String configuration,
        String httpHeaders,
        String providers,
        String request,
        String resourceContext,
        String resourceInfo,
        String securityContext,
        String sse,
        String uriInfo) {

    public static ContextInjectionDescriptor of(final Application application,
                                                final Configuration configuration,
                                                final HttpHeaders httpHeaders,
                                                final Providers providers,
                                                final Request request,
                                                final ResourceContext resourceContext,
                                                final ResourceInfo resourceInfo,
                                                final SecurityContext securityContext,
                                                final Sse sse,
                                                final UriInfo uriInfo) {
        return new ContextInjectionDescriptor(
                application == null ? null : application.getClass().getName(),
                configuration == null ? null : String.valueOf(configuration.getRuntimeType()),
                httpHeaders == null ? null : httpHeaders.getHeaderString("test-header"),
                providers == null ? null : providers.getClass().getName(),
                request == null ? null : request.getMethod(),
                resourceContext == null ? null : resourceContext.getClass().getName(),
                resourceInfo == null ? null : resourceInfo.getResourceMethod().getName(),
                securityContext == null ? null : String.valueOf(securityContext.isSecure()),
                sse == null ? null : sse.getClass().getName(),
                uriInfo == null ? null : uriInfo.getPath());
    }
}
