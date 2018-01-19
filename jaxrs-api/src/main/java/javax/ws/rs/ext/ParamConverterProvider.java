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

package javax.ws.rs.ext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Contract for a provider of {@link ParamConverter} instances.
 * <p>
 * Providers implementing {@code ParamConverterProvider} contract must be either programmatically
 * registered in a JAX-RS runtime or must be annotated with
 * {@link javax.ws.rs.ext.Provider &#64;Provider} annotation to be automatically discovered
 * by the JAX-RS runtime during a provider scanning phase.
 * </p>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface ParamConverterProvider {

    /**
     * Obtain a {@link ParamConverter} that can provide from/to string conversion
     * for an instance of a particular Java type.
     *
     * @param <T>         the supported Java type convertible to/from a {@code String} format.
     * @param rawType     the raw type of the object to be converted.
     * @param genericType the type of object to be converted. E.g. if an String value
     *                    representing the injected request parameter
     *                    is to be converted into a method parameter, this will be the
     *                    formal type of the method parameter as returned by {@code Class.getGenericParameterTypes}.
     * @param annotations an array of the annotations associated with the convertible
     *                    parameter instance. E.g. if a string value is to be converted into a method parameter,
     *                    this would be the annotations on that parameter as returned by
     *                    {@link java.lang.reflect.Method#getParameterAnnotations}.
     * @return the string converter, otherwise {@code null}.
     */
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation annotations[]);
}
