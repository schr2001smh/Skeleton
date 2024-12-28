package bgu.spl.mics.application.messages;
import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private int currentTick;

    public TickBroadcast(int currentTick) {
        this.currentTick = 0;
    }

    public int getTick() {
        return currentTick;
    }

}
