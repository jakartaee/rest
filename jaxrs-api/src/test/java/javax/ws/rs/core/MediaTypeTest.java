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

import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
     * Test that passing {@code null} values to {@link MediaType} constructor does
     * not throw a {@link NullPointerException} and produces expected result.
     */
    @Test
    public void testNullConstructorValues() {
        MediaType actual;

        actual = new MediaType(null, null, (Map<String, String>) null);
        assertEquals(MediaType.WILDCARD_TYPE, actual);

        actual = new MediaType(null, null, (String) null);
        assertEquals(MediaType.WILDCARD_TYPE, actual);
    }
}
