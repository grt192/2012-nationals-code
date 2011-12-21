/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

import com.grt192.sensor.GRTMaxBotixSonar;

/**
 *
 * @author GRTstudent
 */
public class MaxBotixEvent {
    public static final int DEFAULT = 0;

    private GRTMaxBotixSonar source;
    private int id;
    private double range;

    public MaxBotixEvent(GRTMaxBotixSonar source, int id, double range) {
        this.source = source;
        this.id = id;
        this.range = range;
    }

    public double getRange() {
        return range;
    }

    public int getId() {
        return id;
    }

    public GRTMaxBotixSonar getSource() {
        return source;
    }
}
