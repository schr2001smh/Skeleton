package bgu.spl.mics;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import bgu.spl.mics.application.objects.DetectedObject;
import bgu.spl.mics.application.objects.Pose;
import bgu.spl.mics.application.objects.StampedCloudPoints;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorOutput {
  private String error;
  private String faultySensor;
  private Map<String, StampedDetectedObjects> lastCamerasFrame= new HashMap<>();
  private Map<String, StampedCloudPoints> lastLiDarWorkerTrackersFrame= new HashMap<>();
  private List<PoseData> stampedposes;
  private Output statistics = Output.getInstance();

  public void generateOutputJson() {
    Gson gson = new Gson();
    String path = ErrorOutputHolder.filePath.substring(0, ErrorOutputHolder.filePath.lastIndexOf('\\'));
    try (FileWriter writer = new FileWriter(path + "\\.\\output_file.json")) {
        gson.toJson(this, writer);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void setPoses(List<PoseData> stampedposes) {
    this.stampedposes = stampedposes;
  }
  
  public void cutPoses(int systemRuntime){
    for (PoseData poseData : stampedposes) {
      if (poseData.getTime()>systemRuntime) {
        stampedposes.remove(poseData);
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
    if (lastCamerasFrame!=null) {
    if (this.lastCamerasFrame.get(id).getTime()<lastCamerasFrame.getTime()) {
      this.lastCamerasFrame.put(id, lastCamerasFrame);
    }
  }
    
    
  }

  public void setLastLiDarWorkerTrackersFrame( String id ,StampedCloudPoints lastLiDarWorkerTrackersFrame) {
    if(lastLiDarWorkerTrackersFrame!=null)
    {
    if (this.lastLiDarWorkerTrackersFrame.get(id).getTime()<lastLiDarWorkerTrackersFrame.getTime()) {
      this.lastLiDarWorkerTrackersFrame.put(id, lastLiDarWorkerTrackersFrame);
    }
  }
}


  private static class ErrorOutputHolder {
    private static final ErrorOutput instance = new ErrorOutput();
    private static String filePath;
  }

  public static ErrorOutput getInstance() {
    return ErrorOutputHolder.instance;
  }

  private ErrorOutput() {
    statistics = Output.getInstance();
  }

  public void setFilePath(String filePath) {
    ErrorOutputHolder.filePath = filePath;
  }
}


