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

package ee.jakarta.tck.ws.rs.spec.context.cdi.servlet;

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
 * Tests the Servlet-defined types injected into {@code @Inject @Context} fields and resource method parameters. The
 * deployment contains another servlet which is a CDI bean, so a {@code ServletConfig} bean without the
 * {@code @Context} qualifier is not the {@code ServletConfig} of the Jakarta REST application, while an unqualified
 * resource method parameter, identified by type alone, still is.
 */
@ArquillianTest
@Tag("cdi")
@Tag("servlet")
class ServletTypesIT {

    private static final GenericType<Map<String, String>> RESULTS = new GenericType<>() {
    };

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-servlet.war")
                .addClasses(ServletApplication.class,
                        ServletResource.class,
                        OtherServlet.class);
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
     * {@code @Inject @Context} fields resolve to the instances of the Jakarta REST application and of the request.
     */
    @Test
    void qualifiedFields() {
        assertServletTypes("rest/servlet/fields", "@Inject @Context field");
    }

    /**
     * Resource method parameters of Servlet-defined types are identified by type alone.
     */
    @Test
    void unqualifiedParameters() {
        assertServletTypes("rest/servlet/parameters/unqualified", "resource method parameter");
    }

    /**
     * Resource method parameters of Servlet-defined types may be qualified with {@code @Context}.
     */
    @Test
    void qualifiedParameters() {
        assertServletTypes("rest/servlet/parameters/qualified", "@Context resource method parameter");
    }

    /**
     * Asserts the {@code ServletConfig}, {@code ServletContext}, {@code HttpServletRequest} and
     * {@code HttpServletResponse} the resource obtained are those of the Jakarta REST application and of the request.
     * The resource sets the response header to the last segment of the path, which is also the value of the request
     * header.
     */
    private void assertServletTypes(final String path, final String description) {
        final String expected = path.substring(path.lastIndexOf('/') + 1);
        // The context path of the deployment, which is empty for the root context
        final String contextPath = baseUri.getPath().replaceAll("/+$", "");
        final Response response = get(path);
        final String responseHeader = response.getHeaderString(ServletResource.RESPONSE_HEADER);
        final Map<String, String> results = response.readEntity(RESULTS);
        Assertions.assertAll(
                () -> Assertions.assertEquals(ServletApplication.class.getName(), results.get("servletName"),
                        () -> "The ServletConfig %s is not the ServletConfig of the Jakarta REST application: %s"
                                .formatted(description, results)),
                () -> Assertions.assertEquals(contextPath, results.get("contextPath"),
                        () -> "The ServletContext %s is not the ServletContext of the deployment: %s"
                                .formatted(description, results)),
                () -> Assertions.assertEquals(expected, results.get("header"),
                        () -> "The HttpServletRequest %s is not the current request: %s"
                                .formatted(description, results)),
                () -> Assertions.assertEquals(expected, responseHeader,
                        () -> "The HttpServletResponse %s is not the current response".formatted(description))
        );
    }

    private Response get(final String path) {
        final String headerValue = path.substring(path.lastIndexOf('/') + 1);
        final Response response = client.target(UriBuilder.fromUri(baseUri).path(path))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("test-header", headerValue)
                .get();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            Assertions.fail("Unexpected status %d for %s: %s".formatted(response.getStatus(), path,
                    response.readEntity(String.class)));
        }
        return response;
    }
}
