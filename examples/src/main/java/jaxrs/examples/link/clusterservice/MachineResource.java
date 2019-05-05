/*
 * Copyright (c) 2011, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.link.clusterservice;

import java.net.URI;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import jaxrs.examples.link.clusterservice.Machine.Status;

/**
 * MachineResource class.
 *
 * @author Santiago.Pericas-Geertsen@oracle.com
 */
@Path("/cluster/machine/{name}")
public class MachineResource {

    @Context
    private UriInfo uriInfo;

    private Machine machine;

    @GET
    @Produces({ "application/json" })
    public Response self(@PathParam("name") String name) {
        machine = getMachine(name);
        return Response.ok(machine).links(getTransitionalLinks()).build();
    }

    @POST
    @Path("starter")
    @Produces({ "application/json" })
    public Response starter(@PathParam("name") String name) {
        machine = getMachine(name);
        machine.setStatus(Status.STARTED);
        return Response.ok(machine).links(getTransitionalLinks()).build();
    }

    @POST
    @Path("stopper")
    @Produces({ "application/json" })
    public Response stopper(@PathParam("name") String name) {
        machine = getMachine(name);
        machine.setStatus(Status.STOPPED);
        return Response.ok(machine).links(getTransitionalLinks()).build();
    }

    @POST
    @Path("suspender")
    @Produces({ "application/json" })
    public Response suspender(@PathParam("name") String name) {
        machine = getMachine(name);
        machine.setStatus(Status.SUSPENDED);
        return Response.ok(machine).links(getTransitionalLinks()).build();
    }

    private Machine getMachine(String name) {
        Machine m = Model.getMachine(name);
        if (m == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return m;
    }

    private Link[] getTransitionalLinks() {
        String name = machine.getName();
        URI uri = uriInfo.getRequestUri();
        URI baseUri = uriInfo.getBaseUri();

        Link self = Link.fromMethod(getClass(), "self").baseUri(baseUri)
                .rel("self").buildRelativized(uri, name);
        Link starter = Link.fromMethod(getClass(), "starter").baseUri(baseUri)
                .rel("starter").buildRelativized(uri, name);
        Link stopper = Link.fromMethod(getClass(), "stopper").baseUri(baseUri)
                .rel("stopper").buildRelativized(uri, name);
        Link suspender = Link.fromMethod(getClass(), "suspender").baseUri(baseUri)
                .rel("suspender").buildRelativized(uri, name);

        switch (machine.getStatus()) {
        case STOPPED:
            return new Link[] { self, starter };
        case STARTED:
            return new Link[] { self, stopper, suspender };
        case SUSPENDED:
            return new Link[] { self, starter };
        default:
            throw new IllegalStateException();
        }
    }
}
