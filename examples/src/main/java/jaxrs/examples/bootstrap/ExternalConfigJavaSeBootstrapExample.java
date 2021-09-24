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
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * Java SE bootstrap example utilizing an external configuration system.
 * <p>
 * This example demonstrates bootstrapping on Java SE platforms using values retrieved from an external configuration
 * system plus statically overriden properties. In particular, this demo first uses Eclipse Microprofile Config to let
 * the actual implementation retrieve all properties from an external source of configuration, but then explicitly
 * enforces HTTPS within the bootstrap code.
 * </p>
 * <p>
 * To actually run this example, an implementation of Eclipse Microprofile Config has to be added to the classpath, and
 * the configuration options to be customized have to be provided, e. g. as environment variables:
 * </p>
 *
 * <pre>
 * export jakarta.ws.rs.SeBootstrap.Port=8888;
 * java -Djakarta.ws.rs.SeBootstrap.Host=127.0.0.1 ExternalConfigJavaSeBootstrapExample;
 * </pre>
 * <p>
 * This example uses some basic <em>external</em> JSSE configuration:
 * </p>
 * <ul>
 * <li>{@code javax.net.ssl.keyStore=~/.keystore} - HTTPS: Path to a keystore holding an X.509 certificate for
 * {@code CN=localhost}</li>
 * <li>{@code javax.net.ssl.keyStorePassword=...} - HTTPS: Password of that keystore</li>
 * </ul>
 * <p>
 * Due to the <em>implicit</em> use of Microprofile Config in the implementation, this example is <em>not
 * necessarily</em> portable: Support for external configuration is <em>not mandatory</em>. Implementations could choose
 * to not implement it, or to support other external configuration mechanics not mentioned here.
 * </p>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
public final class ExternalConfigJavaSeBootstrapExample {

    private ExternalConfigJavaSeBootstrapExample() {
    }

    /**
     * Runs this example.
     *
     * @param args unused command line arguments
     * @throws InterruptedException when process is killed
     */
    public static void main(final String[] args) throws InterruptedException {
        final Application application = new HelloWorld();

        final Config config = ConfigProvider.getConfig();

        final SeBootstrap.Configuration requestedConfiguration = SeBootstrap.Configuration.builder().from(config).protocol("HTTPS")
                .build();

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
