package bgu.spl.mics.application.services;
import java.util.concurrent.TimeUnit;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;

/**
 * TimeService acts as the global timer for the system, broadcasting TickBroadcast messages
 * at regular intervals and controlling the simulation's duration.
 */
public class TimeService extends MicroService {
    private int TickTime;
    private int Duration;
    private int endtime
    ;
    private  MessageBusImpl messageBus = MessageBusImpl.getInstance();

    /**
     * Constructor for TimeService.
     *
     * @param TickTime  The duration of each tick in milliseconds.
     * @param Duration  The total number of ticks before the service terminates.
     */
    public TimeService(int TickTime, int Duration) {
        super("Change_This_Name");
        this.TickTime = TickTime;
        this.Duration = Duration;
        this.endtime = Duration+=TickTime;
        
    }

    /**
     * Initializes the TimeService.
     * Starts broadcasting TickBroadcast messages and terminates after the specified duration.
     */
    @Override
    protected void initialize() {
        System.err.println("TimeService started:  "+ TickTime + " " + Duration);
        while (TickTime <= endtime) {
            try {
               messageBus.sendBroadcast(new TickBroadcast(TickTime));
                TickTime++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        terminate();
    }
}
