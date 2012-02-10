/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import core.PollingSensor;
import edu.wpi.first.wpilibj.Encoder;
import event.EncoderEvent;
import event.EncoderListener;
import java.util.Vector;

/**
 *
 * @author gerberduffy
 * @author nadia
 */
public class GRTEncoder extends PollingSensor {

    private Encoder rotaryEncoder;
    private double distancePerPulse;
    public static final int KEY_DISTANCE = 0;
    public static final int KEY_DEGREES = 1;
    public static final int KEY_DIRECTION = 2;
    public static final int KEY_PERIOD = 3;
    public static final int NUM_DATA = 4;
    private Vector listeners;
    
    private static final int SAMPLE_SIZE = 16;
    
    public static final double CLOCKWISE = 1;
    public static final double CTRCLKWISE = 0;
    
    private double distance;
    private double direction;
    private double angle;
    private double rate;
    
    private double previousRateAccumulated;

    public GRTEncoder(int channelA, int channelB, double pulseDistance, int pollTime, String id) {
        super(id, pollTime, NUM_DATA);
        rotaryEncoder = new Encoder(channelA, channelB);
        rotaryEncoder.start();
        rotaryEncoder.setDistancePerPulse(pulseDistance);

        distancePerPulse = pulseDistance;
        listeners = new Vector();
    }

    protected void poll() {
        setState(KEY_DISTANCE, rotaryEncoder.getDistance());
        setState(KEY_DEGREES, rotaryEncoder.getDistance() / distancePerPulse);
        setState(KEY_DIRECTION, rotaryEncoder.getDirection() ? CLOCKWISE : CTRCLKWISE);
        setState(KEY_PERIOD, rotaryEncoder.getPeriod());
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        EncoderEvent e;
        switch (id) {
            case KEY_DEGREES:
                angle = newDatum;
                e = new EncoderEvent(this, id, distance, angle, direction, rate);
                for (int i = 0; i < listeners.size(); i++) {
                    ((EncoderListener) listeners.elementAt(i)).degreeChanged(e);
                }
                break;
            case KEY_DIRECTION:
                direction = newDatum;
                e = new EncoderEvent(this, id, distance, angle, direction, rate);
                for (int i = 0; i < listeners.size(); i++) {
                    ((EncoderListener) listeners.elementAt(i)).directionChanged(e);
                }
                break;
            case KEY_DISTANCE:
                distance = newDatum;
                e = new EncoderEvent(this, id, distance, angle, direction, rate);
                for (int i = 0; i < listeners.size(); i++) {
                    ((EncoderListener) listeners.elementAt(i)).distanceChanged(e);
                }
                break;
            case KEY_PERIOD:
                rate = newDatum;
                e = new EncoderEvent(this, id, distance, angle, direction, rate);
                for (int i = 0; i < listeners.size(); i++) {
                    ((EncoderListener) listeners.elementAt(i)).rateChanged(e);
                    
                }
        }
    }

    private double getRate(){
        previousRateAccumulated = (previousRateAccumulated * (SAMPLE_SIZE - 1) + rotaryEncoder.getRate()) / SAMPLE_SIZE;
        return previousRateAccumulated;
    }

    public void addEncoderListener(EncoderListener l) {
        listeners.addElement(l);
    }

    public void removeEncoderListener(EncoderListener l) {
        listeners.removeElement(l);
    }
}
