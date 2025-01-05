package bgu.spl.mics.application.services;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.FusionSlam;
import bgu.spl.mics.application.objects.LandMark;
import bgu.spl.mics.Output;
import bgu.spl.mics.application.services.CameraService;
import bgu.spl.mics.application.services.LiDarService;

/**
 * TimeService acts as the global timer for the system, broadcasting TickBroadcast messages
 * at regular intervals and controlling the simulation's duration.
 */
public class TimeService extends MicroService {
    private int TickTime;
    private int Duration;
    private int StartTime = 0;
    private String filePath;


    /**
     * Constructor for TimeService.
     *
     * @param TickTime  The duration of each tick in seconds.
     * @param Duration  The total number of ticks before the service terminates.
     */
    public TimeService(int TickTime, int Duration, String filePath) {
        super("timeService");
        this.TickTime = TickTime;
        this.Duration = Duration;
        this.filePath = filePath;
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
             System.out.println(StartTime);
             StartTime=StartTime+TickTime;
             
             
             try {
                 Thread.sleep(TickTime * 1000); // 
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
        }
        terminate();
        generateOutput();
        
    }

    public void setFilePath(String filepath){
        this.filePath=filePath;
    }
    private void generateOutput() {
        Output output = new Output();
        // Set the necessary fields for the output instance
        output.setSystemRuntime(Duration);
        FusionSlam fusion = FusionSlam.getInstance();
        System.out.println("fusion: " + fusion.getLandmarks());
        output.setNumLandmarks(fusion.getLandmarks().size());
        output.setLandmarks(fusion.getLandmarks().toArray());

        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        output.setNumTrackedObjects(messageBus.getNumTrackedObjects());
        output.setNumDetectedObjects(messageBus.getNumDetectedObjects());
        
        // ...set other fields as needed...
        output.generateOutputJson(filePath.substring(0, filePath.lastIndexOf('\\')));
    }
}
