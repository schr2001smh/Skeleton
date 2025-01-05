package bgu.spl.mics.application.objects;

import com.google.gson.annotations.SerializedName;

public class Pose {
    private double x, y, z;

    @SerializedName("yaw")
    private double orientation;

    public Pose(double x, double y, double z, double orientation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientation = orientation;
    }

    public Pose(){} // For serialization

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Pose{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + orientation +
                '}';
    }
}