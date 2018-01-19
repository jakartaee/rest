/*
 * Copyright (c) 2014, 2017 Oracle and/or its affiliates. All rights reserved.
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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link javax.ws.rs.core.CacheControl} unit tests.
 *
 * @author Marek Potociar
 */
public class CacheControlTest {

    @Before
    public void setUp() throws Exception {
        RuntimeDelegate.setInstance(new RuntimeDelegateStub());
    }

    @After
    public void tearDown() throws Exception {
        RuntimeDelegate.setInstance(null);
    }

    /**
     * {@code CacheControl.equals()} contract test.
     *
     * This is a reproducer for JAX_RS_SPEC-471.
     */
    @Test
    public void testCacheControlsEqualsContract() {
        CacheControl first = new CacheControl();

        CacheControl second = new CacheControl();
        assertThat(first, equalTo(second));

        second.getCacheExtension(); // trigger lazy initialization
        assertThat(first, equalTo(second));
        assertThat(second, equalTo(first));

        CacheControl third = new CacheControl();
        third.getNoCacheFields(); // trigger lazy initialization
        assertThat(first, equalTo(third));
        assertThat(third, equalTo(first));
        assertThat(second, equalTo(third));
        assertThat(third, equalTo(second));

        CacheControl fourth = new CacheControl();
        fourth.getPrivateFields(); // trigger lazy initialization
        assertThat(first, equalTo(fourth));
        assertThat(fourth, equalTo(first));
        assertThat(second, equalTo(fourth));
        assertThat(fourth, equalTo(second));
        assertThat(third, equalTo(fourth));
        assertThat(fourth, equalTo(third));
    }

    /**
     * {@code CacheControl.hashCode()} contract test.
     *
     * This is a reproducer for JAX_RS_SPEC-471.
     */
    @Test
    public void testCacheControlsHashCodeContract() {
        CacheControl first = new CacheControl();

        CacheControl second = new CacheControl();
        assertThat(first.hashCode(), equalTo(second.hashCode()));

        second.getCacheExtension(); // trigger lazy initialization
        assertThat(first.hashCode(), equalTo(second.hashCode()));

        CacheControl third = new CacheControl();
        third.getNoCacheFields(); // trigger lazy initialization
        assertThat(first.hashCode(), equalTo(third.hashCode()));
        assertThat(second.hashCode(), equalTo(third.hashCode()));

        CacheControl fourth = new CacheControl();
        fourth.getPrivateFields(); // trigger lazy initialization
        assertThat(first.hashCode(), equalTo(fourth.hashCode()));
        assertThat(second.hashCode(), equalTo(fourth.hashCode()));
        assertThat(third.hashCode(), equalTo(fourth.hashCode()));
    }
}
