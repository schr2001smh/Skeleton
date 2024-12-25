package bgu.spl.mics;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only one public method (in addition to getters which can be public solely for unit testing) may be added to this class
 * All other methods and members you add the class must be private.
 */
public class MessageBusImpl implements MessageBus {
	private HashMap<Class<? extends Event<?>>, List<MicroService>> eventMap;
	private HashMap<Class<? extends Broadcast>, List<MicroService>> broadcastMap;
	private HashMap<MicroService, List<Message>> microserviceMap;
	private int roundRobinCounter=0;
	private int counter=0;
	private static class SingletonHolder {
		private static final MessageBusImpl instance = new MessageBusImpl();
	}

	private MessageBusImpl() {
		eventMap = new HashMap<>();
		broadcastMap= new HashMap<>();
	}

	public static MessageBusImpl getInstance() {
		return SingletonHolder.instance;
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if (!eventMap.containsKey(type))
			eventMap.put(type, new ArrayList<MicroService>());
		eventMap.get(type).add(m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if (!broadcastMap.containsKey(type))
			broadcastMap.put(type, new ArrayList<MicroService>());
		broadcastMap.get(type).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		broadcastMap.get(b.getClass()).forEach(m -> {
			microserviceMap.get(m).add(b);
		});

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		counter=0;
		if (roundRobinCounter == eventMap.get(e.getClass()).size())
			roundRobinCounter = 0;
		for (MicroService m : eventMap.get(e.getClass())) {
			if (counter == roundRobinCounter) {
				microserviceMap.get(m).add(e);
				roundRobinCounter++;
				break;
			} else {
				counter++;
			}
		}
		return null;
	}

	@Override
	public void register(MicroService m) {
		microserviceMap.put(m, new ArrayList<Message>());
	}

	@Override
	public void unregister(MicroService m) {
		// need to spread the list to other microservices
		eventMap.values().forEach(l -> l.remove(m));
		broadcastMap.values().forEach(l -> l.remove(m));
		microserviceMap.get(m).forEach(msg -> {
			if (msg instanceof Event)
			sendEvent((Event<?>) msg);
		});
		microserviceMap.remove(m);

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
