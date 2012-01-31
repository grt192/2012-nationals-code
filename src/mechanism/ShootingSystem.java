/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;

/**
 *
 * @author gerberduffy, dan
 */
public class ShootingSystem {
    
    //All of our motors
    private final IMotor rotationMotor;
    private final IMotor visorMotor;
    private final IMotor flywheelMotor1;
    private final IMotor flywheelMotor2;
    private final IMotor flailMotor1;
    private final IMotor flailMotor2;
    private final IMotor transMotor1;
    private final IMotor transMotor2;
    
    
    
    public ShootingSystem(IMotor rotationMotor, IMotor visorMotor,
            IMotor flywheelMotor1, IMotor flywheelMotor2,
            IMotor flailMotor1, IMotor flailMotor2,
            IMotor transMotor1, IMotor transMotor2)
    {
     this.rotationMotor = rotationMotor;
     this.visorMotor = visorMotor;
     this.flywheelMotor1 = flywheelMotor1;
     this.flywheelMotor2 = flywheelMotor2;
     this.flailMotor1 = flailMotor1;
     this.flailMotor2 = flailMotor2;
     this.transMotor1 = transMotor1;
     this.transMotor2 = transMotor2;
        
    }
    
    public void setVisorSpeed(double speed){
        visorMotor.setSpeed(speed);
    }
        
    public void setPanSpeed(double speed){
        rotationMotor.setSpeed(speed);
    }
        
    public void setFlywheelSpeed(double speed){
        flywheelMotor1.setSpeed(speed);
        flywheelMotor2.setSpeed(speed);
    }
        
    public void setTransitionSpeed(double speed){
        transMotor1.setSpeed(speed);
        transMotor2.setSpeed(speed);
    }
    public void setFlailSpeed(double speed){
        flailMotor1.setSpeed(speed);
        flailMotor2.setSpeed(speed);
    }
        
    
}
