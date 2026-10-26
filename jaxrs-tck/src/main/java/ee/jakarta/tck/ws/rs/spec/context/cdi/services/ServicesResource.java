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

package ee.jakarta.tck.ws.rs.spec.context.cdi.services;

import java.util.Map;
import java.util.Objects;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.MediaType;

@Path("/services")
public class ServicesResource {

    @Inject
    Configuration configuration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> get() {
        return Map.of("loadedBy", String.valueOf(Objects.requireNonNullElse(
                        configuration.getProperty(ServiceLoadedFeature.LOADED_BY), "none")),
                "configured", String.valueOf(ServiceLoadedFeature.configured()));
    }
}
