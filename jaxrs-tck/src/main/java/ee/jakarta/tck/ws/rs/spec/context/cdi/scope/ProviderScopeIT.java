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

package ee.jakarta.tck.ws.rs.spec.context.cdi.scope;

import java.net.URI;
import java.util.Map;

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
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
 * Tests the scopes of {@code Application} subclasses and providers which are CDI beans.
 */
@ArquillianTest
@Tag("cdi")
class ProviderScopeIT {

    private static final GenericType<Map<String, String>> SCOPES = new GenericType<>() {
    };

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-scope.war")
                .addClasses(ScopeApplication.class,
                        ScopeResource.class,
                        InstanceIdFilter.class,
                        DefaultScopeFilter.class,
                        SingletonScopeFilter.class);
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
     * An {@code Application} subclass and a provider which do not declare a scope are {@code @ApplicationScoped}.
     * The {@code @Singleton} scope declared by a provider is retained.
     */
    @Test
    void scopes() {
        final Response response = client.target(generateUri("scopes"))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> response.readEntity(String.class));
        final Map<String, String> scopes = response.readEntity(SCOPES);
        Assertions.assertAll(
                () -> Assertions.assertEquals(ApplicationScoped.class.getName(),
                        scopes.get(ScopeApplication.class.getSimpleName()),
                        () -> "Application subclass without a declared scope is not @ApplicationScoped: %s".formatted(scopes)),
                () -> Assertions.assertEquals(ApplicationScoped.class.getName(),
                        scopes.get(DefaultScopeFilter.class.getSimpleName()),
                        () -> "Provider without a declared scope is not @ApplicationScoped: %s".formatted(scopes)),
                () -> Assertions.assertEquals(Singleton.class.getName(),
                        scopes.get(SingletonScopeFilter.class.getSimpleName()),
                        () -> "@Singleton provider is not @Singleton: %s".formatted(scopes))
        );
    }

    /**
     * The implementation uses a single instance of each provider for all requests.
     */
    @Test
    void singleInstance() {
        final Response first = getInstance();
        final Response second = getInstance();
        Assertions.assertAll(
                () -> assertSameInstance(first, second, DefaultScopeFilter.HEADER),
                () -> assertSameInstance(first, second, SingletonScopeFilter.HEADER)
        );
    }

    private Response getInstance() {
        final Response response = client.target(generateUri("scopes/instance"))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        final String entity = response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), entity);
        return response;
    }

    private URI generateUri(final String path) {
        return UriBuilder.fromUri(baseUri).path(path).build();
    }

    private static void assertSameInstance(final Response first, final Response second, final String header) {
        final String firstId = first.getHeaderString(header);
        Assertions.assertNotNull(firstId, () -> "Header %s not found, the filter was not invoked".formatted(header));
        Assertions.assertEquals(firstId, second.getHeaderString(header),
                () -> "Header %s differs between requests, a new provider instance was used".formatted(header));
    }
}
