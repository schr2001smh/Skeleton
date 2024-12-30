package bgu.spl.mics.application.objects;

/**
 * Represents the robot's pose (position and orientation) in the environment.
 * Includes x, y coordinates and the yaw angle relative to a global coordinate system.
 */
public class Pose {
    public float x;
    public float y;
    public float yaw;
    public int time;

    public Pose(float x, float y, float yaw, int time) {
        this.x = x;
        this.y = y;
        this.yaw = yaw;
        this.time = time;
    }
    public Pose(){}; // For serialization
    public float getX(){
        return x;
    }
    public void setX(float x){
        this.x=x;
    }
    public float getY(){
        return y;
    }   
    public void setY(float y){
        this.y=y;
    }   
    public float getYaw(){
        return yaw;
    }
    public void setYaw(float yaw){
        this.yaw=yaw;
    }
    public int getTime(){
        return time;
    }
    public void setTime(int time){
        this.time=time;
    }
    @Override
    public String toString() {
        return "time: " + time + '\'' +
         ", x: " + x + '\''+
         ", y: " + y + '\''+
         ", yaw: " + yaw
        ;
    }
    // "time": 3,
    // "x": -5.7074,
    // "y": 0.1484,
    // "yaw": -92.65
}
