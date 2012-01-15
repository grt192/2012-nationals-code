/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import core.GRTLoggedProcess;

/**
 * Standard 4 motor drivetrain.
 * @author ajc
 */
public class GRTDriveTrain extends GRTLoggedProcess {

    private final IMotor leftFront;
    private final IMotor leftBack;
    private final IMotor rightFront;
    private final IMotor rightBack;

    /**
     * 
     * @param leftFront left front motor
     * @param leftBack left back motor
     * @param rightFront right front motor
     * @param rightBack right back motor
     */
    public GRTDriveTrain(IMotor leftFront, IMotor leftBack,
            IMotor rightFront, IMotor rightBack, String name) {
        super(name);
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
    }


    /**
     * TankDrive uses differential steering.
     * @param leftVelocity
     * @param rightVelocity
     */
    public void tankDrive(double leftVelocity, double rightVelocity) {
        leftFront.setSpeed(-leftVelocity);
        leftBack.setSpeed(-leftVelocity);
        log(100, leftVelocity);
        rightFront.setSpeed(-rightVelocity);
        rightBack.setSpeed(-rightVelocity);
        log(101, rightVelocity);
    }
}
