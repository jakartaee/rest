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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Machine class.
 *
 * @author Santiago.Pericas-Geertsen@oracle.com
 */
@XmlRootElement
public class Machine {

    public enum Status {STOPPED, STARTED, SUSPENDED}

    ;

    /**
     * Machine's unique name.
     */
    private String name;

    /**
     * Number of CPUs.
     */
    private int nOfCpus;

    /**
     * Load, single number for all CPUs.
     */
    private double load;

    /**
     * Machine's internal status.
     */
    private Status status = Status.STOPPED;

    public Machine() {
    }

    public Machine(String name) {
        this.name = name;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public int getnOfCpus() {
        return nOfCpus;
    }

    public void setnOfCpus(int nOfCpus) {
        this.nOfCpus = nOfCpus;
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
