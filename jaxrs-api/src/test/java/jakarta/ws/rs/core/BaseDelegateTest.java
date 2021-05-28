
package jakarta.ws.rs.core;

import jakarta.ws.rs.ext.RuntimeDelegate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

/**
 * Base class to set a delegate stub before tests are executed.
 *
 * @author Santiago Pericas-Geertsen
 */
public class BaseDelegateTest {

    @BeforeAll
    public static void setUp() {
        RuntimeDelegate.setInstance(mock(RuntimeDelegate.class, Mockito.RETURNS_DEEP_STUBS));
    }

    @AfterAll
    public static void tearDown() {
        RuntimeDelegate.setInstance(null);
    }
}
