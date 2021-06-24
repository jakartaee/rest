package jakarta.ws.rs.tck;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Util {
    public static final int HTTP_PORT = Integer.getInteger("http.port");

    public static String getUrlFor(AbstractTest test, String...paths) {
        return "http://localhost:" + HTTP_PORT + "/" + test.getContextRoot() + "/" +
            Arrays.stream(paths).collect(Collectors.joining("/"));
    }
}
