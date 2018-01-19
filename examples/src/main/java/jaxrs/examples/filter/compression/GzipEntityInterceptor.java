/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package jaxrs.examples.filter.compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import javax.annotation.Priority;

/**
 * Example of GZIP entity interceptor.
 *
 * @author Santiago Pericas-Geertsen
 */
@Provider
@Gzipped
@Priority(Priorities.ENTITY_CODER)
public class GzipEntityInterceptor implements ReaderInterceptor, WriterInterceptor {

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext ctx) throws IOException {
        if (!gzipEncoded(ctx)) {
            return ctx.proceed();
        } else {
            InputStream old = ctx.getInputStream();
            ctx.setInputStream(new GZIPInputStream(old));
            try {
                return ctx.proceed();
            } finally {
                ctx.setInputStream(old);
            }
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext ctx) throws IOException {
        if (!acceptsGzip(ctx)) {
            ctx.proceed();
        } else {
            OutputStream old = ctx.getOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(old);
            ctx.setOutputStream(gzipOutputStream);
            try {
                ctx.proceed();
            } finally {
                gzipOutputStream.finish();
                ctx.setOutputStream(old);
            }
        }
    }

    private boolean acceptsGzip(WriterInterceptorContext ctx) {
        // implementation goes here
        return true;
    }

    private boolean gzipEncoded(ReaderInterceptorContext ctx) {
        // implementation goes here
        return true;
    }
}
