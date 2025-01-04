package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;
/**
 * LiDarWorkerTracker is responsible for managing a LiDAR worker.
 * It processes DetectObjectsEvents and generates TrackedObjectsEvents by using data from the LiDarDataBase.
 * Each worker tracks objects and sends observations to the FusionSlam service.
 */
public class LiDarWorkerTracker {
    private int id;
    private int frequency;
    private STATUS status;
    private List<TrackedObject> lastTrackedObjects= new ArrayList<>();

    public LiDarWorkerTracker(int id, int frequency) {
        this.id = id;
        this.frequency = frequency;
        this.status = STATUS.UP;
        this.lastTrackedObjects = new ArrayList<>();
    }

    public void addTrackedObject(TrackedObject trackedObject) {
        lastTrackedObjects.add(trackedObject);
    }
    public void setliDarWorkerTracker(List<TrackedObject> lastTrackedObjects)
    {
        this.lastTrackedObjects = lastTrackedObjects;
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

    public List<TrackedObject> getLastTrackedObjects() {
        return lastTrackedObjects;
    }

    public void setLastTrackedObjects(List<TrackedObject> lastTrackedObjects) {
        this.lastTrackedObjects = lastTrackedObjects;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "LidarConfiguration{" +
               "id=" + id +
               ", frequency=" + frequency +
               '}';
    }
}
