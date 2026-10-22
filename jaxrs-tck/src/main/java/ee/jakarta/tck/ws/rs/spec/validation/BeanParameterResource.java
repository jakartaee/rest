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

package ee.jakarta.tck.ws.rs.spec.validation;

import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * A resource whose resource method has a {@code @BeanParam} parameter. It is in its own class, as the
 * {@link SearchParams} field of {@link ValidationResource} would reject the same value before the parameter is
 * validated.
 */
@Path("/bean-parameter")
public class BeanParameterResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@QueryParam("id") final String id, @BeanParam @Valid final SearchParams searchParams) {
        Invocations.record(id);
        return "invoked";
    }
}
