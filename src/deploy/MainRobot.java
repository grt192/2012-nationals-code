/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;

import actuator.GRTVictor;
import balancer.BalanceController;
import balancer.RobotTiltAccel;
import balancer.RobotTiltGyro;
import balancer.BalanceController;
import controller.PrimaryDriver;
import controller.TestController;
import controller.WedgeAttack3Controller;
import logger.RPCLogger;
import mechanism.GRTDriveTrain;
import mechanism.GRTRobotBase;
import mechanism.Turret;
import mechanism.Wedge;
import rpc.connection.NetworkRPC;
import rpc.telemetry.SensorLogger;
import sensor.*;
import sensor.base.*;

/**
 *
 * Where Execution of Robot Code begins.
 */
public class MainRobot extends GRTRobot {

    /**
     * Buttons which refer to enumerated driver curve profiles.
     * First index refers to Linear drive
     * Second index refers to Square drive
     */
    public static final int[] DRIVER_PROFILE_KEYS = new int[]{1, 2};
    public static final IDriverProfile[] DRIVER_PROFILES = new IDriverProfile[]{new LinearDrive(), new SquareDrive()};
    //Global Controllers
//    private SensorLogger batteryLogger;
    //Teleop Controllers
//    private PrimaryDriver driveControl;
//    private GRTDriverStation driverStation;
//    private GRTRobotBase robotBase;
//    private GRTADXL345 adxl;
//    private final ADXL345DigitalAccelerometer primaryADXL;
//    private final RobotTiltGyro tiltSensor;
//    private final SensorLogger tiltLogger;
//    private BalanceController balancer;

    public MainRobot() {

        System.out.println("Running grtframeworkv6");

        //RPC Connection
        NetworkRPC rpcConn = new NetworkRPC(180);

        //Driver station components
        GRTAttack3Joystick primary = new GRTAttack3Joystick(1, 12, "primary");
        GRTAttack3Joystick secondary = new GRTAttack3Joystick(2, 12, "secondary");
        primary.start();
        secondary.start();
        primary.enable();
        secondary.enable();
        System.out.println("Joysticks initialized");

        //Battery Sensor
        GRTBatterySensor batterySensor = new GRTBatterySensor(10, "battery");
        batterySensor.start();
        batterySensor.enable();

        GRTEncoder encoder1 = new GRTEncoder(2, 1, 4.0, 10, "EncoderLeft");
        GRTEncoder encoder2 = new GRTEncoder(3, 4, 4.0, 10, "EncoderRight");
        encoder1.start();
        encoder1.enable();
        encoder2.start();
        encoder2.enable();
        
        
        /*********************************************
         * VICTORS USED FOR THE DRIVETRAIN.
         */
      
        
        
      GRTVictor leftDT1 = new GRTVictor(1, 3, "leftDT1");
      GRTVictor leftDT2 = new GRTVictor(1, 4, "leftDT2");
      
      GRTVictor rightDT1 = new GRTVictor(2, 3, "rightDT1");
      GRTVictor rightDT2 = new GRTVictor(2, 4, "rightDT2");
      
      leftDT1.start();leftDT1.enable();
      leftDT2.start();leftDT2.enable();
      rightDT1.start();rightDT1.enable();
      rightDT2.start();rightDT2.enable();

//        
//        GRTVictor[] leftAuxVictors = new GRTVictor[6];
//        GRTVictor[] rightAuxVictors = new GRTVictor[6];
        
        
//        for (int i=2 ; i < 8; i++){
//            leftAuxVictors[i-2] = new GRTVictor(i + 3, "Next Victor");
//            leftAuxVictors[i-2].start(); leftAuxVictors[i-2].enable();
//        }
        
        
//        for (int i=2 ; i < 8; i++){
//            rightAuxVictors[i-2] = new GRTVictor(2, i + 3, "Next Victor");
//            rightAuxVictors[i-2].start(); rightAuxVictors[i-2].enable();
//        }
        
        
        /*********************************************
         * VICTORS FOR MECHANISMS
         */
       
        /*
        GRTVictor wedgeVictor = new GRTVictor(2, 5, "Wedge Victor");
        GRTVictor collectionVictor = new GRTVictor(2, 6, "Collection Victor");
        
        wedgeVictor.start();
        wedgeVictor.enable();
        
        collectionVictor.start();
        collectionVictor.enable();
        */
        
        System.out.println("Motors initialized");
        
        
        
        
        
        
        
        
        /**********************************************
         * DRIVETRAIN INITIALIZATION
         */
        
        /*
        
        GRTDriveTrain dt = new GRTDriveTrain(leftDT1, leftDT2, rightDT1, rightDT2, "dt");
        
        dt.start();dt.enable();
        
        dt.addDataLogger(new RPCLogger(rpcConn));
        robotBase = new GRTRobotBase(dt, batterySensor, encoder1, encoder2);
        driverStation = new GRTAttack3DriverStation(primary, secondary, DRIVER_PROFILE_KEYS, DRIVER_PROFILES,
                4, 5, 6, "driverStation");
        driverStation.enable();

        
        */
        System.out.println("Drivetrain initialized");
        
       
        /**********************************************
         * CONTROLLER INITIALIZATION
         */
//        driveControl = new PrimaryDriver(robotBase, driverStation, new LinearDrive(), "driveControl");
//        TestController test = new TestController(leftAuxVictors, rightAuxVictors, primary, secondary);
        
        System.out.println("Controllers Initialized");
        
        
        
        
        /***********************************************
         * LOGGING
         */
        SensorLogger encoderLogger1 = new SensorLogger(encoder1, rpcConn, new int[]{81, 82, 83}, null);
        SensorLogger encoderLogger2 = new SensorLogger(encoder2, rpcConn, new int[]{84, 85, 86}, null);
        encoderLogger1.enable();
        encoderLogger2.enable();
        
        System.out.println("Loggers initialized");


        /******************************************
         * MECHANISM INTIALIZATION
         */
//        Turret turr = new Turret(leftDT1, leftDT2, rightDT1, rightDT2, encoder1, encoder2, primary);
//        turr.start();
//        turr.enable();
//        turr.startListening();
        
        GRTSwitch up = new GRTSwitch(10, 20, "Upper");
        GRTSwitch down = new GRTSwitch(11, 20, "Lower");
        
        up.start();
        down.start();
        up.enable();
        down.enable();
        
        Wedge wedge = new Wedge(leftDT1, up, down, "Wedge");
        
        wedge.start();wedge.enable();
        
        WedgeAttack3Controller wedgeControl = new WedgeAttack3Controller(primary, wedge);
        
        
        System.out.println("Mechanisms initialized");
      
        
        
        /*********************************************
         * ADD OUR TELEOP AND AUTONOMOUS CONTROLLERS,
         * AND LET'S GO!
         */
        addTeleopController(wedgeControl);
//        addAutonomousController(balancer);
        System.out.println("All systems go!");
    }
}
