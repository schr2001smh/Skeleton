package bgu.spl.mics;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only one public method (in addition to getters which can be public solely for unit testing) may be added to this class
 * All other methods and members you add the class must be private.
 */
public class MessageBusImpl implements MessageBus {
	private ConcurrentHashMap<Class<? extends Event<?>>, List<MicroService>> eventMap=new ConcurrentHashMap<>();
	private ConcurrentHashMap<Class<? extends Broadcast>, List<MicroService>> broadcastMap=new ConcurrentHashMap<>();
	private ConcurrentHashMap<MicroService, List<Message>> microserviceMap= new ConcurrentHashMap<>();
	private ConcurrentHashMap<Event<?>, Future<?>> futureMap= new ConcurrentHashMap<>();
	private ConcurrentHashMap<Class<? extends Event<?>>, Integer> roundRobinMap= new ConcurrentHashMap<>();
	private int roundRobinCounter=0;
	private int counter=0;
	
	private static class SingletonHolder {
		private static final MessageBusImpl instance = new MessageBusImpl();
	}

	private MessageBusImpl() {
		eventMap = new ConcurrentHashMap<>();
		broadcastMap= new ConcurrentHashMap<>();
	}

	public static MessageBusImpl getInstance() {
		return SingletonHolder.instance;
	}
	
	@Override
	public synchronized <T> void  subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if (!eventMap.containsKey(type)) {
			eventMap.put(type, new ArrayList<MicroService>());
			roundRobinMap.put(type, 0);
		}
		eventMap.get(type).add(m);
	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if (!broadcastMap.containsKey(type))
			broadcastMap.put(type, new ArrayList<MicroService>());
		broadcastMap.get(type).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> future = (Future<T>) futureMap.get(e);
		if (future != null) {
			future.resolve(result);
		}
		

	}

	@Override
	public  void sendBroadcast(Broadcast b) {
		if (!broadcastMap.containsKey(b.getClass()))
			return;
		broadcastMap.get(b.getClass()).forEach(m -> {
			microserviceMap.get(m).add(b);
		});

	}

	
	@Override
	public   <T> Future<T> sendEvent(Event<T> e) {
		if(roundRobinMap.get((Class<? extends Event<?>>) e.getClass())==null)
			roundRobinMap.put((Class<? extends Event<?>>) e.getClass(), 0);
		roundRobinCounter=roundRobinMap.get(e.getClass());
		counter=0;
		Future<T> future = new Future<>();
		if (!eventMap.containsKey(e.getClass())) {
			future.resolve(null);
			return future;
		}
		
		if (roundRobinCounter == eventMap.get(e.getClass()).size())
			roundRobinCounter = 0;
		for (MicroService m : eventMap.get(e.getClass())) {
			if (counter == roundRobinCounter) {
				microserviceMap.get(m).add(e);
				futureMap.put(e, future);
				roundRobinCounter++;
				roundRobinMap.put((Class<? extends Event<?>>) e.getClass(), roundRobinCounter);
				break;
			} else {
				counter++;
			}
		}
		return future;
	}

	@Override
	public synchronized void register(MicroService m) {
		if (!microserviceMap.containsKey(m))
			microserviceMap.put(m, new ArrayList<Message>());
	}

	@Override
	public void unregister(MicroService m) {
		// need to spread the list to other microservices
		eventMap.values().forEach(l -> l.remove(m));
		broadcastMap.values().forEach(l -> l.remove(m));
		microserviceMap.remove(m);

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		while (microserviceMap.get(m).size() == 0){}
		return microserviceMap.get(m).remove(0);
	}

	

}
