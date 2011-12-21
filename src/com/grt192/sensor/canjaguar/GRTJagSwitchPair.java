package com.grt192.sensor.canjaguar;

import java.util.Vector;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.core.Sensor;
import com.grt192.event.component.JagSwitchEvent;
import com.grt192.event.component.JagSwitchListener;

/**
 * Polls the CANJaguar for switch states on the control IO.
 * 
 * @author ajc
 */
public class GRTJagSwitchPair extends Sensor {

    /** Keys for getState() */
    public static final String LEFT_SWITCH = "leftSwitch";
    public static final String RIGHT_SWITCH = "rightSwitch";
    //source
    private final GRTCANJaguar jaguar;
    private Vector switchListeners;

    /**
     * Called automatically from GRTCANJaguar's <code>getSwitches()</code> method.
     * Therefore use <code>getSwitches()</code>, not this.
     * @see GRTCANJaguar
     */
    public GRTJagSwitchPair(GRTCANJaguar jaguar, int pollTime, String id) {
        this.jaguar = jaguar;
        switchListeners = new Vector();
        this.id = id;
        this.setSleepTime(pollTime);
    }

    public void poll() {
        double previous = getState(LEFT_SWITCH);
        setState(LEFT_SWITCH, jaguar.getLeftLimitStatus());
        if (previous != getState(LEFT_SWITCH)) {
            notifyLeftSwitch(getState(LEFT_SWITCH) == Sensor.TRUE);
        }
        previous = getState(RIGHT_SWITCH);
        setState(RIGHT_SWITCH, jaguar.getRightLimitStatus());
        if (previous != getState(RIGHT_SWITCH)) {
            notifyLeftSwitch(getState(RIGHT_SWITCH) == Sensor.TRUE);
        }
    }

    /**
     * Starts notifying <code>JagSwitchListener</code> l for all events, on event
     * @param l <code>JagSwitchListener</code> to notify
     */
    public void addJagSwitchListener(JagSwitchListener a) {
        switchListeners.addElement(a);
    }

    /**
     * Stops notifying <code>JagSwitchListener</code> l for all events, on event
     * @param l <code>JagSwitchListener</code> to stop notifying
     */
    public void removeJagSwitchListener(JagSwitchListener a) {
        switchListeners.removeElement(a);
    }

    /**
     * Notifies all listeners that the left switch state has changed
     * @param pressed true if switch now pressed
     */
    protected void notifyLeftSwitch(boolean pressed) {
        JagSwitchEvent e = new JagSwitchEvent(this,
                (pressed ? JagSwitchEvent.LEFT_PRESSED : JagSwitchEvent.LEFT_RELEASED), RIGHT_SWITCH);
        for (int i = 0; i < switchListeners.size(); i++) {
            if (pressed) {
                ((JagSwitchListener) switchListeners.elementAt(i)).leftSwitchPressed(e);
            } else {
                ((JagSwitchListener) switchListeners.elementAt(i)).leftSwitchReleased(e);
            }
        }
    }

    /**
     * Notifies all listeners that the right switch state has changed
     * @param pressed true if switch now pressed
     */
    protected void notifyRightSwitch(boolean pressed) {
        JagSwitchEvent e = new JagSwitchEvent(this,
                (pressed ? JagSwitchEvent.RIGHT_PRESSED : JagSwitchEvent.RIGHT_RELEASED), RIGHT_SWITCH);
        for (int i = 0; i < switchListeners.size(); i++) {
            if (pressed) {
                ((JagSwitchListener) switchListeners.elementAt(i)).rightSwitchPressed(e);
            } else {
                ((JagSwitchListener) switchListeners.elementAt(i)).rightSwitchReleased(e);
            }
        }
    }
}
