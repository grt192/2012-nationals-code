/*
 * Generic dual speed controller drivetrain
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author anand
 */
public class GRTDriveTrain extends Actuator{

    private RobotDrive driveTrain;
    private Victor left;
    private Victor right;
    
    private int driveMode;
    public static final int TANK_DRIVE = 0;
    public static final int ARCADE_DRIVE = 1;
    public static final int ARCADE_SPIN_DRIVE = 2;
    public static final int SPEED_CURVE_DRIVE = 3;
    
    public GRTDriveTrain(int leftChannel, int rightChannel) {
        left = new Victor(leftChannel);
        right = new Victor(rightChannel);
        driveTrain = new RobotDrive(left, right);
        driveMode = 0;
    }

    public void setDriveMode(int driveMode) {
        if(driveMode == TANK_DRIVE || driveMode == ARCADE_DRIVE 
                || driveMode == SPEED_CURVE_DRIVE) {
            this.driveMode = driveMode;
        }
    }

    public int getDriveMode() {
        return driveMode;
    }
    
    public void executeCommand(Command c) {

        Command c1 = this.dequeue();
        switch (driveMode) {
            case 0: {
                if(c1 == null)
                    c1 = c;
                driveTrain.tankDrive(c.getValue(), c1.getValue());
                //left.set(c.getValue());
                //right.set(c1.getValue());
                break;
            }
            case 1: {
                driveTrain.arcadeDrive(c.getValue(), c1.getValue(), false);
                break;
            }
            case 2: {
                driveTrain.arcadeDrive(c.getValue(), c1.getValue(), true);
                break;
            }
            case 3: {
                driveTrain.drive(c.getValue(), c1.getValue());
                break;
            }
        }
    }

    public void halt() {
        left.set(0);
        right.set(0);
        //driveTrain.tankDrive(0, 0);
    }

    public String toString() {
        return "Drive Train: "+driveMode;
    }
}
