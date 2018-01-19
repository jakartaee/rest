/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.container;

import java.lang.reflect.Method;

/**
 * An injectable class to access the resource class and resource method
 * matched by the current request. Methods in this class MAY return
 * <code>null</code> if a resource class and method have not been matched,
 * e.g. in a standalone, pre-matching {@link ContainerRequestFilter} that was
 * not provided by a post-matching {@link PreMatching}.
 *
 * @author Santiago Pericas-Geertsen
 * @since 2.0
 */
public interface ResourceInfo {

    /**
     * Get the resource method that is the target of a request,
     * or <code>null</code> if this information is not available.
     *
     * @return resource method instance or null
     * @see #getResourceClass()
     */
    Method getResourceMethod();

    /**
     * Get the resource class that is the target of a request,
     * or <code>null</code> if this information is not available.
     *
     * @return resource class instance or null
     * @see #getResourceMethod()
     */
    Class<?> getResourceClass();
}
