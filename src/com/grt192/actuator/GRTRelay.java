package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Student
 */
public class GRTRelay extends Actuator {

    public static final double RELAY_FORWARD = 1.0;
    public static final double RELAY_REVERSE = -1.0;
    public static final double RELAY_OFF = 0.0;
    private Relay relay;
    
    /**
     * Creates relay and sets it to a port number on the digital side car as specified
     * @param channel
     */
    public GRTRelay(int channel) {
        relay = new Relay(channel);
    }
/**
 * Sends command to relay.  If the value of the command is -1.0 the relay is in reverse,
 * 1.0 is forward, and 0.0 is off.
 * @param c
 */
    public void executeCommand(Command c) {
        if (c.getValue() == RELAY_OFF) {
            relay.set(Relay.Value.kOff);
        } else if(c.getValue() == RELAY_FORWARD) {
            relay.set(Relay.Value.kForward);
        } else if(c.getValue() == RELAY_REVERSE) {
            relay.set(Relay.Value.kReverse);
        }
    }
/**
 * SEts the state of the relay to off
 */
    public void halt() {
        relay.set(Relay.Value.kOff);
    }
/**
 * Returns String
 * @return
 */
    public String toString() {
        return "Relay";
    }
}
