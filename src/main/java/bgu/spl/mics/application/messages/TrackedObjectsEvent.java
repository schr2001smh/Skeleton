package bgu.spl.mics.application.messages;
import java.util.List;

import bgu.spl.mics.application.objects.TrackedObject;
import bgu.spl.mics.Event;

public class TrackedObjectsEvent implements Event<TrackedObject[]> {
    List<TrackedObject> objects;
    int frequency;

    public TrackedObjectsEvent(List<TrackedObject> objects,int frequency){
        this.objects=objects;
        this.frequency=frequency;
    }

    public int getFrequency()
    {
        return frequency;
    }
    
    public List<TrackedObject> getTrackedObjectsEvent(){
        return objects;
    }
}
