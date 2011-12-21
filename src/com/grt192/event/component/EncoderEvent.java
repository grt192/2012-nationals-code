/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

import com.grt192.sensor.GRTEncoder;

/**
 *
 * @author GRTstudent
 */
public class EncoderEvent {
    public static final int DEFAULT = 0;
    public static final int DISTANCE = 1;
    public static final int STOPPED = 2;
    public static final int DIRECTION = 3;
    

    private GRTEncoder source;
    private int id;
    private double distance;
    private boolean forward;

    public EncoderEvent(GRTEncoder source, int id,
                                        double distance, boolean direction) {
        this.source = source;
        this.id = id;
        this.distance = distance;
        this.forward = direction;
    }

    public int getId() {
        return id;
    }

    public GRTEncoder getSource() {
        return source;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    
}
