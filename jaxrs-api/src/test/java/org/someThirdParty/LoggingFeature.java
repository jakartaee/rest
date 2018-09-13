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
package org.someThirdParty;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * Used for {@link javax.ws.rs.client.ClientBuilderListener} unit tests.
 *
 * @author Andy McCright
 * @since 2.2
 */
public class LoggingFeature implements Feature {

    public final static LoggingFeature INSTANCE = new LoggingFeature();

    @Override
    public boolean configure(FeatureContext context) {
        // no-op
        return true;
    }
}
