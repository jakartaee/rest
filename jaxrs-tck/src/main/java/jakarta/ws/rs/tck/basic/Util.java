/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jakarta.ws.rs.tck.basic;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Util {
    public static final int HTTP_PORT = Integer.getInteger("http.port", 8010);

    public static String getUrlFor(String contextRoot, String...paths) {
        return "http://localhost:" + HTTP_PORT + "/" + contextRoot + "/" +
            Arrays.stream(paths).collect(Collectors.joining("/"));
    }
}