package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.PWM;

/**
 * A standard LED
 */
public class GRTLED extends Actuator {

    private static final int DEFAULT_BRIGHTNESS = 255;
    public static final int TURN_OFF = 0;
    public static final int TURN_ON = 255;
    public static final int TOGGLE = 266;
    
    private int brightness;
    private final PWM led;

    /**
     * Creates a new LED in the desired channel at the desired brightness
     * @param channel The channel
     * @param brightness Should be a value from 0 to 255
     */
    public GRTLED(int channel, int brightness) {
        led = new PWM(channel);
        brightness = this.brightness;
    }

    /**
     * Creates a new LED in the desired channel at the default brightness
     */
    public GRTLED(int channel) {
        this(channel, DEFAULT_BRIGHTNESS);
    }

    /**
     * Toggles the state of the LED between off and on
     */
    protected void toggleState() {
        brightness = (brightness == TURN_ON ? TURN_OFF : TURN_ON);
        led.setRaw(brightness);
    }

    /**
     * Executes next Command.  Command should have a value of TURN_OFF for off
     * and TURN_ON for on, or TOGGLE to toggle it.
     * @param c The Command
     */
    protected void executeCommand(Command c) {
        int v = (int) c.getValue();
        if (v >= TURN_OFF && v <= TURN_ON) { //TURN_OFF to TURN_ON
            brightness = v;
            led.setRaw(v);
        } else if (v == TOGGLE) {
            toggleState();
        }
    }

    /**
     * Returns the String "LED"
     * @return "LED"
     */
    public String toString() {
        return "LED";
    }

    /**
     * Turns off the LED
     */
    protected void halt() {
        brightness = GRTLED.TURN_OFF;
        led.setRaw(brightness);
    }
}

