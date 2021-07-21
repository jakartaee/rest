/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jakarta.ws.rs.tck.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.jupiter.api.Test;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.ws.rs.tck.Util;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class BasicResourceTest {
    private static final Logger LOG = Logger.getLogger(BasicResourceTest.class.getName());

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, Util.getWARName(BasicResourceTest.class))
                         .addClass(BasicApp.class)
                         .addClass(BasicResource.class);
    }

    @Test
    public void get() throws Exception {
        LOG.finest("STARTING TEST: get");
        String uri = Util.getUrlFor(this, "app", "basic", "123");
        LOG.finest(() -> "uri = " + uri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(uri))
                                         .version(Version.HTTP_1_1)
                                         .build();
        String response = client.send(request, BodyHandlers.ofString()).body();
        assertEquals("GET basic 123", response);
        LOG.finest("SUCCESS!!!");
    }

    @Test
    public void post() throws Exception {
        LOG.finest("STARTING TEST: post");
        String uri = Util.getUrlFor(this, "app", "basic");
        LOG.finest(() -> "uri = " + uri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(uri))
                                         .POST(BodyPublishers.ofString("abc"))
                                         .version(Version.HTTP_1_1)
                                         .build();
        String response = client.send(request, BodyHandlers.ofString()).body();
        assertEquals("POST basic abc", response);
        LOG.finest("SUCCESS!!!");
    }
}
