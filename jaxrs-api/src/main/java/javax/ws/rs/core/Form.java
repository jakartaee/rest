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

package javax.ws.rs.core;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents the the HTML form data request entity encoded using the
 * {@code "application/x-www-form-urlencoded"} content type.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public class Form {
    private final MultivaluedMap<String, String> parameters;

    /**
     * Create a new form data instance.
     * <p>
     * The underlying form parameter store is configured to preserve the insertion order
     * of the parameters. I.e. parameters can be iterated in the same order as they were
     * inserted into the {@code Form}.
     * </p>
     */
    public Form() {
        this(new AbstractMultivaluedMap<String, String>(new LinkedHashMap<String, List<String>>()) {
            // by default, the items in a Form are iterable based on their insertion order.
        });
    }

    /**
     * Create a new form data instance with a single parameter entry.
     * <p>
     * The underlying form parameter store is configured to preserve the insertion order
     * of the parameters. I.e. parameters can be iterated in the same order as they were
     * inserted into the {@code Form}.
     * </p>
     *
     * @param parameterName  form parameter name.
     * @param parameterValue form parameter value.
     */
    public Form(final String parameterName, final String parameterValue) {
        this();

        parameters.add(parameterName, parameterValue);
    }

    /**
     * Create a new form data instance and register a custom underlying parameter store.
     * <p>
     * This method is useful in situations when a custom parameter store is needed
     * in order to change the default parameter iteration order, improve performance
     * or facilitate other custom requirements placed on the parameter store.
     * </p>
     *
     * @param store form data store used by the created form instance.
     */
    public Form(final MultivaluedMap<String, String> store) {
        this.parameters = store;
    }

    /**
     * Adds a new value to the specified form parameter.
     *
     * @param name  name of the parameter.
     * @param value new parameter value to be added.
     * @return updated {@code Form} instance.
     */
    public Form param(final String name, final String value) {
        parameters.add(name, value);

        return this;
    }

    /**
     * Returns multivalued map representation of the form.
     *
     * @return form represented as multivalued map.
     * @see MultivaluedMap
     */
    public MultivaluedMap<String, String> asMap() {
        return parameters;
    }
}
