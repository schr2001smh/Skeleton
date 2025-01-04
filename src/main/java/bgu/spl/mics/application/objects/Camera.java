package bgu.spl.mics.application.objects;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a camera sensor on the robot.
 * Responsible for detecting objects in the environment.
 */
public class Camera {
    private int id;
    private int frequency;
    private STATUS status;
    public List<StampedDetectedObjects> detectedObjectsList = new ArrayList<>();
    private String camera_key;

    public List<StampedDetectedObjects> objectsDuringTime(int lastTime, int currentTime){
        List<StampedDetectedObjects> objectsDuringTime = new ArrayList<>();
        for (StampedDetectedObjects stampedDetectedObjects : detectedObjectsList) {
            if (stampedDetectedObjects.getTime() > lastTime && stampedDetectedObjects.getTime() <= currentTime&&!objectsDuringTime.contains(stampedDetectedObjects)) {
                objectsDuringTime.add(stampedDetectedObjects);
            }
        }
        return objectsDuringTime;
    }
    public Camera(){}; // For serialization

    public void setstampedDetectedObjects(List<StampedDetectedObjects> stampedDetectedObjects){
        this.detectedObjectsList=stampedDetectedObjects;
        
    }
    
    public Camera(int id, int frequency) {
        this.id=id;
        this.frequency=frequency;
        this.status= STATUS.UP;
    }

    public Camera(int id, int frequency, STATUS status, List<StampedDetectedObjects> detectedObjectsList) {
        this.id=id;
        this.frequency=frequency;
        this.status=status;
        this.detectedObjectsList=detectedObjectsList;
    }
    public  STATUS getStatus(){
        return status;
    }

    public void setStatus(STATUS status){
        this.status=status;
    }

    public int getId(){
        return id;
    }

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency=frequency;
    }

    public void setId(int id){
        this.id=id;
    }
    public List<StampedDetectedObjects> getstaStampedDetectedObjects(){
        return detectedObjectsList;
    }
    
    @Override
    public String toString() {
        return "Camera{" +
               "id=" + id +
               ", frequency=" + frequency +
               ", camera_key='" + id + '\'' +
               '}';
    }
}
// "id": 1,
// "frequency": 0,
// "camera_key": "camera1"
// public String toString() {
//     return "time: " + time + '\'' +
//      ", x: " + x + '\''+
//      ", y: " + y + '\''+
//      ", yaw: " + yaw
//     ;
// }