/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.filter.logging;

import javax.ws.rs.GET;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * Dynamic feature for a enabling a logging request/response post-matching filter
 * that dynamically decides to bind the logging filter only to GET processing
 * resource methods on all subclasses of {@link MyResourceClass} and the
 * {@code MyResourceClass} itself.
 *
 * @author Santiago Pericas-Geertsen
 * @author Marek Potociar
 */
@Provider
public final class DynamicLoggingFilterFeature implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        if (MyResourceClass.class.isAssignableFrom(resourceInfo.getResourceClass())
                && resourceInfo.getResourceMethod().isAnnotationPresent(GET.class)) {
            context.register(new LoggingFilter());
        }
    }
}
