package bgu.spl.mics.application.messages;
import java.util.List;

import bgu.spl.mics.application.objects.TrackedObject;
import bgu.spl.mics.Event;

public class TrackedObjectsEvent implements Event<TrackedObject[]> {
    List<TrackedObject> objects;

    public TrackedObjectsEvent(List<TrackedObject> objects){
        this.objects=objects;
    }
    
    public List<TrackedObject> getTrackedObjectsEvent(){
        return objects;
    }
}
