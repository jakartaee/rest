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

package ee.jakarta.tck.ws.rs.spec.context.cdi.request;

import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;

/**
 * Registers a property for the {@link RequestResource#configured()} resource method only.
 */
@Provider
public class ConfigurationFeature implements DynamicFeature {

    public static final String PROPERTY = "ee.jakarta.tck.ws.rs.spec.context.cdi.request.property";

    @Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext context) {
        if (RequestResource.class.equals(resourceInfo.getResourceClass())
                && "configured".equals(resourceInfo.getResourceMethod().getName())) {
            context.property(PROPERTY, "configured");
        }
    }
}
