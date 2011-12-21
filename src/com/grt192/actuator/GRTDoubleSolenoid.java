package com.grt192.actuator;

import com.grt192.actuator.exception.ActuatorException;
import com.grt192.core.Actuator;
import com.grt192.core.Command;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Abstraction for a double solenoid valve for pneumatics.
 */
public class GRTDoubleSolenoid extends Actuator {

    public static final double FORWARD = 1.0;
    public static final double REVERSE = -1.0;
    public static final double OFF = 0.0;
    private Solenoid forwardValve;
    private Solenoid reverseValve;

    /**
     * Creates a GRTDoubleSolenoid which needs a forward and reverse port. The pin numbers
     * must be specified
     *
     * @param fwdPin
     * @param revPin
     */
    public GRTDoubleSolenoid(int fwdPin, int revPin) {
        forwardValve = new Solenoid(fwdPin);
        reverseValve = new Solenoid(revPin);
    }

    /**
     * Executes a command.  Set the command value to 1.0 to set the solenoid to forward,
     * -1.0 to set the solenoid into reverse, and 0.0 to turn off the solenoid
     * @param c
     * @throws ActuatorException
     */
    protected void executeCommand(Command c) throws ActuatorException {
        if (c.getValue() == FORWARD) {
            reverseValve.set(false);
            forwardValve.set(true);
        } else if (c.getValue() == REVERSE) {
            forwardValve.set(false);
            reverseValve.set(true);
        } else if (c.getValue() == OFF) {
            forwardValve.set(false);
            reverseValve.set(false);
        }
    }

    /**
     * Turns off the solenoid
     */
    protected void halt() {
        forwardValve.set(false);
        reverseValve.set(false);
    }
}
