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

import jakarta.ws.rs.JAXRS;
import jakarta.ws.rs.JAXRS.Configuration;
import jakarta.ws.rs.JAXRS.Configuration.SSLClientAuthentication;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.UriBuilder;

/**
 * Java SE Bootstrap Example using TLS Client Authentication.
 * <p>
 * This example demonstrates bootstrapping on Java SE platforms using HTTPS with <em>bidirectional</em> TLS
 * authentication (i. e. only <em>trustworthy</em> clients may connect). It relies mostly on defaults but explicitly
 * sets the protocol to HTTPS and mandates TLS client certificate validation. The example will effectively startup the
 * {@link HelloWorld} application at the URL {@code https://localhost/} on an implementation-specific default IP port
 * (e. g. 443, 8443, or someting completely different). The actual configuration needs to be queried after
 * bootstrapping, otherwise callers would be unaware of the actual chosen port. If the client's certificate is invalid
 * or cannot be validated, the server will reject the connection.
 * </p>
 * <p>
 * This example uses some basic <em>external</em> JSSE configuration:
 * </p>
 * <ul>
 * <li>{@code javax.net.ssl.keyStore=~/.keystore} - HTTPS: Path to a keystore holding an X.509 certificate for
 * {@code CN=localhost}</li>
 * <li>{@code javax.net.ssl.keyStorePassword=...} - HTTPS: Password of that keystore</li>
 * <li>Client Authentication: The default truststore ({@code $JAVA_HOME/lib/security/cacerts}) must hold the root
 * certificate of the CA and all intermediate certificates used for signing the client's certificate.</li>
 * </ul>
 * </p>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 2.2
 */
public final class ClientAuthenticationJavaSeBootstrapExample {

    /**
     * Runs this example.
     *
     * @param args unused command line arguments
     * @throws InterruptedException when process is killed
     */
    public static final void main(final String[] args) throws InterruptedException {
        final Application application = new HelloWorld();

        final JAXRS.Configuration requestedConfiguration = JAXRS.Configuration.builder().protocol("HTTPS")
                .sslClientAuthentication(SSLClientAuthentication.MANDATORY).build();

        JAXRS.start(application, requestedConfiguration).thenAccept(instance -> {
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
