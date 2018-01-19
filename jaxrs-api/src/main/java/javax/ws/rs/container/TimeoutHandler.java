/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.container;

/**
 * Asynchronous response suspend time-out handler.
 *
 * JAX-RS users may utilize this callback interface to provide
 * custom resolution of time-out events.
 * <p>
 * By default, JAX-RS runtime generates a {@link javax.ws.rs.WebApplicationException}
 * with a {@link javax.ws.rs.core.Response.Status#SERVICE_UNAVAILABLE HTTP 503
 * (Service unavailable)} error response status code. A custom time-out handler
 * may be {@link AsyncResponse#setTimeoutHandler(TimeoutHandler) set} on an
 * asynchronous response instance to provide custom time-out event resolution.
 * </p>
 * <p>
 * In case of a suspend time-out event, a custom time-out handler takes typically one
 * of the following actions:
 * <ul>
 * <li>Resumes the suspended asynchronous response using a {@link AsyncResponse#resume(Object)
 * custom response} or a {@link AsyncResponse#resume(Throwable) custom exception}</li>
 * <li>Cancels the response by calling one of the {@link AsyncResponse} {@code cancel(...)}
 * methods.</li>
 * <li>Extends the suspend period of the response by
 * {@link AsyncResponse#setTimeout(long, java.util.concurrent.TimeUnit)
 * setting a new suspend time-out}</li>
 * </ul>
 * If the registered time-out handler does not take any of the actions above, the
 * default time-out event processing continues and the response is resumed with
 * a generated {@code WebApplicationException} containing the HTTP 503 status code.
 * </p>
 * <p>
 * Following example illustrates the use of a custom {@code TimeoutHandler}:
 * </p>
 * <pre>
 * public class MyTimeoutHandler implements TimeoutHandler {
 *     &hellip;
 *     public void handleTimeout(AsyncResponse ar) {
 *         if (keepSuspended) {
 *             ar.setTimeout(10, SECONDS);
 *         } else if (cancel) {
 *             ar.cancel(retryPeriod);
 *         } else {
 *             ar.resume(defaultResponse);
 *         }
 *     }
 *     &hellip;
 * }
 *
 * &#64;Path("/messages/next")
 * public class MessagingResource {
 *     &hellip;
 *     &#64;GET
 *     public void readMessage(&#64;Suspended AsyncResponse ar) {
 *         ar.setTimeoutHandler(new MyTimeoutHandler());
 *         suspended.put(ar);
 *     }
 *     &hellip;
 * }
 * </pre>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface TimeoutHandler {

    /**
     * Invoked when the suspended asynchronous response is about to time out.
     *
     * Implementing time-out handlers may use the callback method to change the
     * default time-out strategy defined by JAX-RS specification (see
     * {@link javax.ws.rs.container.AsyncResponse} API documentation).
     * <p>
     * A custom time-out handler may decide to either
     * <ul>
     * <li>resume the suspended response using one of it's {@code resume(...)} methods,</li>
     * <li>cancel the suspended response using one of it's {@code cancel(...)} methods, or</li>
     * <li>extend the suspend period by {@link AsyncResponse#setTimeout(long, java.util.concurrent.TimeUnit)
     * setting a new suspend time-out}</li>
     * </ul>
     * </p>
     * In case the time-out handler does not take any of the actions mentioned above,
     * a default time-out strategy is executed by the JAX-RS runtime.
     *
     * @param asyncResponse suspended asynchronous response that is timing out.
     */
    public void handleTimeout(AsyncResponse asyncResponse);
}
