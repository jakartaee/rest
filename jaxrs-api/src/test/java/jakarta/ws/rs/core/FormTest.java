/*
 * Copyright (c) 2024 David Walters. All rights reserved.
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

public class FormTest {

    private static final String AGE_FIELD = "age";
    
    private static final String AGE_VALUE = "25";

    private static final String LOCATION_FIELD = "location";

    private static final String LOCATION_VALUE = "USA";

    private static final String NAME_FIELD = "name";

    private static final String NAME_VALUE = "john";
    
    @Test
    public void testNullEquals() {
        Form form = new Form();
        assertFalse(form.equals(null));
    }

    @Test
    public void testSameObjectEquals() {
        Form form = new Form();
        assertTrue(form.equals(form));
    }

    @Test
    public void testDifferentObjectTypeEquals() {
        Form form = new Form("field", "value");
        Boolean booleanValue = Boolean.TRUE;
        assertFalse(form.equals(booleanValue));
    }

    @Test
    public void testFormContentEquals() {
        Form form1 = new Form(NAME_FIELD,NAME_VALUE).param(AGE_FIELD, AGE_VALUE);
        Form form2 = new Form(NAME_FIELD,NAME_VALUE).param(AGE_FIELD, AGE_VALUE);
        assertTrue(form1.equals(form2));
        form2 = new Form(AGE_FIELD, AGE_VALUE).param(NAME_FIELD,NAME_VALUE);
        assertTrue(form1.equals(form2));
        form2 = new Form(AGE_VALUE, AGE_FIELD).param(NAME_VALUE, NAME_FIELD);
        assertFalse(form1.equals(form2));
        form2 = new Form(AGE_FIELD, AGE_VALUE).param(NAME_FIELD,NAME_VALUE).param(LOCATION_FIELD, LOCATION_VALUE);
        assertFalse(form1.equals(form2));
    }

    @Test
    public void testHashCode() {
        Form form1 = new Form(NAME_FIELD,NAME_VALUE).param(AGE_FIELD, AGE_VALUE);
        Form form2 = new Form(NAME_FIELD,NAME_VALUE).param(AGE_FIELD, AGE_VALUE);
        assertTrue(form1.hashCode() == form2.hashCode());
        form2.param(LOCATION_FIELD, LOCATION_VALUE);
        assertFalse(form1.hashCode() == form2.hashCode());
    }
}
