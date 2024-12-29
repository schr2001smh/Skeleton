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
    public List<TrackedObject> lastTrackedObjects;

    public LiDarWorkerTracker(int id, int frequency) {
        this.id = id;
        this.frequency = frequency;
        this.status = STATUS.UP;
        this.lastTrackedObjects = new ArrayList<>();
    }

    public void addTrackedObject(TrackedObject trackedObject) {
        lastTrackedObjects.add(trackedObject);
    }
}
