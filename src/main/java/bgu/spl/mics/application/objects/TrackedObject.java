package bgu.spl.mics.application.objects;

import java.util.List;

/**
 * Represents an object tracked by the LiDAR.
 * This object includes information about the tracked object's ID, description, 
 * time of tracking, and coordinates in the environment.
 */
public class TrackedObject {
    public String id;
    public int time;
    public String description;
    public List<CloudPoint> coordinates;

    public TrackedObject(String id, int time, String description,List<CloudPoint> coordinates) {
        this.id = id;
        this.time = time;
        this.description = description;
        this.coordinates = coordinates;
    }

    public void addCoordinate(CloudPoint coordinate) {
        coordinates.add(coordinate);
    }
}
