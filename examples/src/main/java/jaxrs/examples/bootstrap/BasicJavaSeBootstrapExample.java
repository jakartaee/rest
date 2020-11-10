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
import jakarta.ws.rs.SeBootstrap.Configuration;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.UriBuilder;

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
 * @since 2.2
 */
public final class BasicJavaSeBootstrapExample {

    /**
     * Runs this example.
     *
     * @param args unused command line arguments
     * @throws InterruptedException when process is killed
     */
    public static final void main(final String[] args) throws InterruptedException {
        final Application application = new HelloWorld();

        final SeBootstrap.Configuration requestedConfiguration = SeBootstrap.Configuration.builder().build();

        SeBootstrap.start(application, requestedConfiguration).thenAccept(instance -> {
            Runtime.getRuntime()
                    .addShutdownHook(new Thread(() -> instance.stop()
                            .thenAccept(stopResult -> System.out.printf("Stop result: %s [Native stop result: %s].%n",
                                    stopResult, stopResult.unwrap(Object.class)))));

            final Configuration actualConfigurarion = instance.configuration();
            final URI uri = UriBuilder.newInstance().scheme(actualConfigurarion.protocol().toLowerCase())
                    .host(actualConfigurarion.host()).port(actualConfigurarion.port())
                    .path(actualConfigurarion.rootPath()).build();
            System.out.printf("Instance %s running at %s [Native handle: %s].%n", instance, uri,
                    instance.unwrap(Object.class));
            System.out.println("Send SIGKILL to shutdown.");
        });

        Thread.currentThread().join();
    }

}
