/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import core.GRTLoggedProcess;
import event.Attack3JoystickEvent;
import event.Attack3JoystickListener;
import event.ButtonEvent;
import event.ButtonListener;
import sensor.GRTAttack3Joystick;
import sensor.GRTEncoder;
import sensor.GRTSwitch;

/**
 *
 * Turret is responsible for the swiveling of the base of the shooter to different angles
 * 
 * @author gerberduffy
 */
public class Turret extends GRTLoggedProcess implements Attack3JoystickListener, ButtonListener{
    
    private IMotor rotator;
    private final IMotor rotator2;
    private IMotor visor;
    private final IMotor visor2;
        
    private double rotatorZeroAngle  = -90.0;                 //The relative zero angle. Defaults to whatever the encoder's current zero is
    private double visorZeroAngle = -90.0;   
    
    
    private double desiredRotationAngle = 0.0;                    //Angle we want to be at
    private double desiredVisorAngle = 0.0;                       //Desired angle to set visor to
    

    //Encoders for the rotation and the visor
    private GRTEncoder rotationEncoder;
    private GRTEncoder visorEncoder;
    
    private GRTAttack3Joystick stick;    
    
    private double previousJoystickXValue;           //Previous joystick values
    private double previousJoystickYValue;

    
    private double P_CONSTANT = -.09;
    private double D_CONSTANT = .01;
    
    
    //Thresholds for movement and joystick control
    public static final double THRESHOLD = 2.0;     //We want precision within this many degrees
    public static final double MOVE_THRESHOLD = .05; //Minimum distance to move the joystick without activating the rotating motor
    public static final double MAX_ROTATION_ANGLE = 90;
    public static final double MAX_VISOR_ANGLE = 90;

    
    public Turret(IMotor rotatorMotor1, IMotor rotatprIMotor2, IMotor visorMotor1, IMotor visorMotor2, GRTEncoder rotationEncoder, GRTEncoder visorEncoder, GRTAttack3Joystick stick){
        super("Turret");
        this.rotationEncoder = rotationEncoder;
        this.visorEncoder = visorEncoder;
        
        this.rotator = rotatorMotor1;
        this.rotator2 = rotatprIMotor2;
        
        this.visor = visorMotor1;
        this.visor2 = visorMotor2;
        
        this.stick = stick;
        
        System.out.println("rotation angle:" + rotationEncoder.getState(GRTEncoder.KEY_DEGREES));
        System.out.println("visor angle:" + visorEncoder.getState(GRTEncoder.KEY_DEGREES));
    }
    
    public void startListening(){
        stick.addJoystickListener(this);
        stick.addButtonListener(this);
        
        log("\n\n");
        log("Turret now listening to joystick");
    }
    
    public void stopListening(){
        stick.removeJoystickListener(this);
        stick.removeButtonListener(this);
    }
    
    /**
     * Set the desired rotation angle to "angle"
     * 
     * The range for the rotation angle is [0,180]
     * 
     * @param angle The desired angle
     */
    public void rotateToAngle(double angle){
        log("Setting rotation angle");
        
        if (angle < MAX_ROTATION_ANGLE){
            this.desiredRotationAngle = angle;
            System.out.println("ROTATION_DESIRED:" + angle);
        }
    }
    
    /**
     * The desired angle for the visor
     * The range for a visor angle is [0,180]
     * @param angle 
     */
    public void setVisorAngle(double angle){
        log("Setting visor angle");
        if (angle < MAX_VISOR_ANGLE){
            this.desiredVisorAngle = angle;
            System.out.println("VISOR_DESIRED:" + angle);
        }
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
    
    /**
     * Turret run method handles swiveling the turret to different directions.
     */
    public void run(){
        
        while(running){
            
            if(enabled){
                if(getRotatedRelativeAngle() > MAX_ROTATION_ANGLE || getRotatedRelativeAngle() < rotatorZeroAngle || getVisorRelativeAngle() >= MAX_VISOR_ANGLE || getVisorRelativeAngle() <= visorZeroAngle){
                    if (getRotatedRelativeAngle() >= MAX_ROTATION_ANGLE || getRotatedRelativeAngle() <= rotatorZeroAngle){
                        rotator.setSpeed(0.0);
                        rotator2.setSpeed(0.0);
                        System.out.println("Rotator out of bounds");
                    }

                    if (getVisorRelativeAngle() >= MAX_VISOR_ANGLE || getRotatedRelativeAngle() <= visorZeroAngle){
                        visor.setSpeed(0.0);
                        visor2.setSpeed(0.0);

                        System.out.println("Visor out of bounds");
                    }
                    
                    continue;
                }
                double rotatedDiff = getRotatedRelativeAngle() - desiredRotationAngle;
                double visorDiff = getVisorRelativeAngle() - desiredVisorAngle;

                System.out.println("Diffs:\tRotated=" + rotatedDiff +"\tVisor="+ visorDiff);
                
                if (rotatedDiff > THRESHOLD){
                    rotator.setSpeed(P_CONSTANT*getRotatedRelativeAngle() - D_CONSTANT*rotatedDiff);
                    rotator2.setSpeed(P_CONSTANT*getRotatedRelativeAngle() - D_CONSTANT*rotatedDiff);
                }
                
                
                if (visorDiff > THRESHOLD){
                    visor.setSpeed(P_CONSTANT*getVisorRelativeAngle() - D_CONSTANT*visorDiff);
                    visor2.setSpeed(P_CONSTANT*getVisorRelativeAngle() - D_CONSTANT*visorDiff);
                } 
            }
        }
        
    }
    
    /*
     * When the x axis of the controller is moved, swivel to the corresponding degree
     */
    public void xAxisMoved(Attack3JoystickEvent e) {
        log("x axis move:" + e.getValue());
        
        //rotator.setSpeed(e.getValue());
        
        
        if(Math.abs(e.getValue() - previousJoystickXValue) >= MOVE_THRESHOLD){
            rotator.setSpeed(90.0*(e.getValue()+1));
            
            previousJoystickXValue = e.getValue();
        }
        
    }
    
    
    public void yAxisMoved(Attack3JoystickEvent e) {
        log("Y axis move:" + e.getValue());
        
        //visor.setSpeed(e.getValue());
        
        if(Math.abs(e.getValue() - previousJoystickYValue) >= MOVE_THRESHOLD){
            visor.setSpeed(90.0*(e.getValue()+1));
            
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
        System.out.println("Encoder angles:\tRotator->" + rotationEncoder.getState(GRTEncoder.KEY_DEGREES) + "\tVisor->" + visorEncoder.getState(GRTEncoder.KEY_DEGREES));
    }

    public void buttonReleased(ButtonEvent e) {
    }
    
}
