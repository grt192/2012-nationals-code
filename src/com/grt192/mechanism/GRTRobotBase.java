/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.mechanism;

import com.grt192.actuator.GRTDriveTrain;
import com.grt192.core.Command;
import com.grt192.core.Mechanism;
import com.grt192.sensor.GRTAccelerometer;
import com.grt192.sensor.GRTGyro;

/**
 * A Robot base consisting of a GRTDriveTrain, Gyroscope and accelerometer
 * that can be driven around
 * @author anand
 */
public class GRTRobotBase extends Mechanism {
    private double leftSpeed;
    private double rightSpeed;
    
    public GRTRobotBase(GRTDriveTrain dt, GRTGyro gy,
            GRTAccelerometer ax) {
        gy.start();
        ax.start();
        dt.start();
        addActuator("DriveTrain", dt);
        addSensor("Gyro", gy);
        addSensor("Accelerometer", ax);
    }

    public GRTRobotBase(int DTports[], int accelerometerPort, int gyroPort) {
        addActuator("DriveTrain", new GRTDriveTrain(DTports[0],DTports[1]));
        getActuator("DriveTrain").start();

        addSensor("Accelerometer",
                new GRTAccelerometer(accelerometerPort, 50, "baseAccel"));
        getSensor("Accelerometer").start();

        addSensor("Gyro", new GRTGyro(gyroPort, 50, "baseGyro"));
        getSensor("Gyro").start();
    }

    /**
     * Get the robotbase's current acceleration
     * @return acceleration
     */
    public double getAcceleration(){
        return getSensor("Accelerometer").getState("Acceleration");
    }

    /**
     * Get the robotbase's current orientation relative to its starting orientation
     * @return angle
     */
    public double getAngle(){
        return getSensor("Gyro").getState("Angle");
    }
    /**
     * Drive the Robotbase directly by setting the speeds of the left and right wheels
     * @param leftValue
     * @param rightValue
     */
    public void tankDrive(double leftValue, double rightValue) {
        ((GRTDriveTrain) getActuator("DriveTrain")).setDriveMode(
                GRTDriveTrain.TANK_DRIVE);
        Command c1 = new Command(leftValue, 0);
        Command c2 = new Command(rightValue, 0);
        getActuator("DriveTrain").enqueueCommands(new Command[]{c1, c2});
    }

    /**
     * Drive the RobotBase by specifying target speeds in the x and y directions
     * Vary left and right DT speeds proportionally
     * @param x
     * @param y
     */
    public void arcadeDrive(double x, double y) {
        arcadeDrive(x, y, false);
    }

    /**
     * Drive the RobotBase by specifying target speeds in the x and y directions
     * For turns reverse one drivetrain to achieve tight turning--"spin"
     * @param x
     * @param y
     * @param spin
     */
    public void arcadeDrive(double x, double y, boolean spin) {
        if (spin) {
            ((GRTDriveTrain) getActuator("DriveTrain")).setDriveMode(
                    GRTDriveTrain.ARCADE_SPIN_DRIVE);
        } else {
            ((GRTDriveTrain) getActuator("DriveTrain")).setDriveMode(
                    GRTDriveTrain.ARCADE_DRIVE);
        }
        Command c1 = new Command(x, 0);
        Command c2 = new Command(y, 0);
        getActuator("DriveTrain").enqueueCommands(new Command[]{c1, c2});
    }

    public double getLeftSpeed() {
        return leftSpeed;
    }

    public double getRightSpeed() {
        return rightSpeed;
    }

    public void turnLeft(double degrees){
        //TODO turnleft
    }

    public void turnRight(double degrees){
        //TODO turnright
    }

    public void driveDistance(double distance){
        //TODO drive distance
    }

    public String toString() {
        return "Robot base "+getAcceleration()+" "+getAngle();
    }
}
