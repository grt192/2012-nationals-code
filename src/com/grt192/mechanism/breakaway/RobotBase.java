/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism.breakaway;

import com.grt192.actuator.GRTVictor;
import com.grt192.core.Command;

/**
 * Robot base drives a standard 4 motor drivetrain.
 * @author ajc
 */
public class RobotBase {
    
    private final GRTVictor leftFront;
    private final GRTVictor leftBack;
    private final GRTVictor rightFront;
    private final GRTVictor rightBack;

    /**
     * 
     * @param leftFront left front motor
     * @param leftBack left back motor
     * @param rightFront right front motor
     * @param rightBack right back motor
     */
    public RobotBase(GRTVictor leftFront, GRTVictor leftBack, GRTVictor rightFront, GRTVictor rightBack){
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        leftFront.start();
        leftBack.start();
        rightFront.start();
        rightBack.start();
    }

    /**
     * TankDrive uses differential steering.
     * @param leftVelocity
     * @param rightVelocity
     */
    public void tankDrive(double leftVelocity, double rightVelocity){
        leftFront.enqueueCommand(leftVelocity);
        leftBack.enqueueCommand(leftVelocity);
        rightFront.enqueueCommand(rightVelocity);
        rightBack.enqueueCommand(rightVelocity);
    }



}
