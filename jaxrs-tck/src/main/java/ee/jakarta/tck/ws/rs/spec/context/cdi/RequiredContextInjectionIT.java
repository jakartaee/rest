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
import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.sse.SseEventSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@ArquillianTest
public class RequiredContextInjectionIT {


    @Deployment(testable = false)
    public static WebArchive createDeployment() throws IOException {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-injection.war")
                .addClasses(RequiredContextApplication.class,
                        RequiredContextResource.class,
                        StringBeanEntityProviderWithInjectables.class,
                        StringBeanEntityProvider.class,
                        PrintingErrorHandler.class,
                        StringBean.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
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
    public void application() throws Exception {
        final Response response = get("application/test.property");
        final var found = response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo(), found);
        Assertions.assertEquals("test value", found);
    }

    @Test
    public void configuration() throws Exception {
        final Response response = get("configuration");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        Assertions.assertEquals(RuntimeType.SERVER.name(), response.readEntity(String.class));
    }

    @Test
    public void httpHeader() {
        final Response response = get("httpHeaders/test-header");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        Assertions.assertEquals("test-value", response.readEntity(String.class));
    }

    @Test
    public void provider() throws Exception {
        final Response response = get("providers");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        final String value = response.readEntity(String.class);
        Assertions.assertEquals("111111111", value, () ->
                "Missing injected types: %s".formatted(StringBeanEntityProviderWithInjectables.notInjected(value)));
    }

    @Test
    public void method() throws Exception {
        final Response response = get("method");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        final String value = response.readEntity(String.class);
        Assertions.assertEquals("111111111", value, () ->
                "Missing injected types: %s".formatted(StringBeanEntityProviderWithInjectables.notInjected(value)));
    }

    @Test
    public void request() throws Exception {
        final Response response = get("request");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        Assertions.assertEquals("GET", response.readEntity(String.class));
    }

    @Test
    public void resourceContext() throws Exception {
        final Response response = get("resourceContext");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        Assertions.assertTrue(response.readEntity(String.class)
                .startsWith(RequiredContextResource.class.getCanonicalName()));
    }

    @Test
    public void resourceInfo() throws Exception {
        final Response response = get("resourceInfo");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        Assertions.assertEquals("resourceInfo", response.readEntity(String.class));
    }

    @Test
    public void securityContext() throws Exception {
        final Response response = get("securityContext");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        Assertions.assertEquals("false", response.readEntity(String.class));
    }

    @Test
    public void uriInfo() throws Exception {
        final Response response = get("uriInfo");
        Assertions.assertEquals(Response.Status.OK, response.getStatusInfo());
        Assertions.assertEquals("/inject/uriInfo", response.readEntity(String.class));
    }

    @Test
    public void sse() throws Exception {
        final WebTarget target = client.target(generateUri("sse"));
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
            Thread.sleep(500L);
        }
        Assertions.assertEquals("test", cf.get(5, TimeUnit.SECONDS));
    }

    private Response get(final String path) {
        return client.target(generateUri(path))
                .request()
                .header("test-header", "test-value")
                .get();
    }

    private URI generateUri(final String path) {
        return UriBuilder.fromUri(baseUri).path("inject/" + path).build();
    }
}
