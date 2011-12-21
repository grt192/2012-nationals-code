/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.controller.breakaway.teleop;

import com.grt192.core.StepController;
import com.grt192.mechanism.breakaway.GRTBreakawayRobotBase;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.sensor.GRTJoystick;
import com.grt192.utils.Util;

/**
 *
 * @author Bonan, James, Ryo
 */
public class BreakawayTeleopDriveController extends StepController {

    public static final double SPIN_THRESHOLD = .1;
    private int driveMode = GRTBreakawayRobotBase.TANK_DRIVE;

    public BreakawayTeleopDriveController(GRTBreakawayRobotBase rb, GRTDriverStation ds) {
        super();
        addMechanism("RobotBase", rb);
        addMechanism("DriverStation", ds);
    }

    public void act() {
        GRTDriverStation ds = (GRTDriverStation) getMechanism("DriverStation");
        GRTBreakawayRobotBase rb =
                (GRTBreakawayRobotBase) getMechanism("RobotBase");
        switch (driveMode) {
            case GRTBreakawayRobotBase.TANK_DRIVE:
                rb.tankDrive(ds.getYLeftJoystick(), ds.getYRightJoystick());
                break;
        }
    }

}

