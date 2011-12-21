package com.grt192.event.component;

import com.grt192.sensor.GRTAccelerometer;

/**
 *
 * @author anand
 */
public class AccelerometerEvent {
    public static final int DEFAULT = 0;
    public static final int NEW_DATA = 1;
    public static final int CHANGE = 2;
    public static final int SPIKE = 3;

    private GRTAccelerometer source;
    private int id;
    private double acceleration;

    public AccelerometerEvent(GRTAccelerometer source, int id, double acceleration) {
        this.source = source;
        this.id = id;
        this.acceleration = acceleration;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public int getId() {
        return id;
    }

    public GRTAccelerometer getSource() {
        return source;
    }
}
