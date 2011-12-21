/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.MaxBotixEvent;
import com.grt192.event.component.MaxBotixListener;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;
import java.util.Vector;

/**
 *
 * @author grtstudent
 */
public class GRTMaxBotixSonar extends Sensor implements PIDSource {

    public static final double SPIKE_THRESHOLD = 1.0;
    public static final double CHANGE_THRESHOLD = .001;
    private AnalogChannel input;
    private Vector maxBotixListeners;

    public GRTMaxBotixSonar(int port, int pollTime, String id) {
        this.id = id;
        this.sleepTime = pollTime;
        input = new AnalogChannel(port);
        maxBotixListeners = new Vector();
    }

    public void poll() {
        //TODO calibrate for range value
        double previousValue = getState("Value");
        setState("Value", input.getAverageVoltage());

        if (Math.abs(getState("Value") - previousValue) >= SPIKE_THRESHOLD) {
            notifyMaxBotixSpike();
        }
        if (Math.abs(getState("Value") - previousValue) >= CHANGE_THRESHOLD) {
            notifyMaxBotixChange();
        }
        notifyMaxBotixListeners();
    }

    protected void notifyMaxBotixSpike() {
        for (int i = 0; i < maxBotixListeners.size(); i++) {
            ((MaxBotixListener) maxBotixListeners.elementAt(i)).didRangeSpike(
                    new MaxBotixEvent(this,
                    MaxBotixEvent.DEFAULT,
                    getState("Value")));
        }
    }

    protected void notifyMaxBotixChange() {
        for (int i = 0; i < maxBotixListeners.size(); i++) {
            ((MaxBotixListener) maxBotixListeners.elementAt(i)).didRangeChange(
                    new MaxBotixEvent(this,
                    MaxBotixEvent.DEFAULT,
                    getState("Value")));
        }
    }

    protected void notifyMaxBotixListeners() {
        for (int i = 0; i < maxBotixListeners.size(); i++) {
            ((MaxBotixListener) maxBotixListeners.elementAt(i)).didReceiveRange(
                    new MaxBotixEvent(this,
                    MaxBotixEvent.DEFAULT,
                    getState("Value")));
        }
    }

    public double pidGet() {
        return input.pidGet();
    }
}
