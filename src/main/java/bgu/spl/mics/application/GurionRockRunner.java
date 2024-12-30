package bgu.spl.mics.application;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import bgu.spl.mics.Configuration;
import bgu.spl.mics.application.objects.LiDarDataBase;
import bgu.spl.mics.application.objects.StampedCloudPoints;

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
        LiDarDataBase liDarDataBase2=LiDarDataBase.getInstance("example_input_2/lidar_data.json");
      Gson g = new GsonBuilder().setPrettyPrinting().create();
      try (FileReader reader = new FileReader("example_input_2/lidar_data.json")) {
     Type employeeListType = new TypeToken<List<StampedCloudPoints>>(){}.getType();
     List<StampedCloudPoints> employeeList = g.fromJson(reader,employeeListType);
     for (StampedCloudPoints employee : employeeList) {
     System.out.println(employee);
     LiDarDataBase liDarDataBase=LiDarDataBase.getInstance("example_input_2/lidar_data.json");
     liDarDataBase.setCloudPoints(employeeList);
     }
     } catch (IOException e) {
     e.printStackTrace();
     }

     
    //  Gson g = new GsonBuilder().setPrettyPrinting().create();
    //  try (FileReader reader = new FileReader("example_input_2/lidar_data.json")) {
    // // Define the type for the list of employees
    // Type employeeListType = new TypeToken<LiDarDataBase>(){}.getType();
    //    // Deserialize JSON to list of employees
    //    LiDarDataBase employeeList = g.fromJson(reader,employeeListType);
    // // Use the employee data
    // System.out.println(employeeList);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    
        String configFilePath = args[0];
        System.err.println("file not empty");
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(configFilePath)) {
            Configuration config = gson.fromJson(reader, Configuration.class);
            
            // TODO: Initialize system components and services using config, cameraData, lidarData, and poseData.
            // TODO: Start the simulation.
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}

