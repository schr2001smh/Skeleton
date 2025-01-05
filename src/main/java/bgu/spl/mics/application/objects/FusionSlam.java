package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the fusion of sensor data for simultaneous localization and mapping (SLAM).
 * Combines data from multiple sensors (e.g., LiDAR, camera) to build and update a global map.
 * Implements the Singleton pattern to ensure a single instance of FusionSlam exists.
 */
public class FusionSlam {
    private List<LandMark> landmarks;
    private List<Pose> poses;
    private Map<String, TrackedObject> map;
    private Pose currentPose;
    

    // Private constructor to prevent instantiation
    private FusionSlam() {
        landmarks = new ArrayList<>();
        poses = new ArrayList<>();
        map = new HashMap<>();
        currentPose = new Pose();
    }

    // Singleton instance holder
    public static class FusionSlamHolder {
        private static final FusionSlam INSTANCE = new FusionSlam();
    }

    // Public method to provide access to the singleton instance
    public static FusionSlam getInstance() {
        return FusionSlamHolder.INSTANCE;
    }

    public Pose getCurrentPose(int tick) {
       if (tick > poses.size()) {
           return null;
       }
       else {
        System.err.println("time is= " + tick +"i think my pose is poses.get(tick)" + poses.get(tick -1));
           return poses.get(tick -1);
       }
    }

    public boolean isNewObject(TrackedObject obj) {
        return !map.containsKey(obj.getId());
    }

    public void addObjectToMap(TrackedObject obj) {
        map.put(obj.getId(), obj);
    }

    public void updateObjectInMap(TrackedObject obj) {
        TrackedObject existingObj = map.get(obj.getId());
        existingObj.updateWithNewData(obj);
    }

    public List<LandMark> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(List<LandMark> landmarks) {
        this.landmarks = landmarks;
    }

    public List<Pose> getPoses() {
        return poses;
    }

    public void setPoses(List<Pose> poses) {
        this.poses = poses;
    }

    public Map<String, TrackedObject> getMap() {
        return map;
    }

    public void setMap(Map<String, TrackedObject> map) {
        this.map = map;
    }
    public String toString() {
        return "FusionSlam{" +
                "landmarks=" + landmarks +
                ", poses=" + poses +
                ", map=" + map +
                ", currentPose=" + currentPose +
                '}';
    }
}
