/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.grt192.deploy;

import com.googlecode.grtframework.rpc.connection.InternetRPC;
import com.grt192.actuator.GRTJaguar;
import com.grt192.actuator.GRTVictor;
import com.grt192.controller.DashBoardController;
import com.grt192.controller.breakaway.teleop.BreakawayTeleopDriveController;
import com.grt192.controller.teleop.XboxDriver;
import com.grt192.core.GRTRobot;
import com.grt192.mechanism.DriverStation;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.mechanism.VoltageMessenger;
import com.grt192.mechanism.breakaway.GRTBreakawayRobotBase;
import com.grt192.mechanism.breakaway.RobotBase;
import com.grt192.sensor.BatterySensor;
import com.grt192.sensor.GRTGyro;
import com.grt192.sensor.GRTJoystick;
import com.grt192.sensor.GRTPotentiometer;
import com.grt192.sensor.GRTSwitch;
import com.grt192.sensor.GRTXboxController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobot extends GRTRobot {

    // Global Controllers
    protected DashBoardController dbController;
    // Teleop Controllers
    protected XboxDriver driveControl;
    protected DriverStation driverStation;
    protected RobotBase robotbase;

    /**
     * Constructor for robot, where all components are created, registered with
     * controllers and mechanisms.
     */
    public void startRobot() {

        System.out.println("Running Base2012: xbox drive");

        
        // Driver station components
        GRTXboxController primary = new GRTXboxController(1, 12, "primary");
        GRTXboxController secondary = null; //new GRTXboxController(2, 12, "secondary");
        System.out.println("Joysticks Initialized");

        // PWM outputs
        GRTVictor leftDT1 = new GRTVictor(4);

        GRTVictor leftDT2 = new GRTVictor(3);

        GRTVictor rightDT1 = new GRTVictor(6);

        GRTVictor rightDT2 = new GRTVictor(10);

        System.out.println("Motors Initialized");

        // analog inputs
        InternetRPC rpc = new InternetRPC(180);
        rpc.start();
        BatterySensor s = new BatterySensor(10);
        s.start();
        VoltageMessenger messenger = new VoltageMessenger(rpc, 23, s);
//        GRTPotentiometer batterySensor = new GRTPotentiometer(1, 50,
//                "batteryVoltage");
//        batterySensor.start();
//        GRTGyro gyro = new GRTGyro(7, 15, "BaseGyro");
        System.out.println("Switches Initialized");

        // Mechanisms
        robotbase = new RobotBase(leftDT1, leftDT2, rightDT1, rightDT2);
        driverStation = new DriverStation(primary, secondary);
        System.out.println("Mechanisms Initialized");

        // camera = new CameraAssembly();
        // System.out.println("Camera Initialized");

        // Controllers
        dbController = new DashBoardController();
        dbController.start();
        System.out.println("Dashboard Initialized");


        driveControl = new XboxDriver(robotbase, driverStation);
        System.out.println("Controllers Initialized");

        teleopControllers.addElement(driveControl);
        System.out.println("Robot initialized OK");
    }
}
