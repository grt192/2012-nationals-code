/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import deploy.MechTester;
import event.*;
import mechanism.BallTracker;
import mechanism.Drawbridge;
import mechanism.ShootingSystem;
import mechanism.Wedge;
import sensor.GRTAttack3Joystick;
import sensor.GRTBGSystemsFXJoystick;

/**
 *
 * @author dan, gerberduffy
 */
public class BetabotController extends EventController
implements ButtonListener,
        BGSystemsFXJoystickListener,
        BallTrackingListener{
    
    //Mechanisms
    private ShootingSystem shootingSystem;
    private Wedge wedge;
    private Drawbridge arm;
    private MechTester test;
    
    //Joysticks
    private final GRTBGSystemsFXJoystick joy;
    
    
    private boolean blackMod, redMod;
    private final GRTAttack3Joystick dtStickLeft;
    private final GRTAttack3Joystick dtStickRight;
    
    //Number of balls we have; we start with none
    private int numBalls = 0 ;
    
    //Booleans
    private boolean maxBallsReached = false;        //True when we have all three balls
    private boolean autoCollectionEnabled = false;         //True if automatic collection is enabled. Full manual collection by default.
    
    public BetabotController(GRTBGSystemsFXJoystick joy, GRTAttack3Joystick dtStickLeft, GRTAttack3Joystick dtStickRight, ShootingSystem sys, 
            Wedge wedge, Drawbridge arm, MechTester test){
        
        super("Mechanism Controller");
        
        //Set joystick fields
        this.joy = joy;
        this.dtStickLeft = dtStickLeft;
        this.dtStickRight = dtStickRight;
        
        //Set mechanism fields
        this.shootingSystem = sys;
        this.wedge = wedge;
        this.arm = arm;
        
        //Set mech tester field
        this.test = test;
        
    }
    
    /**
     * Enable autonomous collection of balls until we reach max
     */
    public void enableAutoCollection(){
        System.out.println("Starting auto ball collection...");
        autoCollectionEnabled = true;
        
        
        //Start collecting if we don't have full number of balls yet
//        if (numBalls <= 3){
            shootingSystem.setFlailSpeed(1.0);
            shootingSystem.setBottomTransitionSpeed(1.0);
            shootingSystem.setTopTransitionSpeed(-1.0);
//        }
        
        //Nix ball collection by driver
        shootingSystem.disableCollection();
    }
    
    /**
     * Disable autonomous collection, and set full manual collection
     */
    public void disableAutoCollection(){
        System.out.println("Stopping auto ball collection...");
        autoCollectionEnabled = false;
        
        //Reenable manual collection setting
        shootingSystem.enableCollection();
        
        //Now stop all previously running shooting system mechs
        shootingSystem.setFlailSpeed(0.0);
        shootingSystem.setBottomTransitionSpeed(0.0);
        shootingSystem.setTopTransitionSpeed(0.0);
        shootingSystem.setFlywheelSpeed(0.0);
    }

    /**
     * Start listening to joysticks.
     */
    protected void startListening() {
        System.out.println("listening for BG joy");
        joy.addJoystickListener(this);
        joy.addButtonListener(this);
        
        dtStickLeft.addButtonListener(this);
        dtStickRight.addButtonListener(this);
    }

    /**
     * Stop listening to joysticks.
     */
    protected void stopListening() {
        joy.removeJoystickListener(this);
        joy.removeButtonListener(this);
        
        dtStickLeft.removeButtonListener(this);
        dtStickRight.removeButtonListener(this);
    }


    /**
     * Respond to button press event.
     * @param e The event.
     */
    public void buttonPressed(ButtonEvent e) {
        System.out.println("got BG joy button "+e.getButtonID());
        
        //If the Ofer stick was pressed:
        if (e.getSource() == joy){ 
            //The red button acts as a mod key. When enabled, the y axis 
            //controls wedge speed
            if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_RED){
                redMod = true;
            } 
            //Black button is mod key to enable y-axis control of the flail arm
            // raising and lowering
            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_BLACK){
                blackMod = true;
            }
            //Pressing down on the black pad sets bottom rollers to full reverse
            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_DOWN){
                shootingSystem.setBottomTransitionSpeed(-1.0);
            } 
            //Pressing up on black pad sets bottom rollers to full collection
            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_UP){
                shootingSystem.setBottomTransitionSpeed(1.0);
            }

            //Half trigger turns the fly wheel @ full speed
            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_TRIGGER_HALF){
                shootingSystem.setFlywheelSpeed(1.0);
            }
            //Full trigger shoots (i.e. starts the top rollers at full speed and 
            // feeds a ball to the flywheel, which is also at full speed
            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_TRIGGER_FULL){
                shootingSystem.setTopTransitionSpeed(-1.0);
            }
            //Starts a mechanism tester - MechTester also uses KEY_GRAY_DOWN, KEY_GRAY_RIGHT, KEY_GRAY_LEFT
            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_GRAY_UP) {
//                test.run();
            }
        }
        
        //On the left Attack3 joystick
        else if (e.getSource() == dtStickLeft){
            //LEFT TRIGGER ----> FLAILS ARE IN FULL REVERSE (REPEL BALLS)
            if (e.getButtonID() == GRTAttack3Joystick.KEY_TRIGGER){
                System.out.println("BC::Left Trigger pressed");
                shootingSystem.setFlailSpeed(-1.0);
            }
            
            else if (e.getButtonID() == GRTAttack3Joystick.KEY_BUTTON_3){
                enableAutoCollection();
            } 
            else if (e.getButtonID() == GRTAttack3Joystick.KEY_BUTTON_2){
                disableAutoCollection();
            }
        }
        
        //On the right Attack3 joystick
        else if (e.getSource() == dtStickRight){
            //RIGHT TRIGGER ---> FLAILS ARE SET TO INTAKE
            if (e.getButtonID() == GRTAttack3Joystick.KEY_TRIGGER){
                System.out.println("BC::Right Trigger pressed");
                shootingSystem.setFlailSpeed(1.0);
            }
        }
    }

    public void buttonReleased(ButtonEvent e) {
        //Ofer stick released handling:
        if (e.getSource() == joy) {
            //RED BUTTON: FLAIL DROPDOWN MODIFIER TO JOYSTICK Y
            if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_RED) {
                redMod = false;
                arm.setDrawbridgeSpeed(0.0);
            //BLACK BUTTON: WEDGE DROPDOWN MODIFIER TO JOYSTICK Y
            } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_BLACK) {
                blackMod = false;
                wedge.setWedgeSpeed(0.0);
            // BLACK PAD DOWN: TRANSITION AND FLAIL SHUTOFF
            } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_DOWN) {
                shootingSystem.setBottomTransitionSpeed(0.0);
            //BLACK PAD UP: TRANSITION AND SPEED SHUTOFF
            } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_UP) {
                shootingSystem.setBottomTransitionSpeed(0.0);
             //TRIGGER RELEASE
            } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_TRIGGER_FULL) {
                shootingSystem.setTopTransitionSpeed(0.0);
            }
        }
            
               //On the left Attack3 joystick
        else if (e.getSource() == dtStickLeft){
            //LEFT TRIGGER RELEASED ----> HALT FLAILS
            if (e.getButtonID() == GRTAttack3Joystick.KEY_TRIGGER){
                shootingSystem.setFlailSpeed(0.0);
            }
        }
        //On the right Attack3 joystick
        else if (e.getSource() == dtStickRight){
            //RIGHT TRIGGER RELEASESD ---> HALT FLAILS
            if (e.getButtonID() == GRTAttack3Joystick.KEY_TRIGGER){
                shootingSystem.setFlailSpeed(0.0);
            }
        }
        
    }

    /**
     * Empty implementation. Move along, move along...
     * @param e The joystick event that we could care less about
     */
    public void xAxisMoved(BGSystemsFXJoystickEvent e) {
        
    }

    /**
     * Ofer stick y-axis movement.
     * 
     * @param e The joystick event received.
     */
    public void yAxisMoved(BGSystemsFXJoystickEvent e) {
        //If the red modifier key and the black modifier key are both unpressed:
        if (!redMod && !blackMod){
            //Set visor set-screw speed to the given y value
            shootingSystem.setVisorSpeed(e.getValue());
        } 
        //If the red modifier key is being held, arm control is enabled
        else if (redMod){
            arm.setDrawbridgeSpeed(e.getValue());
        } 
        //The black modifier key enables wedge control
        else if (blackMod){
            wedge.setWedgeSpeed(e.getValue());
        }
    }

    public void angleChanged(BGSystemsFXJoystickEvent e) {
    }

    public void forceXMoved(BGSystemsFXJoystickEvent e) {
    }

    public void forceYMoved(BGSystemsFXJoystickEvent e) {
        shootingSystem.setTopTransitionSpeed(-e.getValue());
    }

    public void twistChanged(BGSystemsFXJoystickEvent e) {
        shootingSystem.setPanSpeed(-e.getValue());
    }

    /**
     * Respond to ball position changes
     * @param e 
     */
    public void ballPositionChanged(BallEvent e) {
        if(e.getNumBalls() >= 3 && autoCollectionEnabled){
            //If we have all our balls, stop our collection.
            if (e.getBallPosition() == BallTracker.IN_HOPPER && maxBallsReached){
                shootingSystem.haltCollection();
                shootingSystem.enableShooting();
            }
            
            else if (e.getBallPosition() == BallTracker.IN_QUEUE){
                System.out.println("Ball queued");
                shootingSystem.setTopTransitionSpeed(0.0);
            }
            
            else {
                enableAutoCollection();
            }
        }
    }

    /**
     * If the ball count changes, respond.
     * @param e 
     */
    public void ballCountChanged(BallEvent e) {
        System.out.println("We now have " + e.getNumBalls() + " balls.");
        numBalls = e.getNumBalls();     //Set the number of balls we control.
        //Only execute following logic if autonomous ball collection is enabled:
        if (autoCollectionEnabled){
            
            //If we have reached the max number of balls, we only run top rollers, 
            //not the flails
            if (e.getNumBalls() == 3){
                System.out.println("We have all our balls.");
                maxBallsReached = true;
                shootingSystem.enableAllSystems();
                shootingSystem.haltCollection();
                shootingSystem.setFlailSpeed(-1.0);

            } 
            //If we have too many balls, reverse the flails so that they repel,
            //and disable further collection.
            else if (e.getNumBalls() > 3){
                shootingSystem.setFlailSpeed(-1.0);
                //VERY IMPORTANT TO DISABLE SECOND.
                shootingSystem.disableCollection();

            }
            //If we have not filled up the number of balls, keep all systems enabled
            else {
                //If we have lost a ball to now have fewer than 3, we can stop repelling
                if (maxBallsReached){
                    enableAutoCollection(); //Go back to collecting balls.
                }
                maxBallsReached = false;
            }
            
            shootingSystem.enableAllSystems();
        }
        
        else {
            //If auto collection is disabled, we don't respond to anything.
        }

    
    }
    
}
