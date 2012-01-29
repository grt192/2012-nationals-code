/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import core.GRTLoggedProcess;
import event.*;
import sensor.GRTAttack3Joystick;
import sensor.GRTEncoder;
import sensor.GRTSwitch;

/**
 *
 * Turret is responsible for the swiveling of the base of the shooter to different angles
 * 
 * @author gerberduffy
 */
public class Turret extends GRTLoggedProcess implements SwitchListener, EncoderListener {
    
    private IMotor rotator;
    private IMotor visor;
        
    private double rotatorZeroAngle  = -90.0;                 //The relative zero angle. Defaults to whatever the encoder's current zero is
    private double visorZeroAngle = -90.0;   
    
    
    private double desiredRotationAngle = 0.0;                    //Angle we want to be at
    private double desiredVisorAngle = 0.0;                       //Desired angle to set visor to
    

    //Encoders for the rotation and the visor
    private GRTEncoder rotationEncoder;
    private GRTEncoder visorEncoder;
    
    //Limit Switches for the visor and the turret rotation
    private GRTSwitch rotatorLeftLimit;
    private GRTSwitch rotatorRightLimit;
    private GRTSwitch visorUpperLimit;
    private GRTSwitch visorLowerLimit;
    
    
    //Motor speeds to be set when the motors are actively turning.
    private static final double ROTATOR_SPEED = 0.2;
    private static final double VISOR_SPEED = 0.1;
    
    //Thresholds for movement and joystick control
    public static final double THRESHOLD = 2.0;     //We want precision within this many degrees
    public static final double MOVE_THRESHOLD = .05; //Minimum distance to move the joystick without activating the rotating motor
    public static final double MAX_ABSOLUTE_ROTATION_ANGLE = 90;    //The max absolute angle (with encoders set to 0.0 on startup) that the rotator can swivel
    public static final double MAX_ABSOULTE_VISOR_ANGLE = 90;       //The max absolute angle (with encoders set to 0.0 on startup) that the visor can turn.
    public static final double MAX_RELATIVE_ROTATION_ANGLE = 90;       //The max absolute angle (with encoders set to 0.0 on startup) that the visor can turn.
    public static final double MAX_RELATIVE_VISOR_ANGLE = 90;       //The max absolute angle (with encoders set to 0.0 on startup) that the visor can turn.
   
    /**
     * A turret needs two motors (one for the rotation, and one for visor control)
     * as well as 2 limit switches to prevent over-rotation.
     * It uses encoders to test for position control.
     */
    public Turret(IMotor rotatorMotor, IMotor visorMotor, 
            GRTEncoder rotationEncoder, GRTEncoder visorEncoder, 
            GRTSwitch rotatorLeftSwitch, GRTSwitch rotatorRightSwitch,
            GRTSwitch visorTopSwitch, GRTSwitch visorBottomSwitch) {
        super("Turret");
        
        this.rotationEncoder = rotationEncoder;
        this.visorEncoder = visorEncoder;
        
        
        this.rotator = rotatorMotor;
        this.visor = visorMotor;
        
        this.rotatorLeftLimit = rotatorLeftSwitch;
        this.rotatorRightLimit = rotatorRightSwitch;
        this.visorLowerLimit = visorBottomSwitch;
        this.visorUpperLimit = visorTopSwitch;
    }
    
    /*
     * Start listening to the encoders
     */
    public void startListening(){
        visorEncoder.addEncoderListener(this);
        rotationEncoder.addEncoderListener(this);
    }
    
    /**
     * Stop listening to encoders
     */
    public void stopListening(){
        visorEncoder.removeEncoderListener(this);
        rotationEncoder.removeEncoderListener(this);
    }
   
    /**
     * Set the desired rotation angle to "angle"
     * 
     * The range for the rotation angle is [0,180]
     * 
     * @param angle The desired angle
     */
    public void rotateToAngle(double angle){        
        rotator.setSpeed((angle/9.0) - 1.0);
        /*
        if (angle < MAX_RELATIVE_ROTATION_ANGLE && angle > rotatorZeroAngle){
            this.desiredRotationAngle = angle;
            
            log("New rotation angle: " + angle);
            
        }
        * 
        */
    }
    
    /**
     * The desired angle for the visor
     * The range for a visor angle is [0,180]
     * @param angle 
     */
    public void shootAtAngle(double angle){
        visor.setSpeed((angle/90.0) - 1.0);
        /*
        if (angle < MAX_ABSOULTE_VISOR_ANGLE && angle > visorZeroAngle){
            this.desiredVisorAngle = angle;
            
            log("New visor angle: " + desiredVisorAngle);
            
            if(getVisorRelativeAngle() < angle){
                visor.setSpeed(VISOR_SPEED);
            } else if (getVisorRelativeAngle() > angle){
                visor.setSpeed(-VISOR_SPEED);
            } 
            
        }
        *
        */
    }
    
    /**
     * 
     * @return 
     */
    public double getRotatedRelativeAngle(){
        return rotationEncoder.getState(GRTEncoder.KEY_DEGREES) - rotatorZeroAngle;
    }
    
    /**
     * 
     */
    public double getVisorRelativeAngle(){
        return visorEncoder.getState(GRTEncoder.KEY_DEGREES) - visorZeroAngle;
    }
    

    /*
     * See if we've tripped any switches yet
     */
    public void switchStateChanged(SwitchEvent e) {
        //If the switch is now pressed
        if(e.getState() == GRTSwitch.PRESSED){
            //If it was one of the rotator switches, cease rotation
            if(e.getSource() == rotatorLeftLimit || e.getSource() == rotatorRightLimit){
                rotator.setSpeed(0.0);
            } 
            //Otherwise, if we've hit one of the visor switches, stop the visor
            else if (e.getSource() == visorLowerLimit || e.getSource() == visorUpperLimit){
                visor.setSpeed(0.0);
            }
        }
    }

    /*
     * Unused, required for EncoderListener
     */
    public void directionChanged(EncoderEvent e) {
    }

    public void degreeChanged(EncoderEvent e) {/*
        //If the rotation angle has changed:
        if (e.getSource() == rotationEncoder){
            //If we are within "THRESHOLD" degrees of target, stop
            if (Math.abs((desiredRotationAngle - rotatorZeroAngle) - e.getValue()) < THRESHOLD){
                rotator.setSpeed(0.0);
            }
            else {
                //If we are past our desired angle, go in the opposite direction
                if (e.getValue() - (desiredRotationAngle - rotatorZeroAngle) > 0){
                    rotator.setSpeed(-ROTATOR_SPEED);
                } 
                //Otherwise, keep going until we reach our angle
                else {
                    rotator.setSpeed(ROTATOR_SPEED);
                }
            }
        } 
        //If we are dealing with the visor:
        else if (e.getSource() == visorEncoder){
            //If within the threshold, stop
            if (Math.abs(desiredVisorAngle - e.getValue()) < THRESHOLD){
                visor.setSpeed(0.0);
            }
            
            else {
                //If we are past our desired angle, go in the opposite direction
                if (e.getValue() - desiredVisorAngle > 0){
                    visor.setSpeed(-VISOR_SPEED);
                } 
                //Otherwise, keep going until we reach our angle
                else {
                    visor.setSpeed(VISOR_SPEED);
                }
            }
        }
        * 
        */
    }

    public void distanceChanged(EncoderEvent e) {
    }
    
    
}
