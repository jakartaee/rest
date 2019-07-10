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

package javax.ws.rs;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the run-time context in which an annotated provider
 * is applicable. If a {@code @ConstrainedTo} annotation is not
 * present on a provider type declaration, the declared provider
 * may be used in any run-time context. If such a annotation is present,
 * the runtime will enforce the specified usage restriction.
 * <p>
 * The following example illustrates restricting a {@link javax.ws.rs.ext.MessageBodyReader}
 * provider implementation to run only as part of a {@link RuntimeType#CLIENT Client run-time}:
 * </p>
 * <pre>
 *  &#064;ConstrainedTo(RuntimeType.CLIENT)
 *  public class MyReader implements MessageBodyReader {
 *      ...
 *  }
 * </pre>
 * <p>
 * The following example illustrates restricting a {@link javax.ws.rs.ext.WriterInterceptor}
 * provider implementation to run only as part of a {@link RuntimeType#SERVER Server run-time}:
 * </p>
 * <pre>
 *  &#064;ConstrainedTo(RuntimeType.SERVER)
 *  public class MyWriterInterceptor implements WriterInterceptor {
 *      ...
 *  }
 * </pre>
 * <p>
 * It is a configuration error to constraint a provider implementation to
 * a run-time context in which the provider cannot be applied. In such case, the
 * runtime SHOULD inform a user about the issue and ignore the provider implementation in further
 * processing.
 * </p>
 * <p>
 * For example, the following restriction of a {@link javax.ws.rs.client.ClientRequestFilter}
 * to run only as part of the server run-time would be considered invalid:
 * </p>
 * <pre>
 *  // reported as invalid and ignored by the runtime
 *  &#064;ConstrainedTo(RuntimeType.SERVER)
 *  public class MyFilter implements ClientRequestFilter {
 *      ...
 *  }
 * </pre>
 *
 * @author Marek Potociar
 * @since 2.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConstrainedTo {

    /**
     * Define the {@link RuntimeType constraint type} to be placed on a provider.
     */
    RuntimeType value();
}

