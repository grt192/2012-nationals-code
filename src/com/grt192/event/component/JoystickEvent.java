package com.grt192.event.component;

import com.grt192.sensor.GRTJoystick;

/**
 *
 * @author anand
 */
public class JoystickEvent {
    public static final int DEFAULT = 0;
    private GRTJoystick source;
    private int id;
    private double value;

    public JoystickEvent(GRTJoystick source, int id, double value) {
        this.source = source;
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public GRTJoystick getSource() {
        return source;
    }

    public double getValue() {
        return value;
    }

}
