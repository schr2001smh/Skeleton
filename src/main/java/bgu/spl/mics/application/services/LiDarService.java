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
    private int ticktime=1;
    private HashMap<Integer, List<TrackedObject>> trackedMap = new HashMap<>();
    private List<StampedCloudPoints> lidardata= new ArrayList<>();

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
    public void setLidarData(List<StampedCloudPoints> lidardata){
        this.lidardata=lidardata;
    }

    /**
     * Initializes the LiDarService.
     * Registers the service to handle DetectObjectsEvents and TickBroadcasts,
     * and sets up the necessary callbacks for processing data.
     */
    @Override
    protected void initialize() {

      

       subscribeBroadcast(TickBroadcast.class, (TickBroadcast brod) -> {
            lasttime = this.time;
            this.time = brod.getTick();
            this.ticktime = brod.getTickTime();
            for(int i=0;i<this.time;i++)
            {
                if (trackedMap.containsKey(i)) {
                    trackedMap.remove(i);
                }
            }

            int i=this.time/ticktime;
            if (i<lidardata.size() && lidardata.get(i).getId().equals("ERROR")) {
                ErrorOutput output = ErrorOutput.getInstance();
                output.setError(lidardata.get(i).getId());
                 output.setFaultySensor(this.getName());
               sendBroadcast(new CrashedBroadcast(this.getName()));
               terminate();
               System.out.println("LidarService "+this.getName()+" Crashed");
            }
       });

       subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast brod) -> {
        terminate();
       });

       subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast brod) -> {
        terminate();
     });
    

        subscribeEvent(DetectObjectsEvent.class, (DetectObjectsEvent e) -> {
            int time = e.getStampedDetectObjects().getTime();
            StampedDetectedObjects objects = e.getStampedDetectObjects();
            List<DetectedObject> detectedObjects = objects.getDetectedObjects();
            for (DetectedObject detectedObject : detectedObjects) {
                List<List<Double>> cloudPointsList = dataBase.getcloudpoints(time,lasttime,detectedObject.getId());
                List<TrackedObject> trackedObjectsList = new ArrayList<>();

                if(cloudPointsList!=null)
                {
                    prepareData(cloudPointsList, detectedObject, trackedObjectsList);
                    ErrorOutput output = ErrorOutput.getInstance();
                    output.setLastLiDarWorkerTrackersFrame(getName(), new StampedCloudPoints(getName(), time),cloudPointsList);
                }
                trackedMap.put(time + LiDarWorkerTracker.getFrequency(), trackedObjectsList);
                sendEvent(new TrackedObjectsEvent(trackedObjectsList,LiDarWorkerTracker.getFrequency())); 
            }
        });
    }

    public void prepareData(List<List<Double>> cloudPointsList, DetectedObject detectedObject, List<TrackedObject> trackedObjectsList) {
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
}
