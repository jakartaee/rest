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

package jakarta.ws.rs.client;

/**
 * A listener interface for receiving client lifecycle events.
 * <p>
 * Implementations of this interface can be registered with a {@link ClientBuilder}
 * to receive notifications about client lifecycle events such as connection,
 * disconnection, and failures.
 * </p>
 * <p>
 * All methods in this interface have default implementations that do nothing,
 * allowing implementations to override only the events they are interested in.
 * </p>
 */
public interface ClientListener {

    /**
     * Called when the client successfully establishes a connection.
     * <p>
     * This method is invoked after the client has been created and is ready
     * to process requests.
     * </p>
     *
     * @param client the client that connected
     */
    default void connected(Client client) {
    }

    /**
     * Called when the client is closed.
     * <p>
     * This method is invoked when {@link Client#close()} is called or when
     * the client is closed by the container (e.g., during CDI shutdown).
     * Implementations can use this to perform cleanup operations.
     * </p>
     *
     * @param client the client that was closed
     */
    default void closed(Client client) {
    }

    /**
     * Called when a connection failure occurs.
     * <p>
     * This method is invoked when the client encounters a connection error
     * that prevents it from communicating with the server. This could be
     * due to network issues, server unavailability, or other connection
     * problems.
     * </p>
     *
     * @param client the client that experienced the failure
     * @param cause  the exception that caused the failure
     */
    default void connectionFailed(Client client, Throwable cause) {
    }

    /**
     * Called when the client attempts to reconnect after a connection failure.
     * <p>
     * This method is invoked when the client detects a connection failure
     * and is attempting to re-establish the connection. This can be used
     * to notify users or log reconnection attempts.
     * </p>
     *
     * @param client the client that is reconnecting
     */
    default void reconnecting(Client client) {
    }
}
