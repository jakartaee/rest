/*
 * Copyright (c) 2026 Oracle and/or its affiliates. All rights reserved.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.ext.RuntimeDelegate;
import jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

/**
 * Proof that deprecated {@code toString} follows the current {@link RuntimeDelegate}
 * after {@link RuntimeDelegate#setInstance}, instead of a delegate captured when the
 * class was initialized. See https://github.com/jakartaee/rest/issues/607.
 */
public class HeaderDelegateResolveOnUseTest {

    @AfterEach
    public void tearDown() {
        RuntimeDelegate.setInstance(null);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void cookieToStringFollowsTheCurrentDelegate() {
        assertSecondDelegate(new Cookie("n", "v"), Cookie.class);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void newCookieToStringFollowsTheCurrentDelegate() {
        assertSecondDelegate(new NewCookie("n", "v"), NewCookie.class);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void cacheControlToStringFollowsTheCurrentDelegate() {
        assertSecondDelegate(new CacheControl(), CacheControl.class);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void entityTagToStringFollowsTheCurrentDelegate() {
        assertSecondDelegate(new EntityTag("abc"), EntityTag.class);
    }

    @Test
    @SuppressWarnings({"deprecation", "unchecked"})
    public void cookieValueOfFollowsTheCurrentDelegate() {
        final HeaderDelegate<Cookie> first = mock(HeaderDelegate.class);
        final HeaderDelegate<Cookie> second = mock(HeaderDelegate.class);
        final Cookie parsed = new Cookie("n", "v");
        when(first.fromString("cookie-text")).thenReturn(parsed);
        when(second.fromString("cookie-text")).thenReturn(parsed);

        final RuntimeDelegate rd1 = mock(RuntimeDelegate.class);
        when(rd1.createHeaderDelegate(Cookie.class)).thenReturn(first);
        RuntimeDelegate.setInstance(rd1);
        assertEquals(parsed, Cookie.valueOf("cookie-text"));

        final RuntimeDelegate rd2 = mock(RuntimeDelegate.class);
        when(rd2.createHeaderDelegate(Cookie.class)).thenReturn(second);
        RuntimeDelegate.setInstance(rd2);
        assertEquals(parsed, Cookie.valueOf("cookie-text"));
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    private static <T> void assertSecondDelegate(final T value, final Class<T> type) {
        final HeaderDelegate<T> first = mock(HeaderDelegate.class);
        final HeaderDelegate<T> second = mock(HeaderDelegate.class);
        when(first.toString(any())).thenReturn("from-first");
        when(second.toString(any())).thenReturn("from-second");

        final RuntimeDelegate rd1 = mock(RuntimeDelegate.class);
        when(rd1.createHeaderDelegate(type)).thenReturn(first);
        RuntimeDelegate.setInstance(rd1);
        assertEquals("from-first", value.toString());

        final RuntimeDelegate rd2 = mock(RuntimeDelegate.class);
        when(rd2.createHeaderDelegate(type)).thenReturn(second);
        RuntimeDelegate.setInstance(rd2);
        assertEquals("from-second", value.toString());
    }
}
