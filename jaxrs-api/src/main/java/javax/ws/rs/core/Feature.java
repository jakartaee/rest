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

package javax.ws.rs.core;

/**
 * A feature extension contract.
 *
 * Typically encapsulates a concept or facility that involves configuration of multiple providers
 * (e.g. filters or interceptors) and/or properties.
 * <p>
 * A {@code Feature} is a special type of configuration meta-provider. Once a feature is registered,
 * its {@link #configure(FeatureContext)} method is invoked during runtime configuration and bootstrapping
 * phase allowing the feature to further configure the runtime context in which it has been registered.
 * From within the invoked {@code configure(...)} method a feature may provide additional runtime configuration
 * for the facility or conceptual domain it represents, such as registering additional contract providers,
 * including nested features and/or specifying domain-specific properties.
 * </p>
 * <p>
 * Features implementing this interface MAY be annotated with the {@link javax.ws.rs.ext.Provider &#64;Provider}
 * annotation in order to be discovered by the runtime when scanning for resources and providers.
 * Please note that this will only work for server side features. Features in the Client API must
 * be registered programmatically.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface Feature {

    /**
     * A call-back method called when the feature is to be enabled in a given
     * runtime configuration scope.
     *
     * The responsibility of the feature is to properly update the supplied runtime configuration context
     * and return {@code true} if the feature was successfully enabled or {@code false} otherwise.
     * <p>
     * Note that under some circumstances the feature may decide not to enable itself, which
     * is indicated by returning {@code false}. In such case the configuration context does
     * not add the feature to the collection of enabled features and a subsequent call to
     * {@link Configuration#isEnabled(Feature)} or {@link Configuration#isEnabled(Class)} method
     * would return {@code false}.
     * </p>
     *
     * @param context configurable context in which the feature should be enabled.
     * @return {@code true} if the feature was successfully enabled, {@code false}
     *         otherwise.
     */
    public boolean configure(FeatureContext context);
}
