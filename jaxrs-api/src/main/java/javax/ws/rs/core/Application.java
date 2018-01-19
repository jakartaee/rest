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

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Defines the components of a JAX-RS application and supplies additional
 * meta-data. A JAX-RS application or implementation supplies a concrete
 * subclass of this abstract class.
 * <p>
 * The implementation-created instance of an Application subclass may be
 * injected into resource classes and providers using
 * {@link javax.ws.rs.core.Context}.
 * </p>
 * <p>
 * In case any of the {@code Application} subclass methods or it's constructor
 * throws a {@link RuntimeException}, the deployment of the application SHOULD
 * be aborted with a failure.
 * </p>
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @author Marek Potociar
 * @since 1.0
 */
public class Application {

    /**
     * Get a set of root resource, provider and {@link Feature feature} classes.
     *
     * The default life-cycle for resource class instances is per-request. The default
     * life-cycle for providers (registered directly or via a feature) is singleton.
     * <p>
     * Implementations should warn about and ignore classes that do not
     * conform to the requirements of root resource or provider/feature classes.
     * Implementations should warn about and ignore classes for which
     * {@link #getSingletons()} returns an instance. Implementations MUST
     * NOT modify the returned set.
     * </p>
     * <p>
     * The default implementation returns an empty set.
     * </p>
     *
     * @return a set of root resource and provider classes. Returning {@code null}
     *         is equivalent to returning an empty set.
     */
    public Set<Class<?>> getClasses() {
        return Collections.emptySet();
    }

    /**
     * Get a set of root resource, provider and {@link Feature feature} instances.
     *
     * Fields and properties of returned instances are injected with their declared
     * dependencies (see {@link Context}) by the runtime prior to use.
     * <p>
     * Implementations should warn about and ignore classes that do not
     * conform to the requirements of root resource or provider classes.
     * Implementations should flag an error if the returned set includes
     * more than one instance of the same class. Implementations MUST
     * NOT modify the returned set.
     * </p>
     * <p>
     * The default implementation returns an empty set.
     * </p>
     *
     * @return a set of root resource and provider instances. Returning {@code null}
     *         is equivalent to returning an empty set.
     */
    public Set<Object> getSingletons() {
        return Collections.emptySet();
    }

    /**
     * Get a map of custom application-wide properties.
     * <p>
     * The returned properties are reflected in the application {@link Configuration configuration}
     * passed to the server-side features or injected into server-side JAX-RS components.
     * </p>
     * <p>
     * The set of returned properties may be further extended or customized at deployment time
     * using container-specific features and deployment descriptors. For example, in a Servlet-based
     * deployment scenario, web application's {@code <context-param>} and Servlet {@code <init-param>}
     * values may be used to extend or override values of the properties programmatically returned
     * by this method.
     * </p>
     * <p>
     * The default implementation returns an empty set.
     * </p>
     *
     * @return a map of custom application-wide properties. Returning {@code null}
     *         is equivalent to returning an empty set.
     * @since 2.0
     */
    public Map<String, Object> getProperties() {
        return Collections.emptyMap();
    }
}
