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
}
