/*
 * Copyright (c) 2010, 2021 Oracle and/or its affiliates. All rights reserved.
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

import jakarta.ws.rs.core.NewCookie.SameSite;
import java.util.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class NewCookieTest extends BaseDelegateTest {

    @Test
    public void testSameSite() {

        NewCookie sameSiteOmit = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false);
        assertNull(sameSiteOmit.getSameSite());
        sameSiteOmit = new NewCookie.Builder("name").build();
        assertNull(sameSiteOmit.getSameSite());

        NewCookie sameSiteNull = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, null);
        assertNull(sameSiteNull.getSameSite());
        sameSiteNull = new NewCookie.Builder("name").sameSite(null).build();
        assertNull(sameSiteNull.getSameSite());

        NewCookie sameSiteNone = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, NewCookie.SameSite.NONE);
        assertEquals(NewCookie.SameSite.NONE, sameSiteNone.getSameSite());
        sameSiteNone = new NewCookie.Builder("name").sameSite(NewCookie.SameSite.NONE).build();
        assertEquals(NewCookie.SameSite.NONE, sameSiteNone.getSameSite());

        NewCookie sameSiteLax = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, NewCookie.SameSite.LAX);
        assertEquals(NewCookie.SameSite.LAX, sameSiteLax.getSameSite());
        sameSiteLax = new NewCookie.Builder("name").sameSite(NewCookie.SameSite.LAX).build();
        assertEquals(NewCookie.SameSite.LAX, sameSiteLax.getSameSite());

        NewCookie sameSiteStrict = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, NewCookie.SameSite.STRICT);
        assertEquals(NewCookie.SameSite.STRICT, sameSiteStrict.getSameSite());
        sameSiteStrict = new NewCookie.Builder("name").sameSite(NewCookie.SameSite.STRICT).build();
        assertEquals(NewCookie.SameSite.STRICT, sameSiteStrict.getSameSite());

    }

    @Test
    public final void shouldReturnFalseWhenComparingNewCookieToNullObject() {
        NewCookie newCookie = new NewCookie("name", "value");
        assertFalse(newCookie.equals(null));

        newCookie = new NewCookie.Builder("name").value("value").build();
        assertFalse(newCookie.equals(null));
    }

    @Test
    public final void shouldReturnFalseWhenComparingNewCookieToCookie() {
        Cookie cookie = new Cookie("name", "value");
        NewCookie newCookie = new NewCookie("name", "value");
        assertFalse(newCookie.equals(cookie));

        NewCookie thisNewCookie = new NewCookie.Builder("name").value("value").build();
        Cookie thatCookie = new Cookie.Builder("name").value("value").build();
        assertFalse(thisNewCookie.equals(thatCookie));
    }

    @Test
    public final void shouldReturnFalseWhenComparingNewCookiesThatHaveDifferentValues() {
        NewCookie newCookie = new NewCookie("name", "value");
        NewCookie newCookie2 = new NewCookie("name", "value2");
        assertFalse(newCookie.equals(newCookie2));

        NewCookie thisNewCookie = new NewCookie.Builder("name").value("value").build();
        NewCookie thatNewCookie = new NewCookie.Builder("name").value("value2").build();
        assertFalse(thisNewCookie.equals(thatNewCookie));
    }

    @Test
    public final void shouldReturnTrueWhenComparingNewCookiesThatHaveSameValues() {
        NewCookie newCookie = new NewCookie("name", "value");
        NewCookie newCookie1 = new NewCookie("name", "value");
        assertTrue(newCookie.equals(newCookie1));

        NewCookie thisNewCookie = new NewCookie.Builder("name").value("value").build();
        NewCookie thatNewCookie = new NewCookie.Builder("name").value("value").build();
        assertTrue(thisNewCookie.equals(thatNewCookie));
    }

    @Test
    public final void shouldReturnSuppliedCookiePropertiesWhenBuildingNewCookiesFromCookie() {
        Cookie cookie = new Cookie("name", "value", "path", "domain", Cookie.DEFAULT_VERSION);
        NewCookie newCookie = new NewCookie(cookie);
        assertEquals(newCookie.getName(), cookie.getName());
        assertEquals(newCookie.getPath(), cookie.getPath());
        assertEquals(newCookie.getDomain(), cookie.getDomain());
        assertEquals(newCookie.getVersion(), cookie.getVersion());

        cookie = new Cookie.Builder("name")
                .value("value")
                .path("path")
                .domain("domain")
                .version(Cookie.DEFAULT_VERSION)
                .build();
        newCookie = new NewCookie.Builder(cookie).build();
        assertEquals(newCookie.getName(), cookie.getName());
        assertEquals(newCookie.getPath(), cookie.getPath());
        assertEquals(newCookie.getDomain(), cookie.getDomain());
        assertEquals(newCookie.getVersion(), cookie.getVersion());
    }

    @Test
    public final void shouldThrowAnIllegalArgumentExceptionWhenBuildingNewCookieWithNullName() {

        try {
            new NewCookie(null, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "value", "path", "domain", "comment", NewCookie.DEFAULT_MAX_AGE, false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "value", "path", "domain", "comment", NewCookie.DEFAULT_MAX_AGE, false, false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "value", "path", "domain", Cookie.DEFAULT_VERSION, "comment", NewCookie.DEFAULT_MAX_AGE, false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "value", "path", "domain", Cookie.DEFAULT_VERSION, "comment", NewCookie.DEFAULT_MAX_AGE, new Date(), false, false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "value", "path", "domain", Cookie.DEFAULT_VERSION, "comment", NewCookie.DEFAULT_MAX_AGE, new Date(), false, false, SameSite.LAX);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie.Builder((String) null).build();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public final void shouldThrowAnIllegalArgumentExceptionWhenBuildingNewCookieFromNullCookie() {

        try {
            new NewCookie((Cookie)null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "comment", NewCookie.DEFAULT_MAX_AGE, false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "comment", NewCookie.DEFAULT_MAX_AGE, new Date(), false, false);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie(null, "comment", NewCookie.DEFAULT_MAX_AGE, new Date(), false, false, SameSite.LAX);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new NewCookie.Builder((Cookie) null).build();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }
}
