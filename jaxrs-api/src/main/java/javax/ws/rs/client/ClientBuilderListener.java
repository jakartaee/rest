/*
 * Copyright (c) 2018 IBM and Contributors to the Eclipse Foundation
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
 * Implementations of this interface will be notified when new
 * <code>ClientBuilder</code> instances are being constructed. This will allow
 * implementations to register providers on the <code>ClientBuilder</code>, and
 * is intended for global providers.
 * <p>
 * For example, an audit logging implementation might want to register a
 * <code>ClientRequestFilter</code> or <code>ClientResponseFilter</code> to
 * record an outbound request or response.
 * <p>
 * In order for the <code>ClientBuilder</code> to call implementations of this
 * interface, the implementation must be specified such that a ServiceLoader can
 * find it - i.e. it must be specified in the
 * <code>META-INF/services/javax.ws.rs.client.ClientBuilderListener</code> file
 * in an archive on the current thread's context classloader's class path.
 * <p>
 * Note that the <code>onNewBuilder</code> method will be invoked after the
 * <code>ClientBuilder</code> instance has been constructed, but before it is
 * returned from the <code>ClientBuilder.newBuilder()</code> method.
 * This allows the caller to override global providers if they desire.
 *
 * @since 2.2
 * @author Andy McCright
 */
public interface ClientBuilderListener {

    void onNewBuilder(ClientBuilder builder);
}
