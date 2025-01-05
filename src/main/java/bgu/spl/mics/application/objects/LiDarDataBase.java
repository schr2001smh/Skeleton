package bgu.spl.mics.application.objects;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * LiDarDataBase is a singleton class responsible for managing LiDAR data.
 * It provides access to cloud point data and other relevant information for tracked objects.
 */
public class LiDarDataBase {

    private List<StampedCloudPoints> cloudPoints;

    // Private constructor to prevent instantiation
    private LiDarDataBase() {
        // Initialize cloudPoints
    }

    // Singleton instance holder
    private static class LiDarDataBaseHolder {
        private static final LiDarDataBase INSTANCE = new LiDarDataBase();
    }

    /**
     * Returns the singleton instance of LiDarDataBase.
     *
     * @param filePath The path to the LiDAR data file.
     * @return The singleton instance of LiDarDataBase.
     */
    public static LiDarDataBase getInstance(String filePath) {
        LiDarDataBase instance = LiDarDataBaseHolder.INSTANCE;
      Gson g = new GsonBuilder().setPrettyPrinting().create();
      try (FileReader reader = new FileReader(filePath)) {
     Type employeeListType = new TypeToken<List<StampedCloudPoints>>(){}.getType();
     instance.cloudPoints = g.fromJson(reader,employeeListType);
     } catch (IOException e) {
     e.printStackTrace();
     }
        return instance;
    }
    public static LiDarDataBase getInstance() {
        return LiDarDataBaseHolder.INSTANCE;
    }
    public   List<List<Double>> getcloudpoints(int time,int lasttime, String id){
        for (StampedCloudPoints stampedCloudPoints : cloudPoints) {
            if (stampedCloudPoints.getTime() <= time && stampedCloudPoints.getTime() >= lasttime && stampedCloudPoints.getId().equals(id)) { 
                System.out.println("database is thinking that the id is: "+stampedCloudPoints.getId() + " and the time is: " 
                +stampedCloudPoints.getTime() + " and the cloud points are: "+stampedCloudPoints.getCloudPoints());
                return stampedCloudPoints.getCloudPoints();
            }

        }
        return null;
    }

    public  List<DetectedObject> getDetectedObjects(int time) {
        List<DetectedObject> detectedObjects = new ArrayList<>();
        for (StampedCloudPoints stampedCloudPoints : cloudPoints) {
            if (stampedCloudPoints.getTime() == time) {
                // Assuming there's a method to get description from another source
                String description = getDescriptionForId(stampedCloudPoints.getId());
                detectedObjects.add(new DetectedObject(stampedCloudPoints.getId(), description));
            }
        }
        return detectedObjects;
    }

    private String getDescriptionForId(String id) {
        // Implement logic to get description for the given id
        return "Description for " + id;
    }

    public void setCloudPoints(List<StampedCloudPoints> cloudPoints) {
        this.cloudPoints = cloudPoints;
    }

    public List<StampedCloudPoints> getCloudPoints() {
        return cloudPoints;
    }
    
    public void addCloudPoint(StampedCloudPoints cloudPoint) {
        cloudPoints.add(cloudPoint);
    }
    

    @Override
    public String toString() {
        return "LiDarDataBase{" +'\'' +
                "cloudPoints=" + cloudPoints +
                '}';
    }

}
