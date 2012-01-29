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
import sensor.base.GRTAttack3RobotDriver;
import sensor.base.GRTRobotDriver;

/**
 * 
 * An Event Controller that raises and lowers the bridge-lowering mechanism,
 *  and stops when a limit switch has been tripped.
 * 
 * @author dan
 */
public class WedgeAttack3Controller extends EventController implements 
        ButtonListener {
    private final Wedge wedge;
    private final GRTAttack3Joystick stick;
    
    public WedgeAttack3Controller(GRTAttack3Joystick stick, Wedge wedge){
        super("Wedge Controller");
        
        this.stick = stick;
        this.wedge = wedge;
    }

    /**
     * Start listening to joystick input, and 
     * have the wedge start listening for limit switch
     */
    protected void startListening() {
        stick.addButtonListener(this);
        wedge.startListening();
    }

    /**
     * Stop listening to limit switches and
     * joystick buttons.
     */
    protected void stopListening() {
        stick.removeButtonListener(this);
        wedge.stopListening();
    }

    public void buttonPressed(ButtonEvent e) {
        System.out.println("Button pressed");
        if (e.getButtonID() == GRTAttack3Joystick.KEY_BUTTON_2){
            wedge.lowerWedge();
        }
        
        else if (e.getButtonID() == GRTAttack3Joystick.KEY_BUTTON_3){
            wedge.raiseWedge();
        }
    }

    public void buttonReleased(ButtonEvent e) {
    }
}
