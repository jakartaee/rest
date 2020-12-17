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

/**
 * Model class.
 *
 * @author Santiago.Pericas-Geertsen@oracle.com
 */
public final class Model {

    static final Cluster CLUSTER;

    static {
        CLUSTER = new Cluster("cluster1");
        Machine m = new Machine("alpha");
        m.setLoad(1.4);
        m.setnOfCpus(2);
        CLUSTER.getMachines().add(m);
        m = new Machine("beta");
        m.setLoad(0.75);
        m.setnOfCpus(4);
        CLUSTER.getMachines().add(m);
        m = new Machine("gamma");
        m.setLoad(0.2);
        m.setnOfCpus(8);
        CLUSTER.getMachines().add(m);
    }

    static Cluster getCluster() {
        return CLUSTER;
    }

    static Machine getMachine(String name) {
        for (Machine m : CLUSTER.getMachines()) {
            if (name.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }

    private Model() {
    }
}
