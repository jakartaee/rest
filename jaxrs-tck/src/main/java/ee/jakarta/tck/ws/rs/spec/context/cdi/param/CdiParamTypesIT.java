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
import java.util.List;
import java.util.Set;

import ee.jakarta.tck.ws.rs.common.util.CdiSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests various parameter extraction annotation injections work with various required types.
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
@ArquillianTest
@Tag("cdi")
class CdiParamTypesIT {

    @ArquillianResource
    private URI baseUri;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, CdiParamTypesIT.class.getSimpleName() + ".war")
                .addClasses(ParamApplication.class, CdiParamTypesResource.class,
                        TypesDescriptor.class, ParamEnum.class)
                .addAsWebInfResource(CdiSupport.BEANS_XML, "beans.xml");
    }

    @Test
    void allTypes() {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path("types/path1/path2")
                    .queryParam("list", "a", "b", "c")
                    .queryParam("set", "x", "y", "z")
                    .queryParam("sortedSet", "c", "a", "b")
                    .queryParam("array", "one", "two")
                    .queryParam("wrapper", 99)
                    .queryParam("bool", true)
                    .queryParam("enum", "VALUE_ONE")
                    .queryParam("default", "explicit")
                    .queryParam("defaultInt", 7);
            final TypesDescriptor result = client.target(uriBuilder)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(TypesDescriptor.class);
            Assertions.assertNotNull(result);

            // Collection types
            Assertions.assertNotNull(result.getListParam(), () -> "List should not be null: %s".formatted(result));
            Assertions.assertEquals(3, result.getListParam().size(),
                    () -> "Expected list size of 3: %s".formatted(result));
            Assertions.assertTrue(result.getListParam().containsAll(List.of("a", "b", "c")),
                    () -> "Expected list to contain [a, b, c]: %s".formatted(result));

            Assertions.assertNotNull(result.getSetParam(),
                    () -> "Set should not be null: %s".formatted(result));
            Assertions.assertEquals(3, result.getSetParam().size(),
                    () -> "Expected set size of 3: %s".formatted(result));
            Assertions.assertTrue(result.getSetParam().containsAll(Set.of("x", "y", "z")),
                    () -> "Expected set to contain [x, y, z]: %s".formatted(result));

            Assertions.assertNotNull(result.getSortedSetParam(),
                    () -> "SortedSet should not be null: %s".formatted(result));
            Assertions.assertEquals(3, result.getSortedSetParam().size(),
                    () -> "Expected sortedSet size of 3: %s".formatted(result));
            Assertions.assertEquals("a", result.getSortedSetParam().first(),
                    () -> "SortedSet should be sorted, expected 'a' first: %s".formatted(result));

            // Array
            Assertions.assertNotNull(result.getArrayParam(),
                    () -> "Array should not be null: %s".formatted(result));
            Assertions.assertArrayEquals(new String[] {"one", "two"}, result.getArrayParam(),
                    () -> "Expected array [one, two]: %s".formatted(result));

            // Wrapper and primitive types
            Assertions.assertEquals(99, result.getWrapperParam(),
                    () -> "Expected wrapper value of 99: %s".formatted(result));
            Assertions.assertEquals(true, result.getBooleanParam(),
                    () -> "Expected boolean value of true: %s".formatted(result));

            // Enum
            Assertions.assertEquals(ParamEnum.VALUE_ONE, result.getEnumParam(),
                    () -> "Expected enum VALUE_ONE: %s".formatted(result));

            // Explicit values override @DefaultValue
            Assertions.assertEquals("explicit", result.getDefaultParam(),
                    () -> "Expected default string to be 'explicit': %s".formatted(result));
            Assertions.assertEquals(7, result.getDefaultIntParam(),
                    () -> "Expected default int to be 7: %s".formatted(result));

            // PathSegment
            final List<String> listPaths = result.getListPaths();
            Assertions.assertEquals(2, listPaths.size(), () -> "Expected two PathSegments in %s".formatted(result));
            Assertions.assertEquals("path1", listPaths.get(0),
                    () -> "Expected the first PathSegment to be path1 in %s".formatted(result));
            Assertions.assertEquals("path2", listPaths.get(1),
                    () -> "Expected the first PathSegment to be path2 in %s".formatted(result));
            Assertions.assertEquals("path2", result.getSinglePath(),
                    () -> "Expected the single PathSegment to be path2 in %s".formatted(result));
        }
    }

    @Test
    void defaultValues() {
        try (Client client = ClientBuilder.newClient()) {
            final UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                    .path("types/ignored");
            final TypesDescriptor result = client.target(uriBuilder)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(TypesDescriptor.class);
            Assertions.assertNotNull(result);

            // @DefaultValue should apply when params are absent
            Assertions.assertEquals("fallback", result.getDefaultParam(),
                    () -> "Expected default string 'fallback' when param is absent: %s".formatted(result));
            Assertions.assertEquals(42, result.getDefaultIntParam(),
                    () -> "Expected default int 42 when param is absent: %s".formatted(result));

            // Absent optional params without @DefaultValue
            Assertions.assertNull(result.getWrapperParam(),
                    () -> "Absent wrapper should be null: %s".formatted(result));
            Assertions.assertNull(result.getBooleanParam(),
                    () -> "Absent boolean should be null: %s".formatted(result));
            Assertions.assertNull(result.getEnumParam(),
                    () -> "Absent enum should be null: %s".formatted(result));
        }
    }
}
