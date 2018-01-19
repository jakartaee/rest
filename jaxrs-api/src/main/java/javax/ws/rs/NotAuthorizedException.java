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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;
import static javax.ws.rs.core.HttpHeaders.WWW_AUTHENTICATE;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * A runtime exception indicating request authorization failure caused by one of the following
 * scenarios:
 * <ul>
 * <li>
 * a client did not send the required authorization credentials to access the requested resource,
 * i.e. {@link javax.ws.rs.core.HttpHeaders#AUTHORIZATION Authorization} HTTP header is missing
 * in the request,
 * </li>
 * <li>
 * or - in case the request already contains the HTTP {@code Authorization} header - then
 * the exception indicates that authorization has been refused for the credentials contained
 * in the request header.
 * </li>
 * </ul>
 *
 * @author Marek Potociar
 * @since 2.0
 */
public class NotAuthorizedException extends ClientErrorException {

    private static final long serialVersionUID = -3156040750581929702L;

    private transient List<Object> challenges;

    /**
     * Construct a new "not authorized" exception.
     *
     * @param challenge      authorization challenge applicable to the resource requested
     *                       by the client.
     * @param moreChallenges additional authorization challenge applicable to the
     *                       requested resource.
     * @throws NullPointerException in case the {@code challenge} parameter is {@code null}.
     */
    public NotAuthorizedException(Object challenge, Object... moreChallenges) {
        super(createUnauthorizedResponse(challenge, moreChallenges));
        this.challenges = cacheChallenges(challenge, moreChallenges);
    }

    /**
     * Construct a new "not authorized" exception.
     *
     * @param message        the detail message (which is saved for later retrieval
     *                       by the {@link #getMessage()} method).
     * @param challenge      authorization challenge applicable to the resource requested
     *                       by the client.
     * @param moreChallenges additional authorization challenge applicable to the
     *                       requested resource.
     * @throws NullPointerException in case the {@code challenge} parameter is {@code null}.
     */
    public NotAuthorizedException(String message, Object challenge, Object... moreChallenges) {
        super(message, createUnauthorizedResponse(challenge, moreChallenges));
        this.challenges = cacheChallenges(challenge, moreChallenges);
    }

    /**
     * Construct a new "not authorized" exception.
     *
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 401}.
     */
    public NotAuthorizedException(Response response) {
        super(validate(response, UNAUTHORIZED));
    }

    /**
     * Construct a new "not authorized" exception.
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 401}.
     */
    public NotAuthorizedException(String message, Response response) {
        super(message, validate(response, UNAUTHORIZED));
    }

    /**
     * Construct a new "not authorized" exception.
     *
     * @param cause          the underlying cause of the exception.
     * @param challenge      authorization challenge applicable to the requested resource.
     * @param moreChallenges additional authorization challenge applicable to the
     *                       requested resource.
     */
    public NotAuthorizedException(Throwable cause, Object challenge, Object... moreChallenges) {
        super(createUnauthorizedResponse(challenge, moreChallenges), cause);
        this.challenges = cacheChallenges(challenge, moreChallenges);
    }

    /**
     * Construct a new "not authorized" exception.
     *
     * @param message        the detail message (which is saved for later retrieval
     *                       by the {@link #getMessage()} method).
     * @param cause          the underlying cause of the exception.
     * @param challenge      authorization challenge applicable to the requested resource.
     * @param moreChallenges additional authorization challenge applicable to the
     *                       requested resource.
     */
    public NotAuthorizedException(String message, Throwable cause, Object challenge, Object... moreChallenges) {
        super(message, createUnauthorizedResponse(challenge, moreChallenges), cause);
        this.challenges = cacheChallenges(challenge, moreChallenges);
    }

    /**
     * Construct a new "not authorized" exception.
     *
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 401}.
     */
    public NotAuthorizedException(Response response, Throwable cause) {
        super(validate(response, UNAUTHORIZED), cause);
    }

    /**
     * Construct a new "not authorized" exception.
     *
     * @param message  the detail message (which is saved for later retrieval
     *                 by the {@link #getMessage()} method).
     * @param response error response.
     * @param cause    the underlying cause of the exception.
     * @throws IllegalArgumentException in case the status code set in the response
     *                                  is not HTTP {@code 401}.
     */
    public NotAuthorizedException(String message, Response response, Throwable cause) {
        super(message, validate(response, UNAUTHORIZED), cause);
    }

    /**
     * Get the list of authorization challenges associated with the exception and
     * applicable to the resource requested by the client.
     *
     * @return list of authorization challenges applicable to the resource requested
     *         by the client.
     */
    public List<Object> getChallenges() {
        if (challenges == null) {
            this.challenges = getResponse().getHeaders().get(WWW_AUTHENTICATE);
        }
        return challenges;
    }

    private static Response createUnauthorizedResponse(Object challenge, Object[] otherChallenges) {
        if (challenge == null) {
            throw new NullPointerException("Primary challenge parameter must not be null.");
        }

        Response.ResponseBuilder builder = Response.status(UNAUTHORIZED)
                .header(WWW_AUTHENTICATE, challenge);

        if (otherChallenges != null) {
            for (Object oc : otherChallenges) {
                builder.header(WWW_AUTHENTICATE, oc);
            }
        }

        return builder.build();
    }

    private static List<Object> cacheChallenges(Object challenge, Object[] moreChallenges) {
        List<Object> temp = new ArrayList<Object>(1 + ((moreChallenges == null) ? 0 : moreChallenges.length));
        temp.add(challenge);
        if (moreChallenges != null) {
            temp.addAll(Arrays.asList(moreChallenges));
        }
        return Collections.unmodifiableList(temp);
    }
}
