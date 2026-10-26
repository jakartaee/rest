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

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Providers;
import jakarta.ws.rs.sse.Sse;

@Path("/qualifier")
public class QualifierResource {

    @Inject
    @Context
    Configuration qualifiedConfiguration;

    @Inject
    @Context
    HttpHeaders qualifiedHttpHeaders;

    @Inject
    @Context
    Providers qualifiedProviders;

    @Inject
    @Context
    Request qualifiedRequest;

    @Inject
    @Context
    ResourceContext qualifiedResourceContext;

    @Inject
    @Context
    ResourceInfo qualifiedResourceInfo;

    @Inject
    @Context
    SecurityContext qualifiedSecurityContext;

    @Inject
    @Context
    Sse qualifiedSse;

    @Inject
    @Context
    UriInfo qualifiedUriInfo;

    @Inject
    Configuration configuration;

    @Inject
    HttpHeaders httpHeaders;

    @Inject
    Providers providers;

    @Inject
    Request request;

    @Inject
    ResourceContext resourceContext;

    @Inject
    ResourceInfo resourceInfo;

    @Inject
    SecurityContext securityContext;

    @Inject
    Sse sse;

    @Inject
    UriInfo uriInfo;

    @Inject
    @Any
    Instance<Object> instance;

    @Inject
    @Context
    Application qualifiedApplication;

    @Inject
    Application application;

    @Inject
    QualifierApplication subclass;

    private UriInfo setterUriInfo;

    @Inject
    @Context
    public void setSetterUriInfo(final UriInfo setterUriInfo) {
        this.setterUriInfo = setterUriInfo;
    }

    @GET
    @Path("/fields")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, QualifierDescriptor> fields() {
        final Map<String, QualifierDescriptor> result = new HashMap<>();
        result.put("qualified", QualifierDescriptor.of(qualifiedConfiguration, qualifiedHttpHeaders,
                qualifiedProviders, qualifiedRequest, qualifiedResourceContext, qualifiedResourceInfo,
                qualifiedSecurityContext, qualifiedSse, qualifiedUriInfo));
        result.put("default", QualifierDescriptor.of(configuration, httpHeaders, providers, request,
                resourceContext, resourceInfo, securityContext, sse, uriInfo));
        return result;
    }

    @GET
    @Path("/literal")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, QualifierDescriptor> literal() {
        final Map<String, QualifierDescriptor> result = new HashMap<>();
        result.put("default", QualifierDescriptor.of(configuration, httpHeaders, providers, request,
                resourceContext, resourceInfo, securityContext, sse, uriInfo));
        result.put("literal", QualifierDescriptor.of(select(Configuration.class), select(HttpHeaders.class),
                select(Providers.class), select(Request.class), select(ResourceContext.class),
                select(ResourceInfo.class), select(SecurityContext.class), select(Sse.class),
                select(UriInfo.class)));
        return result;
    }

    @GET
    @Path("/literal/application")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> literalApplication() {
        final Map<String, String> result = new HashMap<>();
        result.put("subclass", subclass == null ? null : subclass.instanceId());
        result.put("literal", instanceId(select(Application.class)));
        return result;
    }

    @GET
    @Path("/setter")
    @Produces(MediaType.TEXT_PLAIN)
    public String setter() {
        return setterUriInfo == null ? null : setterUriInfo.getPath();
    }

    @GET
    @Path("/application")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> application(final Application parameter) {
        final Map<String, String> result = new HashMap<>();
        result.put("qualified", instanceId(qualifiedApplication));
        result.put("default", instanceId(application));
        result.put("subclass", subclass == null ? null : subclass.instanceId());
        result.put("parameter", instanceId(parameter));
        return result;
    }

    private <T> T select(final Class<T> type) {
        return instance.select(type, Context.Literal.INSTANCE).get();
    }

    private static String instanceId(final Application application) {
        return application == null ? null : String.valueOf(application.getProperties()
                .get(QualifierApplication.INSTANCE_ID));
    }
}
