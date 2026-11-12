/*
 * Copyright (c) 2010, 2020 Oracle and/or its affiliates. All rights reserved.
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

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for {@link NewCookie.Builder}
 *
 * @author Nicolas NESMON
 * @since 3.1
 */
public final class NewCookieBuilderTest extends BaseDelegateTest {

    @Test
    public final void shouldReturnSuppliedCookieInformationWhenSuppliedCookieIsNotNull() {

        String expectedName = "name";
        String expectedValue = "value";
        int expectedVersion = 2;
        String expectedPath = "/";
        String expectedDomain = "localhost";

        Cookie cookie = new Cookie.Builder(expectedName)
                .value(expectedValue)
                .version(expectedVersion)
                .path(expectedPath)
                .domain(expectedDomain)
                .build();
        NewCookie newCookie = new NewCookie.Builder(cookie).build();

        assertEquals(expectedName, newCookie.getName());
        assertEquals(expectedValue, newCookie.getValue());
        assertEquals(expectedVersion, newCookie.getVersion());
        assertEquals(expectedPath, newCookie.getPath());
        assertEquals(expectedDomain, newCookie.getDomain());
        assertNull(newCookie.getComment());
        assertEquals(NewCookie.DEFAULT_MAX_AGE, newCookie.getMaxAge());
        assertNull(newCookie.getExpiry());
        assertFalse(newCookie.isSecure());
        assertFalse(newCookie.isHttpOnly());
        assertNull(newCookie.getSameSite());
    }

    @Test
    public final void shouldPreserveSecurityAttributesWhenBuiltFromNewCookie() {
        Date expiry = new Date(1_700_000_000_000L);
        NewCookie source = new NewCookie.Builder("session")
                .value("abc")
                .path("/")
                .domain("example.com")
                .version(1)
                .comment("browser session")
                .maxAge(3600)
                .expiry(expiry)
                .secure(true)
                .httpOnly(true)
                .sameSite(NewCookie.SameSite.STRICT)
                .build();

        NewCookie copy = new NewCookie.Builder(source).build();

        assertEquals(source.getName(), copy.getName());
        assertEquals(source.getValue(), copy.getValue());
        assertEquals(source.getPath(), copy.getPath());
        assertEquals(source.getDomain(), copy.getDomain());
        assertEquals(source.getVersion(), copy.getVersion());
        assertEquals("browser session", copy.getComment());
        assertEquals(3600, copy.getMaxAge());
        assertEquals(expiry, copy.getExpiry());
        assertNotSame(expiry, copy.getExpiry());
        assertTrue(copy.isSecure());
        assertTrue(copy.isHttpOnly());
        assertEquals(NewCookie.SameSite.STRICT, copy.getSameSite());

        expiry.setTime(0L);
        assertEquals(new Date(1_700_000_000_000L), copy.getExpiry());
    }

    @Test
    public final void shouldThrowAnIllegalArgumentExceptionWhenSuppliedCookieIsNull() {

        try {
            new NewCookie.Builder((Cookie) null).build();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie.Builder((Cookie) null).comment("comment").maxAge(120).secure(true).build();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public final void shouldReturnNullWhenSameSiteIsNotSet() {
        NewCookie newCookie = new NewCookie.Builder("name").build();
        assertNull(newCookie.getSameSite());
    }

    @Test
    public final void shouldReturnNullWhenSameSiteIsSetToNull() {
        NewCookie newCookie = new NewCookie.Builder("name").sameSite(null).build();
        assertNull(newCookie.getSameSite());
    }

    @Test
    public final void shouldReturnSuppliedValueWhenSameSiteIsSetToNonNullValue() {

        NewCookie.Builder newCookieBuilder = new NewCookie.Builder("name");

        NewCookie newCookie = newCookieBuilder.sameSite(NewCookie.SameSite.NONE).build();
        assertEquals(NewCookie.SameSite.NONE, newCookie.getSameSite());

        newCookie = newCookieBuilder.sameSite(NewCookie.SameSite.LAX).build();
        assertEquals(NewCookie.SameSite.LAX, newCookie.getSameSite());

        newCookie = newCookieBuilder.sameSite(NewCookie.SameSite.STRICT).build();
        assertEquals(NewCookie.SameSite.STRICT, newCookie.getSameSite());
    }

}
