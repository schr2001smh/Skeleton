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
    public String getDescription() {
        return description;
    }
    public int getTime(){
        return time;
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
        double yaw=pose.getOrientation()*(Math.PI/180);
        double x;
        double y;
        for (CloudPoint point : coordinates) {
            x=Math.cos(yaw)*point.getX() - Math.sin(yaw)*point.getY() + pose.getX();
            y=Math.sin(yaw)*point.getX() + Math.cos(yaw)*point.getY() + pose.getY();
            point.setX(x);
            point.setY(y);
        }
    }

    public void updateWithNewData(TrackedObject newData) {
        if (!this.id.equals(newData.getId())) {
            throw new IllegalArgumentException("TrackedObject IDs do not match.");
        }
        this.time = newData.time;
        this.description = newData.description;
        int Max=Math.max(newData.coordinates.size(), this.coordinates.size());
        for (int i = 0; i < Max; i++) {
            if (i<this.coordinates.size()-1) {
                CloudPoint oldPoint = this.coordinates.get(i);
                CloudPoint newPoint = newData.coordinates.get(i);
                double avgX = (oldPoint.getX() + newPoint.getX()) / 2;
                double avgY = (oldPoint.getY() + newPoint.getY()) / 2;
                this.coordinates.get(i).setX(avgX);
                this.coordinates.get(i).setY(avgY);
            }
            else{
                this.coordinates.add(newData.coordinates.get(i));
            }
        }
        
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
