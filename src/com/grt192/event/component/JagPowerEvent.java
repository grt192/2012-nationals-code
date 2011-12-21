package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagPowerSensor;

/**
 * A <code>JagPowerEvent</code> encapsulates CANJaguar power state at 
 * the time of an interesting event
 * @author ajc
 */
public class JagPowerEvent {

    //interesting event ID's
    public static final int VOLTAGE_CHANGE = 0;
    public static final int CURRENT_CHANGE = 1;
    public static final int TEMPERATURE_CHANGE = 2;
    //data
    private final GRTJagPowerSensor source;
    private final int id;
    private final double value;

    public JagPowerEvent(GRTJagPowerSensor source, int id, double value) {
        this.source = source;
        this.id = id;
        this.value = value;
    }

    /**
     * Gets the sensor source of this event
     * @return source <code>GRTJagPowerSensor</code>
     */
    public GRTJagPowerSensor getSource() {
        return source;
    }

    /**
     * Gets an ID associated with what triggered this event
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the value reported at the time of this event
     * @return value units a function of the ID
     */
    public double getValue() {
        return value;
    }
}
