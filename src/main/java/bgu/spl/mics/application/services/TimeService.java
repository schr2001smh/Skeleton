package bgu.spl.mics.application.services;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;

/**
 * TimeService acts as the global timer for the system, broadcasting TickBroadcast messages
 * at regular intervals and controlling the simulation's duration.
 */
public class TimeService extends MicroService {
    private int TickTime;
    private int Duration;
    private int StartTime = 0;


    /**
     * Constructor for TimeService.
     *
     * @param TickTime  The duration of each tick in seconds.
     * @param Duration  The total number of ticks before the service terminates.
     */
    public TimeService(int TickTime, int Duration) {
        super("timeService");
        this.TickTime = TickTime;
        this.Duration = Duration;
        
    }

    /**
     * Initializes the TimeService.
     * Starts broadcasting TickBroadcast messages and terminates after the specified duration.
     */
    @Override
    protected void initialize() {
        System.out.println("TimeService started:  ticktime= "+ TickTime + " duration =" + Duration);

        while (StartTime < Duration) {
             sendBroadcast(new TickBroadcast(StartTime));
             StartTime=StartTime+TickTime;
             
             try {
                 Thread.sleep(TickTime * 1000); // 
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
              System.out.println(StartTime);
        }
        terminate();
    }
}
