package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of cloud points corresponding to a specific timestamp.
 * Used by the LiDAR system to store and process point cloud data for tracked objects.
 */
public class StampedCloudPoints {
    private String id;
    private int time;
    private List<List<Double>> cloudPoints= new ArrayList<>();

    public StampedCloudPoints(String id, int time) {
        this.time = time;
        this.id = id;
        this.cloudPoints = new ArrayList<>();
    }
    public List<List<Double>> getCloudPoints(){
        return cloudPoints;
    }
    public void addCloudPoint(List<Double> cloudPoint) {
        cloudPoints.add(cloudPoint);
    }
    public StampedCloudPoints(){}; // For serialization

    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
    public void setTime(int time){
        this.time=time;
    }
    public int getTime(){
        return time;
    }


    public void setCloudPoints(List<List<Double>> cloudPoints){
        this.cloudPoints=cloudPoints;
    }
    
    @Override
  public String toString() {
        return "time: " + time +'\''+
         ", id:" +  id  +'\''+
         ", cloudPoints" + cloudPoints
         ;
    }
}
// "time": 1,
// "id": "Wall_1",
// "cloudPoints": [

// return "time: " + time + '\'' +
// ", x: " + x + '\''+
// ", y: " + y + '\''+
// ", yaw: " + yaw
// ;
