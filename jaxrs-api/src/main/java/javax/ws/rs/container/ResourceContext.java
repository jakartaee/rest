/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
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

import javax.ws.rs.core.Context;

/**
 * The resource context provides access to instances of resource classes.
 * <p>
 * This interface can be injected using the {@link Context} annotation.
 * </p>
 * <p>
 * The resource context can be utilized when instances of managed resource
 * classes are to be returned by sub-resource locator methods. Such instances
 * will be injected and managed within the declared scope just like instances
 * of root resource classes.
 * </p>
 *
 * @author Marek Potociar
 */
public interface ResourceContext {

    /**
     * Get a resolved instance of a resource or sub-resource class.
     * <p>
     * The resolved resource instance is properly initialized in the context of the
     * current request processing scope. The scope of the resolved resource instance
     * depends on the managing container. For resources managed by the runtime container
     * the default scope is per-request.
     * </p>
     *
     * @param <T>           the type of the resource class.
     * @param resourceClass the resource class.
     * @return an instance if it could be resolved, otherwise {@code null}.
     */
    public <T> T getResource(Class<T> resourceClass);

    /**
     * Initialize the resource or sub-resource instance.
     *
     * All injectable fields in the resource instance will be properly initialized in
     * the context of the current request processing scope.
     *
     * @param <T>      resource instance type.
     * @param resource resource instance.
     * @return initialized (same) resource instance.
     */
    public <T> T initResource(T resource);
}
