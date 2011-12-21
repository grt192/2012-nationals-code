/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.SwitchListener;
import edu.wpi.first.wpilibj.DigitalInput;
import java.util.Vector;

/**
 * A simple switch utilizing a GPIO port
 * Binary switch
 * @author Bonan
 */
public class GRTSwitch extends Sensor {

    public static final double CLOSED = Sensor.TRUE;
    public static final double OPEN = Sensor.FALSE;
    private DigitalInput input;
    private Vector switchListeners;

    public GRTSwitch(int i, int i0) {
        this(i, i0, "");
    }

    public GRTSwitch(int channel, int pollTime, String id) {
        this.sleepTime = pollTime;
        input = new DigitalInput(channel);
        setState("State", OPEN);
        this.id = id;
        switchListeners = new Vector();
    }

    public DigitalInput getInput() {
        return input;
    }

    public void setInput(DigitalInput input) {
        this.input = input;
    }

    public void poll() {
        double previous = getState("State");
        setState("State", input.get() ? CLOSED : OPEN);
        if(getState("State") != previous){
            notifyListeners(getState("State") == CLOSED);
        }
    }

    private void notifyListeners(boolean closing) {
        for (int i = 0; i < switchListeners.size(); i++) {
            if (closing) {
                ((SwitchListener) switchListeners.elementAt(i)).switchPressed(this);
            }else
                ((SwitchListener) switchListeners.elementAt(i)).switchReleased(this);
        }
    }

    public void addSwitchListener(SwitchListener listener) {
        switchListeners.addElement(listener);
    }

    public void removeSwitchListener(SwitchListener listener) {
        switchListeners.removeElement(listener);
    }
}
