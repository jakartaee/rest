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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CookieTest extends BaseDelegateTest {

    @Test
    public final void shouldReturnFalseWhenComparingCookieToNullObject() {
        Cookie cookie = new Cookie("name", "value");
        assertFalse(cookie.equals(null));

        cookie = new Cookie.Builder("name").value("value").build();
        assertFalse(cookie.equals(null));
    }

    @Test
    public final void shouldReturnFalseWhenComparingCookieToNewCookie() {
        Cookie cookie = new Cookie("name", "value");
        NewCookie newCookie = new NewCookie("name", "value");
        assertFalse(cookie.equals(newCookie));

        Cookie thisCookie = new Cookie.Builder("name").value("value").build();
        NewCookie thatNewCookie = new NewCookie.Builder("name").value("value").build();
        assertFalse(thisCookie.equals(thatNewCookie));
    }

    @Test
    public final void shouldReturnFalseWhenComparingCookiesThatHaveDifferentValues() {
        Cookie cookie = new Cookie("name", "value");
        Cookie cookie2 = new Cookie("name", "value2");
        assertFalse(cookie.equals(cookie2));

        Cookie thisCookie = new Cookie.Builder("name").value("value").build();
        Cookie thatCookie = new Cookie.Builder("name").value("value2").build();
        assertFalse(thisCookie.equals(thatCookie));
    }

    @Test
    public final void shouldReturnTrueWhenComparingCookiesThatHaveSameValues() {

        Cookie cookie = new Cookie("name", "value");
        Cookie cookie1 = new Cookie("name", "value");
        NewCookie newCookie = new NewCookie("name", "value");
        assertTrue(cookie.equals(cookie1));
        assertTrue(cookie.equals(newCookie.toCookie()));

        Cookie thisCookie = new Cookie.Builder("name").value("value").build();
        Cookie thatCookie = new Cookie.Builder("name").value("value").build();
        assertTrue(thisCookie.equals(thatCookie));

        thatCookie = new NewCookie.Builder("name").value("value").build().toCookie();
        assertTrue(thisCookie.equals(thatCookie));
    }

    @Test
    public final void shouldThrowAnIllegalArgumentExceptionWhenBuildingCookieWithNullName() {

        try {
            new Cookie(null, "value");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new Cookie(null, "value", "path", "domain");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new Cookie(null, "value", "path", "domain", Cookie.DEFAULT_VERSION);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            new Cookie.Builder(null).build();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

}
