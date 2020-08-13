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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class GenericEntityTest {

    @Test
    public void testListOfString() {
        List<String> list = new ArrayList<String>();
        GenericEntity<List<String>> listOfString = new GenericEntity<List<String>>(list) {
        };
        Class<?> rawType = listOfString.getRawType();
        assertEquals(rawType, ArrayList.class);
        Type type = listOfString.getType();
        assertTrue(type instanceof ParameterizedType);
        ParameterizedType pType = (ParameterizedType) type;
        Type typeArgs[] = pType.getActualTypeArguments();
        assertEquals(1, typeArgs.length);
        assertTrue(typeArgs[0] instanceof Class<?>);
        Class<?> typeArgType = (Class<?>) typeArgs[0];
        assertEquals(typeArgType, String.class);
    }

    @Test
    public void testMapOfStringInteger() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        GenericEntity<Map<String, Integer>> mapOfString = new GenericEntity<Map<String, Integer>>(map) {
        };
        Class<?> rawType = mapOfString.getRawType();
        assertEquals(rawType, HashMap.class);
        Type type = mapOfString.getType();
        assertTrue(type instanceof ParameterizedType);
        ParameterizedType pType = (ParameterizedType) type;
        Type typeArgs[] = pType.getActualTypeArguments();
        assertEquals(2, typeArgs.length);
        assertTrue(typeArgs[0] instanceof Class<?>);
        Class<?> typeArgType = (Class<?>) typeArgs[0];
        assertEquals(typeArgType, String.class);
        assertTrue(typeArgs[1] instanceof Class<?>);
        typeArgType = (Class<?>) typeArgs[1];
        assertEquals(typeArgType, Integer.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testArrayOfListOfString() {
        List<String> array[] = new List[1];
        GenericEntity<List<String>[]> arrayOfListOfString = new GenericEntity<List<String>[]>(array) {
        };
        Class<?> rawType = arrayOfListOfString.getRawType();
        assertTrue(rawType.isArray());
        Type type = arrayOfListOfString.getType();
        assertTrue(type instanceof GenericArrayType);
        type = ((GenericArrayType) type).getGenericComponentType();
        assertTrue(type instanceof ParameterizedType);
        ParameterizedType pType = (ParameterizedType) type;
        Type typeArgs[] = pType.getActualTypeArguments();
        assertEquals(1, typeArgs.length);
        assertTrue(typeArgs[0] instanceof Class<?>);
        Class<?> typeArgType = (Class<?>) typeArgs[0];
        assertEquals(typeArgType, String.class);
    }

    @Test
    public void testNumber() {
        Number n = Integer.valueOf(0);
        GenericEntity<Number> number = new GenericEntity<Number>(n) {
        };
        Class<?> rawType = number.getRawType();
        assertEquals(rawType, Integer.class);
        Type type = number.getType();
        assertTrue(type instanceof Class);
        Class<?> genericType = (Class<?>) type;
        assertEquals(genericType, Number.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCtor() {
        try {
            // check GenericEntity(Integer, Number) works
            Method getNumber = this.getClass().getDeclaredMethod("getNumber");
            Type rt = getNumber.getGenericReturnType();
            new GenericEntity<>(1, rt);
            // check GenericEntity(String, Number) fails
            try {
                new GenericEntity<>("foo", rt);
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
            }
            // check GenericEntity(Integer[], Number[]) works
            Method getNumbers = this.getClass().getDeclaredMethod("getNumbers");
            rt = getNumbers.getGenericReturnType();
            Integer ints[] = { 1, 2 };
            new GenericEntity<>(ints, rt);
            // check GenericEntity(String[], Number[]) fails
            try {
                String strings[] = { "foo", "bar" };
                new GenericEntity<>(strings, rt);
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
            }
            // check GenericEntity(ArrayList<String>, List<String>) works
            Method getList = this.getClass().getDeclaredMethod("getList");
            rt = getList.getGenericReturnType();
            ArrayList<String> als = new ArrayList<String>();
            new GenericEntity<>(als, rt);
            // check GenericEntity(ArrayList<Integer>, List<String>) works
            // note that erasure loses the generic type of the ArrayList
            ArrayList<Integer> ali = new ArrayList<Integer>();
            new GenericEntity<>(ali, rt);
            // check GenericEntity(Set<String>, List<String>) fails
            try {
                Set<String> ss = new HashSet<String>();
                new GenericEntity<>(ss, rt);
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
            }
            // check GenericEntity(ArrayList<String>[], List<String>[]) works
            Method getLists = this.getClass().getDeclaredMethod("getLists");
            rt = getLists.getGenericReturnType();
            ArrayList<String>[] lists = new ArrayList[1];
            new GenericEntity<>(lists, rt);
            // check GenericEntity(ArrayList<Integer>[], List<String>[]) works
            // note that erasure loses the generic type of the ArrayList
            ArrayList<Integer>[] ilists = new ArrayList[1];
            new GenericEntity<>(ilists, rt);
            // check GenericEntity(Set<String>[], List<String>[]) fails
            try {
                Set<String>[] ss = new Set[1];
                new GenericEntity<>(ss, rt);
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unhandled Exception: " + e);
        }
    }

    @SuppressWarnings("unused")
    private Number getNumber() {
        return null;
    }

    @SuppressWarnings("unused")
    private Number[] getNumbers() {
        return null;
    }

    @SuppressWarnings("unused")
    private List<String> getList() {
        return null;
    }

    @SuppressWarnings("unused")
    private List<String>[] getLists() {
        return null;
    }
}
