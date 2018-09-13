/*
 * Copyright (c) 2018 IBM and Contributors to the Eclipse Foundation
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

package javax.ws.rs.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;

/**
 * {@link javax.ws.rs.client.ClientBuilderListener} unit tests.
 *
 * @author Andy McCright
 * @since 2.2
 */
public class ConfigurationStub implements Configuration {
    Map<String,Object> properties = new HashMap<>();
    Map<Object,Integer> providerInstances = new HashMap<>();
    Map<Class<?>,Integer> providerClasses = new HashMap<>();
    Set<Feature> features = new HashSet<>();

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        // unimplemented
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#getContracts(java.lang.Class)
     */
    @Override
    public Map<Class<?>, Integer> getContracts(Class<?> arg0) {
        // unimplemented
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#getInstances()
     */
    @Override
    public Set<Object> getInstances() {
        // unimplemented
        return null;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#getProperties()
     */
    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#getProperty(java.lang.String)
     */
    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#getPropertyNames()
     */
    @Override
    public Collection<String> getPropertyNames() {
        return properties.keySet();
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#getRuntimeType()
     */
    @Override
    public RuntimeType getRuntimeType() {
        return RuntimeType.CLIENT;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#isEnabled(javax.ws.rs.core.Feature)
     */
    @Override
    public boolean isEnabled(Feature feature) {
        return features.contains(feature);
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#isEnabled(java.lang.Class)
     */
    @Override
    public boolean isEnabled(Class<? extends Feature> arg0) {
        // unimplemented
        return false;
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#isRegistered(java.lang.Object)
     */
    @Override
    public boolean isRegistered(Object instance) {
        return providerInstances.containsKey(instance);
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Configuration#isRegistered(java.lang.Class)
     */
    @Override
    public boolean isRegistered(Class<?> clazz) {
        return providerClasses.containsKey(clazz);
    }

}
