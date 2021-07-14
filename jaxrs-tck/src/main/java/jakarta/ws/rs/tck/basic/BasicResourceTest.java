/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jakarta.ws.rs.tck.basic;

import static org.testng.Assert.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

public class BasicResourceTest extends Arquillian {
    private final static Logger LOG = Logger.getLogger(BasicResourceTest.class.getName());

    private final static String CONTEXT_ROOT = "basic";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "basic.war")
                         .addPackage(BasicApp.class.getPackage());
    }

    @Test
    public void get() throws Exception {
        String uri = Util.getUrlFor(CONTEXT_ROOT, "app", "basic", "123");
        LOG.finest(() -> "uri = " + uri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(uri))
                                         .version(Version.HTTP_1_1)
                                         .build();
        String response = client.send(request, BodyHandlers.ofString()).body();
        assertEquals(response, "GET basic 123");
    }

    @Test
    public void post() throws Exception {
        String uri = Util.getUrlFor(CONTEXT_ROOT, "app", "basic");
        LOG.finest(() -> "uri = " + uri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(uri))
                                         .POST(BodyPublishers.ofString("abc"))
                                         .version(Version.HTTP_1_1)
                                         .build();
        String response = client.send(request, BodyHandlers.ofString()).body();
        assertEquals(response, "POST basic abc");
    }
}