/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import event.*;
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
        BGSystemsFXJoystickListener  {
    
    //Mechanisms
    private ShootingSystem shootingSystem;
    private Wedge wedge;
    private Drawbridge arm;
    
    //Joysticks
    private final GRTBGSystemsFXJoystick joy;
    
    
    private boolean blackMod, redMod;
    private final GRTAttack3Joystick dtStickLeft;
    private final GRTAttack3Joystick dtStickRight;
    
    public BetabotController(GRTBGSystemsFXJoystick joy, GRTAttack3Joystick dtStickLeft, GRTAttack3Joystick dtStickRight, ShootingSystem sys, 
            Wedge wedge, Drawbridge arm){
        
        super("Mechanism Controller");
        
        //Set joystick fields
        this.joy = joy;
        this.dtStickLeft = dtStickLeft;
        this.dtStickRight = dtStickRight;
        
        //Set mechanism fields
        this.shootingSystem = sys;
        this.wedge = wedge;
        this.arm = arm;
        
    }

    protected void startListening() {
        System.out.println("listening for BG joy");
        joy.addJoystickListener(this);
        joy.addButtonListener(this);
    }

    protected void stopListening() {
        joy.removeJoystickListener(this);
        joy.removeButtonListener(this);
    }


    public void buttonPressed(ButtonEvent e) {
        System.out.println("got BG joy button "+e.getButtonID());
        if (e.getSource() == joy){
            if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_RED){
                redMod = true;
            } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_BLACK){
                blackMod = true;
            }
            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_DOWN){
                shootingSystem.setFlailSpeed(-1.0);
            } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_UP){
                shootingSystem.setFlailSpeed(1.0);
            }

            else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_TRIGGER_FULL){
                shootingSystem.setTransitionSpeed(-1.0);
            } 
        } 
//        else if (e.getSource() == dtStickLeft){
//            if (e.getButtonID() == )
//        }
    }

    public void buttonReleased(ButtonEvent e) {
        if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_RED){
            redMod = false;
            arm.setDrawbridgeSpeed(0.0);
        } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BUTTON_BLACK){
            blackMod = false;
            wedge.setWedgeSpeed(0.0);
        } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_DOWN){
            shootingSystem.setFlailSpeed(0.0);
        } else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_BLACK_UP){
            shootingSystem.setFlailSpeed(0.0);
        }
        else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_TRIGGER_FULL){
            shootingSystem.setTransitionSpeed(0.0);
        } 

        
        
    }

    public void XAxisMoved(BGSystemsFXJoystickEvent e) {
        
    }

    public void YAxisMoved(BGSystemsFXJoystickEvent e) {
        if (!redMod && !blackMod){
            shootingSystem.setFlywheelSpeed(e.getValue());
        } else if (redMod){
            arm.setDrawbridgeSpeed(e.getValue());
        } else if (blackMod){
            wedge.setWedgeSpeed(e.getValue());
        }
    }

    public void angleChanged(BGSystemsFXJoystickEvent e) {
    }

    public void forceXMoved(BGSystemsFXJoystickEvent e) {
    }

    public void forceYMoved(BGSystemsFXJoystickEvent e) {
        shootingSystem.setVisorSpeed(e.getValue());
    }

    public void twistChanged(BGSystemsFXJoystickEvent e) {
        shootingSystem.setPanSpeed(-e.getValue());
    }
    
}
