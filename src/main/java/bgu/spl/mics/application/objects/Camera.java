package bgu.spl.mics.application.objects;

/**
 * Represents a camera sensor on the robot.
 * Responsible for detecting objects in the environment.
 */
public class Camera {
    public int id;
    public int frequency;
    public STATUS status;
    public StampedDetectedObjects detectedObjectsList;

    public Camera(){}; // For serialization
    
    public Camera(int id, int frequency) {
        this.id=id;
        this.frequency=frequency;
        this.status= STATUS.UP;
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
    
    public String toString() {
        return "Camera " + id + " is " + status + " and has detected the following objects: " + detectedObjectsList;
    }
}