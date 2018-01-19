/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.async;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 * TODO: javadoc.
 *
 * @author Marek Potociar
 */
@Stateless
@Path("/")
public class LongRunningEjbResource {
    @GET
    @Asynchronous
    public void longRunningOperation(@Suspended AsyncResponse ar) {
        final String result = executeLongRunningOperation();
        ar.resume(result);
    }

    private String executeLongRunningOperation() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "done";
    }
}
