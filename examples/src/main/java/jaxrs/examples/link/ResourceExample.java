/*
 * Copyright (c) 2011, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.link;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Link.JaxbAdapter;
import jakarta.ws.rs.core.UriInfo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * ResourceExample class.
 *
 * @author Santiago Pericas-Geertsen (santiago.pericasgeertsen at oracle.com)
 */
@Path("/myresource")
public class ResourceExample {

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces({ "application/xml", "application/json" })
    public MyModel getIt() {
        Link self = Link.fromMethod(getClass(), "getIt").baseUri(uriInfo.getBaseUri())
                .rel("self").buildRelativized(uriInfo.getRequestUri());
        MyModel m = new MyModel();
        m.setLink(self);
        m.setAtomLink(self);
        return m;
    }

    @XmlRootElement
    public static class MyModel {

        private Link link;
        private Link atomLink;

        public MyModel() {
        }

        @XmlElement(name = "link")
        @XmlJavaTypeAdapter(JaxbAdapter.class)
        public Link getLink() {
            return link;
        }

        public void setLink(Link link) {
            this.link = link;
        }

        @XmlElement(namespace = "http://www.w3.org/2005/Atom", name = "link")
        @XmlJavaTypeAdapter(JaxbAdapter.class)
        public Link getAtomLink() {
            return atomLink;
        }

        public void setAtomLink(Link link) {
            this.atomLink = link;
        }
    }
}
