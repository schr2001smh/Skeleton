package bgu.spl.mics.application.services;
import java.util.List;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TrackedObjectsEvent;
import bgu.spl.mics.application.objects.FusionSlam;
import bgu.spl.mics.application.objects.TrackedObject;

/**
 * FusionSlamService integrates data from multiple sensors to build and update
 * the robot's global map.
 * 
 * This service receives TrackedObjectsEvents from LiDAR workers and PoseEvents from the PoseService,
 * transforming and updating the map with new landmarks.
 */
public class FusionSlamService extends MicroService {
    int tick;
    FusionSlam fusionSlam;

    /**
     * Constructor for FusionSlamService.
     *
     * @param fusionSlam The FusionSLAM object responsible for managing the global map.
     */
    public FusionSlamService(FusionSlam fusionSlam) {
        super("fusionslam");
        this.fusionSlam = fusionSlam;
    }
    @Override
    public String toString() {
        return "FusionSlamService{" +
                "tick=" + tick +
                ", fusionSlam=" + fusionSlam +
                '}';
    }

    /**
     * Initializes the FusionSlamService.
     * Registers the service to handle TrackedObjectsEvents, PoseEvents, and TickBroadcasts,
     * and sets up callbacks for updating the global map.
     */
    @Override
    protected void initialize() {
        System.out.println("FusionSlamService started");

       subscribeBroadcast(TickBroadcast.class, (TickBroadcast brod) -> {
          this.tick = brod.getTick();
          
       });

       subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast brod) -> {
        System.out.println(getName()+" detected "+brod.getSenderName()+"terminated");
        terminate();
       });

       subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast brod) -> {
        System.out.println(getName() + "  detected  " + brod.getSenderName() + "crashed");
        terminate();
     });

     subscribeEvent(TrackedObjectsEvent.class, (TrackedObjectsEvent event) -> {
        List<TrackedObject> objects = event.getTrackedObjectsEvent();
        for (TrackedObject obj : objects) {
            
            // Transform the cloud points to the charging station's coordinate system using the current pose
            obj.transformToCoordinateSystem(fusionSlam.getCurrentPose(tick));
            // Check if the object is new or previously detected
            
            if (fusionSlam.isNewObject(obj)) {
                fusionSlam.addObjectToMap(obj);
            } else {
                // If previously detected, update measurements by averaging with previous data
                fusionSlam.updateObjectInMap(obj);
            }
        }
        System.out.println("xxxxxxxxxxxxx"+ fusionSlam.getLandmarks().toString());
     });
    }
}
