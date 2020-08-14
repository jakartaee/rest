/*
 * Copyright (c) 2010, 2019 Oracle and/or its affiliates. All rights reserved.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.ws.rs.ext.RuntimeDelegate;

public class MultivaluedHashMapTest {

    @Before
    public void setUp() throws Exception {
        RuntimeDelegate.setInstance(new RuntimeDelegateStub());
    }

    @After
    public void tearDown() throws Exception {
        RuntimeDelegate.setInstance(null);
    }

    @Test
    public void testEqualsIgnoreOrder() {
        MultivaluedHashMap<String, String> mvm1 = new MultivaluedHashMap<String, String>();
        mvm1.addAll("foo1", "bar1", "bar2");
        mvm1.addAll("foo2", "baz1", "baz2");
        MultivaluedHashMap<String, String> mvm2 = new MultivaluedHashMap<String, String>();
        mvm2.addAll("foo1", "bar2", "bar1");
        mvm2.addAll("foo2", "baz2", "baz1");
        assertTrue(mvm1.equalsIgnoreValueOrder(mvm2));
        assertTrue(mvm2.equalsIgnoreValueOrder(mvm1));
    }

    @Test
    public void testEqualsIgnoreOrderInclusion() {
        MultivaluedHashMap<String, String> mvm1 = new MultivaluedHashMap<String, String>();
        mvm1.addAll("foo1", "bar1", "bar2");
        MultivaluedHashMap<String, String> mvm2 = new MultivaluedHashMap<String, String>();
        mvm2.addAll("foo1", "bar2", "bar1");
        mvm2.addAll("foo2", "baz2", "baz1");
        assertFalse(mvm1.equalsIgnoreValueOrder(mvm2));
        assertFalse(mvm2.equalsIgnoreValueOrder(mvm1));
    }

    @Test
    public void testEqualsIgnoreListSize() {
        MultivaluedHashMap<String, String> mvm1 = new MultivaluedHashMap<String, String>();
        mvm1.addAll("foo1", "bar1", "bar2");
        MultivaluedHashMap<String, String> mvm2 = new MultivaluedHashMap<String, String>();
        mvm2.addAll("foo1", "bar2", "bar1", "bar3");
        assertFalse(mvm1.equalsIgnoreValueOrder(mvm2));
        assertFalse(mvm2.equalsIgnoreValueOrder(mvm1));
    }

    @Test
    public void testEqualsEmpty() {
        MultivaluedHashMap<String, String> mvm1 = new MultivaluedHashMap<String, String>();
        MultivaluedHashMap<String, String> mvm2 = new MultivaluedHashMap<String, String>();
        assertTrue(mvm1.equals(mvm2));
        assertTrue(mvm2.equals(mvm1));
        assertTrue(mvm1.equalsIgnoreValueOrder(mvm2));
        assertTrue(mvm2.equalsIgnoreValueOrder(mvm1));
    }

    @Test
    public void testEqualsSame() {
        MultivaluedHashMap<String, String> mvm1 = new MultivaluedHashMap<String, String>();
        assertTrue(mvm1.equals(mvm1));
        assertTrue(mvm1.equalsIgnoreValueOrder(mvm1));
    }

    @Test
    public void testEqualsWithDuplicates() {
        MultivaluedHashMap<String, String> mvm1 = new MultivaluedHashMap<String, String>();
        mvm1.addAll("foo1", "bar1", "bar2", "bar1");
        MultivaluedHashMap<String, String> mvm2 = new MultivaluedHashMap<String, String>();
        mvm2.addAll("foo1", "bar2", "bar1");
        assertFalse(mvm1.equalsIgnoreValueOrder(mvm2));
        assertFalse(mvm2.equalsIgnoreValueOrder(mvm1));
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        MultivaluedHashMap<String, String> mvm = new MultivaluedHashMap<String, String>();
        mvm.addAll("foo1", "bar1", "bar2", "bar1");

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream objOut = new ObjectOutputStream(out)) {

            objOut.writeObject(mvm);

            try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
                    ObjectInputStream objIn = new ObjectInputStream(in)) {

                assertEquals(mvm, objIn.readObject());
            }
        }

    }
}
