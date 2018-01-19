/*
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
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

import java.util.concurrent.ExecutorService;

/**
 * {@link RxInvoker} provider.
 * <p>
 * {@code RxInvokerProvider} must be registered in the client runtime using {@link Client#register(Class)}.
 * It provides a way to plug-in support for other reactive implementations,
 * see {@link Invocation.Builder#rx(Class)}.
 *
 * @param <T> {@code RxInvoker} subclass type.
 * @author Pavel Bucek
 * @author Santiago Pericas-Geertsen
 * @since 2.1
 */
public interface RxInvokerProvider<T extends RxInvoker> {

    /**
     * Determine if this is a provider for the given {@link RxInvoker} subclass.
     *
     * @param clazz {@code RxInvoker} subclass.
     * @return {@code true} when this provider provides given {@code RxInvoker} subclass, {@code false} otherwise.
     */
    public boolean isProviderFor(Class<?> clazz);

    /**
     * Get {@link RxInvoker} implementation instance.
     * <p>
     * The returned instance has to be thread safe.
     *
     * @param syncInvoker     {@code SyncInvoker} used to execute current request.
     * @param executorService executor service, which should be used for executing reactive callbacks invocations.
     *                        It can be {@code null}; in that case it's up to the implementation to choose the best
     *                        {@code ExecutorService} in given environment.
     * @return instance of the {@code RxInvoker} subclass.
     * @see ClientBuilder#executorService(ExecutorService)
     */
    public T getRxInvoker(SyncInvoker syncInvoker, ExecutorService executorService);

}
