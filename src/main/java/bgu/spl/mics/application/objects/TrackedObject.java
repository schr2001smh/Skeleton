package bgu.spl.mics.application.objects;

/**
 * Represents an object tracked by the LiDAR.
 * This object includes information about the tracked object's ID, description, 
 * time of tracking, and coordinates in the environment.
 */
public class TrackedObject {
    public String id;
    public int time;
    public String description;
    public CloudPoint[] coordinates;

    public TrackedObject(String id, int time, String description) {
        this.id = id;
        this.time = time;
        this.description = description;
        this.coordinates = new CloudPoint[0];
    }

    public void addCoordinate(CloudPoint coordinate) {
        CloudPoint[] newCoordinates = new CloudPoint[coordinates.length + 1];
        for (int i = 0; i < coordinates.length; i++) {
            newCoordinates[i] = coordinates[i];
        }
        newCoordinates[coordinates.length] = coordinate;
        coordinates = newCoordinates;
    }
}
