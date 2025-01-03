package bgu.spl.mics.application.services;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.objects.Camera;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.DetectObjectsEvent;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

import java.util.List;

import bgu.spl.mics.MessageBus;

/**
 * CameraService is responsible for processing data from the camera and
 * sending DetectObjectsEvents to LiDAR workers.
 * 
 * This service interacts with the Camera object to detect objects and updates
 * the system's StatisticalFolder upon sending its observations.
 */
public class CameraService extends MicroService {
    private Camera camera;
    private int time;
    private int lasttime;
    private List<StampedDetectedObjects> objects;
    
    /**
     * Constructor for CameraService.
     *
     * @param camera The Camera object that this service will use to detect objects.
     */
    public CameraService(Camera camera) {
        super("camera");
        this.camera = camera;
    }

    public void sendEvent(DetectObjectsEvent event){
        super.sendEvent(event);
    }
    /**
     * Initializes the CameraService.
     * Registers the service to handle TickBroadcasts and sets up callbacks for sending
     * DetectObjectsEvents.
     */
    @Override
    protected void initialize() {
        objects=camera.getstaStampedDetectedObjects();
       System.out.println("CameraService started");

       subscribeBroadcast(TickBroadcast.class, (TickBroadcast brod) -> {
         lasttime = time;
          this.time = brod.getTick();
          List<StampedDetectedObjects> list = camera.objectsDuringTime(lasttime, time);
          System.out.println("List " + list);
          
            if (!list.isEmpty()) {
            for (StampedDetectedObjects obj : list) {
                sendEvent(new DetectObjectsEvent(obj));
                System.out.println(getName() + " detected " + obj + " objects");
            }
            }
          
       });

       subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast brod) -> {
        System.out.println(getName()+" detected "+brod.getSenderName()+"terminated");
        terminate();
       });

       subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast brod) -> {
        System.out.println(getName() + "  detected  " + brod.getSenderName() + "crashed");
        terminate();
     });
    }
}
