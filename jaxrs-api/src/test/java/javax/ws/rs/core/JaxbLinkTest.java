/*
 * Copyright (c) 2014, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.core;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for Link marshalling and unmarshalling via JAXB.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public class JaxbLinkTest {

    @Test
    public void testSerializationOfJaxbLink() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Link.JaxbLink.class);
        final Marshaller marshaller = jaxbContext.createMarshaller();
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Map<QName, Object> expectedParams = new HashMap<QName, Object>();
        final QName qName = new QName("http://example.ns", "foo");
        expectedParams.put(qName, "test");
        final URI expectedUri = URI.create("/foo/bar");
        Link.JaxbLink expected = new Link.JaxbLink(expectedUri, expectedParams);

        final StringWriter writer = new StringWriter();

        JAXBElement<Link.JaxbLink> jaxbLinkJAXBElement =
                new JAXBElement<Link.JaxbLink>(new QName("", "link"), Link.JaxbLink.class, expected);
        marshaller.marshal(jaxbLinkJAXBElement, writer);

        final Link.JaxbLink actual = unmarshaller.unmarshal(new StreamSource(
                new StringReader(writer.toString())), Link.JaxbLink.class).getValue();

        assertEquals("Unmarshalled JaxbLink instance not equal to the marshalled one.", expected, actual);
        assertEquals("Unmarshalled JaxbLink instance URI not equal to original.", expectedUri, actual.getUri());
        assertEquals("Unmarshalled JaxbLink instance params not equal to original.", expectedParams, actual.getParams());
    }

    @Test
    public void testEqualsHashCode() throws Exception {
        Link.JaxbLink first = new Link.JaxbLink();
        Link.JaxbLink second = new Link.JaxbLink();

        // trigger lazy initialization on first
        first.getParams();

        assertThat(first, equalTo(second));
        assertThat(second, equalTo(first));
        assertThat(first.hashCode(), equalTo(second.hashCode()));
    }
}
