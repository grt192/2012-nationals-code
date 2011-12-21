package com.grt192.sensor.canjaguar;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.core.Sensor;
import com.grt192.event.component.JagPotentiometerEvent;
import com.grt192.event.component.JagPotentiometerListener;
import edu.wpi.first.wpilibj.PIDSource;
import java.util.Vector;

/**
 * A <code>GRTJagPotentiometer</code> polls the Jag for position, from the
 * jaguar control IO.
 * In order to read data, the jaguar must be notified what sensors to poll,
 * using <code>setPositionSensor()</code> from the <code>GRTCANJaguar</code> class.
 *
 * <code>GRTCANJaguar</code> also automatically stores its associated
 * <code>GRTJagPotentiometer</code>, using <code>GRTCANJaguar</code>'s
 * <code>getEncoder()</code>.
 * 
 * Because the CANJaguar doesn't accept reading speed from a potentiometer,
 * it is calculated here.
 *
 * @see GRTCANJaguar
 * @author Data, ajc
 */
public class GRTJagPotentiometer extends Sensor implements PIDSource {

    /** keys for getState() **/
    public static final String DISTANCE = "Distance";
    public static final String DIRECTION = "Direction";
    public static final String STOPPED = "Stopped";
    public static final String SPEED = "Speed";
    public static final String TIME = "Time";
    private static final double MS_PER_SECOND = 1000;
    //source
    private final GRTCANJaguar jaguar;
    private Vector potentiometerListeners;

    /**
     * Called automatically from GRTCANJaguar's <code>getPotentiometer()</code> method.
     * Therefore use <code>getPotentiometer()</code>, not this.
     * @see GRTCANJaguar
     */
    public GRTJagPotentiometer(GRTCANJaguar jag, int pollTime, String id) {
        this.jaguar = jag;
        this.id = id;
        setSleepTime(pollTime);
        potentiometerListeners = new Vector();
    }

    /**
     * Precondition: new time and distance have been saved
     * Calculates speed, as a potentiometer is not a valid speed sensor.
     * @return speed
     */
    private double getSpeed(double prevTime, double prevDistance) {
        //delta Distance / delta Time
        return ((getState(DISTANCE) - prevDistance) /
                (getState(TIME) - prevTime)) * MS_PER_SECOND;
    }

    public void poll() {
        double prevTime = getState(TIME);
        double prevDistance = getState(DISTANCE);
        double prevDirection = getState(DIRECTION);

        setState(TIME, System.currentTimeMillis());
        setState(DISTANCE, jaguar.getPosition());
        setState(SPEED, getSpeed(prevTime, prevDistance));
        setState(DIRECTION, (getState(SPEED) >= 0));//TODO deadzone here?
        setState(STOPPED, (jaguar.getSpeed() == 0));

        if (prevDirection != getState(STOPPED)) {
            if (getState(STOPPED) == Sensor.TRUE) {
                this.notifyPotentiometerStopped();
            } else {
                this.notifyPotentiometerStarted();
            }
        }
        if (prevDirection != getState(DIRECTION)) {
            this.notifyDirectionChanged();
        }
        if (prevDistance != getState(DISTANCE)) {
            this.notifyPotentiometerChange();
        }

    }

    /**
     * Configure the number of turns on the potentiometer.
     * 
     * There is no special support for continuous turn potentiometers.
     * Only integer numbers of turns are supported.
     * @param turns a natural number
     */
    public void setResolution(int turns) {
        jaguar.setPotentiometerTurns(turns);
    }

    /** 
     * Adds a provided <code>JagPotentiometerListener</code> to send events,
     * on event
     * @param a A <code>JagPotentiometerListener</code> to send events to
     */
    public void addPotentiometerListener(JagPotentiometerListener a) {
        potentiometerListeners.addElement(a);
    }

    /** 
     * Removes a provided <code>JagPotentiometerListener</code> to stop sending
     * events, on event
     * @param a A <code>JagPotentiometerListener</code> to notify
     */
    public void removePotentiometerListener(JagPotentiometerListener a) {
        potentiometerListeners.removeElement(a);
    }

    /**
     * Notifies all <code>JagPotentiometerListener</code>s that potentiometer 
     * rotation has changed
     */
    protected void notifyPotentiometerChange() {
        for (int i = 0; i < potentiometerListeners.size(); i++) {
            ((JagPotentiometerListener) potentiometerListeners.elementAt(i))
                    .countDidChange(new JagPotentiometerEvent(this,
                    JagPotentiometerEvent.DISTANCE, getState(DISTANCE),
                    (getState(DISTANCE) == Sensor.TRUE)));
        }
    }

    /**
     * Notifies all <code>JagPotentiometerListener</code>s that potentiometer
     * rotation has started
     */
    protected void notifyPotentiometerStarted() {
        for (int i = 0; i < potentiometerListeners.size(); i++) {
            ((JagPotentiometerListener) potentiometerListeners.elementAt(i))
                    .rotationDidStart(new JagPotentiometerEvent(this,
                    JagPotentiometerEvent.STARTED, getState(DISTANCE),
                    (getState(DISTANCE) == Sensor.TRUE)));
        }
    }

    /**
     * Notifies all <code>JagPotentiometerListener</code>s that potentiometer
     * rotation has stopped
     */
    protected void notifyPotentiometerStopped() {
        for (int i = 0; i < potentiometerListeners.size(); i++) {
            ((JagPotentiometerListener) potentiometerListeners.elementAt(i))
                    .rotationDidStop(new JagPotentiometerEvent(this,
                    JagPotentiometerEvent.STOPPED, getState(DISTANCE),
                    (getState(DISTANCE) == Sensor.TRUE)));
        }
    }

    /**
     * Notifies all <code>JagPotentiometerListener</code>s that potentiometer
     * direction has changed
     */
    protected void notifyDirectionChanged() {
        for (int i = 0; i < potentiometerListeners.size(); i++) {
            ((JagPotentiometerListener) potentiometerListeners.elementAt(i))
                    .changedDirection(new JagPotentiometerEvent(this,
                    JagPotentiometerEvent.DIRECTION, getState(DISTANCE),
                    (getState(DISTANCE) == Sensor.TRUE)));
        }
    }

    public double pidGet() {
        return getState(DISTANCE);
    }
}
