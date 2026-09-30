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

package ee.jakarta.tck.ws.rs.spec.client.inject;

import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@code @Inject} works for {@link jakarta.ws.rs.core.Configuration} and
 * {@link jakarta.ws.rs.ext.Providers} in client-side providers. The Jakarta REST runtime is responsible for
 * resolving these injection points regardless of whether a CDI container is present.
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
@Tag("client")
public class ClientInjectionIT {

    @Test
    void clientFilterInjection() {
        try (Client client = ClientBuilder.newBuilder().register(ClientInjectFilter.class).build()) {
            // We abort the client response so any URI should be acceptable.
            final ClientInjectionDescriptor descriptor = client.target("http://localhost:8080")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(ClientInjectionDescriptor.class);
            Assertions.assertAll(
                    () -> Assertions.assertNotNull(descriptor.configuration(),
                            "Configuration not injected into client filter"),
                    () -> Assertions.assertEquals(RuntimeType.CLIENT.name(), descriptor.configuration(),
                            "Configuration runtime type should be CLIENT"),
                    () -> Assertions.assertNotNull(descriptor.providers(),
                            "Providers not injected into client filter")
            );
        }
    }
}
