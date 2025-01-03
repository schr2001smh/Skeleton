package bgu.spl.mics.application.objects;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * LiDarDataBase is a singleton class responsible for managing LiDAR data.
 * It provides access to cloud point data and other relevant information for tracked objects.
 */
public class LiDarDataBase {

    public List<StampedCloudPoints> cloudPoints;

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
    public synchronized  List<List<Double>> getcloudpoints(int time,int lasttime, String id){
        for (StampedCloudPoints stampedCloudPoints : cloudPoints) {

            if (stampedCloudPoints.getTime() <=time && stampedCloudPoints.getTime() >lasttime && stampedCloudPoints.getId().equals(id)) {
                return stampedCloudPoints.getCloudPoints();
            }
        }
        return null;
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
