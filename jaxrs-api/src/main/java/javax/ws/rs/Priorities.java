/*
 * Copyright (c) 2013, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs;

/**
 * A collection of built-in priority constants for the components that are supposed to be
 * ordered based on their {@code javax.annotation.Priority} class-level annotation value when used
 * or applied by the runtime.
 * <p>
 * For example, filters and interceptors are grouped in chains for each of the message
 * processing extension points: Pre, PreMatch, Post as well as ReadFrom and WriteTo.
 * Each of these chains is sorted based on priorities which are represented as integer numbers.
 * All chains, except Post, are sorted in ascending order; the lower the number the higher the priority.
 * The Post filter chain is sorted in descending order to ensure that response filters are executed in
 * <em>reverse order</em>.
 * </p>
 * <p>
 * Components that belong to the same priority class (same integer value) are executed in an
 * implementation-defined manner. By default, when the {@code @Priority} annotation is absent on a component,
 * for which a priority should be applied, the {@link Priorities#USER} priority value is used.
 * </p>
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 * @since 2.0
 */
public final class Priorities {

    private Priorities() {
        // prevents construction
    }

    /**
     * Security authentication filter/interceptor priority.
     */
    public static final int AUTHENTICATION = 1000;
    /**
     * Security authorization filter/interceptor priority.
     */
    public static final int AUTHORIZATION = 2000;
    /**
     * Header decorator filter/interceptor priority.
     */
    public static final int HEADER_DECORATOR = 3000;
    /**
     * Message encoder or decoder filter/interceptor priority.
     */
    public static final int ENTITY_CODER = 4000;
    /**
     * User-level filter/interceptor priority.
     *
     * This value is also used as a default priority for application-supplied providers.
     */
    public static final int USER = 5000;
}
