package bgu.spl.mics;

import java.util.List;
import java.util.Map;

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
  private Map<String, StampedDetectedObjects> lastCamerasFrame;
  private Map<String, StampedCloudPoints> lastLiDarWorkerTrackersFrame;
  private List<PoseData> stampedposes;
  private Output statistics = Output.getInstance();

  public void generateOutputJson(String filePath) {
    Gson gson = new Gson();
    try (FileWriter writer = new FileWriter(filePath + "\\.\\output_file.json")) {
        gson.toJson(this, writer);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setFaultySensor(String faultySensor) {
    this.faultySensor = faultySensor;
  }

  public void setCameraFrame(String id, StampedDetectedObjects lastCamerasFrame) {
    if (this.lastCamerasFrame.get(id).getTime()<lastCamerasFrame.getTime()) {
      this.lastCamerasFrame.put(id, lastCamerasFrame);
    }
    
    
  }

  public void setLastLiDarWorkerTrackersFrame( String id ,StampedCloudPoints lastLiDarWorkerTrackersFrame) {
    if (this.lastLiDarWorkerTrackersFrame.get(id).getTime()<lastLiDarWorkerTrackersFrame.getTime()) {
      this.lastLiDarWorkerTrackersFrame.put(id, lastLiDarWorkerTrackersFrame);
    }
  }

  public void setPoses(List<Pose> stampedposes) {

  }

  private static class ErrorOutputHolder {
    private static final ErrorOutput instance = new ErrorOutput();
  }

  private ErrorOutput() {
    statistics = Output.getInstance();
  }
}


