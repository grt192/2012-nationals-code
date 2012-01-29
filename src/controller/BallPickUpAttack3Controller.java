/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import event.Attack3JoystickEvent;
import event.Attack3JoystickListener;
import event.ButtonEvent;
import event.ButtonListener;
import mechanism.BallFeeder;
import sensor.GRTAttack3Joystick;

/**
 *
 * @author dan
 */
public class BallPickUpAttack3Controller extends EventController implements 
        ButtonListener, Attack3JoystickListener{
    private final GRTAttack3Joystick stick;
    private final BallFeeder pick;
    public BallPickUpAttack3Controller(GRTAttack3Joystick stick, BallFeeder pick){
        super("Wedge Controller");
        this.stick = stick;
        this.pick = pick;
    }

    protected void startListening() {
        stick.addButtonListener(this);
        stick.addJoystickListener(this);
    }

    protected void stopListening() {
        stick.removeButtonListener(this);
        stick.removeJoystickListener(this);
    }

    public void buttonPressed(ButtonEvent e) {
        if (e.getSource() == stick){
            switch (e.getButtonID()){
                case GRTAttack3Joystick.KEY_TRIGGER:
                    pick.pickUpBall();
                    break;
                case GRTAttack3Joystick.KEY_BUTTON_4:
                    pick.stopPickUp();
                    break;
            }
        }
    }

    public void buttonReleased(ButtonEvent e) {
    }

    public void xAxisMoved(Attack3JoystickEvent e) {
    }

    public void yAxisMoved(Attack3JoystickEvent e) {
    }

    public void angleChanged(Attack3JoystickEvent e) {
    }
    
    
}
