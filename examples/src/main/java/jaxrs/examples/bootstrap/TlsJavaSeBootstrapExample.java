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

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.ws.rs.JAXRS;
import javax.ws.rs.JAXRS.Configuration;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;

/**
 * Java SE bootstrap example using TLS customization.
 * <p>
 * This example demonstrates bootstrapping on Java SE platforms using HTTPS with TLS 1.2 and a <em>particular</em>
 * keystore. It explicitly sets the protocol to {@code HTTPS} using port 443 and provides a {@link SSLContext}
 * customized to TLS v1.2 using a provided keystore file. The example will effectively startup the {@link HelloWorld}
 * application at the URL {@code https://localhost:443/}.
 * </p>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 2.2
 */
public final class TlsJavaSeBootstrapExample {

    /**
     * Runs this example.
     *
     * @param args KEYSTORE_PATH KEYSTORE_PASSWORD
     * @throws GeneralSecurityException in case JSSE fails
     * @throws IOException in case file access fails
     * @throws InterruptedException when process is killed
     */
    public static final void main(final String[] args)
            throws GeneralSecurityException, IOException, InterruptedException {
        final Application application = new HelloWorld();

        final Path keyStorePath = Paths.get(args[0]);
        final char[] passphrase = args[1].toCharArray();

        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(Files.newInputStream(keyStorePath), passphrase);
        final KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, passphrase);
        final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        final JAXRS.Configuration requestedConfiguration = JAXRS.Configuration.builder().protocol("HTTPS")
                .sslContext(sslContext).build();

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
