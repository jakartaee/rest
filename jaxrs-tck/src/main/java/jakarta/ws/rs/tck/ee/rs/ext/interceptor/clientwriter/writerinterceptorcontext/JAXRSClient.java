/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.clientwriter.writerinterceptorcontext;

import com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.TemplateInterceptorBody;
import com.sun.ts.tests.jaxrs.common.client.TextCaser;
import com.sun.ts.tests.jaxrs.common.provider.StringBeanEntityProvider;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.clientwriter.WriterClient;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.ContextOperation;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.OnWriteExceptionThrowingStringBean;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.ProceedException;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.WriterInterceptorOne;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.WriterInterceptorTwo;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSClient extends WriterClient<ContextOperation> {

  private static final long serialVersionUID = 2500912584762173255L;

  public JAXRSClient() {
    setContextRoot(
        "/jaxrs_ee_rs_ext_interceptor_clientwriter_writerinterceptorcontext_web/resource");
    addProviders();
  }

  /**
   * Entry point for different-VM execution. It should delegate to method
   * run(String[], PrintWriter, PrintWriter), and this method should not contain
   * any test configuration.
   */
  public static void main(String[] args) {
    JAXRSClient theTests = new JAXRSClient();
    theTests.run(args);
  }

  /* Run test */
  /*
   * @testName: getEntityTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:933; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Get object to be written as HTTP entity.
   *
   * WriterInterceptor.aroundWriteTo
   */
  public void getEntityTest() throws Fault {
    setOperationAndEntity(ContextOperation.GETENTITY);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.ENTITY);
    invoke();
  }

  /*
   * @testName: getHeadersOperationOnlyTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:934; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Get mutable map of HTTP headers.
   *
   * WriterInterceptor.aroundWriteTo
   */
  public void getHeadersOperationOnlyTest() throws Fault {
    setOperationAndEntity(ContextOperation.GETHEADERS);
    setProperty(Property.SEARCH_STRING_IGNORE_CASE,
        TemplateInterceptorBody.OPERATION);
    invoke();
  }

  /*
   * @testName: getHeadersTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:934; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Get mutable map of HTTP headers.
   *
   * WriterInterceptor.aroundWriteTo
   */
  public void getHeadersTest() throws Fault {
    Property p = Property.UNORDERED_SEARCH_STRING;
    setOperationAndEntity(ContextOperation.GETHEADERS);
    setProperty(p, TemplateInterceptorBody.OPERATION);
    setTextCaser(TextCaser.LOWER);
    for (int i = 0; i != 5; i++) {
      addHeader(TemplateInterceptorBody.PROPERTY + i, "any");
      setProperty(p, TemplateInterceptorBody.PROPERTY + i);
    }
    invoke();
  }

  /*
   * @testName: getHeadersIsMutableTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:934; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Get mutable map of HTTP headers.
   *
   * WriterInterceptor.aroundWriteTo
   */
  public void getHeadersIsMutableTest() throws Fault {
    setOperationAndEntity(ContextOperation.GETHEADERSISMUTABLE);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.PROPERTY);
    invoke();
  }

  /*
   * @testName: getOutputStreamTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:935; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Get the output stream for the object to be written.
   * 
   * WriterInterceptor.aroundWriteTo
   */
  public void getOutputStreamTest() throws Fault {
    Property p = Property.UNORDERED_SEARCH_STRING;
    setOperationAndEntity(ContextOperation.GETOUTPUTSTREAM);
    setProperty(p, TemplateInterceptorBody.ENTITY);
    setProperty(p, TemplateInterceptorBody.NULL);
    invoke();
  }

  /*
   * @testName: proceedThrowsIOExceptionTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:936; JAXRS:JAVADOC:937; JAXRS:JAVADOC:930;
   * JAXRS:JAVADOC:931;
   * 
   * @test_Strategy: Proceed to the next interceptor in the chain.
   * Throws:IOException - if an IO exception arises.
   * 
   * proceed is actually called in every clientwriter.writerinterceptorcontext
   * test
   *
   * WriterInterceptor.aroundWriteTo
   * 
   * WriterInterceptor.aroundWriteTo throws IOException
   */
  public void proceedThrowsIOExceptionTest() throws Fault {
    setOperationAndEntity(ContextOperation.PROCEEDTHROWSIOEXCEPTION);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.IOE);
    invoke();
  }

  /*
   * @testName: proceedThrowsWebApplicationExceptionTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:936; JAXRS:JAVADOC:1009; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Proceed to the next interceptor in the chain.
   * Throws:WebApplicationException - thrown by the wrapped {@code
   * MessageBodyWriter.writeTo} method.
   * 
   * proceed is actually called in every clientwriter.writerinterceptorcontext
   * test
   *
   * WriterInterceptor.aroundWriteTo
   */
  public void proceedThrowsWebApplicationExceptionTest() throws Fault {
    addProvider(StringBeanEntityProvider.class);
    addHeader(TemplateInterceptorBody.OPERATION,
        ContextOperation.PROCEEDTHROWSWEBAPPEXCEPTION.name());
    setRequestContentEntity(
        new OnWriteExceptionThrowingStringBean(TemplateInterceptorBody.ENTITY));
    try {
      invoke();
    } catch (Exception e) {
      ProceedException p = assertCause(e, ProceedException.class,
          "Proceed did not throw exception");
      assertContains(p.getMessage(), TemplateInterceptorBody.WAE,
          "Unexpected message received", p.getMessage());
      logMsg(p.getMessage());
    }
  }

  /*
   * @testName: setEntityTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:938; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Update object to be written as HTTP entity.
   *
   * WriterInterceptor.aroundWriteTo
   */
  public void setEntityTest() throws Fault {
    setOperationAndEntity(ContextOperation.SETENTITY);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.OPERATION);
    invoke();
  }

  /*
   * @testName: setOutputStreamTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:939; JAXRS:JAVADOC:930;
   * 
   * @test_Strategy: Update the output stream for the object to be written.
   *
   * WriterInterceptor.aroundWriteTo
   */
  public void setOutputStreamTest() throws Fault {
    setOperationAndEntity(ContextOperation.SETOUTPUTSTREAM);
    setProperty(Property.SEARCH_STRING,
        TemplateInterceptorBody.ENTITY.replace('t', 'x'));
    invoke();
  }

  // /////////////////////////////////////////////////////////////////////
  @Override
  protected void addProviders() {
    addProvider(new WriterInterceptorTwo());
    addProvider(WriterInterceptorOne.class);
  }
}
