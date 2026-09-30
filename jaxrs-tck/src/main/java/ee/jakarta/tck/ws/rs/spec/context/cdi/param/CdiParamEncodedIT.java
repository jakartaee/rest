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

package ee.jakarta.tck.ws.rs.spec.context.cdi.param;

import java.net.URI;

import ee.jakarta.tck.ws.rs.common.util.CdiSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests that CDI injected parameters work with {@link jakarta.ws.rs.Encoded @Encoded}
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
@ArquillianTest
@Tag("cdi")
class CdiParamEncodedIT {

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, CdiParamEncodedIT.class.getSimpleName() + ".war")
                .addClasses(ParamApplication.class, CdiParamEncodedResource.class,
                        CdiParamNotEncodedResource.class, EncodedDescriptor.class)
                .addAsWebInfResource(CdiSupport.BEANS_XML, "beans.xml");
    }

    @Test
    void classLevelEncoded() {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path("encoded/hello%20world")
                    .queryParam("q", "hello%20world");
            final EncodedDescriptor result = client.target(uriBuilder)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(EncodedDescriptor.class);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.getQueryValue().contains("%20"),
                    () -> "Class-level @Encoded query should retain %%20, got: %s".formatted(result.getQueryValue()));
            Assertions.assertTrue(result.getPathValue().contains("%20"),
                    () -> "Class-level @Encoded path should retain %%20, got: %s".formatted(result.getPathValue()));
        }
    }

    @Test
    void fieldLevelEncoded() {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path("not-encoded/hello%20world")
                    .queryParam("q", "hello%20world")
                    .queryParam("q2", "hello%20world");
            final EncodedDescriptor result = client.target(uriBuilder)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(EncodedDescriptor.class);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.getQueryValue().contains("%20"),
                    () -> "Field-level @Encoded query should retain %%20, got: %s".formatted(result.getQueryValue()));
            Assertions.assertEquals("hello world", result.getDecodedQuery(),
                    () -> "non-encoded query should decode %%20 to space: %s".formatted(result));
            Assertions.assertEquals("hello world", result.getPathValue(),
                    () -> "non-encoded path should decode %%20 to space: %s".formatted(result));
        }
    }
}
