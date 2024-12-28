import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bgu.spl.mics.*;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;

public class messageBusTest {

    @Test
    public void testSubscribeEvent() {
        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        MicroService microService = new MicroService("TestService") {
            @Override
            protected void initialize() {
                // Initialization logic
            }
        };
        messageBus.register(microService);
        messageBus.subscribeEvent(ExampleEvent.class, microService);
        // Add assertions to verify the subscription
    }

    @Test
    public void testSubscribeBroadcast() {
        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        MicroService microService = new MicroService("TestService") {
            @Override
            protected void initialize() {
                // Initialization logic
            }
        };
        messageBus.register(microService);
        messageBus.subscribeBroadcast(ExampleBroadcast.class, microService);
        // Add assertions to verify the subscription
    }

    @Test
    public void testSendEvent() {
        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        MicroService microService = new MicroService("TestService") {
            @Override
            protected void initialize() {
                // Initialization logic
            }
        };
        messageBus.register(microService);
        messageBus.subscribeEvent(ExampleEvent.class, microService);
        Event<String> event = new ExampleEvent("example");
        Future<String> future = messageBus.sendEvent(event);
        assertNotNull(future);
        // Add assertions to verify the event was sent and future is resolved
    }

    @Test
    public void testSendBroadcast() {
        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        MicroService microService = new MicroService("TestService") {
            @Override
            protected void initialize() {
                // Initialization logic
            }
        };
        messageBus.register(microService);
        messageBus.subscribeBroadcast(ExampleBroadcast.class, microService);
        Broadcast broadcast = new ExampleBroadcast("example");
        messageBus.sendBroadcast(broadcast);
        // Add assertions to verify the broadcast was sent
    }

    @Test
    public void testComplete() {
        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        MicroService microService = new MicroService("TestService") {
            @Override
            protected void initialize() {
                // Initialization logic
            }
        };
        messageBus.register(microService);
        Event<String> event = new ExampleEvent("example");
        Future<String> future = messageBus.sendEvent(event);
        messageBus.complete(event, "Test result");
        assertTrue(future.isDone());
        assertEquals(null, future.get());
    }
}
