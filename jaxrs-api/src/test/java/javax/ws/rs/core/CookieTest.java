/*
 * Copyright (c) 2010, 2017 Oracle and/or its affiliates. All rights reserved.
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

import javax.ws.rs.ext.RuntimeDelegate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CookieTest {

    @Before
    public void setUp() throws Exception {
        RuntimeDelegate.setInstance(new RuntimeDelegateStub());
    }

    @After
    public void tearDown() throws Exception {
        RuntimeDelegate.setInstance(null);
    }

    /**
     * Test of equals method, of class Cookie and NewCookie.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object nullObj = null;
        Cookie cookie = new Cookie("name", "value");
        Cookie cookie1 = new Cookie("name", "value");
        Cookie cookie2 = new Cookie("name", "value2");
        NewCookie newCookie = new NewCookie("name", "value");
        NewCookie newCookie1 = new NewCookie("name", "value");
        NewCookie newCookie2 = new NewCookie("name", "value2");
        assertFalse(cookie.equals(nullObj));
        assertFalse(cookie.equals(newCookie));
        assertFalse(cookie.equals(cookie2));
        assertTrue(cookie.equals(cookie1));
        assertTrue(cookie.equals(newCookie.toCookie()));
        assertTrue(newCookie.equals(newCookie1));
        assertFalse(newCookie.equals(newCookie2));
    }
}
