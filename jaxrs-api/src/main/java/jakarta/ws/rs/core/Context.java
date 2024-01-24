/*
 * Copyright (c) 2010, 2024 Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation is used to inject information into a class field, bean property or method parameter.</p>
 *
 * <p>Note that future versions of this API will stop supporting injection via
 * {@code Context} as part of a tighter integration and alignment with
 * <a href="https://jakarta.ee/specifications/cdi/">Jakarta CDI</a>.</p>
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
 * @deprecated This class will be removed in a future version.  Better align with Jakarta CDI.
 */
@Deprecated(forRemoval = true)
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Context {
}
