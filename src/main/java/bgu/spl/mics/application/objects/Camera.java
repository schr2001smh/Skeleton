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
    
    public Camera(int id, int frequency) {
        this.id=id;
        this.frequency=frequency;
        this.status= STATUS.UP;
    }
}