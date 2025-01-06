package bgu.spl.mics;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import bgu.spl.mics.application.objects.CloudPoint;
import bgu.spl.mics.application.objects.DetectedObject;
import bgu.spl.mics.application.objects.FusionSlam;
import bgu.spl.mics.application.objects.LandMark;
import bgu.spl.mics.application.objects.Pose;
import bgu.spl.mics.application.objects.StampedCloudPoints;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class ErrorOutput {
  private String error;
  private String faultySensor;
  private Map<String, StampedDetectedObjects> lastCamerasFrame= new HashMap<>();
  private Map<String, StampedCloudPoints> lastLiDarWorkerTrackersFrame= new HashMap<>();
  private List<PoseData> poses;
  private statistics statistics= new statistics();

 public void generateOutputJson() {
    MessageBusImpl messageBus = MessageBusImpl.getInstance();
    FusionSlam fusion = FusionSlam.getInstance();
    statistics.setNumLandmarks(fusion.getLandmarks().size());
    statistics.setLandmarks(fusion.getLandmarks());
    statistics.setNumTrackedObjects(messageBus.getNumTrackedObjects());
    statistics.setNumDetectedObjects(messageBus.getNumDetectedObjects());
    Gson gson = new GsonBuilder()
    .setPrettyPrinting()
                .create();
    String path = ErrorOutputHolder.filePath.substring(0, ErrorOutputHolder.filePath.lastIndexOf('\\'));
    try (FileWriter writer = new FileWriter(path + "\\output_file.json")) {
        gson.toJson(this, writer);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void setPoses(List<PoseData> poses) {
    this.poses = poses;
  }
  
  public void cutPoses(int systemRuntime){
    for (PoseData poseData : poses) {
      if (poseData.getTime()>systemRuntime) {
        poses.remove(poseData);
      }
    }
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setFaultySensor(String faultySensor) {
    this.faultySensor = faultySensor;
  }

  public void setCameraFrame(String id, StampedDetectedObjects lastCamerasFrame) {
    if (this.lastCamerasFrame.get(id)!=null) {
    if (this.lastCamerasFrame.get(id).getTime()<lastCamerasFrame.getTime()) {
      this.lastCamerasFrame.put(id, lastCamerasFrame);
    }
  }
  else{
    this.lastCamerasFrame.put(id, lastCamerasFrame);
  }
}

  public void setLastLiDarWorkerTrackersFrame( String id ,StampedCloudPoints lastLiDarWorkerTrackersFrame,
  List<List<Double>> cloudPoint) {
    if(this.lastLiDarWorkerTrackersFrame.get(id)!=null)
    {
    if (this.lastLiDarWorkerTrackersFrame.get(id).getTime()<lastLiDarWorkerTrackersFrame.getTime()) {
      this.lastLiDarWorkerTrackersFrame.put(id, lastLiDarWorkerTrackersFrame);
      this.lastLiDarWorkerTrackersFrame.get(id).setCloudPoints(cloudPoint);
    }
  }
  else{
    this.lastLiDarWorkerTrackersFrame.put(id, lastLiDarWorkerTrackersFrame);
    this.lastLiDarWorkerTrackersFrame.get(id).setCloudPoints(cloudPoint);
  }
}
public void setSystemRuntime(int systemRuntime) {
  statistics.setSystemRuntime(systemRuntime);
  poses=poses.subList(0, systemRuntime);
}

//"statistics":{"systemRuntime":14,"numDetectedObjects":9,"numTrackedObjects":9,"numLandmarks":7,"landMarks"
private static class statistics {
  private int systemRuntime=0;
  private int numDetectedObjects=0;
  private int numTrackedObjects=0;
  private int numLandmarks=0;
  private List<LandMark> landMarks;

  public void setSystemRuntime(int systemRuntime) {
    this.systemRuntime = systemRuntime;
  }
  public void setNumDetectedObjects(int numDetectedObjects) {
    this.numDetectedObjects = numDetectedObjects;
  }
  public void setNumTrackedObjects(int numTrackedObjects) {
    this.numTrackedObjects = numTrackedObjects;
  }
  public void setNumLandmarks(int numLandmarks) {
    this.numLandmarks = numLandmarks;
  }
  public void setLandmarks(List<LandMark> landMarks) {
    this.landMarks = landMarks;
  }


}

  private static class ErrorOutputHolder {
    private static final ErrorOutput instance = new ErrorOutput();
    private static String filePath;
  }

  public static ErrorOutput getInstance() {
    return ErrorOutputHolder.instance;
  }



  public void setFilePath(String filePath) {
    ErrorOutputHolder.filePath = filePath;
  }
}


