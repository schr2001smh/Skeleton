package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents objects detected by the camera at a specific timestamp.
 * Includes the time of detection and a list of detected objects.
 */
public class StampedDetectedObjects {
    private int time;
    private List<DetectedObject> detectedObjects;

    public StampedDetectedObjects(int time) {
        this.time = time;
        this.detectedObjects = new ArrayList<>();
    } 

    public StampedDetectedObjects(int time, List<DetectedObject> detectedObjects) {
        this.time = time;
        this.detectedObjects = detectedObjects;
    }
    
    public boolean isContainingError(){
        for (DetectedObject detectedObject : detectedObjects) {
            if (detectedObject.getId().equals("ERROR")) {
                return true;
            }
        }
        return false;
    }
    public void addDetectedObject(DetectedObject detectedObject) {
        detectedObjects.add(detectedObject);
    }
    public StampedDetectedObjects(){}; // For serialization

    public void setTime(int time){
        this.time=time;
    }

    public int getTime(){
        return time;
    }
    
    public List<DetectedObject> getDetectedObjects(){
        return detectedObjects;
    }
    
    public void setDetectedObjects(List<DetectedObject> detectedObjects){
        this.detectedObjects=detectedObjects;
    }

    @Override
    public String toString() {
        return 
                "time=" + time +
                ", detectedObjects=" + detectedObjects +
                '}';
    }
    
}
