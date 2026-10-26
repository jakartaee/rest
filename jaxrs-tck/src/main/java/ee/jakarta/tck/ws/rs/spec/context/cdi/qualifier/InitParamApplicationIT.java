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

import ee.jakarta.tck.ws.rs.common.JAXRSCommonClient;
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
 * Tests that {@code @Inject @Context Application} resolves to the deployed {@code Application} subclass when it is
 * handled by a servlet defined in the {@code web.xml}, identified with the {@code jakarta.ws.rs.Application}
 * initialization parameter, whether or not the subclass is a CDI bean. The servlet class is the
 * implementation-supplied servlet set with the {@code servlet_adaptor} system property.
 */
@ArquillianTest
@Tag("cdi")
@Tag("servlet")
class InitParamApplicationIT {

    private static final StringAsset WEB_XML = new StringAsset("""
            <?xml version="1.0" encoding="UTF-8"?>
            <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
                     version="6.0">
                <servlet>
                    <servlet-name>rest-cdi-init-param</servlet-name>
                    <servlet-class>%s</servlet-class>
                    <init-param>
                        <param-name>jakarta.ws.rs.Application</param-name>
                        <param-value>%s</param-value>
                    </init-param>
                </servlet>
                <servlet-mapping>
                    <servlet-name>rest-cdi-init-param</servlet-name>
                    <url-pattern>/*</url-pattern>
                </servlet-mapping>
            </web-app>""".formatted(JAXRSCommonClient.servletAdaptor, WebXmlApplication.class.getName()));

    private static final GenericType<Map<String, String>> RESULTS = new GenericType<>() {
    };

    private static Client client;

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "rest-cdi-init-param.war")
                .addClasses(WebXmlApplication.class,
                        WebXmlApplicationResource.class)
                .setWebXML(WEB_XML);
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
    void contextApplication() {
        final Response response = client.target(UriBuilder.fromUri(baseUri).path("application"))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                () -> response.readEntity(String.class));
        final Map<String, String> results = response.readEntity(RESULTS);
        Assertions.assertAll(
                () -> Assertions.assertEquals(WebXmlApplication.class.getName(), results.get("qualifiedClass"),
                        () -> "@Inject @Context Application is not the deployed WebXmlApplication: %s".formatted(results)),
                () -> Assertions.assertEquals(results.get("parameter"), results.get("qualified"),
                        () -> "@Inject @Context Application is not the deployed Application instance: %s".formatted(results))
        );
    }
}
