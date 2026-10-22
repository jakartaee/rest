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

import java.net.URI;
import java.util.Map;

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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
 * Tests the {@code @Default} {@code Client} bean built from the application's {@code @Default}
 * {@code ClientBuilder} bean, and the {@code Configuration} and {@code Providers} injected into a provider used by
 * the server runtime and several clients.
 */
@ArquillianTest
@Tag("cdi")
class ProducedClientIT {

    private static final GenericType<Map<String, String>> RESULTS = new GenericType<>() {
    };

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-produced-client.war")
                .addClasses(ClientApplication.class,
                        ProducedClientResource.class,
                        ClientBuilderProducer.class,
                        ConfigurationFilter.class,
                        NameResolver.class,
                        NameResolver.Name.class);
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

    /**
     * The {@code @Default} {@code Client} is built from the {@code @Default} {@code ClientBuilder} bean, which is
     * obtained only once.
     */
    @Test
    void builtFromClientBuilder() {
        for (int i = 0; i < 2; i++) {
            final Map<String, String> results = get("produced-client/produced").readEntity(RESULTS);
            Assertions.assertEquals("CLIENT:produced:produced", results.get("echo"),
                    () -> "The @Default Client was not built from the @Default ClientBuilder bean: %s"
                            .formatted(results));
            Assertions.assertEquals("1", results.get("count"),
                    () -> "The @Default ClientBuilder bean was not obtained exactly once: %s".formatted(results));
        }
    }

    /**
     * A provider instance registered with several clients, and used by the server runtime, obtains the
     * {@code Configuration} and {@code Providers} of the runtime invoking it on each invocation.
     */
    @Test
    void sharedProvider() {
        final Response response = get("produced-client/shared");
        final String server = response.getHeaderString(ConfigurationFilter.SERVER_HEADER);
        final Map<String, String> results = response.readEntity(RESULTS);
        Assertions.assertAll(
                () -> Assertions.assertEquals("CLIENT:produced:produced", results.get("produced"),
                        () -> "The provider did not obtain the Configuration and Providers of the @Default Client: %s"
                                .formatted(results)),
                () -> Assertions.assertEquals("CLIENT:second:second", results.get("second"),
                        () -> "The provider did not obtain the Configuration and Providers of the second Client: %s"
                                .formatted(results)),
                () -> Assertions.assertEquals("SERVER:none:none", server,
                        "The provider did not obtain the Configuration and Providers of the server runtime")
        );
    }

    private Response get(final String path) {
        final Response response = client.target(UriBuilder.fromUri(baseUri).path(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            Assertions.fail("Unexpected status %d for %s: %s".formatted(response.getStatus(), path,
                    response.readEntity(String.class)));
        }
        return response;
    }
}
