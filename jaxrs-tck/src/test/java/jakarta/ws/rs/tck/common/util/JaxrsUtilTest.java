/*
 * Copyright (c) 2022 Markus Karg. All rights reserved.
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

package jakarta.ws.rs.tck.common.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import static jakarta.ws.rs.tck.common.util.JaxrsUtil.unprivilegedPort;

import java.io.IOException;

import org.junit.jupiter.api.Test;

final class JaxrsUtilTest {

    @Test
    final void shouldReturnPortLarger1024() throws IOException {
        // when
        final int chosenPort = unprivilegedPort();

        // then
        assertThat(chosenPort, is(greaterThan(1024)));
    }

}
