package com.grt192.controller.teleop;

import com.grt192.core.EventController;
import com.grt192.event.component.AccelerometerEvent;
import com.grt192.event.component.AccelerometerListener;
import com.grt192.event.component.GyroEvent;
import com.grt192.event.component.GyroListener;
import com.grt192.event.component.JoystickEvent;
import com.grt192.event.component.JoystickListener;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.mechanism.GRTRobotBase;
import com.grt192.sensor.GRTAccelerometer;
import com.grt192.sensor.GRTGyro;
import com.grt192.sensor.GRTJoystick;

/**
 *
 * @author anand
 */
public class DriveEventController extends EventController
        implements JoystickListener, AccelerometerListener, GyroListener {

    public DriveEventController(GRTRobotBase rb, GRTDriverStation ds) {
        super();
        addMechanism("DriverStation", ds);
        ((GRTJoystick) ds.getSensor("leftJoystick")).addJoystickListener(this);
        ((GRTJoystick) ds.getSensor("rightJoystick")).addJoystickListener(this);
        ((GRTGyro) rb.getSensor("Gyro")).addGyroListener(this);
        ((GRTAccelerometer) rb.getSensor("Accelerometer")).addAccelerometerListener(this);
        addMechanism("RobotBase", rb);
        System.out.println("Started Event Controller");
    }

    public void yAxisMoved(JoystickEvent e) {
        GRTRobotBase base = ((GRTRobotBase) getMechanism("RobotBase"));
        if (e.getSource().getId().equals("left")) {
            base.tankDrive(e.getValue(), base.getRightSpeed());

        } else if (e.getSource().getId().equals("right")) {
            base.tankDrive(base.getLeftSpeed(), e.getValue());
        }
    }

    public void xAxisMoved(JoystickEvent e) {
    }

    public void zAxisMoved(JoystickEvent e) {
    }

    public void throttleMoved(JoystickEvent e) {
    }

    public void didReceiveAcceleration(AccelerometerEvent e) {

    }

    public void didAccelerationSpike(AccelerometerEvent e) {
    }

    public void didAccelerationChange(AccelerometerEvent e) {
        System.out.println("Acceleration: "+ e.getAcceleration());
    }

    public void didReceiveAngle(GyroEvent e) {

    }

    public void didAngleChange(GyroEvent e) {
        System.out.println("Angle: " + e.getAngle());
    }

    public void didAngleSpike(GyroEvent e) {
    }
}
