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

import java.util.concurrent.Future;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * Uniform interface for asynchronous invocation of HTTP methods.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public interface AsyncInvoker {

    // GET

    /**
     * Invoke HTTP GET method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * Note that in case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @return invocation response {@link Future future}.
     */
    Future<Response> get();

    /**
     * Invoke HTTP GET method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> get(Class<T> responseType);

    /**
     * Invoke HTTP GET method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> get(GenericType<T> responseType);

    /**
     * Invoke HTTP GET method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> get(InvocationCallback<T> callback);

    // PUT

    /**
     * Invoke HTTP PUT method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response {@link Future future}.
     */
    Future<Response> put(Entity<?> entity);

    /**
     * Invoke HTTP PUT method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> put(Entity<?> entity, Class<T> responseType);

    /**
     * Invoke HTTP PUT method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> put(Entity<?> entity, GenericType<T> responseType);

    /**
     * Invoke HTTP PUT method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param entity   request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                 Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                 {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                 the entity variant information.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> put(Entity<?> entity, InvocationCallback<T> callback);

    // POST

    /**
     * Invoke HTTP POST method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response {@link Future future}.
     * @throws javax.ws.rs.ProcessingException
     *          in case the invocation processing has failed.
     */
    Future<Response> post(Entity<?> entity);

    /**
     * Invoke HTTP POST method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> post(Entity<?> entity, Class<T> responseType);

    /**
     * Invoke HTTP POST method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> post(Entity<?> entity, GenericType<T> responseType);

    /**
     * Invoke HTTP POST method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param entity   request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                 Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                 {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                 the entity variant information.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> post(Entity<?> entity, InvocationCallback<T> callback);

    // DELETE

    /**
     * Invoke HTTP DELETE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @return invocation response {@link Future future}.
     */
    Future<Response> delete();

    /**
     * Invoke HTTP DELETE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> delete(Class<T> responseType);

    /**
     * Invoke HTTP DELETE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> delete(GenericType<T> responseType);

    /**
     * Invoke HTTP DELETE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> delete(InvocationCallback<T> callback);

    // HEAD

    /**
     * Invoke HTTP HEAD method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @return invocation response {@link Future future}.
     */
    Future<Response> head();

    /**
     * Invoke HTTP HEAD method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    Future<Response> head(InvocationCallback<Response> callback);

    // OPTIONS

    /**
     * Invoke HTTP OPTIONS method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @return invocation response {@link Future future}.
     */
    Future<Response> options();

    /**
     * Invoke HTTP OPTIONS method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> options(Class<T> responseType);

    /**
     * Invoke HTTP OPTIONS method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> options(GenericType<T> responseType);

    /**
     * Invoke HTTP OPTIONS method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> options(InvocationCallback<T> callback);

    // TRACE

    /**
     * Invoke HTTP TRACE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @return invocation response {@link Future future}.
     */
    Future<Response> trace();

    /**
     * Invoke HTTP TRACE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> trace(Class<T> responseType);

    /**
     * Invoke HTTP TRACE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> trace(GenericType<T> responseType);

    /**
     * Invoke HTTP TRACE method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> trace(InvocationCallback<T> callback);

    // ARBITRARY METHOD

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param name method name.
     * @return invocation response {@link Future future}.
     */
    Future<Response> method(String name);

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param name         method name.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> method(String name, Class<T> responseType);

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param name         method name.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> method(String name, GenericType<T> responseType);

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param name     method name.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> method(String name, InvocationCallback<T> callback);

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param name   method name.
     * @param entity request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *               Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *               {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *               the entity variant information.
     * @return invocation response {@link Future future}.
     */
    Future<Response> method(String name, Entity<?> entity);

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          response entity type.
     * @param name         method name.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType Java type the response entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> method(String name, Entity<?> entity, Class<T> responseType);

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the specified response type is not {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>          generic response entity type.
     * @param name         method name.
     * @param entity       request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                     Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                     {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                     the entity variant information.
     * @param responseType representation of a generic Java type the response
     *                     entity will be converted to.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> method(String name, Entity<?> entity, GenericType<T> responseType);

    /**
     * Invoke an arbitrary method for the current request asynchronously.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned
     * {@code Future} instance may throw an {@link java.util.concurrent.ExecutionException}
     * that wraps either a {@link javax.ws.rs.ProcessingException} thrown in case of an invocation processing
     * failure or a {@link WebApplicationException} or one of its subclasses thrown in case the
     * received response status code is not {@link javax.ws.rs.core.Response.Status.Family#SUCCESSFUL
     * successful} and the generic type of the supplied response callback is not
     * {@link javax.ws.rs.core.Response}.
     * In case a processing of a properly received response fails, the wrapped processing exception
     * will be of {@link ResponseProcessingException} type and will contain the {@link Response}
     * instance whose processing has failed.
     *
     * @param <T>      generic response entity type.
     * @param name     method name.
     * @param entity   request entity, including it's full {@link javax.ws.rs.core.Variant} information.
     *                 Any variant-related HTTP headers previously set (namely {@code Content-Type},
     *                 {@code Content-Language} and {@code Content-Encoding}) will be overwritten using
     *                 the entity variant information.
     * @param callback asynchronous invocation callback.
     * @return invocation response {@link Future future}.
     */
    <T> Future<T> method(String name, Entity<?> entity, InvocationCallback<T> callback);
}
