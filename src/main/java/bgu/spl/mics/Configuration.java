package bgu.spl.mics;
import java.util.List;

import bgu.spl.mics.application.objects.Camera;

 class cameraz{
    public List<Camera> CamerasConfigurations;
    public String camera_datas_path;
    
    public cameraz(){}; // For serialization
    public List<Camera> getCamerasConfigurations(){
        return CamerasConfigurations;
    }

    public void setCamerasConfigurations(List<Camera> cameras){
        this.CamerasConfigurations=cameras;
    }

    public void setcamera_datas_path(String camera_datas_path){
        this.camera_datas_path=camera_datas_path;
    }
    public String getcamera_datas_path(){
        return camera_datas_path;
    }   
    @Override
    public String toString() {
        return "cameras{" + '\'' +
                "CamerasConfigurations=" + CamerasConfigurations + '\'' +
                ", camera_datas_path='" + camera_datas_path + '\'' +
                '}';
    }
}

 class LiDarWorkerz{
    public List<LiDarWorkers> LidarConfigurations;
    public String lidars_data_path;

    public LiDarWorkerz(){}; // For serialization

    public void setLidarConfigurations(List<LiDarWorkers> LiDarWorkers){
        this.LidarConfigurations=LiDarWorkers;
    }
    public List<LiDarWorkers> getLidarConfigurations(){
        return LidarConfigurations;
    }
    public String getLidars_data_path(){
        return lidars_data_path;
    }

    public void setLidars_data_path(String lidars_data_path){
        this.lidars_data_path=lidars_data_path;
    }
    @Override
    public String toString() {
        return "LiDarWorkers{" + '\'' +
                "LidarConfigurations=" + LidarConfigurations + '\'' +
                ", lidars_data_path='" + lidars_data_path + '\'' +
                '}';
    }
}

public class Configuration {
    public cameraz Cameras;
    public LiDarWorkerz LiDarWorkers;
    public String poseJsonFile;
    public int TickTime;
    public int duration;
    
    public Configuration(){}; // For serialization

    public String print_cameras(){
       return  Cameras.toString();
    }

    public String print_lidars(){
        return LiDarWorkers.toString();
    }

    public List<LiDarWorkers> getLidarConfigurations(){
        return LiDarWorkers.getLidarConfigurations();
    }

    public String getLidars_data_path(){
        return LiDarWorkers.getLidars_data_path();
    }
    public String getCamera_datas_path(){
        return Cameras.getcamera_datas_path();
    }  
    public List<Camera> getCamerasConfigurations(){
        return Cameras.getCamerasConfigurations();
    }

    @Override
    public String toString() {
        return "Configuration{" +'\'' +
                Cameras + '\'' +
                LiDarWorkers +'\'' +
               ", poseJsonFile='" + poseJsonFile + '\'' +
               ", TickTime=" + TickTime +'\'' +
               ", duration=" + duration +'\'' +
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
  
  