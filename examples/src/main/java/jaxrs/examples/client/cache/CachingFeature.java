/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * Example caching feature.
 *
 * @author Marek Potociar
 */
public class CachingFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        final Map<String, CacheEntry> cacheStore = new ConcurrentHashMap<String, CacheEntry>();
        context.register(new CacheEntryLocator(cacheStore)).register(new CacheResponseFilter(cacheStore));

        return true;
    }
}
