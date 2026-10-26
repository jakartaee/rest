/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation
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

package ee.jakarta.tck.ws.rs.spec.validation;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Records the IDs of the requests for which a resource method was invoked.
 */
public final class Invocations {

    private static final Set<String> INVOKED = ConcurrentHashMap.newKeySet();

    private Invocations() {
    }

    public static void record(final String id) {
        INVOKED.add(id);
    }

    public static boolean invoked(final String id) {
        return INVOKED.contains(id);
    }
}
