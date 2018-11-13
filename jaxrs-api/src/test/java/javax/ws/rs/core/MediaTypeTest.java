/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

/**
 * {@link MediaType} unit test.
 *
 * @author Marek Potociar
 */
public class MediaTypeTest {

    /**
     * Test {@link MediaType#withCharset(String)} method.
     */
    @Test
    public void testWithCharset() {
        assertEquals("Unexpected produced media type content.",
                "UTF-8",
                MediaType.APPLICATION_XML_TYPE.withCharset("UTF-8")
                        .getParameters().get(MediaType.CHARSET_PARAMETER));
        assertEquals("Unexpected produced media type content.",
                "ISO-8859-13",
                MediaType.APPLICATION_XML_TYPE.withCharset("UTF-8").withCharset("ISO-8859-13")
                        .getParameters().get(MediaType.CHARSET_PARAMETER));
    }

    /**
     * Test that passing {@code null} values to {@link MediaType} constructor does not throw a {@link NullPointerException}
     * and produces expected result.
     */
    @Test
    public void testNullConstructorValues() {
        MediaType actual;

        actual = new MediaType(null, null, (Map<String, String>) null);
        assertEquals(MediaType.WILDCARD_TYPE, actual);

        actual = new MediaType(null, null, (String) null);
        assertEquals(MediaType.WILDCARD_TYPE, actual);
    }

    /**
     * Test that a wildcard type {@link MediaType} value with explicit subtype is not compatible with
     * a non-wildcard type {@link MediaType} value with a different subtype.
     */
    @Test
    public void testMediaTypeWithWildcardTypeNotCompatibleWhenSubtypeDifferent() {
        MediaType wildcard = new MediaType(MediaType.MEDIA_TYPE_WILDCARD, "json");

        assertFalse(wildcard.isCompatible(MediaType.APPLICATION_OCTET_STREAM_TYPE));
        assertFalse(MediaType.APPLICATION_OCTET_STREAM_TYPE.isCompatible(wildcard));
    }

    /**
     * Test that a wildcard type {@link MediaType} value with explicit subtype is compatible with
     * a non-wildcard type {@link MediaType} value with the same explicit subtype.
     */
    @Test
    public void testMediaTypeWithWildcardTypeCompatibleWhenSubtypeMatches() {
        MediaType wildcard = new MediaType(MediaType.MEDIA_TYPE_WILDCARD, "json");

        assertTrue(wildcard.isCompatible(MediaType.APPLICATION_JSON_TYPE));
        assertTrue(MediaType.APPLICATION_JSON_TYPE.isCompatible(wildcard));
    }

    /**
     * Test that a wildcard type {@link MediaType} value with explicit subtype is not compatible with
     * a non-wildcard type {@link MediaType} value with a different subtype.
     */
    @Test
    public void testMediaTypeWithWildcardTypeCompatibleWithExplicitTypeAndWildcardSubtype() {
        MediaType wildcardType = new MediaType(MediaType.MEDIA_TYPE_WILDCARD, "json");
        MediaType wildcardSubtype = new MediaType("application", MediaType.MEDIA_TYPE_WILDCARD);

        assertTrue(wildcardType.isCompatible(wildcardSubtype));
        assertTrue(wildcardSubtype.isCompatible(wildcardType));
    }

    /**
     * Test that a {@link MediaType} value with explicit type and wildcard subtype
     * is compatible with a {@link MediaType} value with the same type and an explicit subtype.
     */
    @Test
    public void testMediaTypeWithWildcardSubtypeCompatibleWhenTypeMatches() {
        MediaType wildcard = new MediaType("application", MediaType.MEDIA_TYPE_WILDCARD);

        assertTrue(wildcard.isCompatible(MediaType.APPLICATION_JSON_TYPE));
        assertTrue(MediaType.APPLICATION_JSON_TYPE.isCompatible(wildcard));
    }

    /**
     * Test that a {@link MediaType} value with explicit type and wildcard subtype
     * is not compatible with a {@link MediaType} value with a different type and an explicit subtype.
     */
    @Test
    public void testMediaTypeWithWildcardSubtypeNotCompatibleWhenTypeDoesNotMatch() {
        MediaType wildcard = new MediaType("application", MediaType.MEDIA_TYPE_WILDCARD);

        assertFalse(wildcard.isCompatible(MediaType.TEXT_HTML_TYPE));
        assertFalse(MediaType.TEXT_HTML_TYPE.isCompatible(wildcard));
    }

    /**
     * Test that a {@link MediaType} value with explicit type and subtype
     * is compatible with a {@link MediaType} value with the same type and subtype (i.e. itself).
     */
    @Test
    public void testMediaTypeWithExplicitTypeAndSubTypeCompatibleWithSelf() {
        assertTrue(MediaType.APPLICATION_JSON_TYPE.isCompatible(MediaType.APPLICATION_JSON_TYPE));
    }

    /**
     * Test that a {@link MediaType} value with explicit type and subtype
     * is not compatible with a {@link MediaType} value with a different type and same subtype
     */
    @Test
    public void testMediaTypeNotCompatibleWithOtherExplicitMediaTypeWhenTypeDifferent() {
        assertFalse(MediaType.TEXT_XML_TYPE.isCompatible(MediaType.APPLICATION_XML_TYPE));
    }

    /**
     * Test that a {@link MediaType} value with explicit type and subtype
     * is not compatible with a {@link MediaType} value with the same type and different subtype
     */
    @Test
    public void testMediaTypeNotCompatibleWithOtherExplicitMediaTypeWhenSubtypeDifferent() {
        assertFalse(MediaType.TEXT_XML_TYPE.isCompatible(MediaType.TEXT_HTML_TYPE));
    }


    /**
     * Test that a {@link MediaType} value with explicit type and subtype
     * is not compatible with a {@link MediaType} value with a different type and different subtype.
     */
    @Test
    public void testMediaTypeNotCompatibleWithOtherMediaTypeWhenTypeAndSubtypeDifferent() {
        assertFalse(MediaType.TEXT_XML_TYPE.isCompatible(MediaType.APPLICATION_JSON_TYPE));
    }

    /**
     * Test that a {@link MediaType} value is not compatible with a {@code null} value.
     */
    @Test
    public void testMediaTypeNotCompatibleWithNull() {
        assertFalse(MediaType.APPLICATION_JSON_TYPE.isCompatible(null));
    }
}
