package bgu.spl.mics.application;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import bgu.spl.mics.Configuration;
// import bgu.spl.mics.application.objects.Configuration2;
import bgu.spl.mics.application.objects.LiDarDataBase;
import bgu.spl.mics.application.objects.Pose;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

/**
 * The main entry point for the GurionRock Pro Max Ultra Over 9000 simulation.
 * <p>
 * This class initializes the system and starts the simulation by setting up
 * services, objects, and configurations.
 * </p>
 */
public class GurionRockRunner {
    
    /**
     * The main method of the simulation.
     * This method sets up the necessary components, parses configuration files,
     * initializes services, and starts the simulation.
     *
     * @param args Command-line arguments. The first argument is expected to be the path to the configuration file.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Configuration file path not provided.");
            return;
        }

      String folder = args[0].substring(0,args[0].lastIndexOf("\\") + 1);
        System.err.println(folder);
     LiDarDataBase liDarDataBase2=LiDarDataBase.getInstance(folder+"lidar_data.json");
     System.err.println("liDarDataBase2 data have read lidar_data.json!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
     
      Gson g = new GsonBuilder().setPrettyPrinting().create();
      try (FileReader reader = new FileReader(folder+"pose_data.json")) {
     Type employeeListType = new TypeToken<List<Pose>>(){}.getType();
     List<Pose> poses = g.fromJson(reader,employeeListType);
        System.out.println("poses have read pose_data.json!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
     } catch (IOException e) {
     e.printStackTrace();
     }

     Gson g2 = new GsonBuilder().setPrettyPrinting().create();
     try (FileReader reader = new FileReader(folder +"configuration_file.json")) {
        Configuration confi = g2.fromJson(reader,Configuration.class);
        System.out.println("confi have read configuration_file.json!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
     } catch (IOException e) {
        e.printStackTrace();
     }

     
     Gson g3 = new GsonBuilder().setPrettyPrinting().create();
     try (FileReader reader = new FileReader(folder+ "camera_data.json")) {
         // Define the type to parse the JSON structure
         Type type = new TypeToken<Map<String, List<StampedDetectedObjects>>>() {}.getType();
         Map<String, List<StampedDetectedObjects>> cameraData = g3.fromJson(reader, type);
         // Iterate over all cameras and their data
         for (Map.Entry<String, List<StampedDetectedObjects>> entry : cameraData.entrySet()) {
             String cameraName = entry.getKey();
             List<StampedDetectedObjects> detectedObjects = entry.getValue();
         }
         System.out.println("cameraData have read camera_data.json!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
     } catch (IOException e) {
         e.printStackTrace();
     }


    }
}

