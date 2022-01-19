/*
 * Copyright (c) 2007, 2021 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */

package ee.jakarta.tck.ws.rs.lib.util;

/**
 * This exception is thrown by the init method of the TestUtil class, if
 * anything goes wrong while establishing a socket connection back to the
 * harness host.
 * 
 * @author Kyle Grucci
 */
public class RemoteLoggingInitException extends java.lang.Exception {
  /**
   * creates a RemoteLoggingInitException
   */
  public RemoteLoggingInitException() {
    super();
  }

  /**
   * creates a RemoteLoggingInitException with a message
   * 
   * @param s
   *          the message
   */
  public RemoteLoggingInitException(String s) {
    super(s);
  }
}
