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

import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Providers;
import jakarta.ws.rs.sse.Sse;

/**
 * Describes the injected context objects by their behavior, so that context objects resolved through different
 * injection points can be compared regardless of how the implementation provides them.
 *
 * @param configuration   the runtime type of the configuration
 * @param httpHeaders     the value of the {@code test-header} header
 * @param providers       the {@link MessageBodyWriter} found for a {@link String}
 * @param request         the request method
 * @param resourceContext the result of initializing an object
 * @param resourceInfo    the name of the matched resource method
 * @param securityContext whether the request is secure
 * @param sse             the data of an event created with the {@link Sse}
 * @param uriInfo         the path of the request
 */
public record QualifierDescriptor(
        String configuration,
        String httpHeaders,
        String providers,
        String request,
        String resourceContext,
        String resourceInfo,
        String securityContext,
        String sse,
        String uriInfo) {

    public static QualifierDescriptor of(final Configuration configuration,
                                         final HttpHeaders httpHeaders,
                                         final Providers providers,
                                         final Request request,
                                         final ResourceContext resourceContext,
                                         final ResourceInfo resourceInfo,
                                         final SecurityContext securityContext,
                                         final Sse sse,
                                         final UriInfo uriInfo) {
        return new QualifierDescriptor(
                configuration == null ? null : String.valueOf(configuration.getRuntimeType()),
                httpHeaders == null ? null : httpHeaders.getHeaderString("test-header"),
                providers == null ? null : describe(providers.getMessageBodyWriter(String.class, String.class, null,
                        MediaType.TEXT_PLAIN_TYPE)),
                request == null ? null : request.getMethod(),
                resourceContext == null ? null : resourceContext.initResource(new StringBuilder("initialized"))
                        .toString(),
                resourceInfo == null ? null : resourceInfo.getResourceMethod().getName(),
                securityContext == null ? null : String.valueOf(securityContext.isSecure()),
                sse == null ? null : String.valueOf(sse.newEvent("sse").getData()),
                uriInfo == null ? null : uriInfo.getPath());
    }

    private static String describe(final MessageBodyWriter<String> writer) {
        return writer == null ? "none" : writer.getClass().getName();
    }
}
