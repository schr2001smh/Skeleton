package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of cloud points corresponding to a specific timestamp.
 * Used by the LiDAR system to store and process point cloud data for tracked objects.
 */
public class StampedCloudPoints {
    public String id;
    public int time;
    public List<List<Double>> cloudPoints;

    public StampedCloudPoints(String id, int time) {
        this.id = id;
        this.time = time;
        this.cloudPoints = new ArrayList<>();
    }

    public void addCloudPoint(List<Double> cloudPoint) {
        cloudPoints.add(cloudPoint);
    }
}
