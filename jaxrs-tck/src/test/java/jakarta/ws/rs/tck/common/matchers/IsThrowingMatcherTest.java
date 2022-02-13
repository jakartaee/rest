/*
 * Copyright (c) 2022 Jeremias Weber. All rights reserved.
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

package jakarta.ws.rs.tck.common.matchers;

import org.junit.jupiter.api.Test;

import static ee.jakarta.tck.ws.rs.common.matchers.IsThrowingMatcher.isThrowing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

final class IsThrowingMatcherTest {

    @Test 
    final void shouldMatchIfIsInstance() {
        assertThat(
                // when
                () -> { throw new ChildException(); }, 
                // then
                isThrowing(ParentException.class)
        );
    }

    @Test
    final void shouldNotMatchIfIsNotInstance() {
        assertThat(
                // when
                () -> { throw new ParentException(); }, 
                // then
                not(isThrowing(ChildException.class))
        );
    }

    @Test
    final void shouldNotMatchIfNothingThrown() {
        assertThat(
                // when
                () -> {},
                // then
                not(isThrowing(Exception.class))
        );
    }

    private static class ParentException extends RuntimeException { }

    private static final class ChildException extends ParentException { }
    
}
