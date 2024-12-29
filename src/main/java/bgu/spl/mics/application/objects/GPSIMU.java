package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the robot's GPS and IMU system.
 * Provides information about the robot's position and movement.
 */
public class GPSIMU {
    public int currentTick;
    public STATUS status;
    public List<Pose> poses;

    public GPSIMU(int currentTick) {
        this.currentTick = currentTick;
        this.status = STATUS.UP;
        this.poses = new ArrayList<>();
    }

    public void addPose(Pose pose) {
        poses.add(pose);
    }
}
