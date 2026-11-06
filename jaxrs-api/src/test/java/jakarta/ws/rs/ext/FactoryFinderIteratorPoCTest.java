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

package jakarta.ws.rs.ext;

import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.ServiceLoader;

import org.junit.jupiter.api.Test;

/**
 * PoC for the FactoryFinder iterator bug: each {@code ServiceLoader.iterator()}
 * call returns a new iterator starting at the beginning. Using two calls for
 * {@code hasNext()} / {@code next()} is therefore incorrect; FactoryFinder now
 * reuses a single iterator.
 */
public class FactoryFinderIteratorPoCTest {

    @Test
    public void serviceLoaderReturnsIndependentIterators() {
        final ServiceLoader<RuntimeDelegate> loader = ServiceLoader.load(RuntimeDelegate.class);
        assertNotSame(loader.iterator(), loader.iterator(),
                "Two iterator() calls must not be treated as one cursor");
    }
}
