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

package ee.jakarta.tck.ws.rs.spec.context.cdi.client;

import java.util.Objects;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.Providers;

/**
 * An {@code @ApplicationScoped} provider used by the server runtime and registered with several clients. It
 * describes the {@code Configuration} and {@code Providers} injected into it in a header, which is a request header
 * for a client invocation and a response header for a server request.
 */
@Provider
public class ConfigurationFilter implements ClientRequestFilter, ContainerResponseFilter {

    public static final String CLIENT_NAME = "ee.jakarta.tck.ws.rs.spec.context.cdi.client.name";
    public static final String CLIENT_HEADER = "client-runtime";
    public static final String SERVER_HEADER = "server-runtime";

    @Inject
    Configuration configuration;

    @Inject
    Providers providers;

    @Override
    public void filter(final ClientRequestContext requestContext) {
        requestContext.getHeaders().putSingle(CLIENT_HEADER, describe());
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        responseContext.getHeaders().putSingle(SERVER_HEADER, describe());
    }

    /**
     * Describes the runtime as {@code runtime-type:client-name-property:resolved-client-name}.
     */
    private String describe() {
        final ContextResolver<NameResolver.Name> resolver = providers.getContextResolver(NameResolver.Name.class,
                MediaType.WILDCARD_TYPE);
        return "%s:%s:%s".formatted(configuration.getRuntimeType(),
                Objects.requireNonNullElse(configuration.getProperty(CLIENT_NAME), "none"),
                resolver == null ? "none" : resolver.getContext(NameResolver.Name.class).value());
    }
}
