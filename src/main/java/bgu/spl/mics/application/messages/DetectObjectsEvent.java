package bgu.spl.mics.application.messages;
import bgu.spl.mics.application.objects.StampedDetectedObjects;
import bgu.spl.mics.Event;

public class DetectObjectsEvent implements Event<StampedDetectedObjects> {
    StampedDetectedObjects objects;
    
    public DetectObjectsEvent(StampedDetectedObjects objects){
        this.objects=objects;
    }

    public StampedDetectedObjects getStampedDetectObjectsEvent(){
        objects.setTime(objects.getTime());
        return objects;
    }   
}
