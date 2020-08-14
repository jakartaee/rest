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

public class CookieTest extends BaseDelegateTest {

    /**
     * Test of equals method, of class Cookie and NewCookie.
     */
    @Test
    public void testEquals() {
        Object nullObj = null;
        Cookie cookie = new Cookie.Builder("name").value("value").build();
        Cookie cookie1 = new Cookie.Builder("name").value("value").build();
        Cookie cookie2 = new Cookie.Builder("name").value("value2").build();
        NewCookie newCookie = new NewCookie.Builder("name").value("value").build();
        NewCookie newCookie1 = new NewCookie.Builder("name").value("value").build();
        NewCookie newCookie2 = new NewCookie.Builder("name").value("value2").build();
        assertFalse(cookie.equals(nullObj));
        assertFalse(cookie.equals(newCookie));
        assertFalse(cookie.equals(cookie2));
        assertTrue(cookie.equals(cookie1));
        assertTrue(cookie.equals(newCookie.toCookie()));
        assertTrue(newCookie.equals(newCookie1));
        assertFalse(newCookie.equals(newCookie2));
    }
}
