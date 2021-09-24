/*
 * Copyright (c) 2018 Markus KARG. All rights reserved.
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

package jaxrs.examples.bootstrap;

import java.net.URI;

import jakarta.ws.rs.SeBootstrap;

/**
 * Basic Java SE bootstrap example.
 * <p>
 * This example demonstrates bootstrapping on Java SE platforms solely using defaults. It will effectively startup the
 * {@link HelloWorld} application at the URL {@code http://localhost/} on an implementation-specific default IP port (e.
 * g. 80, 8080, or something completely different). The actual configuration needs to be queried after bootstrapping,
 * otherwise callers would be unaware of the actual chosen port.
 * </p>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
public final class BasicJavaSeBootstrapExample {

    private BasicJavaSeBootstrapExample() {
    }

    /**
     * Runs this example.
     *
     * @param args unused command line arguments
     * @throws InterruptedException when process is killed
     */
    public static void main(final String[] args) throws InterruptedException {
        SeBootstrap.start(HelloWorld.class).thenAccept(instance -> {
            instance.stopOnShutdown(stopResult ->
                    System.out.printf("Stop result: %s [Native stop result: %s].%n", stopResult,
                            stopResult.unwrap(Object.class)));
            final URI uri = instance.configuration().baseUri();
            System.out.printf("Instance %s running at %s [Native handle: %s].%n", instance, uri,
                    instance.unwrap(Object.class));
            System.out.println("Send SIGKILL to shutdown.");
        });

        Thread.currentThread().join();
    }

}
