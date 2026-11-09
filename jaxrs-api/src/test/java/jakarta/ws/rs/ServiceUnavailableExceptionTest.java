/*
 * Copyright (c) 2026 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs;

import static jakarta.ws.rs.core.HttpHeaders.RETRY_AFTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.RuntimeDelegate;

/**
 * Unit tests for {@link ServiceUnavailableException}, including a proof that
 * {@code interval * 1000} wraps and that {@code getRetryTime} no longer returns that wrapped instant.
 */
public class ServiceUnavailableExceptionTest {

    @BeforeAll
    public static void setUp() {
        RuntimeDelegate.setInstance(mock(RuntimeDelegate.class, Mockito.RETURNS_DEEP_STUBS));
    }

    @AfterAll
    public static void tearDown() {
        RuntimeDelegate.setInstance(null);
    }

    @Test
    public void getRetryTimeAddsIntervalSecondsAsMillis() {
        final ServiceUnavailableException ex = exceptionWithRetryAfter("120");
        final Date requestTime = new Date(1_700_000_000_000L);

        final Date retryAt = ex.getRetryTime(requestTime);

        assertEquals(requestTime.getTime() + 120_000L, retryAt.getTime());
    }

    /**
     * Proof of the old bug: {@code interval * 1000} wraps once the header is past
     * {@code Long.MAX_VALUE / 1000} seconds, and adding that product to the request
     * time lands before the request. The fixed method must not return that instant.
     */
    @Test
    public void getRetryTimeDoesNotReturnTheWrappedProduct() {
        final long intervalSeconds = Long.MAX_VALUE / 1000L + 1L;
        final long wrappedDelta = intervalSeconds * 1000L;
        final Date requestTime = new Date(1_700_000_000_000L);
        final long wrappedInstant = requestTime.getTime() + wrappedDelta;

        assertTrue(wrappedDelta < 0L, "old interval * 1000 must wrap negative for this header");
        assertTrue(wrappedInstant < requestTime.getTime(), "old formula must land before the request");

        final ServiceUnavailableException ex = exceptionWithRetryAfter(Long.toString(intervalSeconds));
        final Date retryAt = ex.getRetryTime(requestTime);

        assertEquals(Long.MAX_VALUE, retryAt.getTime());
        assertTrue(retryAt.getTime() > requestTime.getTime());
    }

    /**
     * The product itself still fits, but adding it to a normal request time overflows.
     */
    @Test
    public void getRetryTimeDoesNotWrapWhenAddingALargeButSafeProduct() {
        final long intervalSeconds = Long.MAX_VALUE / 1000L;
        final long deltaMillis = intervalSeconds * 1000L;
        final Date requestTime = new Date(1_700_000_000_000L);

        assertTrue(deltaMillis > 0L);
        assertTrue(requestTime.getTime() + deltaMillis < 0L, "old addition wraps for this pair");

        final Date retryAt = exceptionWithRetryAfter(Long.toString(intervalSeconds)).getRetryTime(requestTime);

        assertEquals(Long.MAX_VALUE, retryAt.getTime());
    }

    @Test
    public void getRetryTimeSaturatesOnLargeNegativeInterval() {
        final long intervalSeconds = Long.MIN_VALUE / 500L;
        final ServiceUnavailableException ex = exceptionWithRetryAfter(Long.toString(intervalSeconds));
        final Date requestTime = new Date(1_700_000_000_000L);

        final Date retryAt = ex.getRetryTime(requestTime);

        assertEquals(Long.MIN_VALUE, retryAt.getTime());
    }

    @Test
    public void getRetryTimeReturnsNullWhenHeaderMissing() {
        final ServiceUnavailableException ex = exceptionWithRetryAfter(null);
        assertNull(ex.getRetryTime(new Date()));
    }

    @Test
    public void getRetryTimeRejectsNullRequestTime() {
        final ServiceUnavailableException ex = exceptionWithRetryAfter("10");
        assertThrows(NullPointerException.class, () -> ex.getRetryTime(null));
    }

    private static ServiceUnavailableException exceptionWithRetryAfter(final String retryAfter) {
        final Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(Response.Status.SERVICE_UNAVAILABLE.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Response.Status.SERVICE_UNAVAILABLE);
        when(response.getHeaderString(RETRY_AFTER)).thenReturn(retryAfter);
        return new ServiceUnavailableException(response);
    }
}
