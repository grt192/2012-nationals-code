/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.event.component;

import com.grt192.sensor.GRTXboxController;

/**
 *
 * @author student
 */
public class XboxJoystickEvent {

    public static final int DEFAULT = 0;
    private GRTXboxController source;
    private int id;
    private double value;

    public XboxJoystickEvent(GRTXboxController source, int id, double value) {
        this.source = source;
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public GRTXboxController getSource() {
        return source;
    }

    public double getValue() {
        return value;
    }
}