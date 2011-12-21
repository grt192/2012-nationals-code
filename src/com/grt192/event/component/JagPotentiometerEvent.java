package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagPotentiometer;

/**
 * A <code>JagPotentiometerEvent</code> encapsulates the potentiometer state
 * at the time of an interesting event.
 * @see GRTJagPotentiometer
 * @author Data
 */
public class JagPotentiometerEvent {

    //interesting event ID's
    public static final int DISTANCE = 1;
    public static final int STOPPED = 2;
    public static final int STARTED = 3;
    public static final int DIRECTION = 4;
    //data
    private final GRTJagPotentiometer source;
    private final int id;
    private final double distance;
    private final boolean forward;

    public JagPotentiometerEvent(GRTJagPotentiometer source, int id,
            double distance, boolean direction) {
        this.source = source;
        this.id = id;
        this.distance = distance;
        this.forward = direction;
    }

    /**
     * Gets an ID associated with what triggered this event
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the sensor source of this event
     * @return source <code>GRTJagPotentiometer</code>
     */
    public GRTJagPotentiometer getSource() {
        return source;
    }

    /**
     * Gets the distance reported at the time of this event
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the direction reported at the time of this event
     * @return isForward
     */
    public boolean isForward() {
        return forward;
    }
}
