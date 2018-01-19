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

package javax.ws.rs.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.RuntimeType;

/**
 * A configuration state associated with a {@link Configurable configurable} JAX-RS context.
 * Defines the components as well as additional meta-data for the configured context.
 * <p>
 * A configuration state may be used to retrieve configuration information about
 * of the associated JAX-RS context (e.g. application, resource method, etc.) or component
 * (e.g. {@link javax.ws.rs.client.Client}, {@link javax.ws.rs.client.WebTarget}, etc.).
 * Configuration information consists of properties, registered JAX-RS component classes
 * and/or instances.
 * </p>
 * <p>
 * This interface can be injected using the {@link Context} annotation.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface Configuration {

    /**
     * Get the runtime type of this configuration context.
     *
     * @return configuration context runtime type.
     */
    public RuntimeType getRuntimeType();

    /**
     * Get the immutable bag of configuration properties.
     *
     * @return the immutable view of configuration properties.
     */
    public Map<String, Object> getProperties();

    /**
     * Get the value for the property with a given name.
     *
     * @param name property name.
     * @return the property value for the specified property name or {@code null}
     *         if the property with such name is not configured.
     */
    public Object getProperty(String name);

    /**
     * Returns an immutable {@link java.util.Collection collection} containing the
     * property names available within the context of the current configuration instance.
     * <p>
     * Use the {@link #getProperty} method with a property name to get the value of
     * a property.
     * </p>
     *
     * @return an immutable {@link java.util.Collection collection} of property names.
     * @see #getProperty
     */
    public Collection<String> getPropertyNames();

    /**
     * Check if a particular {@link Feature feature} instance has been previously
     * enabled in the runtime configuration context.
     * <p>
     * Method returns {@code true} only in case an instance equal to the {@code feature}
     * instance is already present among the features previously successfully enabled in
     * the configuration context.
     * </p>
     *
     * @param feature a feature instance to test for.
     * @return {@code true} if the feature instance has been previously enabled in this
     *         configuration context, {@code false} otherwise.
     */
    public boolean isEnabled(Feature feature);

    /**
     * Check if a {@link Feature feature} instance of {@code featureClass} class has been
     * previously enabled in the runtime configuration context.
     * <p>
     * Method returns {@code true} in case any instance of the {@code featureClass} class is
     * already present among the features previously successfully enabled in the configuration
     * context.
     * </p>
     *
     * @param featureClass a feature class to test for.
     * @return {@code true} if a feature of a given class has been previously enabled in this
     *         configuration context, {@code false} otherwise.
     */
    public boolean isEnabled(Class<? extends Feature> featureClass);

    /**
     * Check if a particular JAX-RS {@code component} instance (such as providers or
     * {@link Feature features}) has been previously registered in the runtime configuration context.
     * <p>
     * Method returns {@code true} only in case an instance equal to the {@code component}
     * instance is already present among the components previously registered in the configuration
     * context.
     * </p>
     *
     * @param component a component instance to test for.
     * @return {@code true} if the component instance has been previously registered in this
     *         configuration context, {@code false} otherwise.
     * @see #isEnabled(Feature)
     */
    public boolean isRegistered(Object component);

    /**
     * Check if a JAX-RS component of the supplied {@code componentClass} class has been previously
     * registered in the runtime configuration context.
     * <p>
     * Method returns {@code true} in case a component of the supplied {@code componentClass} class
     * is already present among the previously registered component classes or instances
     * in the configuration context.
     * </p>
     *
     * @param componentClass a component class to test for.
     * @return {@code true} if a component of a given class has been previously registered in this
     *         configuration context, {@code false} otherwise.
     * @see #isEnabled(Class)
     */
    public boolean isRegistered(Class<?> componentClass);

    /**
     * Get the extension contract registration information for a component of a given class.
     *
     * For component classes that are not configured in this configuration context the method returns
     * an empty {@code Map}. Method does not return {@code null}.
     *
     * @return map of extension contracts and their priorities for which the component class
     *         is registered.
     *         May return an empty map in case the component has not been registered for any
     *         extension contract supported by the implementation.
     */
    public Map<Class<?>, Integer> getContracts(Class<?> componentClass);

    /**
     * Get the immutable set of registered JAX-RS component (such as provider or
     * {@link Feature feature}) classes to be instantiated, injected and utilized in the scope
     * of the configurable instance.
     * <p>
     * For each component type, there can be only a single class-based or instance-based registration
     * present in the configuration context at any given time.
     * </p>
     *
     * @return the immutable set of registered JAX-RS component classes. The returned
     *         value may be empty but will never be {@code null}.
     * @see #getInstances
     */
    public Set<Class<?>> getClasses();

    /**
     * Get the immutable set of registered JAX-RS component (such as provider or
     * {@link Feature feature}) instances to be utilized by the configurable instance.
     * Fields and properties of returned instances are injected with their declared dependencies
     * (see {@link Context}) by the runtime prior to use.
     * <p>
     * For each component type, there can be only a single class-based or instance-based registration
     * present in the configuration context at any given time.
     * </p>
     *
     * @return the immutable set of registered JAX-RS component instances. The returned
     *         value may be empty but will never be {@code null}.
     * @see #getClasses
     */
    public Set<Object> getInstances();
}
