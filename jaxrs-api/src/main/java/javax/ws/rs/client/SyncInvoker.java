/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.client;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * Uniform interface for synchronous invocation of HTTP methods.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface SyncInvoker {

    // GET

    /**
     * Invoke HTTP GET method for the current request synchronously.
     *
     * @return invocation response.
     * @throws javax.ws.rs.ProcessingException
     *          in case the invocation processing has failed.
     */
    Response get();

    /**
     * Invoke HTTP GET method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T get(Class<T> responseType);

    /**
     * Invoke HTTP GET method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}
     */
    <T> T get(GenericType<T> responseType);

    // PUT

    /**
     * Invoke HTTP PUT method for the current request synchronously.
     *
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response put(Entity<?> entity);

    /**
     * Invoke HTTP PUT method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T put(Entity<?> entity, Class<T> responseType);

    /**
     * Invoke HTTP PUT method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T put(Entity<?> entity, GenericType<T> responseType);

    // POST

    /**
     * Invoke HTTP POST method for the current request synchronously.
     *
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response post(Entity<?> entity);

    /**
     * Invoke HTTP POST method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T post(Entity<?> entity, Class<T> responseType);

    /**
     * Invoke HTTP POST method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T post(Entity<?> entity, GenericType<T> responseType);

    // DELETE

    /**
     * Invoke HTTP DELETE method for the current request synchronously.
     *
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response delete();

    /**
     * Invoke HTTP DELETE method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T delete(Class<T> responseType);

    /**
     * Invoke HTTP DELETE method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T delete(GenericType<T> responseType);

    // HEAD

    /**
     * Invoke HTTP HEAD method for the current request synchronously.
     *
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response head();

    // OPTIONS

    /**
     * Invoke HTTP OPTIONS method for the current request synchronously.
     *
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response options();

    /**
     * Invoke HTTP OPTIONS method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T options(Class<T> responseType);

    /**
     * Invoke HTTP OPTIONS method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T options(GenericType<T> responseType);

    // TRACE

    /**
     * Invoke HTTP TRACE method for the current request synchronously.
     *
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response trace();

    /**
     * Invoke HTTP TRACE method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T trace(Class<T> responseType);

    /**
     * Invoke HTTP TRACE method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T trace(GenericType<T> responseType);

    // ARBITRARY METHOD

    /**
     * Invoke an arbitrary method for the current request synchronously.
     *
     * @param name method name.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response method(String name);

    /**
     * Invoke an arbitrary method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param name         method name.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T method(String name, Class<T> responseType);

    /**
     * Invoke an arbitrary method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param name         method name.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T method(String name, GenericType<T> responseType);

    /**
     * Invoke an arbitrary method for the current request synchronously.
     *
     * @param name   method name.
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     */
    Response method(String name, Entity<?> entity);

    /**
     * Invoke an arbitrary method for the current request synchronously.
     *
     * @param <T>          response entity type.
     * @param name         method name.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified response type is not
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T method(String name, Entity<?> entity, Class<T> responseType);

    /**
     * Invoke an arbitrary method for the current request synchronously.
     *
     * @param <T>          generic response entity type.
     * @param name         method name.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter
     *                                     or during conversion of the response entity data to an instance
     *                                     of a particular Java type).
     * @throws ProcessingException         in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException     in case the response status code of the response
     *                                     returned by the server is not
     *                                     {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                     successful} and the specified generic response type does not represent
     *                                     {@link javax.ws.rs.core.Response}.
     */
    <T> T method(String name, Entity<?> entity, GenericType<T> responseType);
}
