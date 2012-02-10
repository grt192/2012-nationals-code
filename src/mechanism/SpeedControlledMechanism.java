/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import sensor.GRTEncoder;
import event.EncoderListener;
import core.PollingSensor;
import controller.GRTPIDController;
import event.EncoderEvent;

/**
 *
 * @author calvin
 */
public class SpeedControlledMechanism extends PollingSensor implements EncoderListener{
    private GRTEncoder encoder;
    private GRTPIDController pid;
    private double speed;
    private double targetSpeed;
    private int pollTime;
    private static double MAX_RATE;
    
    public static final int NUM_DATA = 1;
    public SpeedControlledMechanism(GRTEncoder encoder,
            double p, double i, double d,
            double minOutput, double maxOutput,
            int pollTime, String name, double maxRate){
        super(name, pollTime, NUM_DATA);
        MAX_RATE = maxRate;
        this.pollTime = pollTime;
        pid.setPID(p, i, d);
        pid.setOutputRange(minOutput, maxOutput);
    }

    protected void poll() {
        pid.update(speed, targetSpeed, pollTime);
    }

    protected void setTarget(double target){
        this.targetSpeed = target;
    }
    
    protected void notifyListeners(int id, double oldDatum, double newDatum) {
    }
    
    public void startListening(){
        encoder.addEncoderListener(this);
    }
    
    public void stopListening(){
        encoder.removeEncoderListener(this);
    }
    
    public void directionChanged(EncoderEvent e) {
        speed = e.getRate();
    }

    public void degreeChanged(EncoderEvent e) {
        speed = e.getRate();
    }

    public void distanceChanged(EncoderEvent e) {
        speed = e.getRate();
    }

    public void rateChanged(EncoderEvent e) {
        speed = e.getRate();
    }
    
}
