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

import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;

/**
 * A JAX-RS meta-provider for dynamic registration of <i>post-matching</i> providers
 * during a JAX-RS application setup at deployment time.
 *
 * Dynamic feature is used by JAX-RS runtime to register providers that shall be applied
 * to a particular resource class and method and overrides any annotation-based binding
 * definitions defined on any registered resource filter or interceptor instance.
 * <p>
 * Providers implementing this interface MAY be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} annotation in order to be
 * discovered by JAX-RS runtime when scanning for resources and providers.
 * This provider types is supported only as part of the Server API.
 * </p>
 *
 * @author Santiago Pericas-Geertsen
 * @author Bill Burke
 * @author Marek Potociar
 * @see javax.ws.rs.NameBinding
 * @since 2.0
 */
public interface DynamicFeature {

    /**
     * A callback method called by the JAX-RS runtime during the application
     * deployment to register provider instances or classes in a
     * {@link javax.ws.rs.core.Configuration runtime configuration} scope of a particular {@link javax.ws.rs.HttpMethod
     * resource or sub-resource method}; i.e. the providers that should be dynamically bound
     * to the method.
     * <p>
     * The registered provider instances or classes are expected to be implementing one
     * or more of the following interfaces:
     * </p>
     * <ul>
     * <li>{@link ContainerRequestFilter}</li>
     * <li>{@link ContainerResponseFilter}</li>
     * <li>{@link ReaderInterceptor}</li>
     * <li>{@link WriterInterceptor}</li>
     * <li>{@link javax.ws.rs.core.Feature}</li>
     * </ul>
     * <p>
     * A provider instance or class that does not implement any of the interfaces
     * above may be ignored by the JAX-RS implementation. In such case a
     * {@link java.util.logging.Level#WARNING warning} message must be logged.
     * JAX-RS implementations may support additional provider contracts that
     * can be registered using a dynamic feature concept.
     * </p>
     * <p>
     * Conceptually, this callback method is called during a {@link javax.ws.rs.HttpMethod
     * resource or sub-resource method} discovery phase (typically once per each discovered
     * resource or sub-resource method) to register provider instances or classes in a
     * {@code configuration} scope of each particular method identified by the supplied
     * {@link ResourceInfo resource information}.
     * The responsibility of the feature is to properly update the supplied {@code configuration}
     * context.
     * </p>
     *
     * @param resourceInfo resource class and method information.
     * @param context      configurable resource or sub-resource method-level runtime context
     *                     associated with the {@code resourceInfo} in which the feature
     */
    public void configure(ResourceInfo resourceInfo, FeatureContext context);
}
