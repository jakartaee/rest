/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
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

import javax.ws.rs.DefaultValue;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a contract for a delegate responsible for converting between a
 * {@code String} form of a message parameter value and the corresponding custom
 * Java type {@code T}.
 *
 * Conversion of message parameter values injected via
 * {@link javax.ws.rs.PathParam &#64;PathParam}, {@link javax.ws.rs.QueryParam &#64;QueryParam},
 * {@link javax.ws.rs.MatrixParam &#64;MatrixParam}, {@link javax.ws.rs.FormParam &#64;FormParam},
 * {@link javax.ws.rs.CookieParam &#64;CookieParam} and {@link javax.ws.rs.HeaderParam &#64;HeaderParam}
 * is supported.
 * API implementations MUST support the {@code ParamConverter} mechanism for all Java types.
 * If a {@code ParamConverter} is available for a type, it MUST be preferred over all other 
 * conversion strategies mentioned in section 3.2 (i.e. single {@code String} argument constructor, 
 * static {@code valueOf} or {@code fromString} methods, etc.).
 * <p>
 * By default, when used for injection of parameter values, a selected {@code ParamConverter}
 * instance MUST be used eagerly by an API runtime to convert any {@link DefaultValue
 * default value} in the resource or provider model, that is during the application deployment,
 * before any value &ndash; default or otherwise &ndash; is actually required.
 * This conversion strategy ensures that any errors in the default values are reported
 * as early as possible.
 * This default behavior may be overridden by annotating the {@code ParamConverter}
 * implementation class with a {@link Lazy &#64;Lazy} annotation. In such case any default
 * value conversion delegated to the {@code @Lazy}-annotated converter will be deferred
 * to a latest possible moment (i.e. until the injection of such default value is required).
 * </p>
 * <p>
 * NOTE: A service implementing this contract is not recognized as a registrable
 * extension provider. Instead, a {@link ParamConverterProvider} instance
 * responsible for providing {@code ParamConverter} instances has to be registered
 * as one of the extension providers.
 * </p>
 *
 * @param <T> the supported Java type convertible to/from a {@code String} format.
 * @author Marek Potociar
 * @since 2.0
 */
public interface ParamConverter<T> {

    /**
     * Mandates that a conversion of any {@link DefaultValue default value} delegated
     * to a {@link ParamConverter parameter converter} annotated with {@code @Lazy}
     * annotation SHOULD occur only once the value is actually required (e.g. to be
     * injected for the first time).
     *
     * @since 2.0
     */
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public static @interface Lazy {
    }

    /**
     * Parse the supplied value and create an instance of {@code T}.
     *
     * @param value the string value.
     * @return the newly created instance of {@code T}.
     *
     * @throws IllegalArgumentException if the supplied string cannot be
     *                                  parsed or is {@code null}.
     */
    public T fromString(String value);

    /**
     * Convert the supplied value to a String.
     * <p>
     * This method is reserved for future use. Proprietary API extensions may leverage the method.
     * Users should be aware that any such support for the method comes at the expense of producing
     * non-portable code.
     * </p>
     *
     * @param value the value of type {@code T}.
     * @return a String representation of the value.
     *
     * @throws IllegalArgumentException if the supplied object cannot be
     *                                  serialized or is {@code null}.
     */
    public String toString(T value);
}
