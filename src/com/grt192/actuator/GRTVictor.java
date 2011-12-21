package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Student
 */
public class GRTVictor extends Actuator implements PIDOutput {

    Victor victor;

    /**
     * Creates a motor controlled by a Victor Speed controller. Needs a port to be specified
     * @param channel
     */
    public GRTVictor(int channel) {
        victor = new Victor(channel);
    }

    /**
     * Executes a command. The value of the command should be between 1.0 and -1.0 which
     * represend full forward and reverse respectively
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
        victor.set(value);
    }

    /**
     * Turns off the motor
     */
    protected void halt() {
        victor.set(0);
    }

    /**
     *
     * @return
     */
    public String toString() {
        return "Victor";
    }

    /**
     * 
     * @param output
     */
    public void pidWrite(double output) {
        this.enqueueCommand(output);
    }
}
