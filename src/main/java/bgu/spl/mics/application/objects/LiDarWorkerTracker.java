package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;
/**
 * LiDarWorkerTracker is responsible for managing a LiDAR worker.
 * It processes DetectObjectsEvents and generates TrackedObjectsEvents by using data from the LiDarDataBase.
 * Each worker tracks objects and sends observations to the FusionSlam service.
 */
public class LiDarWorkerTracker {
    public int id;
    public int frequency;
    public STATUS status;
    public List<TrackedObject> lastTrackedObjects= new ArrayList<>();

    public LiDarWorkerTracker(int id, int frequency) {
        this.id = id;
        this.frequency = frequency;
        this.status = STATUS.UP;
        this.lastTrackedObjects = new ArrayList<>();
    }

    public void addTrackedObject(TrackedObject trackedObject) {
        lastTrackedObjects.add(trackedObject);
    }

    public LiDarWorkerTracker(){}// For serialization
    public void setId(int id) {
        this.id = id;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public int getId() {
        return id;
    }
    public int getFrequency() {
        return frequency;
    }
    
    @Override
    public String toString() {
        System.out.println("toString() method called for LidarWorkerTracker");
        return "LidarConfiguration{" +
               "id=" + id +
               ", frequency=" + frequency +
               '}';
    }
}
