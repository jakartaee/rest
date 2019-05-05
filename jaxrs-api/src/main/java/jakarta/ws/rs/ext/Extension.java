/*
 * Copyright (c) 2018 Markus KARG. All rights reserved.
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

package jakarta.ws.rs.ext;

import java.util.ServiceLoader;

/**
 * <p>
 * An extension to the JAX-RS runtime.
 * </p>
 * <p>
 * All kinds of JAX-RS components (i. e. filters and interceptors, features, providers, etc.) can be marked as an
 * extension to the JAX-RS runtime, hence will become available to <em>all</em> applications running on thusly extended
 * runtimes.
 * </p>
 * <p>
 * JAX-RS implementations MUST automatically register a class in {@link jakarta.ws.rs.core.Configuration runtime
 * configurations} if the class is a <em>provider class</em> for the {@link Extension} <em>service type</em> according
 * to the rules set out in the description of {@link ServiceLoader}.
 * </p>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 */
public interface Extension {
}
