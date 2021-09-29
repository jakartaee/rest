/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.container;

/**
 * A request processing callback that receives request processing completion events.
 * <p>
 * A completion callback is invoked when the whole request processing is over, i.e. once a response for the request has
 * been processed and sent back to the client.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface CompletionCallback {
    /**
     * A completion callback notification method that will be invoked when the request processing is finished, after a
     * response is processed and is sent back to the client.
     * <p>
     * Deprecated. Since 3.1, a possible throwable occurring during the request processing is always mapped to a response
     * (either by a custom exception mapper or by a default exception mapper) and the method throwable
     * argument is always null.
     * </p>
     *
     * @param throwable is always {@code null}.
     * @deprecated  As of release 3.1, replaced by {@link #onComplete()}.
     */
    @Deprecated(since="3.1", forRemoval = true)
    void onComplete(Throwable throwable);

    /**
     * A completion callback notification method that will be invoked when the request processing is finished, after a
     * response is processed and is sent back to the client.
     *
     * @since 3.1
     */
    @SuppressWarnings("deprecation")
    default void onComplete() {
        onComplete(null);
    }
}
