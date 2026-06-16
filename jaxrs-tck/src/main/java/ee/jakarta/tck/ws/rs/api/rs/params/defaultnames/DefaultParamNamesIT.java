/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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

package ee.jakarta.tck.ws.rs.api.rs.params.defaultnames;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import ee.jakarta.tck.ws.rs.common.JAXRSCommonClient;
import ee.jakarta.tck.ws.rs.lib.util.TestUtil;
import jakarta.ws.rs.core.Response;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
/**
 * Tests for optional parameter names in Jakarta REST parameter binding annotations.
 * Verifies that @PathParam, @QueryParam, @MatrixParam, @FormParam, @HeaderParam,
 * and @CookieParam can infer parameter names when no explicit value is provided.
 * 
 * @see <a href="https://github.com/jakartaee/rest/issues/579">Issue #579</a>
 */
public class DefaultParamNamesIT extends JAXRSCommonClient {

    private static final long serialVersionUID = 1L;

    @BeforeEach
    void logStartTest(TestInfo testInfo) {
        TestUtil.logMsg("STARTING TEST : " + testInfo.getDisplayName());
    }

    @AfterEach
    void logFinishTest(TestInfo testInfo) {
        TestUtil.logMsg("FINISHED TEST : " + testInfo.getDisplayName());
    }

    /*
     * @testName: pathParamDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that @PathParam without an explicit value uses the
     * parameter name from the method signature when the -parameters compiler flag
     * is enabled.
     */
    @Test
    public void pathParamDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/path/john/doe"));
        setProperty(Property.SEARCH_STRING, "john doe");
        invoke();
    }

    /*
     * @testName: queryParamDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that @QueryParam without an explicit value uses the
     * parameter name from the method signature when the -parameters compiler flag
     * is enabled.
     */
    @Test
    public void queryParamDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/query?search=test&filter=active"));
        setProperty(Property.SEARCH_STRING, "search=test filter=active");
        invoke();
    }

    /*
     * @testName: headerParamDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that @HeaderParam without an explicit value uses the
     * parameter name from the method signature when the -parameters compiler flag
     * is enabled.
     */
    @Test
    public void headerParamDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/header"));
        setProperty(Property.REQUEST_HEADERS, "Authorization: Bearer token123");
        setProperty(Property.SEARCH_STRING, "Bearer token123");
        invoke();
    }

    /*
     * @testName: matrixParamDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that @MatrixParam without an explicit value uses the
     * parameter name from the method signature when the -parameters compiler flag
     * is enabled.
     */
    @Test
    public void matrixParamDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/matrix/item;color=red;size=large"));
        setProperty(Property.SEARCH_STRING, "color=red size=large");
        invoke();
    }

    /*
     * @testName: cookieParamDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that @CookieParam without an explicit value uses the
     * parameter name from the method signature when the -parameters compiler flag
     * is enabled.
     */
    @Test
    public void cookieParamDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/cookie"));
        setProperty(Property.REQUEST_HEADERS, "Cookie: sessionId=abc123");
        setProperty(Property.SEARCH_STRING, "abc123");
        invoke();
    }

    /*
     * @testName: formParamDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that @FormParam without an explicit value uses the
     * parameter name from the method signature when the -parameters compiler flag
     * is enabled.
     */
    @Test
    public void formParamDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "resource/form"));
        setProperty(Property.REQUEST_HEADERS, "Content-Type: application/x-www-form-urlencoded");
        setProperty(Property.CONTENT, "username=john&password=secret");
        setProperty(Property.SEARCH_STRING, "username=john password=secret");
        invoke();
    }

    /*
     * @testName: mixedParamDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that a mix of explicit and inferred parameter names
     * works correctly, demonstrating backward compatibility.
     */
    @Test
    public void mixedParamDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/mixed/123?page=1"));
        setProperty(Property.REQUEST_HEADERS, "User-Agent: TestClient/1.0");
        setProperty(Property.SEARCH_STRING, "id=123 page=1 userAgent=TestClient/1.0");
        invoke();
    }

    /*
     * @testName: fieldInjectionDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that field injection with default parameter names
     * works without the -parameters compiler flag, as field names are always
     * available at runtime.
     */
    @Test
    public void fieldInjectionDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resource/field?userId=user123&page=5"));
        setProperty(Property.SEARCH_STRING, "userId=user123 page=5");
        invoke();
    }

    /*
     * @testName: allParamTypesDefaultNameTest
     * 
     * @assertion_ids: JAXRS:SPEC:XXX;
     * 
     * @test_Strategy: Verify that all six parameter binding annotations work
     * together with default parameter names in a single resource method.
     */
    @Test
    public void allParamTypesDefaultNameTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "resource/all/item123;color=blue?filter=active"));
        setProperty(Property.REQUEST_HEADERS, 
            "Authorization: Bearer token456\r\n" +
            "Cookie: sessionId=xyz789\r\n" +
            "Content-Type: application/x-www-form-urlencoded");
        setProperty(Property.CONTENT, "username=alice&action=update");
        setProperty(Property.SEARCH_STRING, 
            "pathId=item123 color=blue filter=active " +
            "authorization=Bearer token456 sessionId=xyz789 " +
            "username=alice action=update");
        invoke();
    }
}