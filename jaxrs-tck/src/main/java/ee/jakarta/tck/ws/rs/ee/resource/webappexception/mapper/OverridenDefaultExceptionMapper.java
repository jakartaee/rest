/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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
package ee.jakarta.tck.ws.rs.ee.resource.webappexception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class OverridenDefaultExceptionMapper implements ExceptionMapper<Throwable> {

    /*
     * 4.4. Exception Mapping Providers states:
     * "A JAX-RS implementation MUST include a default exception mapping provider
     * that implements ExceptionMapper<Throwable> and which SHOULD set the
     * response status to 500."
     * 
     * This class should override the default ExceptionMapper and set the
     * response status to 512 to verify that the default ExceptionMapper
     * was overriden.
     */
  @Override
  public Response toResponse(Throwable throwable) {
    return Response.status(512).build();
  }
    
}
