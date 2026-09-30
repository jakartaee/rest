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

import ee.jakarta.tck.ws.rs.common.provider.StringBeanEntityProvider;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.Providers;

@Provider
public class StringBeanEntityProviderWithInjectables extends StringBeanEntityProvider {

    @Inject
    Application application;

    @Inject
    UriInfo uriInfo;

    @Inject
    Request request;

    @Inject
    HttpHeaders httpHeaders;

    @Inject
    SecurityContext securityContext;

    @Inject
    Providers providers;

    @Inject
    ResourceContext resourceContext;

    @Inject
    Configuration configuration;

    public ContextInjectionDescriptor describe() {
        return ContextInjectionDescriptor.of(application, configuration, httpHeaders, providers,
                request, resourceContext, null, securityContext, null, uriInfo);
    }
}
