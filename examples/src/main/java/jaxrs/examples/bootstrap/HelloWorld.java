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

package jaxrs.examples.bootstrap;

import java.util.Collections;
import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;

/**
 * Demo application simply returning the string {@code "Hello, World!"}.
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
@ApplicationPath("helloworld")
@Path("hello")
public class HelloWorld extends Application {

    /**
     * Returns the sole, singleton resource.
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(HelloWorld.class);
    }

    /**
     * @return {@code "Hello, World!"}
     */
    @GET
    public String sayHello() {
        return "Hello, World!";
    }

}
