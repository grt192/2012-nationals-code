/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.controller.teleop;

import com.grt192.core.EventController;
import com.grt192.core.EventListener;
import com.grt192.event.component.XboxJoystickEvent;
import com.grt192.event.component.XboxJoystickListener;
import com.grt192.mechanism.DriverStation;
import com.grt192.mechanism.breakaway.RobotBase;
import com.grt192.sensor.GRTXboxController;

/**
 * Drives the robot using xbox controllers
 * 
 * @author ajc
 */
public class XboxDriver extends EventController implements XboxJoystickListener{
    
    private final RobotBase base;
    private GRTXboxController joystick;

    private double leftVelocity;
    private double rightVelocity;

    public XboxDriver(RobotBase base, DriverStation ds){
        this.base = base;
        joystick = ds.getPrimaryJoystick();
    }

    public void start(){
        joystick.addJoystickListener(this);
    }

    public void leftXAxisMoved(XboxJoystickEvent e) {
    }

    public void leftYAxisMoved(XboxJoystickEvent e) {
        //left Y axis sets the left velocity
        leftVelocity = e.getValue();
        base.tankDrive(leftVelocity, rightVelocity);
    }

    public void rightXAxisMoved(XboxJoystickEvent e) {
    }

    public void rightYAxisMoved(XboxJoystickEvent e) {
        //right Y axis sets the right velocity
        rightVelocity = e.getValue();
        base.tankDrive(leftVelocity, rightVelocity);
    }

    public void padMoved(XboxJoystickEvent e) {
    }

    public void triggerMoved(XboxJoystickEvent e) {
    }

}
