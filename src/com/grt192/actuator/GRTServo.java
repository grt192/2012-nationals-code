/*
 * Fixed Position Servo
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author Student
 */
public class GRTServo extends Actuator {

    private Servo servo;

    /**
     * Creates servo and sets it to a port number on the digital side car as specified
     * @param channel
     */
    public GRTServo(int channel) {
        servo = new Servo(channel);
    }

    /**
     * Executes a command. The command should have a value betweene 0 and 360.0 to
     * set the angle of the servo.  Note that you can send values to your servo that
     * it cannot get to.
     *
     * @param c
     */
    public void executeCommand(Command c) {
        double value = c.getValue();
        if (value > 0.0 && value < 360.0) {
            servo.setAngle(c.getValue());
        }
    }

    /**
     * The servo returns to angle 90 degrees
     */
    public void halt() {
        servo.setAngle(90);
    }

    /**
     * 
     * @return
     */
    public String toString() {
        return "Servo";
    }
}
