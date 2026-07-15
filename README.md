[//]: # " Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved. "
[//]: # "  "
[//]: # " This program and the accompanying materials are made available under the "
[//]: # " terms of the Eclipse Public License v. 2.0, which is available at "
[//]: # " http://www.eclipse.org/legal/epl-2.0. "
[//]: # "  "
[//]: # " This Source Code may also be made available under the following Secondary "
[//]: # " Licenses when the conditions for such availability set forth in the "
[//]: # " Eclipse Public License v. 2.0 are satisfied: GNU General Public License, "
[//]: # " version 2 with the GNU Classpath Exception, which is available at "
[//]: # " https://www.gnu.org/software/classpath/license.html. "
[//]: # "  "
[//]: # " SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 "

---

# Jakarta RESTful Web Services [![Build Status](https://travis-ci.org/eclipse-ee4j/jaxrs-api.svg?branch=master)](https://travis-ci.org/eclipse-ee4j/jaxrs-api)

**Jakarta RESTful Web Services** provides a specification document, TCK and foundational API to develop web services following the Representational State Transfer (REST) architectural pattern.

Contributions are welcome, please sign the Eclipse Contributor Agreement before submitting PRs: https://www.eclipse.org/contribute/cla

Project page: https://projects.eclipse.org/projects/ee4j.jaxrs

Mailinglist: https://accounts.eclipse.org/mailing-list/jaxrs-dev


# Introduction

Jakarta REST is the solution for development of building Representational State Transfer web services on the Jakarta EE Platform. The specification is easy to learn, and it enables one to construct powerful REST APIs and it also includes APIs for working with web services as a client. As such, this specification is key to the development of microservices and cloud based applications, and it is part of the Jakarta EE Web Profile as well as the full platform.

Using the API, a web service can be developed by placing just a few annotations on a plain old Java object (POJO). The API includes annotations for performing many tasks, such as producing REST responses in specified format(s), consuming data, and performing standard data operations such as CREATE, READ, UPDATE, and DELETE. The specification also includes advanced features for development of robust REST APIs.

## Overview of the API

A minimal resource class only needs two annotations: `@Path`, to declare the URI segment that exposes the resource, and `@GET`, to mark a method as handling HTTP GET requests.

```java
@Path("hello")
public class HelloResource {

    @GET
    public Response ping() {
        return Response.ok("pong").build();
    }
}
```

Jakarta REST also provides annotations for the other common HTTP verbs — `@PUT`, `@POST`, `@DELETE`, `@PATCH`, `@HEAD`, and `@OPTIONS` — along with `@Produces` and `@Consumes` to control the media type returned or accepted by a resource method.

Parameters can be extracted from the request in several ways:

- `@PathParam` — values embedded in the URI path (e.g. `/hello/{name}`)
- `@QueryParam` — values passed as query string parameters
- `@FormParam` — values submitted through an HTML form