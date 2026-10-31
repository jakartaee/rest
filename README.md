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
 
## Configuration
 
When using the Jakarta EE Web Profile or the full Jakarta EE Platform, Jakarta REST is already bundled, so no additional dependency is required. Otherwise, add the following to your build:
 
```xml
<dependency>
    <groupId>jakarta.ws.rs</groupId>
    <artifactId>jakarta.ws.rs-api</artifactId>
    <version>3.1.0</version>
</dependency>
```
 
To enable Jakarta REST in an application, create a class that extends `jakarta.ws.rs.core.Application`, annotated with `@ApplicationPath`, supplying the root URI path under which the RESTful resources will be exposed. For an application named `HelloWorld` with `@ApplicationPath("resources")`, a resource would be reachable at a URI such as `http://hostname:port/HelloWorld/resources/{service-path}`. The `Application` class can also be used to enable all resources automatically, or to register individual resource classes explicitly.
 
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
```java
@GET
@Path("/hello/{name}")
public String helloService(@PathParam("name") String name) {
    return "Hello " + name;
}
```
 
By default, Jakarta REST negotiates the response format with the client. To force a specific format, apply `@Produces` with a `jakarta.ws.rs.core.MediaType`:
 
```java
@GET
@Path("/json")
@Produces(MediaType.APPLICATION_JSON)
public Product getProduct() { ... }
```
 
## Client API
 
Jakarta REST also ships with a client API for invoking resources, mostly found under the `jakarta.ws.rs.client` package. A `Client` is built through `ClientBuilder`, and follows this lifecycle:
 
1. Obtain a `Client` instance
2. Create a `WebTarget`
3. Build a request from the `WebTarget`
4. Submit the request, or prepare an `Invocation` to submit later
```java
Client client = ClientBuilder.newClient();
Response res = client.target("http://localhost:8080/HelloWorld/resources/jakartaee10")
        .request("text/plain").get();
```
 
The API supports a fluent, builder-style chain for configuring properties, adding query parameters, and composing complex URIs by appending path segments:
 
```java
WebTarget base = client.target("http://localhost:8080/HelloWorld/resources/jakartaee10");
Response response = base.path("hello").path("Duke")
        .queryParam("name", "Duke")
        .request("text/plain").get();
```
 
## Additional Features
 
Jakarta REST offers several advanced capabilities for building robust APIs:
 
- **Providers** — enable cross-cutting behavior, such as converting an HTTP payload to and from a Java object. Registered automatically via `@Provider`, or manually.
- **Filters and Interceptors** — built-in providers for tasks like automatic logging, request validation, and payload manipulation, with configurable priority and ordering.
- **Asynchronous processing** — available on both server and client. On the server, a resource method can defer its response and resume the connection later; on the client, an async call returns a `Future` that can be checked once the response arrives.
- **Server-Sent Events (SSE)** — one-way, long-running communication from server to client. The client side uses `SseEventSource` to register consumers; the server side injects an `SseEventSink` and `Sse` object into the resource method (annotated with `@Produces(MediaType.SERVER_SENT_EVENTS)`), and `Sse.newBroadcaster()` allows broadcasting a message to all registered consumers.
## Conclusion
 
Jakarta REST provides an easy-to-use API for developing RESTful web services on Jakarta EE. An application is configured by declaring an `Application` subclass with a root path, resource classes are marked with `@Path`, and methods annotated with `@GET`, `@POST`, and the other HTTP verbs expose the actual endpoints — producing or consuming data as needed. A full-featured client is also available, along with advanced features such as asynchronous invocation and Server-Sent Events.
 
## Code of Conduct
 
This project is governed by the Eclipse Foundation Community Code of Conduct. By participating, you are expected to uphold this code of conduct. Please report unacceptable behavior to [codeofconduct@eclipse.org](mailto:codeofconduct@eclipse.org).
 
## Getting Help
 
Having trouble with Jakarta REST? We'd love to help!
 
Report Jakarta REST bugs at https://github.com/jakartaee/rest/issues.
 
## Building from Source
 
You don't need to build from source to use the project, but you can do so with Maven and Java 17 or higher.
 
```
mvn package
```
 