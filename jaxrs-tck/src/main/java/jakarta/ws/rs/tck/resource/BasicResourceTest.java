package jakarta.ws.rs.tck.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.jupiter.api.Test;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.ws.rs.tck.AbstractTest;
import jakarta.ws.rs.tck.Util;

@ExtendWith(ArquillianExtension.class)
public class BasicResourceTest extends AbstractTest {

    private OkHttpClient client = new OkHttpClient();

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "basic.war")
                         .addClass(AbstractTest.class)
                         .addClass(BasicApp.class)
                         .addClass(BasicResource.class);
    }

    @Test
    public void test_get() throws Exception {
        System.out.println("ANDY STARTING TEST!!!");
        try {
        Request request = new Request.Builder().url(Util.getUrlFor(this, "app", "basic", "123")).build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals("GET basic 123", response.body().string());
        }
        System.out.println("ANDY SUCCESS!!!");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Exception(t);
        }
    }

    @Test
    public void test_post() throws Exception {
        System.out.println("ANDY STARTING TEST!!!");
        try {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "abc");
            Request request = new Request.Builder().url(Util.getUrlFor(this, "app", "basic")).post(body).build();

            try (Response response = client.newCall(request).execute()) {
                assertEquals("POST basic abc", response.body().string());
            }
            System.out.println("ANDY SUCCESS!!!");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Exception(t);
        }
    }

    @Test
    public void just_fail() {
        assertEquals("abc", new String("def"));
    }

    @Override
    public String getContextRoot() {
        return "basic";
    }
}
