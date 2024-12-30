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