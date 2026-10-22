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

package ee.jakarta.tck.ws.rs.spec.context.cdi.client;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.client.ClientBuilder;

/**
 * Defines the {@code @Default} {@code ClientBuilder} bean the implementation builds the {@code @Default}
 * {@code Client} from.
 */
@ApplicationScoped
public class ClientBuilderProducer {

    private final AtomicInteger count = new AtomicInteger();

    @Produces
    @Dependent
    public ClientBuilder clientBuilder(final ConfigurationFilter configurationFilter) {
        count.incrementAndGet();
        return ClientBuilder.newBuilder()
                .property(ConfigurationFilter.CLIENT_NAME, "produced")
                .register(configurationFilter)
                .register(new NameResolver("produced"));
    }

    /**
     * Returns the number of times the {@code ClientBuilder} bean was obtained. The count must only be accessed
     * through methods, as the client proxy has its own state.
     */
    public int count() {
        return count.get();
    }
}
