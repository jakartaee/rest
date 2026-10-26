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

package ee.jakarta.tck.ws.rs.spec.context.cdi.services;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.inject.spi.BeanContainer;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;

/**
 * A feature which is both loaded with {@link java.util.ServiceLoader} and, through {@link Provider @Provider}, a CDI
 * bean. {@code ServiceLoader} uses the public no-argument constructor, while CDI uses the {@link Inject @Inject}
 * constructor, which identifies the instance the implementation registered.
 */
@Provider
public class ServiceLoadedFeature implements Feature {

    public static final String LOADED_BY = "ee.jakarta.tck.ws.rs.spec.context.cdi.services.loaded-by";

    private static final AtomicInteger CONFIGURED = new AtomicInteger();

    private final String loadedBy;

    public ServiceLoadedFeature() {
        this.loadedBy = "service-loader";
    }

    @Inject
    public ServiceLoadedFeature(final BeanContainer beanContainer) {
        this.loadedBy = "cdi";
    }

    @Override
    public boolean configure(final FeatureContext context) {
        CONFIGURED.incrementAndGet();
        context.property(LOADED_BY, loadedBy);
        return true;
    }

    /**
     * Returns the number of times a {@code ServiceLoadedFeature} was configured.
     */
    public static int configured() {
        return CONFIGURED.get();
    }
}
