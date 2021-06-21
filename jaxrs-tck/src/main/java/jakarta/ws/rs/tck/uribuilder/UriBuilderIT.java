/*
 * Copyright (c) 2020 Markus Karg. All rights reserved.
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

package jakarta.ws.rs.tck.uribuilder;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriBuilderException;

/**
 * Compliance Test for URI Builder API of Jakarta REST API
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
@Timeout(value = 1, unit = HOURS)
public final class UriBuilderIT {

    /**
     * Verifies that a valid instance can be created from scratch.
     * 
     * @throws ExecutionException   if the instance didn't boot correctly
     * @throws InterruptedException if the test took much longer than usually
     *                              expected
     */
    @Test
    public final void shouldBuildValidInstanceFromScratch()
            throws InterruptedException, ExecutionException {
        // given
        final UriBuilder uriBuilder = UriBuilder.newInstance();

        // when
        final URI uri = uriBuilder.scheme("scheme").host("host").port(1).build();

        // then
        assertThat(uri.toString(), is("scheme://host:1"));
    }

    /**
     * Verifies that no invalid URI can be created from scratch.
     * 
     * @throws ExecutionException   if the instance didn't boot correctly
     * @throws InterruptedException if the test took much longer than usually
     *                              expected
     */
    @Test
    public final void shouldNotBuildInvalidUriFromScratch() throws InterruptedException, ExecutionException {
        // given
        final UriBuilder uriBuilder = UriBuilder.newInstance();

        // then
        assertThrows(UriBuilderException.class, /* when */ uriBuilder::build);
    }
}
