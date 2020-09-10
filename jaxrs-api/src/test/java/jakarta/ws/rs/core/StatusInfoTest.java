/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.core;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Reminder/regression test for StatusInfo#equals
 *
 * Currently #equals compares instances directly via default implementation. This might surprise users,
 * since the expected behavior is a comparison of the status codes.
 *
 * So a comparison of the properties would be the best way, but without {@code reasonPhrase}, because
 * this <i>can</i> be implementation specific.
 *
 * @See <a href="https://github.com/eclipse-ee4j/jaxrs-api/issues/792">Issue #792</a>
 *
 * @author Christoph Kappel (unexist at subforge.org)
 */
public class StatusInfoTest {

    @Test
    public void reminderThatEqualsShouldCompareStatusCodes() {
        Response response = new ResponseImpl(
                Response.Status.OK.getStatusCode(), "That was okay");

        assertThat(Response.Status.OK,
                equalTo(Response.Status.OK));
        assertThat(Response.Status.OK,
                not(equalTo(response.getStatusInfo())));
    }

    /* Stub implementation to get access to Response.Status */
    class ResponseImpl extends Response {
        private StatusType status;

        ResponseImpl(int statusCode, String reasonPhrase) {
            this.status = new Response.StatusType() {

                public Status.Family getFamily() {
                    return Response.Status.Family.familyOf(statusCode);
                }

                public String getReasonPhrase() {
                    return reasonPhrase;
                }

                public int getStatusCode() {
                    return statusCode;
                }
            };
        }

        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public StatusType getStatusInfo() {
            return this.status;
        }

        @Override
        public Object getEntity() {
            return null;
        }

        @Override
        public <T> T readEntity(Class<T> entityType) {
            return null;
        }

        @Override
        public <T> T readEntity(GenericType<T> entityType) {
            return null;
        }

        @Override
        public <T> T readEntity(Class<T> entityType, Annotation[] annotations) {
            return null;
        }

        @Override
        public <T> T readEntity(GenericType<T> entityType, Annotation[] annotations) {
            return null;
        }

        @Override
        public boolean hasEntity() {
            return false;
        }

        @Override
        public boolean bufferEntity() {
            return false;
        }

        @Override
        public void close() {

        }

        @Override
        public MediaType getMediaType() {
            return null;
        }

        @Override
        public Locale getLanguage() {
            return null;
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public Set<String> getAllowedMethods() {
            return null;
        }

        @Override
        public Map<String, NewCookie> getCookies() {
            return null;
        }

        @Override
        public EntityTag getEntityTag() {
            return null;
        }

        @Override
        public Date getDate() {
            return null;
        }

        @Override
        public Date getLastModified() {
            return null;
        }

        @Override
        public URI getLocation() {
            return null;
        }

        @Override
        public Set<Link> getLinks() {
            return null;
        }

        @Override
        public boolean hasLink(String relation) {
            return false;
        }

        @Override
        public Link getLink(String relation) {
            return null;
        }

        @Override
        public Link.Builder getLinkBuilder(String relation) {
            return null;
        }

        @Override
        public MultivaluedMap<String, Object> getMetadata() {
            return null;
        }

        @Override
        public MultivaluedMap<String, String> getStringHeaders() {
            return null;
        }

        @Override
        public String getHeaderString(String name) {
            return null;
        }
    }
}
