/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.mechanism.breakaway;

import com.grt192.actuator.GRTJaguar;
import com.grt192.actuator.GRTVictor;
import com.grt192.core.Command;
import com.grt192.core.Mechanism;
import com.grt192.event.component.GyroEvent;
import com.grt192.event.component.GyroListener;
import com.grt192.sensor.GRTGyro;
import com.grt192.sensor.GRTPotentiometer;

/**
 * 
 * @author Bonan, James
 */
public class GRTBreakawayRobotBase extends Mechanism implements GyroListener {

    public static final int TANK_DRIVE = 0;

    // the entire robot base, including input and output...
    public GRTBreakawayRobotBase(GRTVictor leftJaguar1, GRTVictor leftJaguar2,
            GRTVictor rightJaguar1, GRTVictor rightJaguar2,
            GRTPotentiometer battery, GRTGyro gyro) {

        leftJaguar1.start();
        leftJaguar2.start();
        rightJaguar1.start();
        rightJaguar2.start();

        battery.start();
        gyro.addGyroListener(this);

        gyro.start();

        addActuator("LeftJaguar1", leftJaguar1);
        addActuator("LeftJaguar2", leftJaguar2);
        addActuator("RightJaguar1", rightJaguar1);
        addActuator("RightJaguar2", rightJaguar2);
        addSensor("Battery", battery);
        addSensor("Gyro", gyro);
    }

    public double getBatteryVoltage() {
        return getSensor("Battery").getState("Value");
    }

    public void tankDrive(double leftValue, double rightValue) {
        tankDrive(leftValue, rightValue, 0);
    }

    public void tankDrive(double leftValue, double rightValue, int time) {
        System.out.println("drive:" + leftValue + "\t" + rightValue);
        Command c1 = new Command(-leftValue, time);
        Command c2 = new Command(-rightValue, time);
        getActuator("LeftJaguar1").enqueueCommand(c1);
        getActuator("LeftJaguar2").enqueueCommand(c1);
        getActuator("RightJaguar1").enqueueCommand(c2);
        getActuator("RightJaguar2").enqueueCommand(c2);
    }

    public void stop() {
        tankDrive(0, 0);
    }

    public void didReceiveAngle(GyroEvent e) {
    }

    public void didAngleChange(GyroEvent e) {
    }

    public void didAngleSpike(GyroEvent e) {
//        MainRobot.getInstance().putGlobal("Angle", new Double(e.getAngle()));
    }
}
