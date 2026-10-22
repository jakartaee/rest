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

package ee.jakarta.tck.ws.rs.spec.context.cdi.services;

import java.net.URI;
import java.util.Map;

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * Tests that a {@code Feature} loaded with {@link java.util.ServiceLoader} which is also discovered as a provider
 * is registered only as the loaded instance.
 */
@ArquillianTest
@Tag("cdi")
class ServiceLoadedFeatureIT {

    private static final GenericType<Map<String, String>> RESULTS = new GenericType<>() {
    };

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-services.war")
                .addClasses(ServicesApplication.class,
                        ServicesResource.class,
                        ServiceLoadedFeature.class)
                .addAsResource(new StringAsset(ServiceLoadedFeature.class.getName()),
                        "META-INF/services/%s".formatted(Feature.class.getName()));
    }

    @BeforeAll
    static void createClient() {
        client = ClientBuilder.newClient();
    }

    @AfterAll
    static void closeClient() {
        if (client != null) {
            client.close();
        }
    }

    @BeforeEach
    void logStartTest(final TestInfo testInfo) {
        TestUtil.logMsg("STARTING TEST : %s".formatted(testInfo.getDisplayName()));
    }

    @AfterEach
    void logFinishTest(final TestInfo testInfo) {
        TestUtil.logMsg("FINISHED TEST : %s".formatted(testInfo.getDisplayName()));
    }

    @Test
    void registeredOnceAsLoadedInstance() {
        final Response response = client.target(UriBuilder.fromUri(baseUri).path("services"))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> response.readEntity(String.class));
        final Map<String, String> results = response.readEntity(RESULTS);
        Assertions.assertAll(
                () -> Assertions.assertEquals("service-loader", results.get("loadedBy"),
                        () -> "The feature was not registered as the instance loaded with ServiceLoader: %s"
                                .formatted(results)),
                () -> Assertions.assertEquals("1", results.get("configured"),
                        () -> "The feature was not configured exactly once: %s".formatted(results))
        );
    }
}
