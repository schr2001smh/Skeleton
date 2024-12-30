package bgu.spl.mics.application.objects;

import java.util.List;

import bgu.spl.mics.LiDarWorkers;

public class Configuration2 {
    public List<Camera> cameras;
    public List<String> camera_datas_path;
    public List<LiDarWorkers> LiDarWorkers;
    public List<String> lidars_data_path;
    public String poseJsonFile;
    public int TickTime;
    public int duration;
    
    public Configuration2(){}; // For serialization

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

    @Override
    public String toString() {
        System.err.println("arrived at config toString");
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
  
  