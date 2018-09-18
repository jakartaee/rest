/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

module java.ws.rs {

    requires transitive java.xml.bind;

    requires java.logging;

    exports javax.ws.rs;
    exports javax.ws.rs.client;
    exports javax.ws.rs.container;
    exports javax.ws.rs.core;
    exports javax.ws.rs.ext;
    exports javax.ws.rs.sse;

    uses javax.ws.rs.client.ClientBuilder;
    uses javax.ws.rs.ext.RuntimeDelegate;
    uses javax.ws.rs.sse.SseEventSource.Builder;
}
