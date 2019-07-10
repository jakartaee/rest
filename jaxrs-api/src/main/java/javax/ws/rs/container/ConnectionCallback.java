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
 * Asynchronous request processing lifecycle callback that receives connection
 * related {@link AsyncResponse asynchronous response} lifecycle events.
 * <p>
 * Support for this type of callback by the runtime is OPTIONAL.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface ConnectionCallback {
    /**
     * This callback notification method is invoked in case the container detects
     * that the remote client connection associated with the asynchronous response
     * has been disconnected.
     *
     * @param disconnected asynchronous response for which the remote client connection
     *                     has been lost.
     */
    public void onDisconnect(AsyncResponse disconnected);
}
