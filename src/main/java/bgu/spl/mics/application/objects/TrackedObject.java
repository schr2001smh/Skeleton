package bgu.spl.mics.application.objects;

import java.util.List;

/**
 * Represents an object tracked by the LiDAR.
 * This object includes information about the tracked object's ID, description, 
 * time of tracking, and coordinates in the environment.
 */
public class TrackedObject {
    private String id;
    private int time;
    private String description;
    private List<CloudPoint> coordinates;

    public TrackedObject(String id, int time, String description, List<CloudPoint> coordinates) {
        this.id = id;
        this.time = time;
        this.description = description;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public List<CloudPoint> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CloudPoint> coordinates) {
        this.coordinates = coordinates;
    }

    public void addCoordinate(CloudPoint coordinate) {
        coordinates.add(coordinate);
    }

    public void transformToCoordinateSystem(Pose pose) {
        for (CloudPoint point : coordinates) {
            point.setX(point.getX() + pose.getX());
            point.setY(point.getY() + pose.getY());
        }
        System.out.println("after current pose calcs \n" + coordinates);
    }

    public void updateWithNewData(TrackedObject newData) {
        if (!this.id.equals(newData.getId())) {
            throw new IllegalArgumentException("TrackedObject IDs do not match.");
        }
        this.time = newData.time;
        this.description = newData.description;
        
        for (int i = 0; i < this.coordinates.size(); i++) {
            CloudPoint oldPoint = this.coordinates.get(i);
            CloudPoint newPoint = newData.coordinates.get(i);
            double avgX = (oldPoint.getX() + newPoint.getX()) / 2;
            double avgY = (oldPoint.getY() + newPoint.getY()) / 2;
            oldPoint.setX(avgX);
            oldPoint.setY(avgY);
        }
        System.out.println("Updated TrackedObject: \n "+ this+ "\n");
    }
    @Override
    public String toString() {
        return "TrackedObject{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
