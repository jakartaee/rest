/*
 * Copyright (c) 2011 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * An injectable class to access the resource class and resource method matched by the current request. Methods in this
 * class MAY return <code>null</code> if a resource class and method have not been matched, e.g. in a standalone,
 * pre-matching {@link ContainerRequestFilter} that was not provided by a post-matching {@link PreMatching}.
 *
 * @author Santiago Pericas-Geertsen
 * @since 2.0
 */
public interface ResourceInfo {

    /**
     * Get the resource method that is the target of a request, or <code>null</code> if this information is not available.
     *
     * @return resource method instance or null
     * @see #getResourceClass()
     */
    Method getResourceMethod();

    /**
     * Get the resource class that is the target of a request, or <code>null</code> if this information is not available.
     *
     * @return resource class instance or null
     * @see #getResourceMethod()
     */
    Class<?> getResourceClass();

    /**
     * How to handle inheritance of custom annotations
     *
     * @author Markus KARG (markus@headcrashing.eu)
     * @since 4.1
     */
    public static enum CustomAnnotationsInheritancePolicy {

        /**
         * Treat custom annotations as JAX-RS annotations according Jakarta REST Specification.
         */
        JAX_RS,

        /**
         * Inherit custom annotations but do not treat them as JAX-RS annotation.
         */
        INHERIT,

        /**
         * Do not inherit custom annoations.
         */
        NONE
    }

    /**
     * Get the annotation of specified type from the target of a request, or {@code null} if this information is not available.
     *
     * This method respects the JAX-RS Annotation Inheritance Policy.
     * The inheritance policy for custom annotations is to be provided.
     *
     * @param <A> the type of the annotation to query for and return if present
     * @param annotationClass the Class object corresponding to the annotation type
     * @param customAnnotationInheritancePolicy specifies how to handle inheritance if {@code annotationClass}
     *        is a custom annotation (ignored for JAX-RS annotations)
     * @return this element's annotation for the specified annotation type if present, else {@code null}
     *
     * @since 4.1
     */
    <A extends Annotation> A getResourceAnnotation(Class<A> annotationClass,
            CustomAnnotationsInheritancePolicy customAnnotationsInheritancePolicy);
}
