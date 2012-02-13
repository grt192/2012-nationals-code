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
import sensor.GRTXBoxJoystick;

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

    //Booleans
    private boolean maxBallsReached = false;        //True when we have all three balls
    
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

    protected void startListening() {
        System.out.println("listening for BG joy");
        joy.addJoystickListener(this);
        joy.addButtonListener(this);
        
        dtStickLeft.addButtonListener(this);
        dtStickRight.addButtonListener(this);
    }

    protected void stopListening() {
        joy.removeJoystickListener(this);
        joy.removeButtonListener(this);
        
        dtStickLeft.removeButtonListener(this);
        dtStickRight.removeButtonListener(this);
    }


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

    public void XAxisMoved(BGSystemsFXJoystickEvent e) {
        
    }

    /**
     * Ofer stick y-axis movement.
     * 
     * @param e The joystick event received.
     */
    public void YAxisMoved(BGSystemsFXJoystickEvent e) {
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
        if (e.getBallPosition() == BallTracker.IN_HOPPER && maxBallsReached){
            shootingSystem.setFlailSpeed(0.0);
            shootingSystem.setBottomTransitionSpeed(0.0);
            shootingSystem.disableCollection();
        }
    }

    /**
     * If the ball count changes, respond
     * @param e 
     */
    public void ballCountChanged(BallEvent e) {
        //If we have reached the max number of balls, we only run top rollers, 
        //not the flails
        if (e.getNumBalls() == 3){
            System.out.println("We have all our balls.");
            maxBallsReached = true;
            shootingSystem.enableAllSystems();
            shootingSystem.disableCollection();
            shootingSystem.setFlailSpeed(-1.0);

        } 
        //If we have too many balls, reverse the flails so that they repel,
        //and disable further collection.
        else if (e.getNumBalls() > 3){
            System.out.println("We have " + e.getNumBalls() + " balls.");
            shootingSystem.disableCollection();
            shootingSystem.setFlailSpeed(-1.0);

        }
        //If we have not filled up the number of balls, keep all systems enabled
        else {
            //If we have lost a ball to now have fewer than 3, we can stop repelling
            if (maxBallsReached){
                shootingSystem.setFlailSpeed(0.0);
            }
            System.out.println("We only have " + e.getNumBalls() + " balls.");
            shootingSystem.enableAllSystems();
        }
    }
    
}
