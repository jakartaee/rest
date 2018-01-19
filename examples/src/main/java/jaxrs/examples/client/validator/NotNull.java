/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.client.validator;

import javax.enterprise.util.AnnotationLiteral;
import javax.validation.Payload;

/**
 * @author Santiago Pericas-Geertsen
 */
public class NotNull extends AnnotationLiteral<javax.validation.constraints.NotNull>
        implements javax.validation.constraints.NotNull {

    private static final long serialVersionUID = -5352564534866654470L;

    @Override
    public String message() {
        return "{javax.validation.constraints.NotNull.message}";
    }

    @Override
    public Class<?>[] groups() {
        return new Class<?>[0];
    }

    @Override
    public Class<? extends Payload>[] payload() {
        return (Class<? extends Payload>[]) new Class<?>[0];
    }
}
