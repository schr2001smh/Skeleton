package bgu.spl.mics.example.services;

import java.util.concurrent.TimeUnit;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;

public class ExampleMessageSenderService extends MicroService {

    private boolean broadcast;

    public ExampleMessageSenderService(String name, String[] args) {
        super(name);

        if (args.length != 1 || !args[0].matches("broadcast|event")) {
            throw new IllegalArgumentException("expecting a single argument: broadcast/event");
        }

        this.broadcast = args[0].equals("broadcast");
    }

    @Override
    protected void initialize() {
        if (broadcast) {
            sendBroadcast(new ExampleBroadcast(getName()));
            terminate();
        } else {
            Future<String> futureObject = (Future<String>)sendEvent(new ExampleEvent(getName()));
            if (futureObject != null) {
            	String resolved = futureObject.get(100, TimeUnit.MILLISECONDS);
            }
            else { }
            terminate();
        }
    }

}
