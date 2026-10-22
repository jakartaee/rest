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

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
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
 * Tests the {@code @Default} {@code Client} bean provided by the implementation when the application defines no
 * {@code ClientBuilder} bean.
 */
@ArquillianTest
@Tag("cdi")
class DefaultClientIT {

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-default-client.war")
                .addClasses(ClientApplication.class,
                        DefaultClientResource.class);
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
     * The {@code @Default} {@code Client} bean is {@code @ApplicationScoped}.
     */
    @Test
    void applicationScoped() {
        Assertions.assertEquals(ApplicationScoped.class.getName(), get("default-client/scope"),
                "The @Default Client bean is not @ApplicationScoped");
    }

    /**
     * The injected {@code Client} can invoke a resource.
     */
    @Test
    void injectedClient() {
        Assertions.assertEquals("echo", get("default-client/call"),
                "The injected Client did not invoke the resource");
    }

    private String get(final String path) {
        final Response response = client.target(UriBuilder.fromUri(baseUri).path(path))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        final String entity = response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), entity);
        return entity;
    }
}
