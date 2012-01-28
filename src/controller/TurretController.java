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
import mechanism.Turret;
import sensor.GRTAttack3Joystick;
import sensor.GRTEncoder;

/**
 * Controller for the Turret Mechanism
 * @author gerberduffy
 */
public class TurretController extends EventController implements Attack3JoystickListener, ButtonListener{

    private Turret turret;
    private GRTAttack3Joystick stick;
    
    
    private double P_CONSTANT = -.09;
    private double D_CONSTANT = .01;
    
    public static final double MOVE_THRESHOLD = .05; //Minimum distance to move the joystick without activating the rotating motor
        
    private double previousJoystickXValue;           //Previous joystick values
    private double previousJoystickYValue;

    
    public TurretController(Turret turr, GRTAttack3Joystick stick){
        
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
            
            
    /*
     * When the x axis of the controller is moved, swivel to the corresponding degree
     */
    public void xAxisMoved(Attack3JoystickEvent e) {
        log("x axis move:" + e.getValue());
        
        //rotator.setSpeed(e.getValue());
        
        
        if(Math.abs(e.getValue() - previousJoystickXValue) >= MOVE_THRESHOLD){
            turret.rotateToAngle(90.0*(e.getValue()+1));
            
            previousJoystickXValue = e.getValue();
        }
        
    }
    
    
    public void yAxisMoved(Attack3JoystickEvent e) {
        log("Y axis move:" + e.getValue());
        
        //visor.setSpeed(e.getValue());
        
        if(Math.abs(e.getValue() - previousJoystickYValue) >= MOVE_THRESHOLD){
            turret.setVisorAngle(90.0*(e.getValue()+1));
            
            System.out.println("y test pass");
            previousJoystickYValue = e.getValue();
        }
    }

    public void angleChanged(Attack3JoystickEvent e) {
        //This isn't the method you're looking for.
        //You can go about your business.
        //Move along, move along...
    }

    public void buttonPressed(ButtonEvent e) {
        if (e.getButtonID() == GRTAttack3Joystick.KEY_BUTTON_4){
            P_CONSTANT-= .01;
        } else if (e.getButtonID() == GRTAttack3Joystick.KEY_BUTTON_5){
            P_CONSTANT += .01;
        }
        System.out.println("P_CONSTANT NOW " + P_CONSTANT);
//        System.out.println("Encoder angles:\tRotator->" + rotationEncoder.getState(GRTEncoder.KEY_DEGREES) + "\tVisor->" + visorEncoder.getState(GRTEncoder.KEY_DEGREES));
    }

    public void buttonReleased(ButtonEvent e) {
    }
    
}
