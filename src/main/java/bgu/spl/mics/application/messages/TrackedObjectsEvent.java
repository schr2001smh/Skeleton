package bgu.spl.mics.application.messages;
import bgu.spl.mics.application.objects.TrackedObject;
import bgu.spl.mics.Event;

public class TrackedObjectsEvent implements Event<TrackedObject[]> {
    TrackedObject[] objects;

    public TrackedObjectsEvent(TrackedObject[] objects){
        this.objects=objects;
    }
    
    public TrackedObject[] getTrackedObjectsEvent(){
        return objects;
    }
}
