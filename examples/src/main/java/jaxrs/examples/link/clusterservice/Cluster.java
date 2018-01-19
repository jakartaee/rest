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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Cluster class.
 *
 * @author Santiago.Pericas-Geertsen@oracle.com
 */
@XmlRootElement
public class Cluster {

    public enum Status {OFFLINE, ONLINE};

    private String name;

    private Status status = Status.ONLINE;

    private List<Machine> machines = new ArrayList<Machine>();

    public Cluster() {
    }

    public Cluster(String name) {
        this.name = name;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
