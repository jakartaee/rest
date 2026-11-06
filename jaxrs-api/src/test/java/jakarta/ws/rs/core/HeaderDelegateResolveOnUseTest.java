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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.ext.RuntimeDelegate;
import jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

/**
 * PoC: deprecated {@code toString}/{@code valueOf} must resolve the header delegate
 * from the current {@link RuntimeDelegate}, not a static snapshot from class init
 * (see https://github.com/jakartaee/rest/issues/607).
 */
public class HeaderDelegateResolveOnUseTest {

    @AfterEach
    public void tearDown() {
        RuntimeDelegate.setInstance(null);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void cookieToStringUsesDelegateFromCurrentRuntimeDelegate() {
        @SuppressWarnings("unchecked")
        final HeaderDelegate<Cookie> first = mock(HeaderDelegate.class);
        @SuppressWarnings("unchecked")
        final HeaderDelegate<Cookie> second = mock(HeaderDelegate.class);

        final RuntimeDelegate rd1 = mock(RuntimeDelegate.class);
        when(rd1.createHeaderDelegate(Cookie.class)).thenReturn(first);
        when(first.toString(any(Cookie.class))).thenReturn("from-first");
        RuntimeDelegate.setInstance(rd1);

        final Cookie cookie = new Cookie("n", "v");
        assertEquals("from-first", cookie.toString());
        verify(first).toString(cookie);

        final RuntimeDelegate rd2 = mock(RuntimeDelegate.class);
        when(rd2.createHeaderDelegate(Cookie.class)).thenReturn(second);
        when(second.toString(any(Cookie.class))).thenReturn("from-second");
        RuntimeDelegate.setInstance(rd2);

        assertEquals("from-second", cookie.toString());
        verify(second).toString(cookie);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void entityTagToStringFollowsSetInstance() {
        @SuppressWarnings("unchecked")
        final HeaderDelegate<EntityTag> first = mock(HeaderDelegate.class);
        @SuppressWarnings("unchecked")
        final HeaderDelegate<EntityTag> second = mock(HeaderDelegate.class);

        final RuntimeDelegate rd1 = mock(RuntimeDelegate.class);
        when(rd1.createHeaderDelegate(EntityTag.class)).thenReturn(first);
        when(first.toString(any(EntityTag.class))).thenReturn("etag-1");
        RuntimeDelegate.setInstance(rd1);

        final EntityTag tag = new EntityTag("abc");
        assertEquals("etag-1", tag.toString());

        final RuntimeDelegate rd2 = mock(RuntimeDelegate.class);
        when(rd2.createHeaderDelegate(EntityTag.class)).thenReturn(second);
        when(second.toString(any(EntityTag.class))).thenReturn("etag-2");
        RuntimeDelegate.setInstance(rd2);

        assertEquals("etag-2", tag.toString());
    }
}
