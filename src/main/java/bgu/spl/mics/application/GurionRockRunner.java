package bgu.spl.mics.application;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import bgu.spl.mics.Configuration;
import bgu.spl.mics.LiDarWorkers;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.objects.Camera;
import bgu.spl.mics.application.objects.DetectedObject;
import bgu.spl.mics.application.objects.FusionSlam;
import bgu.spl.mics.application.objects.GPSIMU;
import bgu.spl.mics.application.objects.LiDarDataBase;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.objects.Pose;
import bgu.spl.mics.application.objects.STATUS;
import bgu.spl.mics.application.objects.StampedCloudPoints;
import bgu.spl.mics.application.objects.StampedDetectedObjects;
import bgu.spl.mics.application.services.CameraService;
import bgu.spl.mics.application.services.FusionSlamService;
import bgu.spl.mics.application.services.LiDarService;
import bgu.spl.mics.application.services.PoseService;
import bgu.spl.mics.application.services.TimeService;

/**
 * The main entry point for the GurionRock Pro Max Ultra Over 9000 simulation.
 * <p>
 * This class initializes the system and starts the simulation by setting up
 * services, objects, and configurations.
 * </p>
 */
public class GurionRockRunner {

    private static final ExecutorService executeService = Executors.newCachedThreadPool();

    /**
     * The main method of the simulation.
     * This method sets up the necessary components, parses configuration files,
     * initializes services, and starts the simulation.
     *
     * @param args Command-line arguments. The first argument is expected to be the path to the configuration file.
     */
    public static void main(String[] args) {
        Map<String, List<StampedDetectedObjects>> cameraData = new HashMap<>();

        if (args.length == 0) {
            args = new String[1];
            args[0] = "example input\\configuration_file.json";  // change before submission!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        String configFilePath = args[0];
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(configFilePath);

            // Define the type for the Configuration object
            Type configType = new TypeToken<Configuration>() {
            }.getType();
            Configuration config = gson.fromJson(reader, configType);

            // Print the loaded configuration data
            System.out.println("Configuration: " + config);
            System.out.println("Cameras: " + config.print_cameras());
            System.out.println("Lidar Workers: " + config.print_lidars());
            System.out.println("Pose JSON File: " + config.poseJsonFile);
            System.out.println("Tick Time: " + config.TickTime);
            System.out.println("Duration: " + config.Duration);
            
            //load other jsons and start the simulation
            String poseJsonFilePath = getFullJsonFilePath(configFilePath, config.poseJsonFile);
            List<Pose> poses = loadPoseData(poseJsonFilePath);
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n" + poses);
            String lidarJsonFilePath = getFullJsonFilePath(configFilePath, config.getLidars_data_path());
            List<StampedCloudPoints> lidarData = loadLidarData(lidarJsonFilePath);
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // System.out.println( lidarData);

            
            
            String cameraJsonFilePath = getFullJsonFilePath(configFilePath, config.getCamera_datas_path());
            System.out.println("Camera JSON File: " + cameraJsonFilePath);
           
            System.out.println("Camera Data: " + loadCameraData(cameraJsonFilePath));
            List<Camera> cameras = loadCameras(config, cameraJsonFilePath);
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // System.out.println(cameras);
            


            System.out.println("SUMMARY:"
                    +"\nDuration: " + config.Duration + " seconds"
                    +"\nTick Time: " + config.TickTime + " seconds");
            for (Camera camera : cameras) {
                System.out.println(camera);
            }

            System.out.println(config);
            System.out.println("******");

            System.out.println("******************************************************************************************************************");
            Gson g3 = new GsonBuilder().setPrettyPrinting().create();

            try (FileReader reader2 = new FileReader(getFullJsonFilePath(configFilePath, config.getCamera_datas_path()))) {
                // Define the type to parse the JSON structure

                Type type = new TypeToken<Map<String, List<StampedDetectedObjects>>>() {}.getType();
                cameraData = g3.fromJson(reader2, type);
                System.out.println(cameraData);

                // Iterate over all cameras and their data
                for (Map.Entry<String, List<StampedDetectedObjects>> entry : cameraData.entrySet()) {
                    String cameraName = entry.getKey();
                    List<StampedDetectedObjects> detectedObjects = entry.getValue();
                    System.out.println("Camera: " + cameraName);
                    for (StampedDetectedObjects stampedDetectedObjects : detectedObjects) {
                        System.out.println(stampedDetectedObjects);
                    }
                }
                System.out.println("camera data have been loaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("******************************************************************************************************************");
            // initialize the services


            //create pose service
            GPSIMU gpsImu = new GPSIMU(0, STATUS.UP, poses);
            PoseService poseService = new PoseService(gpsImu);


            //create lidar database and lidar services
            LiDarDataBase liDarDataBase = LiDarDataBase.getInstance(lidarJsonFilePath);
            liDarDataBase.setCloudPoints(lidarData);//update lidar data

            List<LiDarWorkerTracker> liDarWorkerTrackers = new ArrayList<>();//all lidarWorkers
            List<LiDarService> liDarServices = new ArrayList<>();//all lidarServices

            List<LiDarWorkers> lidarWorkers = config.getLidarConfigurations();
            for (LiDarWorkers liDarWorker : lidarWorkers) {
                LiDarWorkerTracker liDarWorkerTracker = new LiDarWorkerTracker(liDarWorker.id, liDarWorker.frequency);

                liDarWorkerTrackers.add(liDarWorkerTracker);
                liDarServices.add(new LiDarService(liDarWorkerTracker));
            }

            //create camera services
            List<CameraService> cameraServices = new ArrayList<>();
            int counter = 0;
            for (Camera camera : cameras) {
                counter++;
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                camera.setstampedDetectedObjects(cameraData.get("camera"+camera.getId()));
                cameraServices.add(new CameraService(camera));
                
            }

            //create fusion slam service
            FusionSlam fusionSlam = FusionSlam.getInstance();
            fusionSlam.setPoses(poses);
            System.out.println("#######################################################\n"+ poses);
            FusionSlamService fusionSlamService = new FusionSlamService(fusionSlam);

            //create time service
            TimeService timeService = new TimeService(config.TickTime, config.Duration, configFilePath);

            //start the program
            List<MicroService> services = new ArrayList<>();
            services.add(poseService);
            services.addAll(liDarServices);
            services.addAll(cameraServices);
            services.add(fusionSlamService);
            services.add(timeService);//add time service last so that when he starts all the services will already be registered
            startServices(services);

            reader.close();


        } catch (IOException e) {
            System.err.println("Error reading or parsing the configuration file.");
            e.printStackTrace();
        }
    }


    private static void startServices(List<MicroService> services) {
        for (MicroService service : services) {
         Thread thread = new Thread(service);
            thread.start();
            // executeService.submit(service);
        }
    }




    // Helper method to extract the pose JSON file path from the directory of the config file
    private static String getFullJsonFilePath(String configFilePath, String poseJsonFileName) {
        Path configPath = Paths.get(configFilePath).getParent(); // Get the directory of the config file
        return configPath.resolve(poseJsonFileName).toString(); // Resolve the full path to the pose JSON file
    }

    // Helper method to load and parse the pose JSON file
    private static List<Pose> loadPoseData(String poseJsonFilePath) {
        try {

            Gson gson = new Gson();
            FileReader reader = new FileReader(poseJsonFilePath);
            // Define the type for the Pose list
            Type poseListType = new TypeToken<List<Pose>>() {
            }.getType();
            List<Pose> poses = gson.fromJson(reader, poseListType);

            // Print the loaded pose data
            System.out.println("\n******Pose Data: ");
            for (Pose pose : poses) {
                System.out.println(pose);
            }
            System.out.println("******");
            reader.close();
            return poses;
        } catch (IOException e) {
            System.err.println("Error reading or parsing the pose JSON file.");
            e.printStackTrace();

        }
        return null;
    }


    private static List<StampedCloudPoints> loadLidarData(String lidarJsonFilePath) {
        try {
            // Create a GsonBuilder and register the custom deserializer for CloudPoint
            GsonBuilder gsonBuilder = new GsonBuilder();
            // gsonBuilder.registerTypeAdapter(CloudPoint.class, new CloudPoint());
            Gson gson = gsonBuilder.create();

            FileReader reader = new FileReader(lidarJsonFilePath);

            // Define the type for the StampedCloudPoints list
            Type lidarListType = new TypeToken<List<StampedCloudPoints>>() {
            }.getType();
            List<StampedCloudPoints> lidarData = gson.fromJson(reader, lidarListType); 
            // Print the loaded LiDAR data
            // System.out.println("\n******LiDAR Data: ");
            // for (StampedCloudPoints stampedCloudPoints : lidarData) {
            //     System.out.println(stampedCloudPoints);
            // }
            System.out.println("******");
            reader.close();
            return lidarData;
        } catch (IOException e) {
            System.err.println("Error reading or parsing the LiDAR JSON file.");
            e.printStackTrace();
        }
        return null;
    }

    private static List<Camera> loadCameras(Configuration config, String cameraJsonFilePath) {
        //pull the camera data from the json file and create a list of cameras from combining the data from the json file and the configuration file
        List<Camera> cameras = new ArrayList<>();
        for (Camera camera : config.getCamerasConfigurations()) {
            cameras.add(camera);
        }
      return cameras;
    }

    private static Map<String,List<StampedDetectedObjects>> loadCameraData(String cameraJsonFilePath) {
        Map<String, List<StampedDetectedObjects>> cameraDataMap = new HashMap<>();
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(cameraJsonFilePath)) {
            // Parse the JSON file into a JsonObject
            JsonObject rootObject = gson.fromJson(reader, JsonObject.class);

            // Iterate through each camera in the JSON
            for (Map.Entry<String, JsonElement> entry : rootObject.entrySet()) {
                String cameraKey = entry.getKey();
                JsonArray cameraDataArray = entry.getValue().getAsJsonArray().get(0).getAsJsonArray();

                List<StampedDetectedObjects> stampedDetectedObjectsList = new ArrayList<>();
                

                // Iterate through the camera's array of detected data
                for (JsonElement element : cameraDataArray) {
                    JsonObject dataObject = element.getAsJsonObject();
                    int time = dataObject.get("time").getAsInt();

                    List<DetectedObject> detectedObjects = new ArrayList<>();
                    JsonArray detectedObjectsArray = dataObject.get("detectedObjects").getAsJsonArray();

                    // Parse detected objects
                    for (JsonElement objElement : detectedObjectsArray) {
                        JsonObject obj = objElement.getAsJsonObject();
                        String id = obj.get("id").getAsString();
                        String description = obj.get("description").getAsString();
                        detectedObjects.add(new DetectedObject(id, description));
                    }

                    // Add the StampedDetectedObjects to the list
                    stampedDetectedObjectsList.add(new StampedDetectedObjects(time, detectedObjects));
                }

                // Add the list to the map with the camera key
                cameraDataMap.put(cameraKey, stampedDetectedObjectsList);
            }
            System.out.println("\n******Camera Data: ");
            for (Map.Entry<String, List<StampedDetectedObjects>> entry : cameraDataMap.entrySet()) {
                System.out.println("Camera Key: " + entry.getKey());
                for (StampedDetectedObjects stampedDetectedObjects : entry.getValue()) {
                    System.out.println(stampedDetectedObjects);
                }
            }
            System.out.println("******\n");
        } catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing the JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return cameraDataMap;
    }
}