package com.grt192.sensor.canjaguar;

import java.util.Vector;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.core.Sensor;
import com.grt192.event.component.JagPowerEvent;
import com.grt192.event.component.JagPowerListener;

/**
 * The power sensor tracks changes in  GRTCANJaguar voltage, current,
 * and temperature.
 * 
 * There is a distinction between this and voltage/current/temperature faults;
 * this sensor doesn't track them. That is done by a GRTJagFaultSensor.
 *
 * @see GRTJagFaultSensor
 * @author ajc
 */
public class GRTJagPowerSensor extends Sensor {

    /** keys for getState() **/
    public static final String VOLTAGE = "Voltage";
    public static final String CURRENT = "Current";
    public static final String TEMPERATURE = "Temperature";
    //source
    private final GRTCANJaguar jaguar;
    private Vector powerListeners;

    /**
     * Called automatically from GRTCANJaguar's <code>getPowerSensor()</code> method.
     * Therefore use <code>getEncoder()</code>, not this.
     * @see GRTCANJaguar
     */
    public GRTJagPowerSensor(GRTCANJaguar jag, int pollTime, String id) {
        jaguar = jag;
        this.id = id;
        this.setSleepTime(pollTime);
        powerListeners = new Vector();
    }

    public void poll() {
        double previous = getState(VOLTAGE);
        setState(VOLTAGE, jaguar.getOutputVoltage());
        if (previous != getState(VOLTAGE)) {
            notifyVoltageChange();
        }
        previous = getState(CURRENT);
        setState(CURRENT, jaguar.getOutputCurrent());
        if (previous != getState(CURRENT)) {
            notifyCurrentChange();
        }
        previous = getState(TEMPERATURE);
        setState(TEMPERATURE, jaguar.getTemperature());
        if (previous != getState(TEMPERATURE)) {
            notifyTemperatureChange();
        }
    }

    /**
     * Adds a provided <code>JagPowerListener</code> to send events,
     * on event
     * @param a A <code>JagPowerListener</code> to send events to
     */
    public void addPowerListener(JagPowerListener a) {
        powerListeners.addElement(a);
    }

    /**
     * Removes a provided <code>JagPowerListener</code> to stop sending
     * events, on event
     * @param a A <code>JagPowerListener</code> to notify
     */
    public void removePowerListener(JagPowerListener a) {
        powerListeners.removeElement(a);
    }

    /** Notifies all listeners that the voltage has changed */
    protected void notifyVoltageChange() {
        for (int i = 0; i < powerListeners.size(); i++) {
            ((JagPowerListener) powerListeners.elementAt(i)).voltageChanged(new JagPowerEvent(this,
                    JagPowerEvent.VOLTAGE_CHANGE, getState(VOLTAGE)));
        }
    }

    /** Notifies all listeners that current has changed */
    protected void notifyCurrentChange() {
        for (int i = 0; i < powerListeners.size(); i++) {
            ((JagPowerListener) powerListeners.elementAt(i)).currentChanged(new JagPowerEvent(this,
                    JagPowerEvent.CURRENT_CHANGE, getState(CURRENT)));
        }
    }

    /* Notifies all listeners that temperature has changed */
    protected void notifyTemperatureChange() {
        for (int i = 0; i < powerListeners.size(); i++) {
            ((JagPowerListener) powerListeners.elementAt(i)).temperatureChanged(new JagPowerEvent(this,
                    JagPowerEvent.TEMPERATURE_CHANGE, getState(TEMPERATURE)));
        }
    }
}
