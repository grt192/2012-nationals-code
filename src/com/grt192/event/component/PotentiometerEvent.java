package com.grt192.event.component;

import com.grt192.sensor.GRTPotentiometer;

/**
 *
 * @author anand
 */
public class PotentiometerEvent {

    public static final int DEFAULT = 0;
    public static final int INCREASE = 1;
    public static final int DECREASE = 2;
    private GRTPotentiometer source;
    private int id;
    private double previousValue;
    private double value;

    public PotentiometerEvent(GRTPotentiometer source, int id, double value, double previousValue) {
        this.source = source;
        this.id = id;
        this.value = value;
        this.previousValue = previousValue;
    }

    public int getId() {
        return id;
    }

    public GRTPotentiometer getSource() {
        return source;
    }

    public double getValue() {
        return value;
    }

    public double getPreviousValue() {
        return previousValue;
    }
}
