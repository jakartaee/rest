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

package ee.jakarta.tck.ws.rs.spec.validation;

import java.net.URI;
import java.util.UUID;

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
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
 * Tests the values an implementation is required to validate in a product that supports Jakarta Validation.
 */
@ArquillianTest
@Tag("cdi")
@Tag("validation")
class ValidationIT {

    private static final String VALID = "abc";
    private static final String INVALID = "too long";

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-validation.war")
                .addClasses(ValidationApplication.class,
                        ValidationResource.class,
                        GetterResource.class,
                        BeanParameterResource.class,
                        SearchParams.class,
                        Invocations.class);
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
     * Values which satisfy their constraints do not prevent the resource method from being invoked.
     */
    @Test
    void valid() {
        final String id = UUID.randomUUID().toString();
        final Response response = target("validation/injected", id)
                .queryParam("field", VALID)
                .queryParam("property", VALID)
                .queryParam("bean", VALID)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        assertStatus(response, Response.Status.OK);
        Assertions.assertTrue(invoked(id), "The resource method was not invoked for valid values");
    }

    /**
     * A field annotated with a parameter extraction annotation is validated before the resource method is invoked.
     */
    @Test
    void injectedField() {
        assertRejected("field");
    }

    /**
     * A property whose setter is annotated with a parameter extraction annotation is validated against the
     * constraints declared on the property before the resource method is invoked.
     */
    @Test
    void injectedProperty() {
        assertRejected("property");
    }

    /**
     * A {@code @BeanParam} field annotated with {@code @Valid} is validated before the resource method is invoked.
     */
    @Test
    void beanParam() {
        assertRejected("bean");
    }

    /**
     * The parameters of the resource method are validated before it is invoked.
     */
    @Test
    void parameter() {
        final String id = UUID.randomUUID().toString();
        final Response response = target("validation/parameter", id)
                .queryParam("value", INVALID)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        assertStatus(response, Response.Status.BAD_REQUEST);
        Assertions.assertFalse(invoked(id), "The resource method was invoked with an invalid parameter");
    }

    /**
     * A {@code @BeanParam} parameter of the resource method annotated with {@code @Valid} is validated before the
     * resource method is invoked.
     */
    @Test
    void beanParameter() {
        final String validId = UUID.randomUUID().toString();
        assertStatus(target("bean-parameter", validId)
                .queryParam("bean", VALID)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(), Response.Status.OK);
        Assertions.assertTrue(invoked(validId),
                "The resource method was not invoked for a valid @BeanParam parameter");

        final String invalidId = UUID.randomUUID().toString();
        assertStatus(target("bean-parameter", invalidId)
                .queryParam("bean", INVALID)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(), Response.Status.BAD_REQUEST);
        Assertions.assertFalse(invoked(invalidId),
                "The resource method was invoked with an invalid @BeanParam parameter");
    }

    /**
     * A {@code ValidationException} which is not a {@code ConstraintViolationException} is mapped to a response with
     * status code 500.
     */
    @Test
    void validationException() {
        assertStatus(client.target(generateUri("validation/exception"))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(), Response.Status.INTERNAL_SERVER_ERROR);
    }

    /**
     * The entity parameter of the resource method is validated before it is invoked.
     */
    @Test
    void entity() {
        final String id = UUID.randomUUID().toString();
        final Response response = target("validation/entity", id)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.text(INVALID));
        assertStatus(response, Response.Status.BAD_REQUEST);
        Assertions.assertFalse(invoked(id), "The resource method was invoked with an invalid entity");
    }

    /**
     * The value returned by the resource method is validated.
     */
    @Test
    void returnValue() {
        assertStatus(client.target(generateUri("validation/return"))
                .queryParam("value", VALID)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(), Response.Status.OK);
        assertStatus(client.target(generateUri("validation/return"))
                .queryParam("value", INVALID)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(), Response.Status.INTERNAL_SERVER_ERROR);
    }

    /**
     * A resource method whose signature obeys the rules for getters follows the default validation rules of Jakarta
     * Validation, so its returned value is not validated. Its constraint is also a property constraint, which an
     * implementation may validate, so a 400 response is allowed.
     */
    @Test
    void getterNotValidated() {
        final Response response = client.target(generateUri("getter"))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        final String entity = response.readEntity(String.class);
        Assertions.assertNotEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus(),
                () -> "The value returned by a getter resource method was validated: %s".formatted(entity));
    }

    private void assertRejected(final String queryParam) {
        final String id = UUID.randomUUID().toString();
        final Response response = target("validation/injected", id)
                .queryParam(queryParam, INVALID)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        assertStatus(response, Response.Status.BAD_REQUEST);
        Assertions.assertFalse(invoked(id),
                () -> "The resource method was invoked with an invalid %s value".formatted(queryParam));
    }

    private boolean invoked(final String id) {
        final Response response = client.target(generateUri("validation/invoked/%s".formatted(id)))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();
        final String entity = response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), entity);
        return Boolean.parseBoolean(entity);
    }

    private WebTarget target(final String path, final String id) {
        return client.target(generateUri(path))
                .queryParam("id", id);
    }

    private URI generateUri(final String path) {
        return UriBuilder.fromUri(baseUri).path(path).build();
    }

    private static void assertStatus(final Response response, final Response.Status expected) {
        final String entity = response.readEntity(String.class);
        Assertions.assertEquals(expected.getStatusCode(), response.getStatus(), entity);
    }
}
