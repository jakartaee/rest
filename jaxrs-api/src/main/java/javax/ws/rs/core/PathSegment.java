/*
 * Copyright (c) 2010, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.core;

/**
 * Represents a URI path segment and any associated matrix parameters. When an
 * instance of this type is injected with {@link javax.ws.rs.PathParam}, the
 * value of the annotation identifies which path segment is selected and the
 * presence of an {@link javax.ws.rs.Encoded} annotation will result in an
 * instance that supplies the path and matrix parameter values in
 * URI encoded form.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see UriInfo#getPathSegments
 * @see javax.ws.rs.PathParam
 * @since 1.0
 */
public interface PathSegment {

    /**
     * Get the path segment.
     * <p>
     *
     * @return the path segment
     */
    String getPath();

    /**
     * Get a map of the matrix parameters associated with the path segment.
     * The map keys are the names of the matrix parameters with any
     * percent-escaped octets decoded.
     *
     * @return the map of matrix parameters
     * @see <a href="http://www.w3.org/DesignIssues/MatrixURIs.html">Matrix URIs</a>
     */
    MultivaluedMap<String, String> getMatrixParameters();
}
