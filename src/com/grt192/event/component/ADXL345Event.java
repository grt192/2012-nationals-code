package com.grt192.event.component;

import com.grt192.sensor.GRTADXL345;


/**
 *
 * @author anand
 */
public class ADXL345Event {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;

    private GRTADXL345 source;
    private int id;
    private double acceleration;

    public ADXL345Event(GRTADXL345 source, int id, double acceleration) {
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

    public GRTADXL345 getSource() {
        return source;
    }
}
