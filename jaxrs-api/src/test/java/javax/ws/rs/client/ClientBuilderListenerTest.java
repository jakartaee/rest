/*
 * Copyright (c) 2018 IBM and Contributors to the Eclipse Foundation
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

package javax.ws.rs.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * {@link javax.ws.rs.client.ClientBuilderListener} unit tests.
 *
 * @author Andy McCright
 * @since 2.2
 */
public class ClientBuilderListenerTest {

    @Test
    public void testClientBuilderListenerPropertiesAreRegistered() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        assertEquals("true", builder.getConfiguration()
                                    .getProperty("org.someThirdParty.isRegistered"));
    }

    @Test
    public void testClientBuilderListenerProvidersAreRegistered() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        assertTrue(builder.getConfiguration()
                          .isRegistered(org.someThirdParty.LoggingClientRequestFilter.class));
    }

    @Test
    public void testClientBuilderListenerFeaturesAreRegistered() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        assertTrue(builder.getConfiguration()
                          .isEnabled(org.someThirdParty.LoggingFeature.INSTANCE));
    }
}
