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
 * Java SE bootstrap example demonstrating the use of native properties.
 * <p>
 * This example demonstrates bootstrapping on Java SE platforms with native properties, in particular by explicitly
 * selecting the Grizzly2 backend in Jersey. It will effectively startup the {@link HelloWorld} application at the URL
 * {@code http://localhost:80/}, i. e. the Grizzly2 backend selects exactly port 80 as this is its default IP port. The
 * actual configuration needs to be queried after bootstrapping, otherwise callers would be unaware of the actual chosen
 * port, as Grizzly2's default behavior is not publicly documented.
 * </p>
 * <p>
 * This is a native example, hence it only works with Jersey, and in particular only with its Grizzly2 backend. To
 * actually run this demo, the following libraries have to be found on the classpath:
 * </p>
 * <ul>
 * <li>jersey-server</li>
 * <li>jersey-container-grizzly2-http</li>
 * </ul>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 2.2
 */
public final class NativeJavaSeBootstrapExample {

    /**
     * Runs this example.
     *
     * @param args unused command line arguments
     * @throws InterruptedException when process is killed
     * @throws ClassNotFoundException when Jersey's Grizzly backend is not on the classpath
     */
    public static final void main(final String[] args) throws InterruptedException, ClassNotFoundException {
        final Application application = new HelloWorld();

        final SeBootstrap.Configuration requestedConfiguration = SeBootstrap.Configuration.builder()
                .property("jersey.config.server.httpServerClass",
                        Class.forName("org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServer"))
                .build();

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
