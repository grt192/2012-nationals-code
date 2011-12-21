/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.controller.teleop;

import com.grt192.actuator.GRTDriveTrain;
import com.grt192.core.StepController;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.mechanism.GRTRobotBase;

/**
 *
 * @author Student
 */
public class DriveController extends StepController {

    private int mode;

    public DriveController(GRTRobotBase rb, GRTDriverStation ds) {
        super();
        addMechanism("DriverStation", ds);
        addMechanism("RobotBase", rb);  
        mode = GRTDriveTrain.TANK_DRIVE;
        System.out.println("DriveController Starting");
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void act() {
        GRTDriverStation driverStation =
                ((GRTDriverStation) getMechanism("DriverStation"));
        //Reverse to match drivetrain direction
        double x = 0;
        double y = 0;
        
        switch(mode){
            case GRTDriveTrain.TANK_DRIVE: 
                x = -1 * driverStation.getYLeftJoystick();
                y = -1 * driverStation.getYRightJoystick();
                ((GRTRobotBase) getMechanism("RobotBase")).tankDrive(x, y);  
                break;
            case GRTDriveTrain.ARCADE_DRIVE:
                x = -1 * driverStation.getXRightJoystick();
                y = -1 * driverStation.getYRightJoystick();
                ((GRTRobotBase) getMechanism("RobotBase")).arcadeDrive(x, y);
                break;
            case GRTDriveTrain.ARCADE_SPIN_DRIVE:
                x = -1 * driverStation.getXRightJoystick();
                y = -1 * driverStation.getYRightJoystick();
                ((GRTRobotBase) getMechanism("RobotBase")).arcadeDrive(x, y, true);
                break;
        }
        System.out.println("Drive "+x+" , "+y);
        
    }

    public String toString() {
        return "Drive train controller";
    }


}
