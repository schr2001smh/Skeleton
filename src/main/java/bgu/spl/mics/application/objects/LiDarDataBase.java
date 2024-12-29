package bgu.spl.mics.application.objects;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            instance.cloudPoints = gson.fromJson(reader, List.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
