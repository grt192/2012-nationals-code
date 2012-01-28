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
import mechanism.Wedge;
import sensor.GRTAttack3Joystick;

/**
 *
 * @author dan
 */
public class WedgeAttack3Controller extends EventController implements 
        ButtonListener, Attack3JoystickListener{
    private final Wedge wedge;
    private final GRTAttack3Joystick stick;
    public WedgeAttack3Controller(GRTAttack3Joystick stick, Wedge wedge){
        super("Wedge Controller");
        this.stick = stick;
        this.wedge = wedge;
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
                case GRTAttack3Joystick.KEY_BUTTON_2:
                    wedge.lowerWedge();
                    break;
                case GRTAttack3Joystick.KEY_BUTTON_3:
                    wedge.raiseWedge();
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
