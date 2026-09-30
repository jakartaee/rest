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

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Providers;
import jakarta.ws.rs.sse.Sse;

@Path("/inject-constructor")
public class RequiredContextConstructorResource {

    private final Application application;
    private final Configuration configuration;
    private final HttpHeaders httpHeaders;
    private final Providers providers;
    private final Request request;
    private final ResourceContext resourceContext;
    private final ResourceInfo resourceInfo;
    private final SecurityContext securityContext;
    private final Sse sse;
    private final UriInfo uriInfo;

    RequiredContextConstructorResource() {
        this.application = null;
        this.configuration = null;
        this.httpHeaders = null;
        this.providers = null;
        this.request = null;
        this.resourceContext = null;
        this.resourceInfo = null;
        this.securityContext = null;
        this.sse = null;
        this.uriInfo = null;
    }

    @Inject
    public RequiredContextConstructorResource(final Application application,
                                              final Configuration configuration,
                                              final HttpHeaders httpHeaders,
                                              final Providers providers,
                                              final Request request,
                                              final ResourceContext resourceContext,
                                              final ResourceInfo resourceInfo,
                                              final SecurityContext securityContext,
                                              final Sse sse,
                                              final UriInfo uriInfo) {
        this.application = application;
        this.configuration = configuration;
        this.httpHeaders = httpHeaders;
        this.providers = providers;
        this.request = request;
        this.resourceContext = resourceContext;
        this.resourceInfo = resourceInfo;
        this.securityContext = securityContext;
        this.sse = sse;
        this.uriInfo = uriInfo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ContextInjectionDescriptor get() {
        return ContextInjectionDescriptor.of(application, configuration, httpHeaders, providers,
                request, resourceContext, resourceInfo, securityContext, sse, uriInfo);
    }
}
