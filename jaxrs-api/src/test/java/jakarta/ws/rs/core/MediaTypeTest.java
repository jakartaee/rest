/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.core;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Map;

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

    @Test
    public void testMediaTypeWithWildcardTypeNotCompatibleWhenSubtypeDifferent() {
        MediaType anyJson = new MediaType(MediaType.MEDIA_TYPE_WILDCARD, "json");

        assertThat(anyJson, not(isCompatibleWith(MediaType.APPLICATION_OCTET_STREAM_TYPE)));
        assertThat(MediaType.APPLICATION_OCTET_STREAM_TYPE, not(isCompatibleWith(anyJson)));
    }

    @Test
    public void testMediaTypeWithWildcardTypeCompatibleWhenSubtypeMatches() {
        MediaType anyJson = new MediaType(MediaType.MEDIA_TYPE_WILDCARD, "json");

        assertThat(anyJson, isCompatibleWith(MediaType.APPLICATION_JSON_TYPE));
        assertThat(MediaType.APPLICATION_JSON_TYPE, isCompatibleWith(anyJson));
    }

    @Test
    public void testMediaTypeWithWildcardTypeCompatibleWithExplicitTypeAndWildcardSubtype() {
        MediaType anyJson = new MediaType(MediaType.MEDIA_TYPE_WILDCARD, "json");
        MediaType applicationAny = new MediaType("application", MediaType.MEDIA_TYPE_WILDCARD);

        assertThat(anyJson, isCompatibleWith(applicationAny));
        assertThat(applicationAny, isCompatibleWith(anyJson));
    }

    @Test
    public void testMediaTypeWithWildcardSubtypeCompatibleWhenTypeMatches() {
        MediaType applicationAny = new MediaType("application", MediaType.MEDIA_TYPE_WILDCARD);

        assertThat(applicationAny, isCompatibleWith(MediaType.APPLICATION_JSON_TYPE));
        assertThat(MediaType.APPLICATION_JSON_TYPE, isCompatibleWith(applicationAny));
    }

    @Test
    public void testMediaTypeWithWildcardSubtypeNotCompatibleWhenTypeDoesNotMatch() {
        MediaType applicationAny = new MediaType("application", MediaType.MEDIA_TYPE_WILDCARD);

        assertThat(applicationAny, not(isCompatibleWith(MediaType.TEXT_HTML_TYPE)));
        assertThat(MediaType.TEXT_HTML_TYPE, not(isCompatibleWith(applicationAny)));
    }

    @Test
    public void testMediaTypeWithExplicitTypeAndSubTypeCompatibleWithSelf() {
        assertThat(MediaType.APPLICATION_JSON_TYPE, isCompatibleWith(MediaType.APPLICATION_JSON_TYPE));
    }

    @Test
    public void testMediaTypeNotCompatibleWithOtherExplicitMediaTypeWhenTypeDifferent() {
        assertThat(MediaType.TEXT_XML_TYPE, not(isCompatibleWith(MediaType.APPLICATION_XML_TYPE)));
    }

    @Test
    public void testMediaTypeNotCompatibleWithOtherExplicitMediaTypeWhenSubtypeDifferent() {
        assertThat(MediaType.TEXT_XML_TYPE, not(isCompatibleWith(MediaType.TEXT_HTML_TYPE)));
    }

    @Test
    public void testMediaTypeNotCompatibleWithOtherMediaTypeWhenTypeAndSubtypeDifferent() {
        assertThat(MediaType.TEXT_XML_TYPE, not(isCompatibleWith(MediaType.APPLICATION_JSON_TYPE)));
    }

    @Test
    public void testMediaTypeNotCompatibleWithNull() {
        assertThat(MediaType.APPLICATION_JSON_TYPE, not(isCompatibleWith(null)));
    }

    private static Matcher<MediaType> isCompatibleWith(MediaType other) {
        return new DiagnosingMatcher<MediaType>() {

            @Override
            protected boolean matches(Object o, Description description) {
                if (o instanceof MediaType) {
                    MediaType mediaType = (MediaType) o;

                    if (!mediaType.isCompatible(other)) {
                        description.appendText("incompatible");
                        return false;
                    }

                    return true;
                }

                description.appendText("not a media type");

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a compatible media type");
            }
        };
    }
}
