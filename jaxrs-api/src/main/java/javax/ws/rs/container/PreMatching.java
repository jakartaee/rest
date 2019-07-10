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

package javax.ws.rs.container;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Global binding annotation that can be applied to a {@link ContainerRequestFilter
 * container request filter} to indicate that such filter should be applied globally
 * on all resources in the application before the actual resource matching occurs.
 * <p>
 * The runtime will apply the filters marked with the {@code @PreMatching}
 * annotation globally to all resources, before the incoming request has been matched
 * to a particular resource method.
 * Any {@link javax.ws.rs.NameBinding named binding annotations} will be ignored on
 * a component annotated with the {@code @PreMatching} annotation.
 * </p>
 *
 * @author Marek Potociar
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreMatching {
}
