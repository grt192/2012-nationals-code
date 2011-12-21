package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagEncoder;

/**
 * A <code>JagEncoderEvent</code> encapsulates the encoder state
 * at the time of an interesting event.
 * @see GRTJagEncoder
 * @author ajc
 */
public class JagEncoderEvent {

    //interesting event ID's
    public static final int DISTANCE = 1;
    public static final int STOPPED = 2;
    public static final int STARTED = 3;
    public static final int DIRECTION = 4;
    //data
    private final GRTJagEncoder source;
    private final int id;
    private final double distance;
    private final boolean forward;

    public JagEncoderEvent(GRTJagEncoder source, int id,
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
     * @return source <code>GRTJagEncoder</code>
     */
    public GRTJagEncoder getSource() {
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
