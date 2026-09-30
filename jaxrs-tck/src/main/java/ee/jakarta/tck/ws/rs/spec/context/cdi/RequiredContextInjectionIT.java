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

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import ee.jakarta.tck.ws.rs.common.provider.PrintingErrorHandler;
import ee.jakarta.tck.ws.rs.common.provider.StringBean;
import ee.jakarta.tck.ws.rs.common.provider.StringBeanEntityProvider;
import ee.jakarta.tck.ws.rs.common.util.CdiSupport;
import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.sse.SseEventSource;
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

@ArquillianTest
@Tag("cdi")
class RequiredContextInjectionIT {

    @Deployment(testable = false)
    public static WebArchive createDeployment() throws IOException {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-injection.war")
                .addClasses(RequiredContextApplication.class,
                        RequiredContextResource.class,
                        RequiredContextConstructorResource.class,
                        ContextInjectionDescriptor.class,
                        StringBeanEntityProviderWithInjectables.class,
                        StringBeanEntityProvider.class,
                        PrintingErrorHandler.class,
                        StringBean.class)
                .addAsWebInfResource(CdiSupport.BEANS_XML, "beans.xml");
    }

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @BeforeAll
    static void createClient() {
        client = ClientBuilder.newBuilder()
                .build();
    }

    @AfterAll
    static void closeClient() {
        if (client != null) {
            client.close();
        }
    }

    @BeforeEach
    void logStartTest(final TestInfo testInfo) {
        TestUtil.logMsg("STARTING TEST : " + testInfo.getDisplayName());
    }

    @AfterEach
    void logFinishTest(final TestInfo testInfo) {
        TestUtil.logMsg("FINISHED TEST : " + testInfo.getDisplayName());
    }

    @Test
    void constructorInjection() {
        final ContextInjectionDescriptor descriptor = getDescriptor("inject-constructor");
        assertAllInjected(descriptor, "constructor");
        Assertions.assertEquals("GET", descriptor.request(),
                () -> "Request method incorrect via constructor injection: %s".formatted(descriptor));
        Assertions.assertEquals(RuntimeType.SERVER.name(), descriptor.configuration(),
                () -> "Configuration runtime type incorrect via constructor injection: %s".formatted(descriptor));
        Assertions.assertEquals("test-value", descriptor.httpHeaders(),
                () -> "HttpHeaders value incorrect via constructor injection: %s".formatted(descriptor));
        Assertions.assertEquals("get", descriptor.resourceInfo(),
                () -> "ResourceInfo method name incorrect via constructor injection: %s".formatted(descriptor));
    }

    @Test
    void fieldInjection() {
        final ContextInjectionDescriptor descriptor = getDescriptor("inject/field");
        assertAllInjected(descriptor, "field");
        Assertions.assertEquals("GET", descriptor.request(),
                () -> "Request method incorrect via field injection: %s".formatted(descriptor));
        Assertions.assertEquals(RuntimeType.SERVER.name(), descriptor.configuration(),
                () -> "Configuration runtime type incorrect via field injection: %s".formatted(descriptor));
        Assertions.assertEquals("test-value", descriptor.httpHeaders(),
                () -> "HttpHeaders value incorrect via field injection: %s".formatted(descriptor));
        Assertions.assertEquals("field", descriptor.resourceInfo(),
                () -> "ResourceInfo method name incorrect via field injection: %s".formatted(descriptor));
    }

    @Test
    void methodInjection() {
        final ContextInjectionDescriptor descriptor = getDescriptor("inject/method");
        assertAllInjected(descriptor, "method parameter");
        Assertions.assertEquals("GET", descriptor.request(),
                () -> "Request method incorrect via method parameter injection: %s".formatted(descriptor));
        Assertions.assertEquals(RuntimeType.SERVER.name(), descriptor.configuration(),
                () -> "Configuration runtime type incorrect via method parameter injection: %s".formatted(descriptor));
        Assertions.assertEquals("test-value", descriptor.httpHeaders(),
                () -> "HttpHeaders value incorrect via method parameter injection: %s".formatted(descriptor));
        Assertions.assertEquals("method", descriptor.resourceInfo(),
                () -> "ResourceInfo method name incorrect via method parameter injection: %s".formatted(descriptor));
    }

    @Test
    void providerInjection() {
        final Response response = getJson("inject/providers");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> "Failed to look up provider: " + response.readEntity(String.class));
        final ContextInjectionDescriptor descriptor = response.readEntity(ContextInjectionDescriptor.class);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(descriptor.application(), () -> "Application not injected into provider: %s".formatted(descriptor)),
                () -> Assertions.assertNotNull(descriptor.configuration(), () -> "Configuration not injected into provider: %s".formatted(descriptor)),
                () -> Assertions.assertNotNull(descriptor.httpHeaders(), () -> "HttpHeaders not injected into provider: %s".formatted(descriptor)),
                () -> Assertions.assertNotNull(descriptor.providers(), () -> "Providers not injected into provider: %s".formatted(descriptor)),
                () -> Assertions.assertNotNull(descriptor.request(), () -> "Request not injected into provider: %s".formatted(descriptor)),
                () -> Assertions.assertNotNull(descriptor.resourceContext(), () -> "ResourceContext not injected into provider: %s".formatted(descriptor)),
                () -> Assertions.assertNotNull(descriptor.securityContext(), () -> "SecurityContext not injected into provider: %s".formatted(descriptor)),
                () -> Assertions.assertNotNull(descriptor.uriInfo(), () -> "UriInfo not injected into provider: %s".formatted(descriptor))
        );
    }

    @Test
    void application() {
        final Response response = get("inject/application/test.property");
        final var found = response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), found);
        Assertions.assertEquals("test value", found);
    }

    @Test
    void configuration() {
        final Response response = get("inject/configuration");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(RuntimeType.SERVER.name(), response.readEntity(String.class));
    }

    @Test
    void httpHeader() {
        final Response response = get("inject/httpHeaders/test-header");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("test-value", response.readEntity(String.class));
    }

    @Test
    void request() {
        final Response response = get("inject/request");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("GET", response.readEntity(String.class));
    }

    @Test
    void resourceContext() {
        final Response response = get("inject/resourceContext");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertTrue(response.readEntity(String.class)
                .startsWith(RequiredContextResource.class.getCanonicalName()));
    }

    @Test
    void resourceInfo() {
        final Response response = get("inject/resourceInfo");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("resourceInfo", response.readEntity(String.class));
    }

    @Test
    void securityContext() {
        final Response response = get("inject/securityContext");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("false", response.readEntity(String.class));
    }

    @Test
    void uriInfo() {
        final Response response = get("inject/uriInfo");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("/inject/uriInfo", response.readEntity(String.class));
    }

    @Test
    void sse() throws Exception {
        final WebTarget target = client.target(generateUri("inject/sse"));
        final CompletableFuture<String> cf = new CompletableFuture<>();
        try (SseEventSource source = SseEventSource.target(target).build()) {
            source.register(event -> {
                try {
                    cf.complete(event.readData());
                } catch (Throwable t) {
                    cf.completeExceptionally(t);
                }
            });
            source.open();
            Assertions.assertEquals("test", cf.get(5, TimeUnit.SECONDS));
        }
    }

    @Test
    void asyncResponseByTypeAlone() {
        final Response response = get("inject/async");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> "AsyncResponse by type alone failed: %s".formatted(response.readEntity(String.class)));
        Assertions.assertEquals("async-test", response.readEntity(String.class));
    }

    private static void assertAllInjected(final ContextInjectionDescriptor descriptor, final String injectionType) {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(descriptor.application(), () -> "Application not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.configuration(), () -> "Configuration not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.httpHeaders(), () -> "HttpHeaders not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.providers(), () -> "Providers not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.request(), () -> "Request not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.resourceContext(), () -> "ResourceContext not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.resourceInfo(), () -> "ResourceInfo not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.securityContext(), () -> "SecurityContext not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.sse(), () -> "Sse not injected via %s: %s".formatted(injectionType, descriptor)),
                () -> Assertions.assertNotNull(descriptor.uriInfo(), () -> "UriInfo not injected via %s: %s".formatted(injectionType, descriptor))
        );
    }

    private ContextInjectionDescriptor getDescriptor(final String path) {
        final Response response = getJson(path);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> "Unexpected status for " + path + ": " + response.readEntity(String.class));
        return response.readEntity(ContextInjectionDescriptor.class);
    }

    private Response get(final String path) {
        return client.target(generateUri(path))
                .request()
                .header("test-header", "test-value")
                .get();
    }

    private Response getJson(final String path) {
        return client.target(generateUri(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("test-header", "test-value")
                .get();
    }

    private URI generateUri(final String path) {
        return UriBuilder.fromUri(baseUri).path(path).build();
    }
}
