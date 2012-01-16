/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

import balancer.RobotTiltGyro;
import core.EventController;
import event.Attack3JoystickListener;
import event.RobotTiltListener;
import event.RobotTiltEvent;
import mechanism.GRTRobotBase;
import sensor.GRTAttack3Joystick;
import event.ButtonEvent;
import event.ButtonListener;

/**
 *
 * @author calvin
 */
public class BalanceController extends EventController implements RobotTiltListener, ButtonListener {

    private double previousAngle;
    private double currentAngle;
    private double deltaAngle;
    private double PConstant = .024;
    private double DConstant = 1;
    private final RobotTiltGyro robotTilt;
    private final GRTRobotBase base;
    private double DRIVE_THRESHOLD = .01;
    private GRTAttack3Joystick joystick;

    public BalanceController(GRTRobotBase base, RobotTiltGyro robotTilt, GRTAttack3Joystick joystick, String name) {
        super(name);
        this.base = base;
        this.robotTilt = robotTilt;
        this.joystick = joystick;
    }

    public void startBalancing() {
        startListening();
    }

    public void stopBalancing() {
        stopListening();
    }

    public void startListening() {
        robotTilt.addRobotTiltListeners(this);
        joystick.addButtonListener(this);
        
    }

    public void stopListening() {
        robotTilt.removeRobotTiltListeners(this);
        joystick.removeButtonListener(this);
    }

    public void RobotTiltChange(RobotTiltEvent e) {
        currentAngle = e.getTilt();
        deltaAngle = currentAngle - previousAngle;
        double drivePower = PConstant * currentAngle - DConstant * deltaAngle;
        if (Math.abs(drivePower) >= DRIVE_THRESHOLD) {
            base.tankDrive(drivePower, drivePower);
        } else {
            base.tankDrive(0, 0);
        }

        log(drivePower);
    }

    public void buttonPressed(ButtonEvent e) {
    }

    public void buttonReleased(ButtonEvent e) {
    }
}
