import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;

import bgu.spl.mics.Future;

public class futureTest {
    
    @Test
    public void testFutureInitialState() {
        Future<String> future = new Future<>();
        assertFalse(future.isDone());
        assertNull(future.get(1, TimeUnit.SECONDS));
    }

    @Test
    public void testFutureResolve() {
        Future<String> future = new Future<>();
        future.resolve("Test result");
        assertTrue(future.isDone());
        assertEquals("Test result", future.get());
    }

    @Test
    public void testFutureGetWithTimeout() {
        Future<String> future = new Future<>();
        assertNull(future.get(1000, TimeUnit.MILLISECONDS));
        future.resolve("Test result");
        assertEquals("Test result", future.get(1000, TimeUnit.MILLISECONDS));
    }

    @Test
    public void testFutureMultipleResolves() {
        Future<String> future = new Future<>();
        future.resolve("First result");
        future.resolve("Second result");
        assertEquals("First result", future.get());
    }
}
