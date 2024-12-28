package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;

public class PoseEvent implements Event<Integer> {
    private int x;
    private int y;

    public PoseEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
