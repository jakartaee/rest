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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Type literal construction unit tests.
 *
 * @author Marek Potociar
 * @author Martin Matula
 */
public class GenericTypeTest {

    private static final Type arrayListOfStringsType = new ArrayList<String>() {

        private static final long serialVersionUID = 3109256773218160485L;
    }.getClass().getGenericSuperclass();

    public GenericTypeTest() {
    }

    private static class ParameterizedSubclass1 extends GenericType<ArrayList<String>> {
    }

    private static class ParameterizedSubclass2<T, V> extends GenericType<V> {
    }

    @Test
    public void testParameterizedSubclass1() {
        ParameterizedSubclass1 ps = new ParameterizedSubclass1() {
        };

        assertEquals(arrayListOfStringsType, ps.getType());
        assertEquals(ArrayList.class, ps.getRawType());
    }

    @Test
    public void testParameterizedSubclass2() {
        ParameterizedSubclass2<String, ArrayList<String>> ps =
                new ParameterizedSubclass2<String, ArrayList<String>>() {
                };

        assertEquals(arrayListOfStringsType, ps.getType());
        assertEquals(ArrayList.class, ps.getRawType());
    }

    @Test
    public void testConstructor() {

        GenericType type = new GenericType(new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{String.class};
            }

            @Override
            public Type getRawType() {
                return ArrayList.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        });

        assertEquals(ArrayList.class, type.getRawType());
        assertEquals(arrayListOfStringsType, type.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGenericType() throws NoSuchMethodException {
        ArrayList<String> al = new ArrayList<String>();
        Method addMethod = al.getClass().getMethod("add", Object.class);
        final Type type = addMethod.getGenericParameterTypes()[0];
        new GenericType(type);
    }

    @Test
    public void testConstructor2() {
        GenericType gt = new GenericType(arrayListOfStringsType);
        assertEquals(ArrayList.class, gt.getRawType());
        assertEquals(arrayListOfStringsType, gt.getType());
    }

    @Test
    public void testAnonymousConstruction() {
        GenericType<ArrayList<String>> tl = new GenericType<ArrayList<String>>() {
        };
        assertEquals(ArrayList.class, tl.getRawType());
        assertEquals(arrayListOfStringsType, tl.getType());
    }

    @Test
    public void testGenericTypeOfArray() {
        assertEquals(List[].class, new GenericType<List<String>[]>() {
        }.getRawType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullGenericType() {
        new GenericType(null);
    }

    // Regression test for JAX_RS_SPEC-274
    @Test
    public void testGenericTypeOfNonGenericArray() {
        assertEquals(String[].class, new GenericType<String[]>(){}.getRawType());
    }
}
