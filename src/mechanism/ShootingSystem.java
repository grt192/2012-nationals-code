/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import core.GRTDataLogger;
import core.GRTLoggedProcess;
import event.EncoderEvent;
import event.EncoderListener;
import sensor.GRTEncoder;

/**
 *
 * @author gerberduffy, dan, nadia
 */
public class ShootingSystem extends GRTLoggedProcess implements EncoderListener{

    //All of our motors
    private final IMotor rotationMotor;
    private final IMotor visorMotor;
    private final IMotor flywheelMotor1;
    private final IMotor flywheelMotor2;
    private final IMotor flailMotor;
    private final IMotor topTransMotor1;
    private final IMotor topTransMotor2;
    private final IMotor botTransMotor1;
    private final IMotor botTransMotor2;
    public static final boolean STARTED = true;
    public static final boolean STOPPED = !STARTED;

    //Encoders
    private final GRTEncoder rotationEncoder;
    private final GRTEncoder visorEncoder;
    
    //Other Fields
    private double rotationAngle;
    private double visorAngle;
    
    //Enable booleans for actuation logic.
    private boolean visorEnabled = false;
    private boolean rotationEnabled = false;
    private boolean collectionEnabled = false;
    private boolean shootingEnabled = false;
    
    private double currentFlywheelSpeed;
    //Constants
    private double rotationRatio = 1.0/7.1; //(radius of gear/radius of lazy susan)
    private boolean flywheelState = STOPPED;
    private boolean topTransitionState = STOPPED;
    private double flywheelRampSpeed;
    private boolean ramping;
    private static final long RAMPING_SLEEP_TIME = 5;



    public ShootingSystem(IMotor rotationMotor, IMotor visorMotor,
            IMotor flywheelMotor1, IMotor flywheelMotor2,
            IMotor flailMotor,
            IMotor topTransMotor1, IMotor topTransMotor2,
            IMotor botTransMotor1, IMotor botTransMotor2, GRTEncoder rotationEncoder, GRTEncoder visorEncoder) {
        super("Shooting System");
        this.rotationMotor = rotationMotor;
        this.visorMotor = visorMotor;
        this.flywheelMotor1 = flywheelMotor1;
        this.flywheelMotor2 = flywheelMotor2;
        this.flailMotor = flailMotor;
        this.topTransMotor1 = topTransMotor1;
        this.topTransMotor2 = topTransMotor2;
        this.botTransMotor1 = botTransMotor1;
        this.botTransMotor2 = botTransMotor2;
        this.rotationEncoder = rotationEncoder;
        this.visorEncoder = visorEncoder;

    }

    //changes rotation of turret
    public void setPanSpeed(double speed) {
        rotationMotor.setSpeed(speed);
        
        log(118, speed);
//        if (enable && rotationEnable) {
//            if (speed<=0.0) { //moves right
//                while(rotationAngle>-90.0) rotationMotor.setSpeed(speed); //limits movement right
//            }
//            else { //moves left
//                while(rotationAngle<90.0) rotationMotor.setSpeed(speed); //limits movement left
//            }
//        }
    }
    
    //changes angle of turret visor
    public void setVisorSpeed(double speed) {
        visorMotor.setSpeed(speed);
//        if (enable && visorEnable) {
//            if (speed<=0.0) {  // moves down\\
//                while(visorAngle<0.0) visorMotor.setSpeed(speed); //limits the movement of visor down
//            }
//            else {  //moves up
//                while(visorAngle>90.0) visorMotor.setSpeed(speed); //limits the movement of visor up
//            }
//        }
    }


    //changes speed of the turret flywheel
    public void setFlywheelSpeed(double speed) {
        if (flywheelState == STARTED) {
            flywheelMotor1.setSpeed(speed);
            flywheelMotor2.setSpeed(speed);
        }
        currentFlywheelSpeed = speed;

    }

    /**
     * Changes speed of the transition into turret
     * This should not be affected by any enables/disables.
     * 
     */
    public void setTopTransitionSpeed(double speed) {
            topTransMotor1.setSpeed(speed); 
            topTransMotor2.setSpeed(speed);
    }

    /*
     * Changes collector speed
     * @param speed The new Flail speed
     */
    public void setFlailSpeed(double speed) {
        if (collectionEnabled) {
            System.out.println("SS::Setting flail speed");
            flailMotor.setSpeed(speed);
        }
    }

    /**
     * Changes speed of the transition after collector
     */
    public void setBottomTransitionSpeed(double speed) {
        if (collectionEnabled) {
            System.out.println("SS::Setting Bottom Trans speed");
            botTransMotor1.setSpeed(speed);
            botTransMotor2.setSpeed(speed);
        }
    }
    
    
    /******************************************************
     * METHODS TO ENABLE/DISABLE DIFFERENT ASPECTS OF THE *
     *             SHOOTING AND COLLECTION SYSTEM         *                        
     ******************************************************/
    
    
    /**
     * Enable ALL SHOOTING/COLLECTION SYSTEMS.
     * NOTE: You should do this for each instance of 
     * a shooting system, as all systems are disabled
     * by default.
     */
    public void enableAllSystems(){
        enableCollection();
        enableRotation();
        enableVisor();
        enableShooting();
    }
    
    /**
     * Disables ALL SHOOTING/COLLECTION SYSTEMS
     */
    public void disableAllSystems(){
        disableCollection();
        disableRotation();
        disableVisor();
        disableShooting();    
    }
    public void stopFlywheel(){
        double tmp = currentFlywheelSpeed;
        setFlywheelSpeed(0);
        currentFlywheelSpeed = tmp;
        flywheelState = STOPPED;
        System.out.println("Stopping fly");


    }
    public void startFlywheel(){
        flywheelState = STARTED;
        setFlywheelSpeed(currentFlywheelSpeed);
        System.out.println("Starting fly");

    }
    public void toggleFlywheel(){
        if (flywheelState == STARTED){
            stopFlywheel();
        }
        else {
            startFlywheel();
        }
    }
    public void stopTopTransition(){
        setTopTransitionSpeed(0);
        topTransitionState = STOPPED;
        System.out.println("Stopping trans");

    }
    public void startTopTransition(){
        setTopTransitionSpeed(-1);
         topTransitionState = STARTED;
         System.out.println("Starting trans");

    }
    public void toggleTopTransition(){
         if (topTransitionState == STARTED){
             
            stopTopTransition();
        }
        else {
            startTopTransition();
        }
        
    }
    public void incrementFlywheelSpeed(double speedInc){
        System.out.println("Setting Flywheel to: "+ (currentFlywheelSpeed  + speedInc));
        setFlywheelSpeed(currentFlywheelSpeed  + speedInc);
    }
    public void loadBall(){
        startTopTransition();
    }
    public void enable() {
        enabled = true;
    }
    
    /**
     * Halt ALL SYSTEMS.
     */
    public void haltAllSystems(){
        haltCollection();
        haltRotation();
        haltShooting();
        haltVisor();
    }
    
    /**
     * Enable the turret rotation
     */
    public void enableRotation() {
        System.out.println("enabling rotation");
        rotationEnabled = true;
    }

    /**
     * Disable turret rotation
     */
    public void disableRotation() {
        rotationEnabled = false;
        //Halt turret actuation
        rotationMotor.setSpeed(0.0);
    }
    
    /**
     * Enable visor angle adjustment.
     */
    public void enableVisor() {
        System.out.println("enabling visor");
        visorEnabled = true;
    }
    
    /**
     * Disable visor angle adjustment
     */
    public void disableVisor() {
        visorEnabled = false;
        //Halt visor actuation
        visorMotor.setSpeed(0.0);
    }
    
    /**
     * Enable the collection mechanisms.
     * This includes just the lower rollers and the flails
     */
    public void enableCollection(){
        System.out.println("enabling collection");
        collectionEnabled = true;
    }
    
    /**
     * Disables the collection mechanisms.
     * This includes just the lower rollers and the flails
     * @param e 
     */
    public void disableCollection(){
        collectionEnabled = false;
    }
    
    /**
     * Halt rotation motor.
     */
    public void haltRotation(){
        rotationMotor.setSpeed(0.0);
    }
    
    /**
     * Halt shooting motor.
     */
    public void haltShooting(){
        flywheelMotor1.setSpeed(0.0);
        flywheelMotor2.setSpeed(0.0);
        
        topTransMotor1.setSpeed(0.0);
        topTransMotor2.setSpeed(0.0);
    }
    
    /**
     * Halt visor motor.
     */
    public void haltVisor(){
        visorMotor.setSpeed(0.0);
    }
    
    
    
    /**
     * Halt all collection.
     */
    public void haltCollection(){
        botTransMotor1.setSpeed(0.0);
        botTransMotor2.setSpeed(0.0);
        flailMotor.setSpeed(0.0);
    }
    
    /**
     * Enable shooting (flywheel).
     */
    public void enableShooting(){
        System.out.println("enabling shooting");
        shootingEnabled = true;
    }
    
    /**
     * Disable shooting (flywheel)
     */
    public void disableShooting(){
        shootingEnabled = false;
        //Halt all shooting actuation
        flywheelMotor1.setSpeed(0.0);
        flywheelMotor2.setSpeed(0.0);
    }
    
    /**
     * Respond to encoder direction change
     * @param e 
     */
    public void directionChanged(EncoderEvent e) {
    }

    /**
     * Respond to the angle change of the encoder
     * @param e 
     */
    public void degreeChanged(EncoderEvent e) {
        if (e.getSource() == rotationEncoder) rotationAngle = (rotationAngle + (rotationRatio * e.getAngle()));
        else if (e.getSource() == visorEncoder) visorAngle = (visorAngle + e.getAngle());
    }

    public void distanceChanged(EncoderEvent e) {
    }

    public void rateChanged(EncoderEvent e) {
    }
    
    
    /**
     * Accelerate the flywheel at a constant rate.
     * @param percentPerSecond the rate in % of total speed per second to
     * accelerate the flywheel towards a rate of 0% or 100%.
     */
    public void setFlywheelRampSpeed(double percentPerSecond){
        flywheelRampSpeed = percentPerSecond;
        startRamping();
    }

    private void startRamping() {
        if (ramping){
            return;
        }
        else{
            ramping = true;
        }
        while(currentFlywheelSpeed > 0 && currentFlywheelSpeed < 1){
            incrementFlywheelSpeed(flywheelRampSpeed*RAMPING_SLEEP_TIME/1000);
            try {
                Thread.sleep(RAMPING_SLEEP_TIME);
            } catch (InterruptedException ex) {
                break;
            }
        }
        ramping = false;
    }

}
