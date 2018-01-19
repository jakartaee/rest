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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NewCookieTest {

    @Before
    public void setUp() throws Exception {
        RuntimeDelegate.setInstance(new RuntimeDelegateStub());
    }

    @After
    public void tearDown() throws Exception {
        RuntimeDelegate.setInstance(null);
    }

    /**
     * Test of valueOf method, of class NewCookie.
     */
    @Test
    public void testCtor() {
        System.out.println("ctor");
        Cookie c = new Cookie("name", "value");
        NewCookie nc = new NewCookie(c);
        assertEquals(nc.getName(), c.getName());
        try {
            nc = new NewCookie(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
        try {
            nc = new NewCookie(null, "comment", 120, true);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }
}
