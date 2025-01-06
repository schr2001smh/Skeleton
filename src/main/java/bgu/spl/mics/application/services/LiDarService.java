package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import bgu.spl.mics.ErrorOutput;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.DetectObjectsEvent;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TrackedObjectsEvent; // Ensure this class exists or remove if not needed
import bgu.spl.mics.application.objects.CloudPoint;
import bgu.spl.mics.application.objects.DetectedObject;
import bgu.spl.mics.application.objects.LiDarDataBase;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.objects.StampedCloudPoints;
import bgu.spl.mics.application.objects.StampedDetectedObjects;
import bgu.spl.mics.application.objects.TrackedObject;

/**
 * LiDarService is responsible for processing data from the LiDAR sensor and
 * sending TrackedObjectsEvents to the FusionSLAM service.
 * 
 * This service interacts with the LiDarWorkerTracker object to retrieve and process
 * cloud point data and updates the system's StatisticalFolder upon sending its
 * observations.
 */
public class LiDarService extends MicroService {
    private  LiDarDataBase dataBase = LiDarDataBase.getInstance();
    private LiDarWorkerTracker LiDarWorkerTracker;
    private int time;
    private int lasttime;
    private HashMap<Integer, List<TrackedObject>> trackedMap = new HashMap<>();

    /**
     * Constructor for LiDarService.
     *
     * @param LiDarWorkerTracker A LiDAR Tracker worker object that this service will use to process data.
     */
    public LiDarService(LiDarWorkerTracker LiDarWorkerTracker) {
        super("Lidar service"+ LiDarWorkerTracker.getId());
        this.LiDarWorkerTracker = LiDarWorkerTracker;
    }

    public void sendEvent(List<TrackedObjectsEvent> points){
        sendEvent(points);
    }

    /**
     * Initializes the LiDarService.
     * Registers the service to handle DetectObjectsEvents and TickBroadcasts,
     * and sets up the necessary callbacks for processing data.
     */
    @Override
    protected void initialize() {

       System.out.println("lidarservice started");
      

       subscribeBroadcast(TickBroadcast.class, (TickBroadcast brod) -> {
            lasttime = this.time;
            this.time = brod.getTick();
            for(int i=0;i<this.time;i++)
            {
                if (trackedMap.containsKey(i)) {
                 //sendEvent(new TrackedObjectsEvent(trackedMap.get(i),LiDarWorkerTracker.getFrequency()));
                    trackedMap.remove(i);
                }
            }
       });

       subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast brod) -> {
        System.out.println(getName()+" detected "+brod.getSenderName()+"terminated");
        System.out.println("lidar terminated");
        terminate();
       });

       subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast brod) -> {
        System.out.println(getName() + "  detected  " + brod.getSenderName() + "crashed");
        terminate();
     });
    

     subscribeEvent(DetectObjectsEvent.class, (DetectObjectsEvent e) -> {

        StampedDetectedObjects objects = e.getStampedDetectObjects();
        int time = objects.getTime();
        List<DetectedObject> detectedObjects = objects.getDetectedObjects();

        for (DetectedObject detectedObject : detectedObjects) {
           List<List<Double>> cloudPointsList = dataBase.getcloudpoints(time,lasttime,detectedObject.getId());

           List<TrackedObject> trackedObjectsList = new ArrayList<>();

           if(cloudPointsList!=null)
           {
            TrackedObject trackedObjects = new TrackedObject(getName(), time, getName(), null);
            List<CloudPoint> cloudPointObjects = new ArrayList<>();
                for (List<Double> cloudPoints : cloudPointsList) {
                    cloudPointObjects.add(new CloudPoint(cloudPoints.get(0), cloudPoints.get(1)));   
                }  
                trackedObjects = new TrackedObject(detectedObject.getId(), time, detectedObject.getDescription(), cloudPointObjects);
                if(!trackedObjectsList.contains(trackedObjects)) {
                        
                    trackedObjectsList.add(trackedObjects);
                } 
            }
            
            trackedMap.put(time + LiDarWorkerTracker.getFrequency(), trackedObjectsList);
          sendEvent(new TrackedObjectsEvent(trackedObjectsList,LiDarWorkerTracker.getFrequency()));
           ErrorOutput output = ErrorOutput.getInstance();
            output.setLastLiDarWorkerTrackersFrame(getName(), new StampedCloudPoints(getName(), time));
            System.out.println("TESTINTTTTT");
            System.out.println(new StampedCloudPoints(getName(), time));
        
           // System.out.println(trackedObjectsList+"Meaning it sends good coordinates");

      
        }
     });
    //     public TrackedObject(String id, int time, String description,List<CloudPoint> coordinates) {
    //     this.id = id;
    //     this.time = time;
    //     this.description = description;
    //     this.coordinates = coordinates;
    // }
    }
}
