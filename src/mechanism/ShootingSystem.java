/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import event.EncoderEvent;
import event.EncoderListener;
import sensor.GRTEncoder;

/**
 *
 * @author gerberduffy, dan, nadia
 */
public class ShootingSystem implements EncoderListener{

    //All of our motors
    private final IMotor rotationMotor;
    private final IMotor visorMotor;
    private final IMotor flywheelMotor1;
    private final IMotor flywheelMotor2;
    private final IMotor flailMotor1;
    private final IMotor topTransMotor1;
    private final IMotor topTransMotor2;
    private final IMotor botTransMotor1;
    private final IMotor botTransMotor2;
    
    //Encoders
    private final GRTEncoder rotationEncoder;
    private final GRTEncoder visorEncoder;
    
    //Other Fields
    private boolean enable;
    private boolean visorEnable;
    private boolean rotationEnable;
    private double rotationAngle;
    private double visorAngle;
    
    //Constants
    private double rotationRatio = 1.0/7.1; //(radius of gear/radius of lazy susan)

    public ShootingSystem(IMotor rotationMotor, IMotor visorMotor,
            IMotor flywheelMotor1, IMotor flywheelMotor2,
            IMotor flailMotor,
            IMotor topTransMotor1, IMotor topTransMotor2,
            IMotor botTransMotor1, IMotor botTransMotor2, GRTEncoder rotationEncoder, GRTEncoder visorEncoder) {
        this.rotationMotor = rotationMotor;
        this.visorMotor = visorMotor;
        this.flywheelMotor1 = flywheelMotor1;
        this.flywheelMotor2 = flywheelMotor2;
        this.flailMotor1 = flailMotor;
        this.topTransMotor1 = topTransMotor1;
        this.topTransMotor2 = topTransMotor2;
        this.botTransMotor1 = botTransMotor1;
        this.botTransMotor2 = botTransMotor2;
        this.rotationEncoder = rotationEncoder;
        this.visorEncoder = visorEncoder;

    }

    //changes rotation of turret
    public void setPanSpeed(double speed) {
        rotationMotor.setSpeed(speed);
//        if (enable && rotationEnable) {
//            if (speed<=0.0) { //moves right
//                while(rotationAngle>-90.0) rotationMotor.setSpeed(speed); //limits movement right
//            }
//            else { //moves left
//                while(rotationAngle<90.0) rotationMotor.setSpeed(speed); //limits movement left
//            }
//        }
    }
    
    //changes angle of turret visor
    public void setVisorSpeed(double speed) {
        visorMotor.setSpeed(speed);
//        if (enable && visorEnable) {
//            if (speed<=0.0) {  // moves down\\
//                while(visorAngle<0.0) visorMotor.setSpeed(speed); //limits the movement of visor down
//            }
//            else {  //moves up
//                while(visorAngle>90.0) visorMotor.setSpeed(speed); //limits the movement of visor up
//            }
//        }
    }


    //changes speed of the turret flywheel
    public void setFlywheelSpeed(double speed) {
//        if (enable) {
            flywheelMotor1.setSpeed(speed);
            flywheelMotor2.setSpeed(speed);
//        }
    }

    //changes speed of the transition into turret
    public void setTopTransitionSpeed(double speed) {
//        if (enable) {
            topTransMotor1.setSpeed(speed); 
            topTransMotor2.setSpeed(speed);
//        }
    }

    //changes collector speed
    public void setFlailSpeed(double speed) {
        if (enable) flailMotor1.setSpeed(speed);
    }

    //changes speed of the transition after collector
    public void setBotTransitionSpeed(double speed) {
//        if (enable) {
            botTransMotor1.setSpeed(speed);
            botTransMotor2.setSpeed(speed);
//        }
    }
    
    public void enable() {
        enable = true;
    }
    
    public void disable() {
        enable = false;
    }
    
    public void enableRotation() {
        rotationEnable = true;
    }

    public void disableRotation() {
        rotationEnable = false;
    }
    
    public void enableVisor() {
        visorEnable = true;
    }
    
    public void disableVisor() {
        visorEnable = false;
    }
    
    public void directionChanged(EncoderEvent e) {
    }

    public void degreeChanged(EncoderEvent e) {
        if (e.getSource() == rotationEncoder) rotationAngle = (rotationAngle + (rotationRatio * e.getAngle()));
        else if (e.getSource() == visorEncoder) visorAngle = (visorAngle + e.getAngle());
    }

    public void distanceChanged(EncoderEvent e) {
    }

    public void rateChanged(EncoderEvent e) {
    }
}
