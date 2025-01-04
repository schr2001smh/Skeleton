package bgu.spl.mics.application.objects;

/**
 * CloudPoint represents a specific point in a 3D space as detected by the LiDAR.
 * These points are used to generate a point cloud representing objects in the environment.
 */
public class CloudPoint {
    private double  x;
    private double y;

    public CloudPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public CloudPoint(){}; // For serialization
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    @Override
    public String toString() {
        return "x: " + x + '\'' +
         ", y: " + y + '\'' +
         "0.10400000214576721"
        ;
    }
}
