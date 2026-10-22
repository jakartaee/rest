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

package ee.jakarta.tck.ws.rs.spec.context.cdi.qualifier;

import java.net.URI;
import java.util.Map;

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.ws.rs.RuntimeType;
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
 * Tests the {@link jakarta.ws.rs.core.Context @Context} CDI qualifier.
 */
@ArquillianTest
@Tag("cdi")
class ContextQualifierIT {

    private static final GenericType<Map<String, QualifierDescriptor>> DESCRIPTORS = new GenericType<>() {
    };
    private static final GenericType<Map<String, String>> INSTANCE_IDS = new GenericType<>() {
    };

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-qualifier.war")
                .addClasses(QualifierApplication.class,
                        QualifierResource.class,
                        QualifierConstructorResource.class,
                        QualifierDescriptor.class);
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
     * Context types resolve with both the {@code @Default} and the {@code @Context} qualifier, and both resolve to
     * the same context object.
     */
    @Test
    void qualifiedFields() {
        final Map<String, QualifierDescriptor> descriptors = getDescriptors("qualifier/fields");
        final QualifierDescriptor expected = descriptors.get("default");
        assertExpected(expected, "/qualifier/fields", "fields");
        Assertions.assertEquals(expected, descriptors.get("qualified"),
                "@Inject @Context fields did not resolve to the same context objects as @Inject fields");
    }

    /**
     * {@link jakarta.ws.rs.core.Context.Literal} selects the {@code @Context}-qualified beans, including the deployed
     * {@code Application}.
     */
    @Test
    void literal() {
        final Map<String, QualifierDescriptor> descriptors = getDescriptors("qualifier/literal");
        final QualifierDescriptor expected = descriptors.get("default");
        assertExpected(expected, "/qualifier/literal", "literal");
        Assertions.assertEquals(expected, descriptors.get("literal"),
                "Instance.select(Context.Literal.INSTANCE) did not resolve to the same context objects as @Inject fields");

        final Map<String, String> ids = getInstanceIds("qualifier/literal/application");
        Assertions.assertEquals(ids.get("subclass"), ids.get("literal"),
                () -> "The Application selected with Context.Literal is not the deployed Application: %s".formatted(ids));
    }

    /**
     * The parameters of an {@code @Inject} constructor and of a resource method can be qualified with
     * {@code @Context}.
     */
    @Test
    void qualifiedParameters() {
        final Map<String, QualifierDescriptor> descriptors = getDescriptors("qualifier-constructor");
        final QualifierDescriptor expected = descriptors.get("parameter");
        assertExpected(expected, "/qualifier-constructor", "get");
        Assertions.assertEquals(expected, descriptors.get("constructor"),
                "@Context qualified constructor parameters did not resolve to the same context objects as @Context resource method parameters");
    }

    /**
     * The parameter of an {@code @Inject} setter annotated with {@code @Context} is {@code @Context}-qualified.
     */
    @Test
    void qualifiedSetter() {
        final Response response = client.target(generateUri("qualifier/setter"))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        final String path = response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), path);
        Assertions.assertEquals("/qualifier/setter", path, "UriInfo not injected through the @Inject @Context setter");
    }

    /**
     * {@code @Inject @Context Application}, the {@code @Default} {@code Application} bean, the subclass bean and
     * the {@code Application} resource method parameter are the deployed {@code Application} instance.
     */
    @Test
    void application() {
        final Map<String, String> ids = getInstanceIds("qualifier/application");
        final String expected = ids.get("subclass");
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected, ids.get("qualified"),
                        () -> "@Inject @Context Application is not the deployed Application: %s".formatted(ids)),
                () -> Assertions.assertEquals(expected, ids.get("default"),
                        () -> "@Inject Application is not the deployed Application: %s".formatted(ids)),
                () -> Assertions.assertEquals(expected, ids.get("parameter"),
                        () -> "The Application resource method parameter is not the deployed Application: %s".formatted(ids))
        );
    }

    private Map<String, QualifierDescriptor> getDescriptors(final String path) {
        final Response response = client.target(generateUri(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("test-header", "test-value")
                .get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> "Unexpected status for %s: %s".formatted(path, response.readEntity(String.class)));
        return response.readEntity(DESCRIPTORS);
    }

    private Map<String, String> getInstanceIds(final String path) {
        final Response response = client.target(generateUri(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> "Unexpected status for %s: %s".formatted(path, response.readEntity(String.class)));
        final Map<String, String> ids = response.readEntity(INSTANCE_IDS);
        Assertions.assertNotNull(ids.get("subclass"), () -> "The Application subclass was not injected: %s".formatted(ids));
        return ids;
    }

    private URI generateUri(final String path) {
        return UriBuilder.fromUri(baseUri).path(path).build();
    }

    private static void assertExpected(final QualifierDescriptor descriptor, final String path,
                                       final String resourceMethod) {
        Assertions.assertNotNull(descriptor, "No descriptor returned");
        Assertions.assertAll(
                () -> Assertions.assertEquals(RuntimeType.SERVER.name(), descriptor.configuration(),
                        () -> "Configuration runtime type incorrect: %s".formatted(descriptor)),
                () -> Assertions.assertEquals("test-value", descriptor.httpHeaders(),
                        () -> "HttpHeaders value incorrect: %s".formatted(descriptor)),
                () -> Assertions.assertNotEquals("none", descriptor.providers(),
                        () -> "Providers did not find a MessageBodyWriter for String: %s".formatted(descriptor)),
                () -> Assertions.assertEquals("GET", descriptor.request(),
                        () -> "Request method incorrect: %s".formatted(descriptor)),
                () -> Assertions.assertEquals("initialized", descriptor.resourceContext(),
                        () -> "ResourceContext did not initialize the resource: %s".formatted(descriptor)),
                () -> Assertions.assertEquals(resourceMethod, descriptor.resourceInfo(),
                        () -> "ResourceInfo method name incorrect: %s".formatted(descriptor)),
                () -> Assertions.assertEquals("false", descriptor.securityContext(),
                        () -> "SecurityContext secure flag incorrect: %s".formatted(descriptor)),
                () -> Assertions.assertEquals("sse", descriptor.sse(),
                        () -> "Sse did not create the event: %s".formatted(descriptor)),
                () -> Assertions.assertEquals(path, descriptor.uriInfo(),
                        () -> "UriInfo path incorrect: %s".formatted(descriptor))
        );
    }
}
