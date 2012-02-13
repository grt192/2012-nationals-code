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
import controller.*;
import controller.rpc.RPCShootingController;

import logger.RPCLogger;
import mechanism.*;
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
     * Buttons which refer to enumerated driver curve profiles. First index
     * refers to Linear drive Second index refers to Square drive
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
        GRTBGSystemsFXJoystick tertiary = new GRTBGSystemsFXJoystick(3, 12, "Xbox Joystick");

        primary.start();
        secondary.start();
        primary.enable();
        secondary.enable();
        tertiary.start();
        tertiary.enable();
        System.out.println("Joysticks initialized");

        //Battery Sensor
        GRTBatterySensor batterySensor = new GRTBatterySensor(10, "battery");
        batterySensor.start();
        batterySensor.enable();

        //DriveTrain Encoders
        GRTEncoder dtEncoderLeft = new GRTEncoder(2, 1, 0.32, 10, "EncoderLeft");
        GRTEncoder dtEncoderRight = new GRTEncoder(3, 4, 0.32, 10, "EncoderRight");
        dtEncoderLeft.start();
        dtEncoderLeft.enable();
        dtEncoderRight.start();
        dtEncoderRight.enable();
        
        //Mechanism Encoders
        GRTEncoder turretRotationEncoder = new GRTEncoder(5,6,4.0,10,"RotationEncoder");
        GRTEncoder turretVisorEncoder = new GRTEncoder(7,8,4.0,10,"Visor Encoder");
        turretRotationEncoder.start();
        turretRotationEncoder.enable();
        turretVisorEncoder.start();
        turretVisorEncoder.enable();
        

        /**
         * *******************************************
         * VICTORS USED FOR THE DRIVETRAIN.
         */

        /*
         * //Bessy Code GRTVictor leftDT1 = new GRTVictor(LEFT_SIDECAR_MODULE,
         * 2, "leftDT1"); GRTVictor leftDT2 = new GRTVictor(LEFT_SIDECAR_MODULE
         * ,3, "leftDT2"); GRTVictor rightDT1 = new
         * GRTVictor(LEFT_SIDECAR_MODULE, 8, "rightDT1"); GRTVictor rightDT2 =
         * new GRTVictor(LEFT_SIDECAR_MODULE, 9, "rightDT2");
         */
        GRTVictor leftDT1 = new GRTVictor(LEFT_SIDECAR_MODULE, 9, "leftDT1");
        GRTVictor leftDT2 = new GRTVictor(LEFT_SIDECAR_MODULE, 10, "leftDT2");
        GRTVictor rightDT1 = new GRTVictor(RIGHT_SIDECAR_MODULE, 9, "rightDT1");
        GRTVictor rightDT2 = new GRTVictor(RIGHT_SIDECAR_MODULE, 10, "rightDT2");


        leftDT1.start();
        leftDT1.enable();
        leftDT2.start();
        leftDT2.enable();
        rightDT1.start();
        rightDT1.enable();
        rightDT2.start();
        rightDT2.enable();

        /**
         * *******************************************
         * VICTORS FOR MECHANISMS
         */
        //Mechanism Victors
        GRTVictor wedgeVictor = new GRTVictor(LEFT_SIDECAR_MODULE, 4, "Wedge Victor");
        GRTVictor rotationVictor = new GRTVictor(RIGHT_SIDECAR_MODULE, 3, "Turret Rotator Victor");
        GRTVictor visorVictor = new GRTVictor(RIGHT_SIDECAR_MODULE, 4, "Visor Tilt Victor");
        GRTVictor flywheelVictor1 = new GRTVictor(RIGHT_SIDECAR_MODULE, 7, "flywheel1");
        GRTVictor flywheelVictor2 = new GRTVictor(RIGHT_SIDECAR_MODULE, 8, "flywheel2");
        GRTVictor drawbridgeVictor = new GRTVictor(RIGHT_SIDECAR_MODULE, 5, "drawbridge");
        GRTVictor flailVictor1 = new GRTVictor(LEFT_SIDECAR_MODULE, 3, "flail1");
        GRTVictor topTransVictor1 = new GRTVictor(LEFT_SIDECAR_MODULE, 5, "trans1");
        GRTVictor topTransVictor2 = new GRTVictor(LEFT_SIDECAR_MODULE, 6, "trans2");
        GRTVictor botTransVictor1 = new GRTVictor(LEFT_SIDECAR_MODULE, 7, "bottrans1");
        GRTVictor botTransVictor2 = new GRTVictor(LEFT_SIDECAR_MODULE, 8, "bottrans2");




        wedgeVictor.start();
        wedgeVictor.enable();
        rotationVictor.start();
        rotationVictor.enable();
        visorVictor.start();
        visorVictor.enable();
        flywheelVictor1.start();
        flywheelVictor1.enable();
        flywheelVictor2.start();
        flywheelVictor2.enable();
        drawbridgeVictor.start();
        drawbridgeVictor.enable();
        flailVictor1.start();
        flailVictor1.enable();
//      flailVictor2.start();		flailVictor2.enable();
        topTransVictor1.start();
        topTransVictor1.enable();
        topTransVictor2.start();
        topTransVictor2.enable();
        botTransVictor1.start();
        botTransVictor1.enable();
        botTransVictor2.start();
        botTransVictor2.enable();




        System.out.println("Motors initialized");

        /**
         * *******************************************
         * SWITCHES FOR MECHANISMS
         */
        /**
         * ********************************************
         * DRIVETRAIN INITIALIZATION
         */
        GRTDriveTrain dt = new GRTDriveTrain(leftDT1, leftDT2, rightDT1, rightDT2, "dt");
        dt.start();
        dt.enable();

        dt.addDataLogger(new RPCLogger(rpcConn));
        robotBase = new GRTRobotBase(dt, batterySensor, dtEncoderLeft, dtEncoderRight);
        driverStation = new GRTAttack3RobotDriver(primary, secondary,
                DRIVER_PROFILE_KEYS, DRIVER_PROFILES, -1, -1, -1, "driverStation");
        driverStation.enable();
        System.out.println("Drivetrain initialized");

        /**
         * ********************************************
         * CONTROLLER INITIALIZATION
         */
        driveControl = new PrimaryDriver(robotBase, driverStation, new LinearDrive(), "driveControl");

        System.out.println("Controllers initialized");




        /**
         * *********************************************
         * LOGGING
         */
//        SensorLogger encoderLogger1 = new SensorLogger(dtEncoderLeft, rpcConn, new int[]{10, 11, 12, 13}, null);
//        SensorLogger encoderLogger2 = new SensorLogger(dtEncoderRight, rpcConn, new int[]{14, 15, 16, 17}, null);
//        encoderLogger1.start();
//        encoderLogger1.enable();
//        encoderLogger2.start();
//        encoderLogger2.enable();

        batterySensor = new GRTBatterySensor(10, "batterySEnsor");
        batterySensor.start();
        batterySensor.enable();

        SensorLogger batteryLogger = new SensorLogger(batterySensor, rpcConn, new int[] {141}, "batteryLogger");
        batteryLogger.start();
        batteryLogger.enable();

        System.out.println("Loggers initialized");


        /**
         * ****************************************
         * MECHANISM INTIALIZATION
         */
        
        //
        GRTSwitch collectionSwitch = new GRTSwitch(LEFT_SIDECAR_MODULE,14, 14, "Rotation Left Switch");
        GRTSwitch upperRollersSwitch = new GRTSwitch(LEFT_SIDECAR_MODULE,13, 14, "Rotation Left Switch");
        GRTSwitch hopperSwitch = new GRTSwitch(LEFT_SIDECAR_MODULE, 12, 14, "Rotation Left Switch");
        GRTSwitch ballQueueSwitch = new GRTSwitch(LEFT_SIDECAR_MODULE, 11, 14, "Rotation Left Switch");
        
        collectionSwitch.start(); collectionSwitch.enable();
        upperRollersSwitch.start(); upperRollersSwitch.enable();
        hopperSwitch.start(); hopperSwitch.enable();
        ballQueueSwitch.start(); ballQueueSwitch.enable();

        Wedge wedge = new Wedge(wedgeVictor, null, null, "Wedge");
        wedge.start();
        wedge.enable();
        Drawbridge drawbridge = new Drawbridge(drawbridgeVictor, null, null, "DrawBridge");
        drawbridge.start();
        drawbridge.enable();

        ShootingSystem ss = new ShootingSystem(rotationVictor, visorVictor,
                flywheelVictor1, flywheelVictor2,
                flailVictor1,
                topTransVictor1, topTransVictor2,
                botTransVictor1, botTransVictor2, 
                turretRotationEncoder, turretVisorEncoder);
        ss.start(); ss.enable();
        ss.enableAllSystems();      //YOU MUST DO THIS BEFORE USING THE SHOOTING SYSTEMS
        
        ss.addDataLogger(new RPCLogger(rpcConn));
        
        BallTracker bt = new BallTracker(collectionSwitch, hopperSwitch, upperRollersSwitch, ballQueueSwitch);
        bt.start(); bt.enable();
        
        
        MechTester tester = new MechTester(tertiary, dt, wedge, drawbridge, ss, "Mechanism Tester");
        
        BetabotController bc = new BetabotController(tertiary, primary, secondary, ss, wedge, drawbridge, tester);
        
        bt.addBallListener(bc);
        
        RPCShootingController shootControl = new RPCShootingController(rpcConn, ss, 88);
        
//        RPCController rpcController = new RPCController(rpcConn, new int[] {1183} , dt);
//        rpcController.start(); rpcController.enable();

        System.out.println("Mechanisms initialized");



        /**
         * *******************************************
         * ADD OUR TELEOP AND AUTONOMOUS CONTROLLERS, AND LET'S GO!
         */
        addTeleopController(driveControl);
        addTeleopController(bc);
        addTeleopController(shootControl);
//        addTeleopController(encTest);
//        addTeleopController(rpcController);
//        addTeleopController(distTest);
//        addAutonomousController(balancer);
        System.out.println("All systems go!!");
    }
}
