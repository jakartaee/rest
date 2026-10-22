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

package ee.jakarta.tck.ws.rs.spec.context.cdi.qualifier;

import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Providers;
import jakarta.ws.rs.sse.Sse;

@Path("/qualifier-constructor")
public class QualifierConstructorResource {

    private final Configuration configuration;
    private final HttpHeaders httpHeaders;
    private final Providers providers;
    private final Request request;
    private final ResourceContext resourceContext;
    private final ResourceInfo resourceInfo;
    private final SecurityContext securityContext;
    private final Sse sse;
    private final UriInfo uriInfo;

    QualifierConstructorResource() {
        this(null, null, null, null, null, null, null, null, null);
    }

    @Inject
    public QualifierConstructorResource(@Context final Configuration configuration,
                                        @Context final HttpHeaders httpHeaders,
                                        @Context final Providers providers,
                                        @Context final Request request,
                                        @Context final ResourceContext resourceContext,
                                        @Context final ResourceInfo resourceInfo,
                                        @Context final SecurityContext securityContext,
                                        @Context final Sse sse,
                                        @Context final UriInfo uriInfo) {
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
    public Map<String, QualifierDescriptor> get(@Context final Configuration configuration,
                                                @Context final HttpHeaders httpHeaders,
                                                @Context final Providers providers,
                                                @Context final Request request,
                                                @Context final ResourceContext resourceContext,
                                                @Context final ResourceInfo resourceInfo,
                                                @Context final SecurityContext securityContext,
                                                @Context final Sse sse,
                                                @Context final UriInfo uriInfo) {
        final Map<String, QualifierDescriptor> result = new HashMap<>();
        result.put("constructor", QualifierDescriptor.of(this.configuration, this.httpHeaders, this.providers,
                this.request, this.resourceContext, this.resourceInfo, this.securityContext, this.sse,
                this.uriInfo));
        result.put("parameter", QualifierDescriptor.of(configuration, httpHeaders, providers, request,
                resourceContext, resourceInfo, securityContext, sse, uriInfo));
        return result;
    }
}
