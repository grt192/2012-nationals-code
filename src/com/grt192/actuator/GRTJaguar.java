 /*
 * Luminary Micro Jaguar Speed Controller
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Student
 */
public class GRTJaguar extends Actuator implements PIDOutput {

    private Jaguar jaguar;

    /**
     * Creates a jaguar at a channel. The channel is the port number on the Digital side car
     * @param channel
     */
    public GRTJaguar(int channel) {
        jaguar = new Jaguar(4,channel);
    }

    /**
     * Sends a command to the Jaguar.  The parameter of the command should be a double
     * between -1.0 and 1.0.  1.0 is full forward and -1.0 is full reverse
     *
     * @param c
     */
    public void executeCommand(Command c) {
        double value = c.getValue();
        if (value > 1.0) {
            value = 1.0;
        }
        if (value < -1.0) {
            value = -1.0;
        }
        jaguar.set(value);
    }

    /**
     * Sets the motor speed to 0
     */
    public void halt() {
        //Sets motor speed to 0;
        jaguar.set(0);
    }

    /**
     *
     * @return
     */
    public String toString() {
        return "Jaguar";
    }

    /**
     *
     * @param output
     */
    public void pidWrite(double output) {
        this.enqueueCommand(output);
    }
}
