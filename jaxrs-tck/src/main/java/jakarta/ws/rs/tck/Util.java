/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jakarta.ws.rs.tck;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class.getName());
    public static final int HTTP_PORT = Integer.getInteger("http.port");

    public static String getUrlFor(Object test, String...paths) {
        return "http://localhost:" + HTTP_PORT + "/" + getContextRoot(test.getClass()) + "/" +
            Arrays.stream(paths).collect(Collectors.joining("/"));
    }

    public static String getWARName(Class<?> testClass) {
        String warName = getContextRoot(testClass) + ".war";
        LOG.finest(warName);
        return warName;
    }

    public static String getContextRoot(Class<?> testClass) {
        String fullPkgName = testClass.getPackage().getName();
        return fullPkgName.contains(".") ? fullPkgName.substring(fullPkgName.lastIndexOf(".") + 1) : fullPkgName;
    }
}
