/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.SensorEvent;
import com.grt192.event.component.ButtonListener;
import com.grt192.event.component.XboxJoystickListener;
import com.grt192.event.component.XboxJoystickEvent;
import edu.wpi.first.wpilibj.Joystick;
import java.util.Vector;

/**
 *
 * @author keshav
 */
public class GRTXboxController extends Sensor {

    public static final int NUM_OF_BUTTONS = 10;
    public static final double BUTTON_PRESSED = Sensor.TRUE;
    public static final double BUTTON_UNPRESSED = Sensor.FALSE;
    
    private Joystick joystick;
    private Vector buttonListeners;
    private Vector joystickListeners;

    public GRTXboxController(int channel, int pollTime, String id) {
        joystick = new Joystick(channel);
        setSleepTime(pollTime);
        setState("leftXValue", 0.0);
        setState("leftYValue", 0.0);
       // setState("zValue", 0.0);
        for (int i = 0; i <= NUM_OF_BUTTONS; i++) {
            setState("Button" + i, FALSE);
        }
        setState("Throttle", 0.0);
        buttonListeners = new Vector();
        joystickListeners = new Vector();
        this.id = id;
    }

    public void poll() {
        double previousState;
        for (int i = 0; i <= NUM_OF_BUTTONS; i++) {
            String button = "Button" + i;
            previousState = getState(button);
            setState(button, (joystick.getRawButton(i)) ? TRUE : FALSE);
            notifyButtonListeners(button, previousState);
        }
        previousState = getState("leftXValue");
        setState("leftXValue", joystick.getX());
        if (getState("leftXValue") != previousState) {
            notifyLeftXAxisChange();
        }

        previousState = getState("leftYValue");
        setState("leftYValue", joystick.getY());
        if (getState("leftYValue") != previousState) {
            notifyLeftYAxisChange();
        }

       previousState = getState("rightXValue");
        setState("rightXValue", joystick.getRawAxis(4));
        if (getState("rightXValue") != previousState) {
            notifyRightXAxisChange();
        }

        previousState = getState("rightYValue");
        setState("rightYValue", joystick.getRawAxis(5));
        if (getState("rightYValue") != previousState) {
            notifyRightYAxisChange();
        }

        previousState = getState("JoystickAngle");
        setState("joystickAngle", joystick.getDirectionDegrees());

        previousState = getState("triggerValue");
        setState("triggerValue", joystick.getZ());
        if (getState("triggerValue") != previousState) {
            notifyTriggerChange();
        }

		previousState = getState("padValue");
        setState("padValue", joystick.getRawAxis(6));
		if(getState("padValue")!=previousState) {
			notifyPadChange();
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

    protected void notifyLeftXAxisChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((XboxJoystickListener) joystickListeners.elementAt(i)).leftXAxisMoved(
                    new XboxJoystickEvent(this,
                    XboxJoystickEvent.DEFAULT,
                    getState("leftXValue")));
        }
    }

    protected void notifyLeftYAxisChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((XboxJoystickListener) joystickListeners.elementAt(i)).leftYAxisMoved(
                    new XboxJoystickEvent(this,
                    XboxJoystickEvent.DEFAULT,
                    getState("leftYValue")));
        }
//        System.out.println(getState("l"))
    }
      protected void notifyRightXAxisChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((XboxJoystickListener) joystickListeners.elementAt(i)).rightXAxisMoved(
                    new XboxJoystickEvent(this,
                    XboxJoystickEvent.DEFAULT,
                    getState("rightXValue")));
        }
    }

    protected void notifyRightYAxisChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((XboxJoystickListener) joystickListeners.elementAt(i)).rightYAxisMoved(
                    new XboxJoystickEvent(this,
                    XboxJoystickEvent.DEFAULT,
                    getState("rightYValue")));
        }
    }

    protected void notifyTriggerChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((XboxJoystickListener) joystickListeners.elementAt(i)).triggerMoved(
                    new XboxJoystickEvent(this,
                    XboxJoystickEvent.DEFAULT,
                    getState("triggerValue")));
        }
    }



	private void notifyPadChange() {
        for (int i = 0; i < joystickListeners.size(); i++) {
            ((XboxJoystickListener) joystickListeners.elementAt(i)).padMoved(
                    new XboxJoystickEvent(this,
                    XboxJoystickEvent.DEFAULT,
                    getState("padValue")));
        }
	}


    public String getId() {
        return id;
    }

    public Vector getJoystickListeners() {
        return joystickListeners;
    }

    public void addJoystickListener(XboxJoystickListener l) {
        joystickListeners.addElement(l);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void removeJoystickListener(XboxJoystickListener l) {
      joystickListeners.removeElement(l);
    }
}