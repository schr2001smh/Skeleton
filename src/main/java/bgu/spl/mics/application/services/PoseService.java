package bgu.spl.mics.application.services;

import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.GPSIMU;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;

/**
 * PoseService is responsible for maintaining the robot's current pose (position and orientation)
 * and broadcasting PoseEvents at every tick.
 */
public class PoseService extends MicroService {
    private GPSIMU gpsimu;
    private int tick;
    private MessageBusImpl messageBus = MessageBusImpl.getInstance();
    /**
     * Constructor for PoseService.
     *
     * @param gpsimu The GPSIMU object that provides the robot's pose data.
     */
    public PoseService(GPSIMU gpsimu) {
        super("PoseService started");
        this.gpsimu = gpsimu;
    }

    /**
     * Initializes the PoseService.
     * Subscribes to TickBroadcast and sends PoseEvents at every tick based on the current pose.
     */
    @Override
    protected void initialize() {
        

       subscribeBroadcast(TickBroadcast.class, (TickBroadcast brod) -> {
          this.tick = brod.getTick();
          if (messageBus.getServiceCounter()<=2) {
            terminate();
        }
       });

       subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast brod) -> {
        if (messageBus.getServiceCounter()<=2) {
            terminate();
        }
        
       });
     subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast brod) -> {
        terminate();
     });
    }
}
