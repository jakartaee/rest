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

/**
 *
 * @author <a href="mailto:jperkins@ibm.com">James R. Perkins</a>
 */
public class ParamDescriptor {
    private String cookieParam;
    private String formParam;
    private String headerParam;
    private long matrixParam;
    private String pathParam;
    private int queryParam;

    public static ParamDescriptor of() {
        return new ParamDescriptor();
    }

    public String getCookieParam() {
        return cookieParam;
    }

    public ParamDescriptor setCookieParam(final String cookieParam) {
        this.cookieParam = cookieParam;
        return this;
    }

    public String getFormParam() {
        return formParam;
    }

    public ParamDescriptor setFormParam(final String formParam) {
        this.formParam = formParam;
        return this;
    }

    public String getHeaderParam() {
        return headerParam;
    }

    public ParamDescriptor setHeaderParam(final String headerParam) {
        this.headerParam = headerParam;
        return this;
    }

    public long getMatrixParam() {
        return matrixParam;
    }

    public ParamDescriptor setMatrixParam(final long matrixParam) {
        this.matrixParam = matrixParam;
        return this;
    }

    public String getPathParam() {
        return pathParam;
    }

    public ParamDescriptor setPathParam(final String pathParam) {
        this.pathParam = pathParam;
        return this;
    }

    public int getQueryParam() {
        return queryParam;
    }

    public ParamDescriptor setQueryParam(final int queryParam) {
        this.queryParam = queryParam;
        return this;
    }

    @Override
    public String toString() {
        return "ParamDescriptor{" + "cookieParam='" + cookieParam + '\'' +
                ", formParam='" + formParam + '\'' +
                ", headerParam='" + headerParam + '\'' +
                ", matrixParam=" + matrixParam +
                ", pathParam='" + pathParam + '\'' +
                ", queryParam=" + queryParam +
                '}';
    }
}
