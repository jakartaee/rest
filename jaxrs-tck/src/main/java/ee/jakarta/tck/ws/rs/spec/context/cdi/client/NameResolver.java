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

package ee.jakarta.tck.ws.rs.spec.context.cdi.client;

import jakarta.ws.rs.ext.ContextResolver;

/**
 * Identifies the client it is registered with. It is only registered with clients, so the {@code Providers} of the
 * server runtime cannot resolve it.
 */
public class NameResolver implements ContextResolver<NameResolver.Name> {

    private final String name;

    public NameResolver(final String name) {
        this.name = name;
    }

    @Override
    public Name getContext(final Class<?> type) {
        return new Name(name);
    }

    public record Name(String value) {
    }
}
