package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagSwitchPair;

/**
 * A <code>JagSwitchEvent</code> encapsulates the switch states
 * at the time of an interesting event.
 * @see GRTJagSwitchPair
 * @author ajc
 */
public class JagSwitchEvent {

    //interesting event ID's
    public static final int LEFT_PRESSED = 0;
    public static final int LEFT_RELEASED = 1;
    public static final int RIGHT_PRESSED = 2;
    public static final int RIGHT_RELEASED = 3;
    //data
    private final GRTJagSwitchPair source;
    private final int id;
    private final String key;

    public JagSwitchEvent(GRTJagSwitchPair source, int id,
            String key) {
        this.source = source;
        this.id = id;
        this.key = key;
    }

    /**
     * Gets the sensor source of this event
     * @return source <code>GRTJagSwitchPair</code>
     */
    public GRTJagSwitchPair getSource() {
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
     * Gets the key of the datum that changed on this event
     * @return distance
     */
    public String getKey() {
        return key;
    }
}
