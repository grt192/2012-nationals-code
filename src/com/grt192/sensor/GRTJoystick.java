/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.ButtonListener;
import com.grt192.event.SensorEvent;
import com.grt192.event.component.JoystickEvent;
import com.grt192.event.component.JoystickListener;
import edu.wpi.first.wpilibj.Joystick;
import java.util.Vector;

/**
 * A driverstation-connected Joystick/Game Controller driver
 * @author Student
 */
public class GRTJoystick extends Sensor {

    public static final int NUM_OF_BUTTONS = 10;
    public static final double BUTTON_PRESSED = Sensor.TRUE;
    public static final double BUTTON_UNPRESSED = Sensor.FALSE;

    private Joystick joystick;
    private Vector buttonListeners;
    private Vector joystickListeners;

    public GRTJoystick(int channel, int pollTime, String id) {
        joystick = new Joystick(channel);
        setSleepTime(pollTime);
        setState("xValue", 0.0);
        setState("yValue", 0.0);
        setState("zValue", 0.0);
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            setState("Button" + i, FALSE);
        }
        setState("Throttle", 0.0);
        buttonListeners = new Vector();
        joystickListeners = new Vector();
        this.id = id;
    }

    public void poll() {
        double previousState;
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            String button = "Button" + i;
            previousState = getState(button);
            setState(button, (joystick.getRawButton(i)) ? TRUE : FALSE);
            notifyButtonListeners(button, previousState);
        }
        previousState = getState("xValue");
        setState("xValue", joystick.getX());
        if (getState("xValue") != previousState) {
            notifyXAxisChange();
        }

        previousState = getState("yValue");
        setState("yValue", joystick.getY());
        if (getState("yValue") != previousState) {
            notifyYAxisChange();
        }

        previousState = getState("zValue");
        setState("zValue", joystick.getZ());
        if (getState("zValue") != previousState) {
            notifyZAxisChange();
        }

        previousState = getState("JoystickAngle");
        setState("joystickAngle", joystick.getDirectionDegrees());

        previousState = getState("Throttle");
        setState("Throttle", joystick.getThrottle());
        if (getState("Throttle") != previousState) {
            notifyThrottleChange();
        }
    }

    public String toString() {
        return "Joystick";
    }

    public Vector getButtonListeners() {
        return buttonListeners;
    }

    public void addButtonListener(ButtonListener b) {
        buttonListeners.addElement(b);
    }

    public void removeButtonListener(ButtonListener b) {
        buttonListeners.removeElement(b);
    }

    private void notifyButtonListeners(String button, double previousState) {
        for (int i = 0; i < buttonListeners.size(); i++) {
            if (getState(button) == TRUE && getState(button) != previousState) {
                ((ButtonListener) buttonListeners.elementAt(i)).buttonDown(
                        new SensorEvent(this,
                        SensorEvent.DATA_AVAILABLE,
                        this.state), button);
            } else if (getState(button) != TRUE && getState(button) != previousState) {
                ((ButtonListener) buttonListeners.elementAt(i)).buttonUp(
                        new SensorEvent(this,
                        SensorEvent.DATA_AVAILABLE,
                        this.state),
                        button);
            }
        }
    }

    protected void notifyXAxisChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((JoystickListener) joystickListeners.elementAt(i)).xAxisMoved(
                    new JoystickEvent(this,
                    JoystickEvent.DEFAULT,
                    getState("xValue")));
        }
    }

    protected void notifyYAxisChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((JoystickListener) joystickListeners.elementAt(i)).yAxisMoved(
                    new JoystickEvent(this,
                    JoystickEvent.DEFAULT,
                    getState("yValue")));
        }
    }

    protected void notifyZAxisChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((JoystickListener) joystickListeners.elementAt(i)).zAxisMoved(
                    new JoystickEvent(this,
                    JoystickEvent.DEFAULT,
                    getState("zValue")));
        }
    }

    protected void notifyThrottleChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((JoystickListener) joystickListeners.elementAt(i)).throttleMoved(
                    new JoystickEvent(this,
                    JoystickEvent.DEFAULT,
                    getState("Throttle")));
        }
    }

    public String getId() {
        return id;
    }

    public Vector getJoystickListeners() {
        return joystickListeners;
    }

    public void addJoystickListener(JoystickListener l) {
        joystickListeners.addElement(l);
    }

    public void setId(String id) {
        this.id = id;
    }
}
