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
import jakarta.ws.rs.SeBootstrap.Configuration.SSLClientAuthentication;
import jakarta.ws.rs.core.Application;

/**
 * Java SE bootstrap example with explicit configuration.
 * <p>
 * This example demonstrates bootstrapping on Java SE platforms using hardly no defaults at all (besides JSSE defaults).
 * It applies parameters passed at the command line.
 * </p>
 * <p>
 * Depending of the actual parameters passed at the command line (i. e. with protocol set up {@code HTTPS}), this
 * example might need to have additional <em>external</em> JSSE configuration:
 * </p>
 * <ul>
 * <li>{@code javax.net.ssl.keyStore=~/.keystore} - HTTPS: Path to a keystore holding an X.509 certificate for
 * {@code CN=localhost}</li>
 * <li>{@code javax.net.ssl.keyStorePassword=...} - HTTPS: Password of that keystore</li>
 * </ul>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
public final class ExplicitJavaSeBootstrapExample {

    private ExplicitJavaSeBootstrapExample() {
    }

    /**
     * Runs this example.
     *
     * @param args configuration to be used in exact this order: {@code PROTOCOL HOST PORT ROOT_PATH CLIENT_AUTH} where the
     * protocol can be either {@code HTTP} or {@code HTTPS} and the client authentication is one of
     * {@code NONE, OPTIONAL, MANDATORY}.
     * @throws InterruptedException when process is killed
     */
    public static void main(final String[] args) throws InterruptedException {
        final Application application = new HelloWorld();

        final String protocol = args[0];
        final String host = args[1];
        final int port = Integer.parseInt(args[2]);
        final String rootPath = args[3];
        final SSLClientAuthentication clientAuth = SSLClientAuthentication.valueOf(args[4]);

        final SeBootstrap.Configuration requestedConfiguration = SeBootstrap.Configuration.builder().protocol(protocol).host(host)
                .port(port).rootPath(rootPath).sslClientAuthentication(clientAuth).build();

        SeBootstrap.start(application, requestedConfiguration).thenAccept(instance -> {
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
