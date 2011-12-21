/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism;

import com.grt192.sensor.GRTXboxController;

/**
 * The Driverstation provides the human interface to the robot.
 * @author ajc
 */
public class DriverStation {
    private final GRTXboxController primary;
    private final GRTXboxController secondary;

    public DriverStation(GRTXboxController primary, GRTXboxController secondary){
        this.primary = primary;
        this.secondary = secondary;
        primary.start();
//        secondary.start();
    }

    public GRTXboxController getPrimaryJoystick(){
        return primary;
    }

    public GRTXboxController getSecondaryJoystick(){
        return secondary;
    }

}
