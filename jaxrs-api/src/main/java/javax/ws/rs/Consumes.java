/*
 * Copyright (c) 2010, 2017 Oracle and/or its affiliates. All rights reserved.
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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the media types that the methods of a resource class or
 * {@link javax.ws.rs.ext.MessageBodyReader} can accept. If
 * not specified, a container will assume that any media type is acceptable.
 * Method level annotations override a class level annotation. A container
 * is responsible for ensuring that the method invoked is capable of consuming
 * the media type of the HTTP request entity body. If no such method is
 * available the container must respond with a HTTP "415 Unsupported Media Type"
 * as specified by RFC 2616.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see javax.ws.rs.ext.MessageBodyReader
 * @since 1.0
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Consumes {

    /**
     * A list of media types. Each entry may specify a single type or consist
     * of a comma separated list of types, with any leading or trailing white-spaces
     * in a single type entry being ignored. For example:
     * <pre>
     *  {"image/jpeg, image/gif ", " image/png"}
     * </pre>
     * Use of the comma-separated form allows definition of a common string constant
     * for use on multiple targets.
     */
    String[] value() default "*/*";
}
