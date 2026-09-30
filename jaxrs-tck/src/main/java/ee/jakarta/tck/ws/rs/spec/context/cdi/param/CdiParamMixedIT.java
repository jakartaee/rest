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
import jakarta.json.JsonObject;
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
 * Tests that CDI injection works on a constructor with both parameter extraction annotations and standard CDI beans.
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
@ArquillianTest
@Tag("cdi")
class CdiParamMixedIT {

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, CdiParamMixedIT.class.getSimpleName() + ".war")
                .addClasses(ParamApplication.class, CdiParamMixedResource.class)
                .addAsWebInfResource(CdiSupport.BEANS_XML, "beans.xml");
    }

    @Test
    void mixedConstructor() {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path("mixed")
                    .queryParam("q", "testValue");
            final JsonObject result = client.target(uriBuilder)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Custom", "custom-value")
                    .get(JsonObject.class);
            Assertions.assertNotNull(result);
            Assertions.assertEquals("testValue", result.getString("query"),
                    () -> "@QueryParam should be injected in mixed constructor: %s".formatted(result));
            Assertions.assertFalse(result.getString("path").isEmpty(),
                    () -> "UriInfo should be injected in mixed constructor: %s".formatted(result));
            Assertions.assertEquals("custom-value", result.getString("customHeader"),
                    () -> "@HeaderParam should be injected in mixed constructor: %s".formatted(result));
        }
    }
}
