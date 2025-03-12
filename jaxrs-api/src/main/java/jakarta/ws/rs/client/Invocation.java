/*
 * Copyright (c) 2011, 2019 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.client;

import java.util.Locale;
import java.util.concurrent.Future;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

/**
 * A client request invocation.
 *
 * An invocation is a request that has been prepared and is ready for execution. Invocations provide a generic (command)
 * interface that enables a separation of concerns between the creator and the submitter. In particular, the submitter
 * does not need to know how the invocation was prepared, but only how it should be executed (synchronously or
 * asynchronously) and when.
 *
 * @author Marek Potociar
 * @author Santiago Pericas-Geertsen
 * @see Invocation.Builder Invocation.Builder
 */
public interface Invocation {

    /**
     * A client request invocation builder.
     *
     * The builder, obtained via a call to one of the {@code request(...)} methods on a {@link WebTarget resource target},
     * provides methods for preparing a client request invocation. Once the request is prepared the invocation builder can
     * be either used to build an {@link Invocation} with a generic execution interface:
     *
     * <pre>
     * Client client = ClientBuilder.newClient();
     * WebTarget resourceTarget = client.target("http://examples.jaxrs.com/");
     *
     * // Build a HTTP GET request that accepts "text/plain" response type
     * // and contains a custom HTTP header entry "Foo: bar".
     * Invocation invocation = resourceTarget.request("text/plain")
     *         .header("Foo", "bar").buildGet();
     *
     * // Invoke the request using generic interface
     * String response = invocation.invoke(String.class);
     * </pre>
     *
     * Alternatively, one of the inherited {@link SyncInvoker synchronous invocation methods} can be used to invoke the
     * prepared request and return the server response in a single step, e.g.:
     *
     * <pre>
     * Client client = ClientBuilder.newClient();
     * WebTarget resourceTarget = client.target("http://examples.jaxrs.com/");
     *
     * // Build and invoke the get request in a single step
     * String response = resourceTarget.request("text/plain")
     *         .header("Foo", "bar").get(String.class);
     * </pre>
     *
     * Once the request is fully prepared for invoking, switching to an {@link AsyncInvoker asynchronous invocation} mode is
     * possible by calling the {@link #async() } method on the builder, e.g.:
     *
     * <pre>
     * Client client = ClientBuilder.newClient();
     * WebTarget resourceTarget = client.target("http://examples.jaxrs.com/");
     *
     * // Build and invoke the get request asynchronously in a single step
     * Future&lt;String&gt; response = resourceTarget.request("text/plain")
     *         .header("Foo", "bar").async().get(String.class);
     * </pre>
     */
    public static interface Builder extends SyncInvoker {

        // Invocation builder methods

        /**
         * Build a request invocation using an arbitrary request method name.
         *
         * @param method request method name.
         * @return invocation encapsulating the built request.
         */
        public Invocation build(String method);

        /**
         * Build a request invocation using an arbitrary request method name and request entity.
         *
         * @param method request method name.
         * @param entity request entity, including its full {@link jakarta.ws.rs.core.Variant} information. Any variant-related
         * HTTP headers previously set (namely {@code Content-Type}, {@code Content-Language} and {@code Content-Encoding}) will
         * be overwritten using the entity variant information.
         * @return invocation encapsulating the built request.
         */
        public Invocation build(String method, Entity<?> entity);

        /**
         * Build a GET request invocation.
         *
         * @return invocation encapsulating the built GET request.
         */
        public Invocation buildGet();

        /**
         * Build a DELETE request invocation.
         *
         * @return invocation encapsulating the built DELETE request.
         */
        public Invocation buildDelete();

        /**
         * Build a POST request invocation.
         *
         * @param entity request entity, including its full {@link jakarta.ws.rs.core.Variant} information. Any variant-related
         * HTTP headers previously set (namely {@code Content-Type}, {@code Content-Language} and {@code Content-Encoding}) will
         * be overwritten using the entity variant information.
         * @return invocation encapsulating the built POST request.
         */
        public Invocation buildPost(Entity<?> entity);

        /**
         * Build a PUT request invocation.
         *
         * @param entity request entity, including its full {@link jakarta.ws.rs.core.Variant} information. Any variant-related
         * HTTP headers previously set (namely {@code Content-Type}, {@code Content-Language} and {@code Content-Encoding}) will
         * be overwritten using the entity variant information.
         * @return invocation encapsulating the built PUT request.
         */
        public Invocation buildPut(Entity<?> entity);

        /**
         * Access the asynchronous uniform request invocation interface to asynchronously invoke the built request.
         *
         * @return asynchronous uniform request invocation interface.
         */
        public AsyncInvoker async();

        /**
         * Add the accepted response media types.
         *
         * @param mediaTypes accepted response media types.
         * @return the updated builder.
         */
        public Builder accept(String... mediaTypes);

        /**
         * Add the accepted response media types.
         *
         * @param mediaTypes accepted response media types.
         * @return the updated builder.
         */
        public Builder accept(MediaType... mediaTypes);

        /**
         * Add acceptable languages.
         *
         * @param locales an array of the acceptable languages.
         * @return the updated builder.
         */
        public Builder acceptLanguage(Locale... locales);

        /**
         * Add acceptable languages.
         *
         * @param locales an array of the acceptable languages.
         * @return the updated builder.
         */
        public Builder acceptLanguage(String... locales);

        /**
         * Add acceptable encodings.
         *
         * @param encodings an array of the acceptable encodings.
         * @return the updated builder.
         */
        public Builder acceptEncoding(String... encodings);

        /**
         * Add a cookie to be set.
         *
         * @param cookie to be set.
         * @return the updated builder.
         */
        public Builder cookie(Cookie cookie);

        /**
         * Add a cookie to be set.
         *
         * @param name the name of the cookie.
         * @param value the value of the cookie.
         * @return the updated builder.
         */
        public Builder cookie(String name, String value);

        /**
         * Set the cache control data of the message.
         *
         * @param cacheControl the cache control directives, if {@code null} any existing cache control directives will be
         * removed.
         * @return the updated builder.
         */
        public Builder cacheControl(CacheControl cacheControl);

        /**
         * Add an arbitrary header.
         *
         * @param name the name of the header
         * @param value the value of the header, the header will be serialized using a
         * {@link jakarta.ws.rs.ext.RuntimeDelegate.HeaderDelegate} if one is available via
         * {@link jakarta.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)} for the class of {@code value} or using
         * its {@code toString} method if a header delegate is not available. If {@code value} is {@code null} then all current
         * headers of the same name will be removed.
         * @return the updated builder.
         */
        public Builder header(String name, Object value);

        /**
         * Replaces all existing headers with the newly supplied headers.
         *
         * @param headers new headers to be set, if {@code null} all existing headers will be removed.
         * @return the updated builder.
         */
        public Builder headers(MultivaluedMap<String, Object> headers);

        /**
         * Set a new property in the context of a request represented by this invocation builder.
         * <p>
         * The property is available for a later retrieval via {@link ClientRequestContext#getProperty(String)} or
         * {@link jakarta.ws.rs.ext.InterceptorContext#getProperty(String)}. If a property with a given name is already set in the
         * request context, the existing value of the property will be updated. Setting a {@code null} value into a property
         * effectively removes the property from the request property bag.
         * </p>
         *
         * @param name property name.
         * @param value (new) property value. {@code null} value removes the property with the given name.
         * @return the updated builder.
         * @see Invocation#property(String, Object)
         */
        public Builder property(String name, Object value);

        /**
         * Access the default reactive invoker based on {@link java.util.concurrent.CompletionStage}.
         *
         * @return default reactive invoker instance.
         * @since 2.1
         * @see jakarta.ws.rs.client.Invocation.Builder#rx(Class)
         */
        public CompletionStageRxInvoker rx();

        /**
         * Access a reactive invoker based on a {@link RxInvoker} subclass provider. Note that corresponding
         * {@link RxInvokerProvider} must be registered in the client runtime.
         * <p>
         * This method is an extension point for JAX-RS implementations to support other types representing asynchronous
         * computations.
         *
         * @param <T> generic invoker type.
         * @param clazz {@link RxInvoker} subclass.
         * @return reactive invoker instance.
         * @throws IllegalStateException when provider for given class is not registered.
         * @see jakarta.ws.rs.client.Client#register(Class)
         * @since 2.1
         */
        public <T extends RxInvoker> T rx(Class<T> clazz);

    }

    /**
     * Set a new property in the context of a request represented by this invocation.
     * <p>
     * The property is available for a later retrieval via {@link ClientRequestContext#getProperty(String)} or
     * {@link jakarta.ws.rs.ext.InterceptorContext#getProperty(String)}. If a property with a given name is already set in the
     * request context, the existing value of the property will be updated. Setting a {@code null} value into a property
     * effectively removes the property from the request property bag.
     * </p>
     *
     * @param name property name.
     * @param value (new) property value. {@code null} value removes the property with the given name.
     * @return the updated invocation.
     * @see Invocation.Builder#property(String, Object)
     */
    public Invocation property(String name, Object value);

    /**
     * Synchronously invoke the request and receive a response back.
     *
     * @return {@link Response response} object as a result of the request invocation.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter or during
     * conversion of the response entity data to an instance of a particular Java type).
     * @throws ProcessingException in case the request processing or subsequent I/O operation fails.
     */
    public Response invoke();

    /**
     * Synchronously invoke the request and receive a response of the specified type back.
     *
     * @param <T> response type
     * @param responseType Java type the response should be converted into.
     * @return response object of the specified type as a result of the request invocation.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter or during
     * conversion of the response entity data to an instance of a particular Java type).
     * @throws ProcessingException in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException in case the response status code of the response returned by the server is not
     * {@link jakarta.ws.rs.core.Response.Status.Family#SUCCESSFUL successful} and the specified response type is not
     * {@link jakarta.ws.rs.core.Response}.
     */
    public <T> T invoke(Class<T> responseType);

    /**
     * Synchronously invoke the request and receive a response of the specified generic type back.
     *
     * @param <T> generic response type
     * @param responseType type literal representing a generic Java type the response should be converted into.
     * @return response object of the specified generic type as a result of the request invocation.
     * @throws ResponseProcessingException in case processing of a received HTTP response fails (e.g. in a filter or during
     * conversion of the response entity data to an instance of a particular Java type).
     * @throws ProcessingException in case the request processing or subsequent I/O operation fails.
     * @throws WebApplicationException in case the response status code of the response returned by the server is not
     * {@link jakarta.ws.rs.core.Response.Status.Family#SUCCESSFUL successful}.
     */
    public <T> T invoke(GenericType<T> responseType);

    /**
     * Submit the request for an asynchronous invocation and receive a future response back.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned {@code Future} instance may
     * throw an {@link java.util.concurrent.ExecutionException} that wraps a {@link ProcessingException} thrown in case of
     * an invocation processing failure. In case a processing of a properly received response fails, the wrapped processing
     * exception will be of {@link ResponseProcessingException} type and will contain the {@link Response} instance whose
     * processing has failed.
     * </p>
     *
     * @return future {@link Response response} object as a result of the request invocation.
     */
    public Future<Response> submit();

    /**
     * Submit the request for an asynchronous invocation and receive a future response of the specified type back.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned {@code Future} instance may
     * throw an {@link java.util.concurrent.ExecutionException} that wraps either a {@link ProcessingException} thrown in
     * case of an invocation processing failure or a {@link WebApplicationException} or one of its subclasses thrown in case
     * the received response status code is not {@link jakarta.ws.rs.core.Response.Status.Family#SUCCESSFUL successful} and
     * the specified response type is not {@link jakarta.ws.rs.core.Response}. In case a processing of a properly received
     * response fails, the wrapped processing exception will be of {@link ResponseProcessingException} type and will contain
     * the {@link Response} instance whose processing has failed.
     * </p>
     *
     * @param <T> response type
     * @param responseType Java type the response should be converted into.
     * @return future response object of the specified type as a result of the request invocation.
     */
    public <T> Future<T> submit(Class<T> responseType);

    /**
     * Submit the request for an asynchronous invocation and receive a future response of the specified generic type back.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned {@code Future} instance may
     * throw an {@link java.util.concurrent.ExecutionException} that wraps either a {@link ProcessingException} thrown in
     * case of an invocation processing failure or a {@link WebApplicationException} or one of its subclasses thrown in case
     * the received response status code is not {@link jakarta.ws.rs.core.Response.Status.Family#SUCCESSFUL successful} and
     * the specified response type is not {@link jakarta.ws.rs.core.Response}. In case a processing of a properly received
     * response fails, the wrapped processing exception will be of {@link ResponseProcessingException} type and will contain
     * the {@link Response} instance whose processing has failed.
     * </p>
     *
     * @param <T> generic response type
     * @param responseType type literal representing a generic Java type the response should be converted into.
     * @return future response object of the specified generic type as a result of the request invocation.
     */
    public <T> Future<T> submit(GenericType<T> responseType);

    /**
     * Submit the request for an asynchronous invocation and register an {@link InvocationCallback} to process the future
     * result of the invocation.
     * <p>
     * Note that calling the {@link java.util.concurrent.Future#get()} method on the returned {@code Future} instance may
     * throw an {@link java.util.concurrent.ExecutionException} that wraps either a {@link ProcessingException} thrown in
     * case of an invocation processing failure or a {@link WebApplicationException} or one of its subclasses thrown in case
     * the received response status code is not {@link jakarta.ws.rs.core.Response.Status.Family#SUCCESSFUL successful} and
     * the generic type of the supplied response callback is not {@link jakarta.ws.rs.core.Response}. In case a processing of
     * a properly received response fails, the wrapped processing exception will be of {@link ResponseProcessingException}
     * type and will contain the {@link Response} instance whose processing has failed.
     * </p>
     *
     * @param <T> response type
     * @param callback invocation callback for asynchronous processing of the request invocation result.
     * @return future response object of the specified type as a result of the request invocation.
     */
    public <T> Future<T> submit(InvocationCallback<T> callback);
}
