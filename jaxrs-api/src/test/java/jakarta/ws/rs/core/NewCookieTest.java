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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NewCookieTest extends BaseDelegateTest {

    /**
     * Test of valueOf method, of class NewCookie.
     */
    @Test
    public void testCtor() {
        Cookie c = new Cookie("name", "value");
        NewCookie nc = new NewCookie(c);
        assertEquals(nc.getName(), c.getName());
        try {
            nc = new NewCookie((Cookie) null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
        try {
            nc = new NewCookie(null, "comment", 120, true);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSameSite() {

        NewCookie sameSiteOmit = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false);
        assertNull(sameSiteOmit.getSameSite());

        NewCookie sameSiteNull = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, null);
        assertNull(sameSiteNull.getSameSite());

        NewCookie sameSiteNone = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, NewCookie.SameSite.NONE);
        assertEquals(NewCookie.SameSite.NONE, sameSiteNone.getSameSite());

        NewCookie sameSiteLax = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, NewCookie.SameSite.LAX);
        assertEquals(NewCookie.SameSite.LAX, sameSiteLax.getSameSite());

        NewCookie sameSiteStrict = new NewCookie("name", "value", "/", "localhost", 1, null, 0, null, false, false, NewCookie.SameSite.STRICT);
        assertEquals(NewCookie.SameSite.STRICT, sameSiteStrict.getSameSite());

    }

    @Test
    public final void shouldReturnFalseWhenComparingNewCookieToNullObject() {
        NewCookie newCookie = new NewCookie.Builder("name").value("value").build();
        assertFalse(newCookie.equals(null));
    }

    @Test
    public final void shouldReturnFalseWhenComparingNewCookieToCookie() {
        NewCookie thisNewCookie = new NewCookie.Builder("name").value("value").build();
        Cookie thatCookie = new Cookie.Builder("name").value("value").build();
        assertFalse(thisNewCookie.equals(thatCookie));
    }

    @Test
    public final void shouldReturnFalseWhenComparingNewCookiesThatHaveDifferentValues() {
        NewCookie thisNewCookie = new NewCookie.Builder("name").value("value").build();
        NewCookie thatNewCookie = new NewCookie.Builder("name").value("value2").build();
        assertFalse(thisNewCookie.equals(thatNewCookie));
    }

    @Test
    public final void shouldReturnTrueWhenComparingNewCookiesThatHaveSameValues() {
        NewCookie thisNewCookie = new NewCookie.Builder("name").value("value").build();
        NewCookie thatNewCookie = new NewCookie.Builder("name").value("value").build();
        assertTrue(thisNewCookie.equals(thatNewCookie));
    }

    @Test
    public final void shouldReturnNullWhenComparingNewCookiesThatHaveSameValues() {
        NewCookie thisNewCookie = new NewCookie.Builder("name").value("value").build();
        NewCookie thatNewCookie = new NewCookie.Builder("name").value("value").build();
        assertTrue(thisNewCookie.equals(thatNewCookie));
    }
}
