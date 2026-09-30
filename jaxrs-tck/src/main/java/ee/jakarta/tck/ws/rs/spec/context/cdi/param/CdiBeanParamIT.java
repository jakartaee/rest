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
 * Tests that a {@link jakarta.ws.rs.BeanParam @BeanParam} works with CDI.
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
@ArquillianTest
@Tag("cdi")
class CdiBeanParamIT {

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, CdiBeanParamIT.class.getSimpleName() + ".war")
                .addClasses(ParamApplication.class, CdiBeanParamResource.class, SearchParams.class)
                .addAsWebInfResource(CdiSupport.BEANS_XML, "beans.xml");
    }

    @Test
    void beanParam() {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path("bean-param")
                    .queryParam("q", "testValue")
                    .queryParam("limit", 25);
            final SearchParams result = client.target(uriBuilder)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Accept-Language", "en-US")
                    .get(SearchParams.class);
            Assertions.assertNotNull(result);
            Assertions.assertEquals("testValue", result.getQuery(),
                    () -> "Expected a result of 'testValue' in the query parameter: %s".formatted(result));
            Assertions.assertEquals(25, result.getLimit(),
                    () -> "Expected a result of '25' in the limit parameter: %s".formatted(result));
            Assertions.assertEquals("en-US", result.getLanguage(),
                    () -> "Expected a result of 'en-US' in the Accepted-Language parameter: %s".formatted(result));
        }
    }

    @Test
    void beanParamDefaults() {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path("bean-param")
                    .queryParam("q", "test");
            final SearchParams result = client.target(uriBuilder)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(SearchParams.class);
            Assertions.assertNotNull(result);
            Assertions.assertEquals("test", result.getQuery(),
                    () -> "Expected a result of 'test' in the query parameter: %s".formatted(result));
            Assertions.assertEquals(10, result.getLimit(),
                    () -> "Expected a result of '10', @DefaultValue, in the limit parameter: %s".formatted(result));
            Assertions.assertNull(result.getLanguage(), () -> "Accept-Language header should be null: %s".formatted(result));
        }
    }
}
