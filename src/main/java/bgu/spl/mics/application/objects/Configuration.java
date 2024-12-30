package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.LiDarWorkers;

public class Configuration {
    public List<Camera> cameras= new ArrayList<>();
    public List<String> camera_datas_path = new ArrayList<>();
    public List<LiDarWorkers> LiDarWorkers = new ArrayList<>();
    public List<String> lidars_data_path= new ArrayList<>();
    public String poseJsonFile;
    public int TickTime;
    public int duration;
    
    public Configuration(){} // For serialization

    public List<Camera> getCameras(){
        return cameras;
    }
    public List<LiDarWorkers> getLiDarWorkers(){
        return LiDarWorkers;
    }
    public void setCameras(List<Camera> cameras){
        this.cameras=cameras;
    }
    public void setLiDarWorkers(List<LiDarWorkers> LiDarWorkers){
        this.LiDarWorkers=LiDarWorkers;
    }
    public List<String> getCamera_datas_path(){
        return camera_datas_path;
    }
    public List<String> getLidars_data_path(){
        return lidars_data_path;
    }
    public String getPoseJsonFile(){
        return poseJsonFile;
    }
    public int getTickTime(){
        return TickTime;
    }
    public int getDuration(){
        return duration;
    }
    public void setTime(int TickTime){
        this.TickTime=TickTime;
    }
    public void setDuration(int duration){
        this.duration=duration;
    }
    public void setPoseJsonFile(String poseJsonFile){
        this.poseJsonFile=poseJsonFile;
    }


    public void setCamera_datas_path(List<String> camera_datas_path){
        this.camera_datas_path=camera_datas_path;
    }
    public void setLidars_data_path(List<String> lidars_data_path){
        this.lidars_data_path=lidars_data_path;
    }

    @Override
    public String toString() {
     System.out.println("toString() method called for Configuration");

        return "Configuration{" +
               "cameras=" + cameras +
               ", camera_datas_path='" + camera_datas_path + '\'' +
               ", LiDarWorkers=" + LiDarWorkers +
               ", lidars_data_path='" + lidars_data_path + '\'' +
               ", poseJsonFile='" + poseJsonFile + '\'' +
               ", TickTime=" + TickTime +
               ", duration=" + duration +
               '}';
    }

}

// {
//     "Cameras": {
//       "CamerasConfigurations": [
//         {
//           "id": 1,
//           "frequency": 0,
//           "camera_key": "camera1"
//         }
//       ],
//       "camera_datas_path": "./camera_data.json"
//     },
//     "LiDarWorkers": {
//       "LidarConfigurations": [
//         {
//           "id": 1,
//           "frequency": 0
//         }
//       ],
//       "lidars_data_path": "./lidar_data.json"
//     },
//     "poseJsonFile": "./pose_data.json",
//     "TickTime": 1,
//     "Duration": 30
//   }
  
  