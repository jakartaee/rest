/*
 * Copyright (c) 2017, 2019 Oracle and/or its affiliates. All rights reserved.
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

module jakarta.ws.rs {

    requires transitive jakarta.xml.bind;

    requires java.logging;

    exports jakarta.ws.rs;
    exports jakarta.ws.rs.client;
    exports jakarta.ws.rs.container;
    exports jakarta.ws.rs.core;
    exports jakarta.ws.rs.ext;
    exports jakarta.ws.rs.sse;

    uses jakarta.ws.rs.client.ClientBuilder;
    uses jakarta.ws.rs.ext.RuntimeDelegate;
    uses jakarta.ws.rs.sse.SseEventSource.Builder;

    opens jakarta.ws.rs.core to jakarta.xml.bind;
}
