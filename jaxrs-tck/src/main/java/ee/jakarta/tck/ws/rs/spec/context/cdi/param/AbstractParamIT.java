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

package ee.jakarta.tck.ws.rs.spec.context.cdi.param;

import java.net.URI;

import ee.jakarta.tck.ws.rs.common.util.CdiSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
@Tag("cdi")
abstract class AbstractParamIT {

    private final String contextPath;

    @ArquillianResource
    protected URI baseUri;

    AbstractParamIT(final String contextPath) {
        this.contextPath = contextPath;
    }

    static WebArchive defaultDeployment(final Class<? extends AbstractParamIT> testClass) {
        return ShrinkWrap.create(WebArchive.class, testClass.getSimpleName() + ".war")
                .addClasses(ParamApplication.class, ParamDescriptor.class, ParamResource.class)
                .addAsWebInfResource(CdiSupport.BEANS_XML, "beans.xml");
    }

    @Test
    void constructorParameters() {
        final ParamDescriptor param = invokeRequest("constructor");
        // Check the parameters
        Assertions.assertEquals("cookieParamValue", param.getCookieParam(),
                () -> "@CookieParam parameter was not set on the constructor: %s".formatted(param));
        Assertions.assertEquals("formParamValue", param.getFormParam(),
                () -> "@FormParam parameter was not set on the constructor: %s".formatted(param));
        Assertions.assertEquals("headerParamValue", param.getHeaderParam(),
                () -> "@HeaderParam parameter was not set on the constructor: %s".formatted(param));
        Assertions.assertEquals(10, param.getMatrixParam(),
                () -> "@MatrixParam parameter was not set on the constructor: %s".formatted(param));
        Assertions.assertEquals("constructorParameters", param.getPathParam(),
                () -> "@PathParam parameter was not set on the constructor: %s".formatted(param));
        Assertions.assertEquals(100, param.getQueryParam(),
                () -> "@QueryParam parameter was not set on the constructor: %s".formatted(param));
    }

    @Test
    void fieldParameters() {
        final ParamDescriptor param = invokeRequest("field/fieldParamValue");
        // Check the parameters
        Assertions.assertEquals("fieldCookieParamValue", param.getCookieParam(),
                () -> "@CookieParam parameter was not set on the field: %s".formatted(param));
        Assertions.assertEquals("fieldFormParamValue", param.getFormParam(),
                () -> "@FormParam parameter was not set on the field: %s".formatted(param));
        Assertions.assertEquals("fieldHeaderParamValue", param.getHeaderParam(),
                () -> "@HeaderParam parameter was not set on the field: %s".formatted(param));
        Assertions.assertEquals(20, param.getMatrixParam(),
                () -> "@MatrixParam parameter was not set on the field: %s".formatted(param));
        Assertions.assertEquals("fieldParamValue", param.getPathParam(),
                () -> "@PathParam parameter was not set on the field: %s".formatted(param));
        Assertions.assertEquals(200, param.getQueryParam(),
                () -> "@QueryParam parameter was not set on the field: %s".formatted(param));
    }

    @Test
    void methodParameters() {
        final ParamDescriptor param = invokeRequest("method/methodParamValue");
        // Check the parameters
        Assertions.assertEquals("methodCookieParamValue", param.getCookieParam(),
                () -> "@CookieParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals("methodFormParamValue", param.getFormParam(),
                () -> "@FormParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals("methodHeaderParamValue", param.getHeaderParam(),
                () -> "@HeaderParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals(30, param.getMatrixParam(),
                () -> "@MatrixParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals("methodParamValue", param.getPathParam(),
                () -> "@PathParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals(300, param.getQueryParam(),
                () -> "@QueryParam parameter was not set on the method: %s".formatted(param));
    }

    @Test
    void methodParametersAnnotated() {
        final ParamDescriptor param = invokeRequest("method-param/methodParamValuePa");
        // Check the parameters
        Assertions.assertEquals("methodCookieParamValuePa", param.getCookieParam(),
                () -> "@CookieParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals("methodFormParamValuePa", param.getFormParam(),
                () -> "@FormParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals("methodHeaderParamValuePa", param.getHeaderParam(),
                () -> "@HeaderParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals(40, param.getMatrixParam(),
                () -> "@MatrixParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals("methodParamValuePa", param.getPathParam(),
                () -> "@PathParam parameter was not set on the method: %s".formatted(param));
        Assertions.assertEquals(400, param.getQueryParam(),
                () -> "@QueryParam parameter was not set on the method: %s".formatted(param));
    }

    private ParamDescriptor invokeRequest(final String path) {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path(contextPath + "/constructorParameters/" + path)
                    .matrixParam("matrixParam", 10)
                    .matrixParam("fieldMatrixParam", 20)
                    .matrixParam("methodMatrixParam", 30)
                    .matrixParam("methodMatrixParamPa", 40)
                    .queryParam("queryParam", 100)
                    .queryParam("fieldQueryParam", 200)
                    .queryParam("methodQueryParam", 300)
                    .queryParam("methodQueryParamPa", 400);
            final Form form = new Form()
                    .param("formParam", "formParamValue")
                    .param("fieldFormParam", "fieldFormParamValue")
                    .param("methodFormParam", "methodFormParamValue")
                    .param("methodFormParamPa", "methodFormParamValuePa");
            final WebTarget target = client.target(uriBuilder);
            final ParamDescriptor param = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .cookie("cookieParam", "cookieParamValue")
                    .cookie("fieldCookieParam", "fieldCookieParamValue")
                    .cookie("methodCookieParam", "methodCookieParamValue")
                    .cookie("methodCookieParamPa", "methodCookieParamValuePa")
                    .header("headerParam", "headerParamValue")
                    .header("fieldHeaderParam", "fieldHeaderParamValue")
                    .header("methodHeaderParam", "methodHeaderParamValue")
                    .header("methodHeaderParamPa", "methodHeaderParamValuePa")
                    .post(Entity.form(form), ParamDescriptor.class);
            Assertions.assertNotNull(param, () -> "Failed to find the parameters with URI %s".formatted(uriBuilder.build()));
            return param;
        }
    }

}
