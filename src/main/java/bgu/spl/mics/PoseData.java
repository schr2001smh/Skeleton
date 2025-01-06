package bgu.spl.mics;
import bgu.spl.mics.application.objects.Pose;

public class PoseData {
  public int time;
    public Pose pose;

    public PoseData(int time, Pose pose) {
        this.time = time;
        this.pose = pose;
      }
      public PoseData(){}; // For serialization

      public void setPose(Pose pose) {
        this.pose = pose;
      }
      public int getTime() {
        return time;
      }
      @Override
      public String toString() {
        return "time=" + time +"\n"+
         ", pose=" + pose + "}";
      }
    
}

