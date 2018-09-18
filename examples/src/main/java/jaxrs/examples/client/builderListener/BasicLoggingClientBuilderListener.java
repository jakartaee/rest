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

package jaxrs.examples.client.builderListener;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientBuilderListener;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

/**
 * This class is an example of how to intercept calls to <code>ClientBuilder.newBuilder()</code>.
 * In order to be discovered, the class needs to be registered to be found by the
 * <code>ServiceLoader</code>.  In unnamed modules or JVMs not using JPMS, this is accomplished
 * by creating a file named <code>META-INF/services/javax.ws.rs.client.ClientBuilderListener</code>
 * in which the contents is the fully qualified class name of the listener implementation class - in
 * this case, it would be "jaxrs.examples.client.builderListener.BasicLoggingClientBuilderListener".
 * <p>
 * If using JPMS, then your module-info.java file would need to contain a clause like this:
 * <code>
 * provides javax.ws.rs.client.ClientBuilderListener
 *     with jaxrs.examples.client.builderListener.BasicLoggingClientBuilderListener;
 * </code>
 * </p>
 * <p>
 * When some code in the JVM attempts to create a new JAX-RS ClientBuilder, this code would be
 * executed first, allowing it to register a <code>ClientRequestFilter</code> and
 * <code>ClientResponseFilter</code> that will log requests and responses, respectively.
 * </p>
 *
 * @author andymc
 * @since 2.2
 *
 */
public class BasicLoggingClientBuilderListener implements ClientBuilderListener {
    private static Logger LOG = Logger.getLogger(BasicLoggingClientBuilderListener.class.getName());

    @Override
    public void onNewBuilder(ClientBuilder builder) {
        builder.register(new ClientRequestFilter() {

            @Override
            public void filter(ClientRequestContext reqCtx) throws IOException {
                LOG.info("Outbound request: " + reqCtx.getUri());
            }});

        builder.register(new ClientResponseFilter() {

            @Override
            public void filter(ClientRequestContext reqCtx, ClientResponseContext resCtx) throws IOException {
                LOG.info("Inbound response: " + resCtx.getStatus());
            }});
    }

}
