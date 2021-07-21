/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jakarta.ws.rs.tck.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private OkHttpClient client = new OkHttpClient();

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, Util.getWARName(BasicResourceTest.class))
                         .addClass(BasicApp.class)
                         .addClass(BasicResource.class);
    }

    @Test
    public void get() throws Exception {
        LOG.finest("STARTING TEST: get");
        try {
        Request request = new Request.Builder().url(Util.getUrlFor(this, "app", "basic", "123")).build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals("GET basic 123", response.body().string());
        }
        LOG.finest("SUCCESS!!!");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Exception(t);
        }
    }

    @Test
    public void post() throws Exception {
        LOG.finest("STARTING TEST: post");
        try {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "abc");
            Request request = new Request.Builder().url(Util.getUrlFor(this, "app", "basic")).post(body).build();

            try (Response response = client.newCall(request).execute()) {
                assertEquals("POST basic abc", response.body().string());
            }
            LOG.finest("SUCCESS!!!");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Exception(t);
        }
    }
}
