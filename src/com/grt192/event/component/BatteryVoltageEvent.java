/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

import com.grt192.sensor.BatterySensor;

/**
 *
 * @author ajc
 */
public class BatteryVoltageEvent {
    private final BatterySensor sensor;
    private final double voltage;

    public BatteryVoltageEvent(BatterySensor sensor, double voltage){
        this.sensor = sensor;
        this.voltage = voltage;
    }

    public BatterySensor getSource(){
        return sensor;
    }

    public double getVoltage(){
        return voltage;
    }

}
