/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.link.clusterservice;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import jaxrs.examples.link.clusterservice.Cluster.Status;

/**
 * ClusterResource class.
 *
 * @author Santiago.Pericas-Geertsen@oracle.com
 */
@Path("/cluster")
public class ClusterResource {

    @Context
    private UriInfo uriInfo;

    private Cluster cluster = Model.getCluster();

    @GET
    @Produces({"application/json"})
    public Response self() {
        return Response.ok(cluster).links(getTransitionalLinks()).build();
    }

    @POST
    @Path("onliner")
    @Produces({"application/json"})
    public Response onliner() {
        cluster.setStatus(Status.ONLINE);
        return Response.ok(cluster).links(getTransitionalLinks()).build();
    }

    @POST
    @Path("offliner")
    @Produces({"application/json"})
    public Response offliner() {
        cluster.setStatus(Status.OFFLINE);
        return Response.ok(cluster).links(getTransitionalLinks()).build();
    }

    private Link[] getTransitionalLinks() {
        URI uri = uriInfo.getRequestUri();
        URI baseUri = uriInfo.getBaseUri();
        Link self = Link.fromMethod(getClass(), "self").baseUri(baseUri).rel("self").buildRelativized(uri);
        Link item = Link.fromMethod(MachineResource.class, "self").baseUri(baseUri).rel("item").buildRelativized(uri);
        Link onliner = Link.fromMethod(getClass(), "onliner").baseUri(baseUri).rel("onliner").buildRelativized(uri);
        Link offliner = Link.fromMethod(getClass(), "offliner").baseUri(baseUri).rel("offliner").buildRelativized(uri);

        return cluster.getStatus() == Status.ONLINE ?
                new Link[]{self, item, offliner} : new Link[]{self, item, onliner};
    }

}
