/*
 * Copyright (c) 2015, 2017 Oracle and/or its affiliates. All rights reserved.
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

import javax.ws.rs.core.GenericType;

/**
 * Uniform interface for reactive invocation of HTTP methods. All reactive invokers must
 * implement this interface. The type parameter {@code T} represents the Java type of an asynchronous
 * computation. All API implementations MUST support the default reactive invoker based on
 * {@link java.util.concurrent.CompletionStage}.
 *
 * @param <T> a type representing the asynchronous computation.
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 * @see javax.ws.rs.client.CompletionStageRxInvoker
 * @since 2.1
 */
public interface RxInvoker<T> {

    /**
     * Invoke HTTP GET method for the current request.
     *
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T get();

    /**
     * Invoke HTTP GET method for the current request.
     *
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T get(Class<R> responseType);

    /**
     * Invoke HTTP GET method for the current request.
     *
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T get(GenericType<R> responseType);

    /**
     * Invoke HTTP PUT method for the current request.
     *
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T put(Entity<?> entity);

    /**
     * Invoke HTTP PUT method for the current request.
     *
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T put(Entity<?> entity, Class<R> responseType);

    /**
     * Invoke HTTP PUT method for the current request.
     *
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T put(Entity<?> entity, GenericType<R> responseType);

    /**
     * Invoke HTTP POST method for the current request.
     *
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T post(Entity<?> entity);

    /**
     * Invoke HTTP POST method for the current request.
     *
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T post(Entity<?> entity, Class<R> responseType);

    /**
     * Invoke HTTP POST method for the current request.
     *
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T post(Entity<?> entity, GenericType<R> responseType);

    /**
     * Invoke HTTP DELETE method for the current request.
     *
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T delete();

    /**
     * Invoke HTTP DELETE method for the current request.
     *
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T delete(Class<R> responseType);

    /**
     * Invoke HTTP DELETE method for the current request.
     *
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T delete(GenericType<R> responseType);

    /**
     * Invoke HTTP HEAD method for the current request.
     *
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T head();

    /**
     * Invoke HTTP OPTIONS method for the current request.
     *
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T options();

    /**
     * Invoke HTTP OPTIONS method for the current request.
     *
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T options(Class<R> responseType);

    /**
     * Invoke HTTP OPTIONS method for the current request.
     *
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T options(GenericType<R> responseType);

    /**
     * Invoke HTTP TRACE method for the current request.
     *
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T trace();

    /**
     * Invoke HTTP TRACE method for the current request.
     *
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T trace(Class<R> responseType);

    /**
     * Invoke HTTP TRACE method for the current request.
     *
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type.
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T trace(GenericType<R> responseType);

    /**
     * Invoke an arbitrary method for the current request.
     *
     * @param name method name.
     * @return invocation response wrapped in the completion aware type..
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T method(String name);

    /**
     * Invoke an arbitrary method for the current request.
     *
     * @param name         method name.
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type..
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T method(String name, Class<R> responseType);

    /**
     * Invoke an arbitrary method for the current request.
     *
     * @param name         method name.
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type..
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T method(String name, GenericType<R> responseType);

    /**
     * Invoke an arbitrary method for the current request.
     *
     * @param name   method name.
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response wrapped in the completion aware type..
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     */
    public T method(String name, Entity<?> entity);

    /**
     * Invoke an arbitrary method for the current request.
     *
     * @param name         method name.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @param <R>          response entity type.
     * @return invocation response wrapped in the completion aware type..
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T method(String name, Entity<?> entity, Class<R> responseType);

    /**
     * Invoke an arbitrary method for the current request.
     *
     * @param name         method name.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response entity will be converted to.
     * @param <R>          generic response entity type.
     * @return invocation response wrapped in the completion aware type..
     * @throws javax.ws.rs.client.ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a
     *                                                        filter or during conversion of the response entity data to an
     *                                                        instance of a particular Java type).
     * @throws javax.ws.rs.ProcessingException                in case the request processing or subsequent I/O operation fails.
     * @throws javax.ws.rs.WebApplicationException            in case the response status code of the response returned by the
     *                                                        server is not
     *                                                        {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     *                                                        successful} and the specified response type is not
     *                                                        {@link javax.ws.rs.core.Response}.
     */
    public <R> T method(String name, Entity<?> entity, GenericType<R> responseType);
}

