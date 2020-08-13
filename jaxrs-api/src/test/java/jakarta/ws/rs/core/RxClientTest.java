/*
 * Copyright (c) 2013, 2019 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.core;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

import org.junit.Ignore;
import org.junit.Test;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.CompletionStageRxInvoker;
import jakarta.ws.rs.client.RxInvokerProvider;
import jakarta.ws.rs.client.SyncInvoker;

/**
 * Class RxClientTest.
 *
 * @author Santiago Pericas-Geertsen
 */
public class RxClientTest {

    private Client client = null; // does not run

    /**
     * Shows how to use the default reactive invoker by calling method {@link jakarta.ws.rs.client.Invocation.Builder#rx()}
     * without any arguments.
     */
    @Test
    @Ignore
    public void testRxClient() {
        CompletionStage<List<String>> cs = client.target("remote/forecast/{destination}")
                .resolveTemplate("destination", "mars")
                .request()
                .header("Rx-User", "Java8")
                .rx() // gets CompletionStageRxInvoker
                .get(new GenericType<List<String>>() {
                });

        // TODO: replace system out with in-memory stream and verify expected results
        cs.thenAccept(System.out::println);
    }

    /**
     * Shows how other reactive invokers could be plugged in using the class as an argument in
     * {@link jakarta.ws.rs.client.Invocation.Builder#rx(Class)}.
     */
    @Test
    @Ignore
    public void testRxClient2() {
        Client rxClient = client.register(CompletionStageRxInvokerProvider.class, RxInvokerProvider.class);

        CompletionStage<List<String>> cs = rxClient.target("remote/forecast/{destination}")
                .resolveTemplate("destination", "mars")
                .request()
                .header("Rx-User", "Java8")
                .rx(CompletionStageRxInvoker.class)
                .get(new GenericType<List<String>>() {
                });

        // TODO: replace system out with in-memory stream and verify expected results
        cs.thenAccept(System.out::println);
    }

    /**
     * Shows how other reactive invokers could be plugged in using the class instance as an argument in
     * {@link jakarta.ws.rs.client.Invocation.Builder#rx(Class)}.
     */
    @Test
    @Ignore
    public void testRxClient3() {
        Client rxClient = client.register(CompletionStageRxInvokerProvider.class, RxInvokerProvider.class);

        CompletionStage<String> cs = rxClient.target("remote/forecast/{destination}")
                .resolveTemplate("destination", "mars")
                .request()
                .header("Rx-User", "Java8")
                .rx(CompletionStageRxInvoker.class)
                .get(String.class);

        // TODO: replace system out with in-memory stream and verify expected results
        cs.thenAccept(System.out::println);
    }

    /**
     * RxInvokerProvider provided by the app/other framework.
     */
    public static class CompletionStageRxInvokerProvider implements RxInvokerProvider<CompletionStageRxInvoker> {

        @Override
        public boolean isProviderFor(final Class<?> clazz) {
            return CompletionStageRxInvoker.class.equals(clazz);
        }

        @Override
        public CompletionStageRxInvoker getRxInvoker(final SyncInvoker syncInvoker, final ExecutorService executorService) {
            return null;
        }
    }
}
