package bgu.spl.mics.application.messages;
import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private int currentTick;
    private int TickTime;

    public TickBroadcast(int currentTick, int TickTime) {
        this.currentTick = currentTick;
    }

    public int getTick() {
        return currentTick;
    }
    public int getTickTime() {
        return TickTime;
    }

}
