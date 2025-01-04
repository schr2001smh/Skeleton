package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

public class DetectObjectsEvent implements Event<StampedDetectedObjects> {
    StampedDetectedObjects objects;
    
    public DetectObjectsEvent(StampedDetectedObjects objects){
        this.objects=objects;
    }

    public StampedDetectedObjects getStampedDetectObjects(){
        // System.out.println(objects);
        return objects;
    }   
}
