/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import event.*;
import mechanism.Turret;
import sensor.GRTAttack3Joystick;
import sensor.GRTEncoder;
import sensor.GRTXBoxJoystick;

/**
 * Controller for the Turret Mechanism
 * @author gerberduffy
 */
public class TurretController extends EventController implements XboxJoystickListener, ButtonListener{

    private Turret turret;
    private GRTXBoxJoystick stick;
    
    public static final double MOVE_THRESHOLD = .05; //Minimum distance to move the joystick without activating the rotating motor
        
    private double previousJoystickXValue;           //Previous joystick values
    private double previousJoystickYValue;

    
    public TurretController(Turret turr, GRTXBoxJoystick stick){
        
        super("Turret Controller");
        
        this.turret = turr;
        this.stick = stick;
        
    }
    
    
    protected void startListening() {
        stick.addJoystickListener(this);
        stick.addButtonListener(this);
    }

    protected void stopListening() {
        stick.removeJoystickListener(this);
        stick.removeButtonListener(this);
    }

    /**
     * Left X-axis movement is mapped to turret swiveling.
     * @param e 
     */
    public void leftXAxisMoved(XboxJoystickEvent e) {
        System.out.println("pan at speed:" + e.getValue());
        turret.panAtSpeed(e.getValue());
        
//        //If we are within the required joystick movement threshold
//        if(Math.abs(e.getValue() - previousJoystickXValue) >= MOVE_THRESHOLD){
//            turret.rotateToAngle(90.0*(e.getValue()+1));    //e.getValue goes from [-1.0,1.0]. So +1 and *90 gives [0,180]
//
//
//            previousJoystickXValue = e.getValue();
//        }
    }

    /**
     * Left Y-axis movement is mapped to changing the visor angle.
     * @param e 
     */
    public void leftYAxisMoved(XboxJoystickEvent e) {
        System.out.println("tilt at speed:" + e.getValue());
        turret.tiltAtSpeed(e.getValue());
//        log("Y axis move:" + e.getValue());
//
//        if(Math.abs(e.getValue() - previousJoystickYValue) >= MOVE_THRESHOLD){
//            turret.shootAtAngle(90.0*(e.getValue()+1));
//
//            System.out.println("y test pass");
//            previousJoystickYValue = e.getValue();
//        }
    }

    public void leftAngleChanged(XboxJoystickEvent e) {
    }

    public void rightXAxisMoved(XboxJoystickEvent e) {
    }

    public void rightYAxisMoved(XboxJoystickEvent e) {
        System.out.println("flywheel at speed:" + e.getValue());
        turret.shootAtSpeed(e.getValue());
    }

    public void padMoved(XboxJoystickEvent e) {
    }

    public void triggerMoved(XboxJoystickEvent e) {
    }

    public void buttonPressed(ButtonEvent e) {
    }

    public void buttonReleased(ButtonEvent e) {
    }

    
}
