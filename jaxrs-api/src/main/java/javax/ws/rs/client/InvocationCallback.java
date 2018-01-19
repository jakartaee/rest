/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.client;

/**
 * Callback that can be implemented to receive the asynchronous processing
 * events from the invocation processing.
 *
 * @param <RESPONSE> response type. It can be either a general-purpose
 *                   {@link javax.ws.rs.core.Response} or the anticipated response entity
 *                   type.
 * @author Marek Potociar
 * @since 2.0
 */
public interface InvocationCallback<RESPONSE> {

    /**
     * Called when the invocation was successfully completed. Note that this does
     * not necessarily mean the response has bean fully read, which depends on the
     * parameterized invocation callback response type.
     * <p>
     * Once this invocation callback method returns, the underlying {@link javax.ws.rs.core.Response}
     * instance will be automatically closed by the runtime.
     * </p>
     *
     * @param response response data.
     */
    public void completed(RESPONSE response);

    /**
     * Called when the invocation has failed for any reason.
     * <p>
     * Note that the provided {@link Throwable} may be a {@link javax.ws.rs.ProcessingException} in case the
     * invocation processing failure has been caused by a client-side runtime component error.
     * The {@code Throwable} may also be a {@link javax.ws.rs.WebApplicationException} or one
     * of its subclasses in case the response status code is not
     * {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL successful} and the generic
     * callback type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link javax.ws.rs.core.Response}
     * instance whose processing has failed.
     * A {@link java.util.concurrent.CancellationException} would be indicate that the invocation
     * has been cancelled.
     * An {@link InterruptedException} would indicate that the thread executing the invocation has
     * been interrupted.
     * </p>
     * <p>
     * Once this invocation callback method returns, the underlying {@link javax.ws.rs.core.Response}
     * instance will be automatically closed by the runtime.
     * </p>
     *
     * @param throwable contains failure details.
     */
    public void failed(Throwable throwable);
}
