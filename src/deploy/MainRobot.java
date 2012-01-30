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
import controller.TurretController;
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
    private static final int LEFT_SIDECAR_MODULE = 1;
    private static final int RIGHT_SIDECAR_MODULE = 2;
    private PrimaryDriver driveControl;
    private GRTRobotDriver driverStation;
    private GRTRobotBase robotBase;


    public MainRobot() {

        System.out.println("Running grtframeworkv6");

        //RPC Connection
        NetworkRPC rpcConn = new NetworkRPC(180);

        //Driver station components
        GRTAttack3Joystick primary = new GRTAttack3Joystick(1, 12, "primary");
        GRTAttack3Joystick secondary = new GRTAttack3Joystick(2, 12, "secondary");
        
        GRTXBoxJoystick tertiary = new GRTXBoxJoystick(3, 12, "Xbox Joystick");
        
        primary.start();    secondary.start();
        primary.enable();   secondary.enable();
        tertiary.start();   tertiary.enable();
        System.out.println("Joysticks initialized");

        //Battery Sensor
        GRTBatterySensor batterySensor = new GRTBatterySensor(10, "battery");
        batterySensor.start();  batterySensor.enable();

        GRTEncoder encoder1 = new GRTEncoder(2, 1, 4.0, 10, "EncoderLeft");
        GRTEncoder encoder2 = new GRTEncoder(3, 4, 4.0, 10, "EncoderRight");
        encoder1.start();   encoder1.enable();
        encoder2.start();   encoder2.enable();
        
        
        /*********************************************
         * VICTORS USED FOR THE DRIVETRAIN.
         */
        
      GRTVictor leftDT1 = new GRTVictor(LEFT_SIDECAR_MODULE, 9, "leftDT1");
      GRTVictor leftDT2 = new GRTVictor(LEFT_SIDECAR_MODULE ,10, "leftDT2");
      GRTVictor rightDT1 = new GRTVictor(RIGHT_SIDECAR_MODULE, 9, "rightDT1");
      GRTVictor rightDT2 = new GRTVictor(RIGHT_SIDECAR_MODULE, 10, "rightDT2");
      
      
      leftDT1.start();	leftDT1.enable();
      leftDT2.start();	leftDT2.enable();
      rightDT1.start();	rightDT1.enable();
      rightDT2.start();	rightDT2.enable();
      
      /*********************************************
         * VICTORS FOR MECHANISMS
         */
      
      //Mechanism Victors
      GRTVictor wedgeVictor = new GRTVictor(LEFT_SIDECAR_MODULE, 4, "Wedge Victor");
      GRTVictor rotationVictor = new GRTVictor(RIGHT_SIDECAR_MODULE, 3, "Turret Rotator Victor");
      GRTVictor visorVictor = new GRTVictor(RIGHT_SIDECAR_MODULE, 4, "Visor Tilt Victor");
      GRTVictor flywheelVictor1 = new GRTVictor(RIGHT_SIDECAR_MODULE, 7, "flywheel1");
      GRTVictor flywheelVictor2 = new GRTVictor(RIGHT_SIDECAR_MODULE, 8, "flywheel2");
      GRTVictor drawbridgeVictor = new GRTVictor(RIGHT_SIDECAR_MODULE, 5, "drawbridge");
      GRTVictor flailVictor1 = new GRTVictor(LEFT_SIDECAR_MODULE, 7, "flail1");
      GRTVictor flailVictor2 = new GRTVictor(LEFT_SIDECAR_MODULE, 8, "flail2");
      GRTVictor transVictor1 = new GRTVictor(LEFT_SIDECAR_MODULE, 5, "trans1");
      GRTVictor transVictor2 = new GRTVictor(LEFT_SIDECAR_MODULE, 6, "trans2");
      
      
      
      wedgeVictor.start(); 		wedgeVictor.enable();
      rotationVictor.start(); 		rotationVictor.enable();
      visorVictor.start(); 		visorVictor.enable();
      flywheelVictor1.start(); 		flywheelVictor1.enable();  
      flywheelVictor2.start(); 		flywheelVictor2.enable();  
      drawbridgeVictor.start();		drawbridgeVictor.enable(); 
      flailVictor1.start();		flailVictor1.enable(); 
      flailVictor2.start();		flailVictor2.enable(); 
      transVictor1.start();		transVictor1.enable(); 
      transVictor2.start();		transVictor2.enable(); 


       System.out.println("Motors initialized");
       
         /*********************************************
         * SWITCHES FOR MECHANISMS
         */
      
        
    
        /**********************************************
         * DRIVETRAIN INITIALIZATION
         */
        
        GRTDriveTrain dt = new GRTDriveTrain(leftDT1, leftDT2, rightDT1, rightDT2,"dt");
        
        dt.start();     dt.enable();
        
        dt.addDataLogger(new RPCLogger(rpcConn));
        robotBase = new GRTRobotBase(dt, batterySensor, encoder1, encoder2);
        driverStation = new GRTAttack3RobotDriver(primary, secondary,
                DRIVER_PROFILE_KEYS, DRIVER_PROFILES, -1,-1,-1, "driverStation");
        driverStation.enable();
        System.out.println("Drivetrain initialized");
            
        /**********************************************
         * MECHANISM INITIALIZATION
         */
//        Wedge wedge = new Wedge(wedgeVictor, up, down, "Wedge");
//        wedge.start();wedge.enable();
//        WedgeAttack3Controller wedgeControl = new WedgeAttack3Controller(primary, wedge);
        
        
        
       
        /**********************************************
         * CONTROLLER INITIALIZATION
         */
        driveControl = new PrimaryDriver(robotBase, driverStation, new LinearDrive(), "driveControl");

        System.out.println("Controllers Initialized");
        
        
        
        
        /***********************************************
         * LOGGING
         */
//        SensorLogger encoderLogger1 = new SensorLogger(encoder1, rpcConn, new int[]{81, 82, 83}, null);
//        SensorLogger encoderLogger2 = new SensorLogger(encoder2, rpcConn, new int[]{84, 85, 86}, null);
//        encoderLogger1.enable();
//        encoderLogger2.enable();
        
        System.out.println("Loggers initialized");


        /******************************************
         * MECHANISM INTIALIZATION
         */
        
        
//        GRTSwitch rotationLeftSwitch = new GRTSwitch(1, 14, "Rotation Left Switch");
//        GRTSwitch rotationRightSwitch = new GRTSwitch(1, 14, "Rotation Left Switch");
//        GRTSwitch visorBottomSwitch = new GRTSwitch(1, 14, "Rotation Left Switch");
//        GRTSwitch visorUpperSwitch = new GRTSwitch(1, 14, "Rotation Left Switch");
//        
//        Wedge wedge = new Wedge(wedgeVictor, up, down, "Wedge");
//        wedge.start();wedge.enable();
//        WedgeAttack3Controller wedgeControl = new WedgeAttack3Controller(primary, wedge);
        
        
//        Turret turr = new Turret(rotationVictor, visorVictor, null, null, rotationLeftSwitch, rotationRightSwitch, visorUpperSwitch, visorBottomSwitch);
        
//        TurretController turrControl = new TurretController(turr, tertiary);
        
        System.out.println("Mechanisms initialized");
      
        
        
        /*********************************************
         * ADD OUR TELEOP AND AUTONOMOUS CONTROLLERS,
         * AND LET'S GO!
         */
        addTeleopController(driveControl);
//        addTeleopController(turrControl);
//        addAutonomousController(balancer);
        System.out.println("All systems go!");
    }
}
