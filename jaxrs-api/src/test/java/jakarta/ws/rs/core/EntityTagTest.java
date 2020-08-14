/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.ws.rs.ext.RuntimeDelegate;

import static org.mockito.Mockito.*;

public class EntityTagTest {

    @Before
    public void setUp() {
        RuntimeDelegate.setInstance(mock(RuntimeDelegate.class));
    }

    @After
    public void tearDown() throws Exception {
        RuntimeDelegate.setInstance(null);
    }

    @Test
    public void shouldBeEqualToTheSameInstance() {
        EntityTag entityTag = new EntityTag("value", true);

        assertThat(entityTag, equalTo(entityTag));
        assertThat(entityTag.hashCode(), equalTo(entityTag.hashCode()));
    }

    @Test
    public void shouldBeEqualsForSameFieldValues() {
        EntityTag entityTag = new EntityTag("value", true);
        EntityTag entityTagWithSameValues = new EntityTag("value", true);
        assertThat(entityTag, equalTo(entityTagWithSameValues));
        assertThat(entityTag.hashCode(), equalTo(entityTagWithSameValues.hashCode()));
    }

    @Test
    public void shouldNotBeEqualIfValueFieldDiffers() {
        EntityTag entityTag = new EntityTag("value", true);
        EntityTag entityTagWithDifferentValue = new EntityTag("differentValue", true);

        assertThat(entityTag, not(equalTo(entityTagWithDifferentValue)));
        assertThat(entityTag.hashCode(), not(equalTo(entityTagWithDifferentValue.hashCode())));
    }

    @Test
    public void shouldNotBeEqualIfWeekSettingDiffers() {
        EntityTag entityTag = new EntityTag("value", true);
        EntityTag entityTagWithDifferentWeakSetting = new EntityTag("value", false);

        assertThat(entityTag, not(equalTo(entityTagWithDifferentWeakSetting)));
        assertThat(entityTag.hashCode(), not(equalTo(entityTagWithDifferentWeakSetting.hashCode())));
    }

    @Test
    public void shouldNeverEqualsNull() {
        EntityTag entityTag = new EntityTag("value", true);

        assertThat(entityTag, not(equalTo(null)));
    }

    @Test
    public void shouldNotBeEqualToObjectFromDifferentClass() {
        EntityTag entityTag = new EntityTag("value", true);

        assertThat(entityTag, not(equalTo(new Object())));
    }
}
