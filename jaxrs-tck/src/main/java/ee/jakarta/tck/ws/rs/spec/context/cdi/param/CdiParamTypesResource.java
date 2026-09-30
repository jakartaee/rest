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

package ee.jakarta.tck.ws.rs.spec.context.cdi.param;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;

@Path("/types/{paths:.+}")
@RequestScoped
public class CdiParamTypesResource {

    @Inject
    @QueryParam("list")
    private List<String> listParam;

    @Inject
    @QueryParam("set")
    private Set<String> setParam;

    @Inject
    @QueryParam("sortedSet")
    private SortedSet<String> sortedSetParam;

    @Inject
    @QueryParam("array")
    private String[] arrayParam;

    @Inject
    @QueryParam("wrapper")
    private Integer wrapperParam;

    @Inject
    @QueryParam("bool")
    private Boolean booleanParam;

    @Inject
    @QueryParam("enum")
    private ParamEnum enumParam;

    @Inject
    @QueryParam("default")
    @DefaultValue("fallback")
    private String defaultParam;

    @Inject
    @QueryParam("defaultInt")
    @DefaultValue("42")
    private int defaultIntParam;

    @Inject
    @PathParam("paths")
    private List<PathSegment> listPaths;

    @Inject
    @PathParam("paths")
    private PathSegment singlePath;

    CdiParamTypesResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TypesDescriptor get() {
        return TypesDescriptor.of()
                .setListParam(listParam)
                .setSetParam(setParam)
                .setSortedSetParam(sortedSetParam)
                .setArrayParam(arrayParam)
                .setWrapperParam(wrapperParam)
                .setBooleanParam(booleanParam)
                .setEnumParam(enumParam)
                .setDefaultParam(defaultParam)
                .setDefaultIntParam(defaultIntParam)
                .setListPaths(listPaths.stream().map(PathSegment::getPath).toList())
                .setSinglePath(singlePath.getPath());
    }
}
