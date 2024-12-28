package bgu.spl.mics.application.objects;

import java.util.List;
/**
 * Manages the fusion of sensor data for simultaneous localization and mapping (SLAM).
 * Combines data from multiple sensors (e.g., LiDAR, camera) to build and update a global map.
 * Implements the Singleton pattern to ensure a single instance of FusionSlam exists.
 */
public class FusionSlam {
    public LandMark[] landmarks;
    public List<Pose> poses;
    // Singleton instance holder
    public static class FusionSlamHolder {
        // TODO: Implement singleton instance logic.
    }
}
