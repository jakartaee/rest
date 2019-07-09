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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Inject a suspended {@link AsyncResponse} into a parameter of an invoked
 * {@link javax.ws.rs.HttpMethod resource or sub-resource method}.
 *
 * The injected {@code AsyncResponse} instance is bound to the processing
 * of the active request and can be used to resume the request processing when
 * a response is available.
 * <p>
 * By default there is {@link AsyncResponse#NO_TIMEOUT no suspend timeout set} and
 * the asynchronous response is suspended indefinitely. The suspend timeout as well
 * as a custom {@link TimeoutHandler timeout handler} can be specified programmatically
 * using the {@link AsyncResponse#setTimeout(long, TimeUnit)} and
 * {@link AsyncResponse#setTimeoutHandler(TimeoutHandler)} methods. For example:
 * <p/>
 * <pre>
 *  &#64;Stateless
 *  &#64;Path("/")
 *  public class MyEjbResource {
 *    &hellip;
 *    &#64;GET
 *    &#64;Asynchronous
 *    public void longRunningOperation(&#64;Suspended AsyncResponse ar) {
 *      ar.setTimeoutHandler(customHandler);
 *      ar.setTimeout(10, TimeUnit.SECONDS);
 *      final String result = executeLongRunningOperation();
 *      ar.resume(result);
 *    }
 *
 *    private String executeLongRunningOperation() { &hellip; }
 *  }
 * </pre>
 * <p>
 * A resource or sub-resource method that injects a suspended instance of an
 * {@code AsyncResponse} using the {@code @Suspended} annotation is expected
 * be declared to return {@code void} type. Methods that inject asynchronous
 * response instance using the {@code @Suspended} annotation and declare a
 * return type other than {@code void} MUST be detected by the the runtime and
 * a warning message MUST be logged. Any response value returned from such resource
 * or sub-resource method MUST be ignored by the framework:
 * </p>
 * <pre>
 * &#64;Path("/messages/next")
 * public class MessagingResource {
 *     &hellip;
 *     &#64;GET
 *     public String readMessage(&#64;Suspended AsyncResponse ar) {
 *         suspended.put(ar);
 *         return "This response will be ignored.";
 *     }
 *     &hellip;
 * }
 * </pre>
 *
 * @author Marek Potociar
 * @since 2.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Suspended {
}
