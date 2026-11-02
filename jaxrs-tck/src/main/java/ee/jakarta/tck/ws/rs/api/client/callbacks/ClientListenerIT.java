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

package ee.jakarta.tck.ws.rs.api.client.callbacks;

import java.util.concurrent.atomic.AtomicBoolean;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.ClientListener;

import ee.jakarta.tck.ws.rs.lib.util.TestUtil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class ClientListenerIT {

    @BeforeEach
    void logStartTest(TestInfo testInfo) {
        TestUtil.logMsg("STARTING TEST : " + testInfo.getDisplayName());
    }

    @AfterEach
    void logFinishTest(TestInfo testInfo) {
        TestUtil.logMsg("FINISHED TEST : " + testInfo.getDisplayName());
    }

    /**
    * Verify registering a {@link ClientListener} with
    * {@link ClientBuilder#listener(ClientListener)} returns the same builder instance.
    */
    @Test
    public void listenerReturnsBuilderTest() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        ClientListener listener = new ClientListener() {
        };

        ClientBuilder returnedBuilder = builder.listener(listener);

        Assertions.assertSame(builder, returnedBuilder,
                "listener did not return the updated client builder instance");
    }

    /**
    * Verify a {@link ClientListener} registered with
    * {@link ClientBuilder#listener(ClientListener)} is notified via
    * {@link ClientListener#closed(Client)} when the built {@link Client} is closed.
    */
    @Test
    public void closedCallbackInvokedOnClientCloseTest() {
        AtomicBoolean closedCalled = new AtomicBoolean(false);
        Client[] closedClient = new Client[1];
        ClientListener listener = new ClientListener() {
            @Override
            public void closed(Client client) {
                closedCalled.set(true);
                closedClient[0] = client;
            }
        };

        Client client = ClientBuilder.newBuilder().listener(listener).build();
        Assertions.assertNotNull(client, "could not create Client instance");

        client.close();

        Assertions.assertTrue(closedCalled.get(), "ClientListener.closed was not called");
        Assertions.assertSame(client, closedClient[0],
                "ClientListener.closed did not receive the closed client instance");
    }
}
