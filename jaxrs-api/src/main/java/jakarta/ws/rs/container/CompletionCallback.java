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
 * A completion callback is always invoked when the whole request processing is over, i.e. once a response for the request has
 * been processed and sent back to the client (including processing by a custom exception mapper) or when an unmapped
 * exception or error is being propagated to the default exception mapper.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface CompletionCallback {
    /**
     * An unmapped throwable is propagated to the default exception mapper in case no {@link jakarta.ws.rs.ext.ExceptionMapper
     * exception mapper} has been found for a request processing failure. In this case a non-{@code null} unmapped throwable
     * instance is passed to the method. Note that the throwable instance represents the actual unmapped exception thrown during
     * the request processing before it has been mapped to the response by the default exception mapper.
     *
     * @param throwable is {@code null}, if the request processing has completed with a response that has been sent to the
     * client (including processing by a custom exception mapper). In case the request processing resulted in an unmapped
     * exception or error that has yet to be propagated to the default exception mapper, this parameter contains the unmapped
     * exception instance.
     */
    public void onComplete(Throwable throwable);
}
