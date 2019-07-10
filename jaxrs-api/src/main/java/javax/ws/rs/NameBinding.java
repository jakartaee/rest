/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
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
 * Meta-annotation used to create name binding annotations for filters
 * and interceptors.
 * <p>
 * Name binding via annotations is only supported as part of the Server API.
 * In name binding, a <i>name-binding</i> annotation is first defined using the
 * {@code @NameBinding} meta-annotation:
 *
 * <pre>
 *  &#64;Target({ ElementType.TYPE, ElementType.METHOD })
 *  &#64;Retention(value = RetentionPolicy.RUNTIME)
 *  <b>&#64;NameBinding</b>
 *  <b>public @interface Logged</b> { }
 * </pre>
 *
 * The defined name-binding annotation is then used to decorate a filter or interceptor
 * class (more than one filter or interceptor may be decorated with the same name-binding
 * annotation):
 *
 * <pre>
 *  <b>&#64;Logged</b>
 *  public class LoggingFilter
 *          implements ContainerRequestFilter, ContainerResponseFilter {
 *      ...
 *  }
 * </pre>
 *
 * At last, the name-binding annotation is applied to the resource method(s) to which the
 * name-bound provider(s) should be bound to:
 *
 * <pre>
 *  &#64;Path("/")
 *  public class MyResourceClass {
 *      &#64;GET
 *      &#64;Produces("text/plain")
 *      &#64;Path("{name}")
 *      <b>&#64;Logged</b>
 *      public String hello(@PathParam("name") String name) {
 *          return "Hello " + name;
 *      }
 *  }
 * </pre>
 *
 * A name-binding annotation may also be attached to a custom
 * {@link javax.ws.rs.core.Application} subclass. In such case a name-bound provider
 * bound by the annotation will be applied to all {@link HttpMethod resource and sub-resource
 * methods} in the application:
 *
 * <pre>
 *  <b>&#64;Logged</b>
 *  &#64;ApplicationPath("myApp")
 *  public class MyApplication extends javax.ws.rs.core.Application {
 *      ...
 *  }
 * </pre>
 * </p>
 *
 * @author Santiago Pericas-Geertsen
 * @author Marek Potociar
 * @since 2.0
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NameBinding {
}
