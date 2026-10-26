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

package ee.jakarta.tck.ws.rs.spec.context.cdi.request;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.AsyncResponse;

/**
 * Hands a suspended {@link AsyncResponse} from one request to another.
 */
@ApplicationScoped
public class SuspendedResponses {

    private final Map<String, CompletableFuture<AsyncResponse>> responses = new ConcurrentHashMap<>();

    public void suspend(final String id, final AsyncResponse asyncResponse) {
        responses.computeIfAbsent(id, key -> new CompletableFuture<>()).complete(asyncResponse);
    }

    public CompletableFuture<AsyncResponse> take(final String id) {
        return responses.computeIfAbsent(id, key -> new CompletableFuture<>())
                .whenComplete((asyncResponse, error) -> responses.remove(id));
    }
}
