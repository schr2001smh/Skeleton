package bgu.spl.mics.application.services;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
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
    public boolean terminate = false;
    MessageBusImpl messageBus = MessageBusImpl.getInstance();


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
        Output output = new Output();
        output.getInstance(filePath);
    }

    /**
     * Initializes the TimeService.
     * Starts broadcasting TickBroadcast messages and terminates after the specified duration.
     */
    @Override
    protected void initialize() {
        
        subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast brod) -> {
        if (messageBus.getServiceCounter()<=2) {
            generateOutput();
            terminate();
            terminate=true;
        }

       });
       subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast brod) -> {
        terminate();
        terminate=true;
     });
        
        subscribeBroadcast(TickBroadcast.class, (TickBroadcast brod) -> {
            Output output = Output.getInstance();
            output.setSystemRuntime(StartTime -1 );
            FusionSlam fusion = FusionSlam.getInstance();
            output.setNumLandmarks(fusion.getLandmarks().size());
            output.setLandmarks(fusion.getLandmarks().toArray());
            output.setNumTrackedObjects(messageBus.getNumTrackedObjects());
            output.setNumDetectedObjects(messageBus.getNumDetectedObjects());

            if (!terminate) {
                sendBroadcast(new TickBroadcast(StartTime));
                System.out.println(StartTime);
                
                StartTime=StartTime+TickTime;
                try {
                    Thread.sleep(TickTime * 1000); // 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                generateOutput();
                terminate();
            }
       });

       sendBroadcast(new TickBroadcast(0));
    }

    public void setFilePath(String filePath){
        this.filePath=filePath;
    }
    
    private void generateOutput() {
        
        Output output = new Output();
        // Set the necessary fields for the output instance
        
        
        // ...set other fields as needed...
        output.generateOutputJson(filePath.substring(0, filePath.lastIndexOf('\\')));
    }
}
