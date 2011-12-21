package com.grt192.sensor.canjaguar;

import java.util.Vector;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.core.Sensor;
import com.grt192.event.component.EncoderEvent;
import com.grt192.event.component.JagEncoderEvent;
import com.grt192.event.component.JagEncoderListener;

import edu.wpi.first.wpilibj.PIDSource;

/**
 * A GRTJagEncoder polls the CANJaguar for Speed and position,
 * from the jaguar control IO. In order to read data, the jaguar
 * must be notified what sensors to poll, by using
 * <code>setSpeedSensor()</code> and <code>setPositionSensor()</code>,
 * from the GRTCANJaguar class.
 *
 * GRTCANJaguar also automatically stores its associated GRTJagEncoder,
 * using GRTCANJaguar's <code>getEncoder()</code>.
 * 
 * @see GRTCANJaguar
 * @author ajc
 */
public class GRTJagEncoder extends Sensor implements PIDSource {

    /** keys for getState() **/
    public static final String DISTANCE = "Distance";
    public static final String DIRECTION = "Direction";
    public static final String STOPPED = "Stopped";
    public static final String SPEED = "Speed";
    //source
    private final GRTCANJaguar jaguar;
    private Vector encoderListeners;

    /**
     * Called automatically from GRTCANJaguar's <code>getEncoder()</code> method.
     * Therefore use <code>getEncoder()</code>, not this.
     * @see GRTCANJaguar
     */
    public GRTJagEncoder(GRTCANJaguar jag, int pollTime, String id) {
        this.id = id;
        setSleepTime(pollTime);
        this.jaguar = jag;
        encoderListeners = new Vector();
    }

    public void poll() {
        double previous = getState(DIRECTION);
        setState(DIRECTION, (jaguar.getSpeed() >= 0));
        if (previous != getState(DIRECTION)) {
            this.notifyDirectionChanged();
        }

        setState(SPEED, jaguar.getSpeed());
        setState(DISTANCE, jaguar.getPosition());
        if (previous != getState(DISTANCE)) {
            this.notifyEncoderChange();
        }

        setState(STOPPED, (jaguar.getSpeed() == 0));
        if (previous != getState(STOPPED)) {
            if (getState(STOPPED) == Sensor.TRUE) {
                this.notifyEncoderStopped();
            } else {
                this.notifyEncoderStarted();
            }
        }

    }

    /**
     * Sets the encoder resolution in counts per revolution
     * @param  countsPerRev (360 for the e4p encoder)
     */
    public void setResolution(int countsPerRev) {
        jaguar.setEncoderResolution((short) (countsPerRev));
    }

    /**
     * adds a provided <code>JagEncoderListener</code> to send events, on event
     * @param a A <code>JagEncoderListener</code> to notify
     */
    public void addEncoderListener(JagEncoderListener a) {
        encoderListeners.addElement(a);
    }

    /**
     * removes a provided <code>JagEncoderListener</code> to stop sending
     * events, on event
     * @param a A <code>JagEncoderListener</code> to stop notifying
     */
    public void removeEncoderListener(JagEncoderListener a) {
        encoderListeners.removeElement(a);
    }

    /** Notifies all <code>JagEncoderListener</code>s that the count has changed */
    protected void notifyEncoderChange() {
        for (int i = 0; i < encoderListeners.size(); i++) {
            ((JagEncoderListener) encoderListeners.elementAt(i)).countDidChange(new JagEncoderEvent(this,
                    JagEncoderEvent.DISTANCE, getState(DISTANCE),
                    (getState(DIRECTION) == Sensor.TRUE)));
        }
    }

    /** Notifies all <code>JagEncoderListener</code>s that encoder rotation has started */
    protected void notifyEncoderStarted() {
        for (int i = 0; i < encoderListeners.size(); i++) {
            ((JagEncoderListener) encoderListeners.elementAt(i)).rotationDidStart(new JagEncoderEvent(this,
                    JagEncoderEvent.STARTED, getState(DISTANCE),
                    (getState(DIRECTION) == Sensor.TRUE)));
        }
    }

    /** Notifies all <code>JagEncoderListener</code>s that encoder rotation has stopped */
    protected void notifyEncoderStopped() {
        for (int i = 0; i < encoderListeners.size(); i++) {
            ((JagEncoderListener) encoderListeners.elementAt(i)).rotationDidStop(new JagEncoderEvent(this,
                    JagEncoderEvent.STOPPED, getState(DISTANCE),
                    (getState(DIRECTION) == Sensor.TRUE)));
        }
    }

    /** Notifies all <code>JagEncoderListener</code>s that encoder direction has changed */
    protected void notifyDirectionChanged() {
        for (int i = 0; i < encoderListeners.size(); i++) {
            ((JagEncoderListener) encoderListeners.elementAt(i)).rotationDidStop(new JagEncoderEvent(this,
                    JagEncoderEvent.DIRECTION, getState(DISTANCE),
                    (getState(DIRECTION) == Sensor.TRUE)));
        }
    }

    public double pidGet() {
        return getState(DISTANCE);
    }
}
