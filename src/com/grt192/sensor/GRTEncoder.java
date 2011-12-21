package com.grt192.sensor;

import java.util.Vector;

import com.grt192.core.Sensor;
import com.grt192.event.component.EncoderEvent;
import com.grt192.event.component.EncoderListener;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * Quadrature Encoder
 * @author grtstudent
 */
public class GRTEncoder extends Sensor implements PIDSource{
    private Encoder rotaryEncoder;
    private Vector encoderListeners;
    public static final double DIST_PER_PULSE = Math.PI * 16.0/(360 * 4 *12);

    public GRTEncoder(int channela, int channelb, int pollTime, 
                        String id){
        this(channela, channelb, pollTime, DIST_PER_PULSE, id);
    }

    public GRTEncoder(int channela, int channelb, int pollTime, 
                                double pulseDistance, String id){
        rotaryEncoder = new Encoder(channela, channelb);
        rotaryEncoder.start();
        rotaryEncoder.setDistancePerPulse(pulseDistance);
        setSleepTime(pollTime);
        encoderListeners = new Vector();
        this.id = id;
    }

    public void poll() {
        double previous = getState("Direction");
        setState("Direction", rotaryEncoder.getDirection());
        if(previous != getState("Direction"))
            this.notifyDirectionChanged();
        
        setState("Rate", rotaryEncoder.getRate());

        setState("Count", rotaryEncoder.get());
        
        previous = getState("Distance");
        setState("Previous", previous);
        setState("Distance", rotaryEncoder.getDistance());
        if(previous != getState("Distance"))
            this.notifyEncoderChange();

        previous = getState("Stopped");
        setState("Stopped", rotaryEncoder.getStopped());
        if(previous != getState("Stopped"))
            if(getState("Stopped") == Sensor.TRUE)
                this.notifyEncoderStopped();
            else
                this.notifyEncoderStarted();

    }

    public void addEncoderListener(EncoderListener a){
        encoderListeners.addElement(a);
    }
    public void removeEncoderListener(EncoderListener a){
        encoderListeners.removeElement(a);
    }

    protected void notifyEncoderChange(){
        for(int i=0; i<encoderListeners.size(); i++){
            ((EncoderListener)
                    encoderListeners.elementAt(i)).countDidChange(
                            new EncoderEvent(this,
                                                   EncoderEvent.DISTANCE,
                                                   getState("Distance"),
                                                   (getState("Direction")
                                                            == Sensor.TRUE)
                                                   )
                            );
        }
    }

    protected void notifyEncoderStarted(){
        for(int i=0; i<encoderListeners.size(); i++){
            ((EncoderListener)
                    encoderListeners.elementAt(i)).rotationDidStart(
                            new EncoderEvent(this,
                                                   EncoderEvent.STOPPED,
                                                   getState("Distance"),
                                                   (getState("Direction")
                                                            == Sensor.TRUE)
                                                   )
                            );
        }
    }

    protected void notifyEncoderStopped(){
        for(int i=0; i<encoderListeners.size(); i++){
            ((EncoderListener)
                    encoderListeners.elementAt(i)).rotationDidStop(
                            new EncoderEvent(this,
                                                   EncoderEvent.STOPPED,
                                                   getState("Distance"),
                                                   (getState("Direction")
                                                            == Sensor.TRUE)
                                                   )
                            );
        }
    }
    protected void notifyDirectionChanged(){
        for(int i=0; i<encoderListeners.size(); i++){
            ((EncoderListener)
                    encoderListeners.elementAt(i)).rotationDidStop(
                            new EncoderEvent(this,
                                                   EncoderEvent.DIRECTION,
                                                   getState("Distance"),
                                                   (getState("Direction")
                                                            == Sensor.TRUE)
                                                   )
                            );
        }
    }

	public double pidGet() {
		return getState("Distance");
	}

}
