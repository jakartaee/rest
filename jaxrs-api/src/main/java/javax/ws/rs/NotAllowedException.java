/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * A runtime exception indicating a client requesting a resource method that is
 * {@link javax.ws.rs.core.Response.Status#METHOD_NOT_ALLOWED not allowed}.
 *
 * @author Marek Potociar
 * @since 2.0
 */
public class NotAllowedException extends ClientErrorException {

    private static final long serialVersionUID = -586776054369626119L;

    /**
     * Construct a new method not allowed exception.
     *
     * @param allowed     allowed request method.
     * @param moreAllowed more allowed request methods.
     * @throws NullPointerException in case the allowed method is {@code null}.
     */
    public NotAllowedException(String allowed, String... moreAllowed) {
        super(validateAllow(createNotAllowedResponse(allowed, moreAllowed)));
    }

    /**
     * Construct a new method not allowed exception.
     *
     * @param message     the detail message (which is saved for later retrieval
     *                    by the {@link #getMessage()} method).
     * @param allowed     allowed request method.
     * @param moreAllowed more allowed request methods.
     * @throws NullPointerException in case the allowed method is {@code null}.
     */
    public NotAllowedException(String message, String allowed, String... moreAllowed) {
        super(message, validateAllow(createNotAllowedResponse(allowed, moreAllowed)));
    }

    private static Response createNotAllowedResponse(String allowed, String... moreAllowed) {
        if (allowed == null) {
            throw new NullPointerException("No allowed method specified.");
        }
        Set<String> methods;
        if (moreAllowed != null && moreAllowed.length > 0) {
            methods = new HashSet<String>(moreAllowed.length + 1);
            methods.add(allowed);
            Collections.addAll(methods, moreAllowed);
        } else {
            methods = Collections.singleton(allowed);
        }

        return Response.status(Response.Status.METHOD_NOT_ALLOWED).allow(methods).build();
    }

    /**
     * Construct a new method not allowed exception.
     * <p>
     * Note that this constructor does not validate the presence of HTTP
     * {@code Allow} header. I.e. it is possible
     * to use the constructor to create a client-side exception instance
     * even for an invalid HTTP {@code 405} response content returned from a server.
     * </p>
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 405}.
     */
    public NotAllowedException(Response response) {
        super(validate(response, Response.Status.METHOD_NOT_ALLOWED));
    }

    /**
     * Construct a new method not allowed exception.
     * <p>
     * Note that this constructor does not validate the presence of HTTP
     * {@code Allow} header. I.e. it is possible
     * to use the constructor to create a client-side exception instance
     * even for an invalid HTTP {@code 405} response content returned from a server.
     * </p>
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 405}.
     */
    public NotAllowedException(String message, Response response) {
        super(message, validate(response, Response.Status.METHOD_NOT_ALLOWED));
    }

    /**
     * Construct a new method not allowed exception.
     *
     * @param cause          the underlying cause of the exception.
     * @param allowedMethods allowed request methods.
     * @throws IllegalArgumentException in case the allowed methods varargs are {@code null}.
     */
    public NotAllowedException(Throwable cause, String... allowedMethods) {
        super(validateAllow(Response.status(Response.Status.METHOD_NOT_ALLOWED).allow(allowedMethods).build()), cause);
    }

    /**
     * Construct a new method not allowed exception.
     *
     * @param message        the detail message (which is saved for later retrieval
     *                       by the {@link #getMessage()} method).
     * @param cause          the underlying cause of the exception.
     * @param allowedMethods allowed request methods.
     * @throws IllegalArgumentException in case the allowed methods varargs are {@code null}.
     */
    public NotAllowedException(String message, Throwable cause, String... allowedMethods) {
        super(message, validateAllow(Response.status(Response.Status.METHOD_NOT_ALLOWED).allow(allowedMethods).build()), cause);
    }

    /**
     * Construct a new method not allowed exception.
     *
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 405} or does not contain
     *                                  an HTTP {@code Allow} header.
     */
    public NotAllowedException(Response response, Throwable cause) {
        super(validateAllow(validate(response, Response.Status.METHOD_NOT_ALLOWED)), cause);
    }

    /**
     * Construct a new method not allowed exception.
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 405} or does not contain
     *                                  an HTTP {@code Allow} header.
     */
    public NotAllowedException(String message, Response response, Throwable cause) {
        super(message, validateAllow(validate(response, Response.Status.METHOD_NOT_ALLOWED)), cause);
    }

    private static Response validateAllow(final Response response) {
        if (!response.getHeaders().containsKey(HttpHeaders.ALLOW)) {
            throw new IllegalArgumentException("Response does not contain required 'Allow' HTTP header.");
        }

        return response;
    }
}
