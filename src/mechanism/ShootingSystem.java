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
    private final IMotor topTransMotor1;
    private final IMotor topTransMotor2;
    private final IMotor botTransMotor1;
    private final IMotor botTransMotor2;

    public ShootingSystem(IMotor rotationMotor, IMotor visorMotor,
            IMotor flywheelMotor1, IMotor flywheelMotor2,
            IMotor flailMotor,
            IMotor topTransMotor1, IMotor topTransMotor2,
            IMotor botTransMotor1, IMotor botTransMotor2) {
        this.rotationMotor = rotationMotor;
        this.visorMotor = visorMotor;
        this.flywheelMotor1 = flywheelMotor1;
        this.flywheelMotor2 = flywheelMotor2;
        this.flailMotor1 = flailMotor;
        this.topTransMotor1 = topTransMotor1;
        this.topTransMotor2 = topTransMotor2;
        this.botTransMotor1 = botTransMotor1;
        this.botTransMotor2 = botTransMotor2;

    }

    public void setVisorSpeed(double speed) {
        visorMotor.setSpeed(speed);
    }

    public void setPanSpeed(double speed) {
        rotationMotor.setSpeed(speed);
    }

    public void setFlywheelSpeed(double speed) {
        flywheelMotor1.setSpeed(speed);
        flywheelMotor2.setSpeed(speed);
    }

    public void setTopTransitionSpeed(double speed) {
        topTransMotor1.setSpeed(speed);
        topTransMotor2.setSpeed(speed);
    }

    public void setFlailSpeed(double speed) {
        flailMotor1.setSpeed(speed);
    }

    public void setBotTransitionSpeed(double speed) {
        botTransMotor1.setSpeed(speed);
        botTransMotor2.setSpeed(speed);
    }
}
