/*
 * Copyright (c) 2010, 2019 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.core;

import java.io.Serial;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Qualifier;

/**
 * <p>This annotation is used to inject information into a class field, bean property or method parameter.</p>
 *
 * <p>In a <a href="https://jakarta.ee/specifications/cdi/">Jakarta CDI</a> environment, {@code Context} is a CDI
 * qualifier. An injection point qualified with {@code Context}, e.g. {@code @Inject @Context UriInfo}, resolves to the
 * instance managed by the Jakarta REST implementation.</p>
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see Application
 * @see UriInfo
 * @see Request
 * @see HttpHeaders
 * @see SecurityContext
 * @see jakarta.ws.rs.ext.Providers
 * @since 1.0
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface Context {

    /**
     * Supports inline instantiation of the {@link Context} annotation.
     *
     * @since 5.0
     */
    final class Literal extends AnnotationLiteral<Context> implements Context {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * Default Context literal
         */
        public static final Context INSTANCE = new Literal();

        private Literal() {
        }
    }
}
