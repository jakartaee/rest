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

package ee.jakarta.tck.ws.rs.spec.context.cdi.request;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
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
 * Tests that injected context objects reflect the request being processed when they are used by code the
 * implementation invokes, regardless of when they were injected.
 */
@ArquillianTest
@Tag("cdi")
class RequestContextIT {

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-request.war")
                .addClasses(RequestApplication.class,
                        RequestResource.class,
                        ApplicationScopedResource.class,
                        SuspendedResponses.class,
                        EchoFilter.class,
                        ConfigurationFeature.class);
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
     * The context objects injected once into an {@code @ApplicationScoped} resource reflect each request.
     */
    @Test
    void applicationScopedResource() {
        assertEntity(get("application-scoped/first", "first"), "/application-scoped/first:first");
        assertEntity(get("application-scoped/second", "second"), "/application-scoped/second:second");
    }

    /**
     * The context objects injected once into an {@code @ApplicationScoped} provider reflect each request.
     */
    @Test
    void applicationScopedProvider() {
        assertEcho(get("application-scoped/first", "first"), "/application-scoped/first", "first", "get");
        assertEcho(get("request/unconfigured", "second"), "/request/unconfigured", "second", "unconfigured");
    }

    /**
     * An injected {@code Configuration} reflects the properties registered by a {@code DynamicFeature} for the
     * matched resource method, although the resource is injected before the method is matched.
     */
    @Test
    void dynamicFeatureConfiguration() {
        assertEntity(get("request/configured", "configured"), "configured");
        assertEntity(get("request/unconfigured", "unconfigured"), "none");
    }

    /**
     * A provider invoked after an {@code AsyncResponse} is resumed from another thread reflects the suspended
     * request.
     */
    @Test
    void asyncResponse() {
        final Response response = get("request/async", "async");
        assertEntity(response, "async");
        assertEcho(response, "/request/async", "async", "async");
    }

    /**
     * A provider invoked after a returned {@code CompletionStage} completes on another thread reflects the request.
     */
    @Test
    void completionStage() {
        final Response response = get("request/stage", "stage");
        assertEntity(response, "stage");
        assertEcho(response, "/request/stage", "stage", "stage");
    }

    /**
     * A provider invoked after an {@code AsyncResponse} is resumed while another request is processed, on the
     * thread processing that request, reflects the suspended request.
     */
    @Test
    void resumedByAnotherRequest() throws Exception {
        final String id = UUID.randomUUID().toString();
        final Future<Response> suspended = client.target(generateUri("request/suspend/%s".formatted(id)))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("test-header", "suspended")
                .async()
                .get();
        final Response resumer = get("request/resume/%s".formatted(id), "resumer");
        assertEntity(resumer, "resumed");
        assertEcho(resumer, "/request/resume/%s".formatted(id), "resumer", "resume");

        final Response response = suspended.get(30, TimeUnit.SECONDS);
        assertEntity(response, "suspended");
        assertEcho(response, "/request/suspend/%s".formatted(id), "suspended", "suspend");
    }

    private Response get(final String path, final String headerValue) {
        return client.target(generateUri(path))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("test-header", headerValue)
                .get();
    }

    private URI generateUri(final String path) {
        return UriBuilder.fromUri(baseUri).path(path).build();
    }

    private static void assertEntity(final Response response, final String expected) {
        // Buffer the entity, otherwise reading it closes the response and assertEcho() cannot buffer it
        response.bufferEntity();
        final String entity = response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), entity);
        Assertions.assertEquals(expected, entity);
    }

    private static void assertEcho(final Response response, final String path, final String header,
                                   final String method) {
        response.bufferEntity();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> response.readEntity(String.class));
        Assertions.assertAll(
                () -> Assertions.assertEquals(path, response.getHeaderString(EchoFilter.PATH),
                        () -> "UriInfo in the provider did not reflect the request to %s".formatted(path)),
                () -> Assertions.assertEquals(header, response.getHeaderString(EchoFilter.HEADER),
                        () -> "HttpHeaders in the provider did not reflect the request to %s".formatted(path)),
                () -> Assertions.assertEquals(method, response.getHeaderString(EchoFilter.METHOD),
                        () -> "ResourceInfo in the provider did not reflect the request to %s".formatted(path))
        );
    }
}
