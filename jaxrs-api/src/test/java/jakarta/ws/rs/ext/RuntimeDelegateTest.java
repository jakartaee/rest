package jakarta.ws.rs.ext;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * {@link jakarta.ws.rs.ext.RuntimeDelegate} unit tests.
 *
 * @author Niklas Mehner
 */
public class RuntimeDelegateTest {

    @Test
    public void testGetInstanceFailsIfNoImplementationAvailable() {
        try {
            RuntimeDelegate.getInstance();
            fail();
        } catch (RuntimeException e) {
            assertEquals(
                    "java.lang.ClassNotFoundException: Provider for jakarta.ws.rs.ext.RuntimeDelegate cannot be found",
                    e.getMessage());
        }
    }
}
