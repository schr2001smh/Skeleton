package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;
/**
 * Manages the fusion of sensor data for simultaneous localization and mapping (SLAM).
 * Combines data from multiple sensors (e.g., LiDAR, camera) to build and update a global map.
 * Implements the Singleton pattern to ensure a single instance of FusionSlam exists.
 */
public class FusionSlam {
    public List<LandMark> landmarks;
    public List<Pose> poses;

    // Private constructor to prevent instantiation
    private FusionSlam() {
        landmarks = new ArrayList<>();
        poses = new ArrayList<>();
    }

    // Singleton instance holder
    public static class FusionSlamHolder {
        private static final FusionSlam INSTANCE = new FusionSlam();
    }

    // Public method to provide access to the singleton instance
    public static FusionSlam getInstance() {
        return FusionSlamHolder.INSTANCE;
    }
}
