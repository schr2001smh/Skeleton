package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the robot's GPS and IMU system.
 * Provides information about the robot's position and movement.
 */
public class GPSIMU {
    private int currentTick;
    private STATUS status;
    private List<Pose> poses;

    public GPSIMU(int currentTick) {
        this.currentTick = currentTick;
        this.status = STATUS.UP;
        this.poses = new ArrayList<>();
    }

    public GPSIMU(int currentTick, STATUS status, List<Pose> poses) {
        this.currentTick = currentTick;
        this.status = status;
        this.poses = poses;
    }

    public void addPose(Pose pose) {
        poses.add(pose);
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public List<Pose> getPoses() {
        return poses;
    }

    public void setPoses(List<Pose> poses) {
        this.poses = poses;
    }
}
