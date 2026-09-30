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

package ee.jakarta.tck.ws.rs.spec.context.cdi.param;

public class EncodedDescriptor {
    private String queryValue;
    private String pathValue;
    private String decodedQuery;

    public static EncodedDescriptor of() {
        return new EncodedDescriptor();
    }

    public String getQueryValue() {
        return queryValue;
    }

    public EncodedDescriptor setQueryValue(final String queryValue) {
        this.queryValue = queryValue;
        return this;
    }

    public String getPathValue() {
        return pathValue;
    }

    public EncodedDescriptor setPathValue(final String pathValue) {
        this.pathValue = pathValue;
        return this;
    }

    public String getDecodedQuery() {
        return decodedQuery;
    }

    public EncodedDescriptor setDecodedQuery(final String decodedQuery) {
        this.decodedQuery = decodedQuery;
        return this;
    }

    @Override
    public String toString() {
        return "EncodedDescriptor{" + "queryValue='" + queryValue + '\'' +
                ", pathValue='" + pathValue + '\'' +
                ", decodedQuery='" + decodedQuery + '\'' +
                '}';
    }
}
