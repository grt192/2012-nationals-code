package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Abstraction for any single solenoid valve for pneumatics
 */
public class GRTSolenoid extends Actuator {

    private Solenoid solenoid;
    public static final double ON = 1.0;
    public static final double OFF = 0.0;

    /**
     * Creates a new solenoid on a defined channel.
     * Note: uses the default slot: 8
     * @param channel
     */
    public GRTSolenoid(int channel) {
        solenoid = new Solenoid(channel);
    }

    /**
     * Creates a new solenoid on a desired channel in a specific slot.
     * @param slot The slot where the 9472 "relay" module is plugged into.
     * @param channel
     */
    public GRTSolenoid(int slot, int channel) {
        solenoid = new Solenoid(slot, channel);
    }

    /**
     * Executes next Command.  Command should have a value of 1.0 for forward and
     * -1.0 for reverse.
     * @param c
     */
    public void executeCommand(Command c) {
        if (c.getValue() == ON) {
            solenoid.set(true);
        } else if (c.getValue() == OFF) {
            solenoid.set(false);
        }
    }

    /**
     * Solenoid is in the off position
     */
    protected void halt() {
        solenoid.set(false);
    }

    /**
     * Returns the String "Solenoid"
     * @return
     */
    public String toString() {
        return "Solenoid";
    }
}
