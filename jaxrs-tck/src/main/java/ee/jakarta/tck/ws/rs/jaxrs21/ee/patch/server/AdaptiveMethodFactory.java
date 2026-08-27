/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */

package ee.jakarta.tck.ws.rs.jaxrs21.ee.patch.server;

import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;

import ee.jakarta.tck.ws.rs.common.webclient.http.MethodFactory;

public class AdaptiveMethodFactory {

  private AdaptiveMethodFactory() {
  }

  public static Map<String, Class<? extends HttpUriRequestBase>> getMethodMap() {
    return MethodFactory.getMethodMap();
  }

  public static HttpUriRequestBase getInstance(String request) {
    return MethodFactory.getInstance(request);
  }
}
