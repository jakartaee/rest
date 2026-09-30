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

package ee.jakarta.tck.ws.rs.spec.client.inject;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Providers;

/**
 * A client-side filter that verifies {@code @Inject} works for {@link Configuration} and {@link Providers} as a
 * replacement for the deprecated {@code @Context} annotation on the client side. The Jakarta REST runtime is
 * responsible for resolving these injection points regardless of whether a CDI container is present.
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
public class ClientInjectFilter implements ClientRequestFilter {

    @Inject
    private Configuration configuration;

    @Inject
    private Providers providers;

    @Override
    public void filter(final ClientRequestContext requestContext) {
        final ClientInjectionDescriptor descriptor = ClientInjectionDescriptor.of(configuration, providers);
        requestContext.abortWith(Response.ok(descriptor).type(MediaType.APPLICATION_JSON_TYPE).build());
    }
}
