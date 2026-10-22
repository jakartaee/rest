/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation
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

package ee.jakarta.tck.ws.rs.spec.context.cdi.qualifier;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jakarta.ws.rs.core.Application;

/**
 * An application which is deployed through the {@code web.xml}. It declares neither a bean defining annotation nor
 * an {@link jakarta.ws.rs.ApplicationPath @ApplicationPath}, so whether it is a CDI bean is not specified. The
 * resource is returned from {@link #getClasses()}, so the tests do not depend on scanning.
 */
public class WebXmlApplication extends Application {

    public static final String CLASS_NAME = "class.name";
    public static final String INSTANCE_ID = "instance.id";

    private final String instanceId = UUID.randomUUID().toString();

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(WebXmlApplicationResource.class);
    }

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(CLASS_NAME, getClass().getName(),
                INSTANCE_ID, instanceId);
    }
}
