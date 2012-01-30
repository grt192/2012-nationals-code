/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import actuator.IMotor;
import core.EventController;
import event.Attack3JoystickEvent;
import event.Attack3JoystickListener;
import event.BGSystemsFXJoystickEvent;
import event.BGSystemsFXJoystickListener;
import event.ButtonEvent;
import event.ButtonListener;
import sensor.GRTBGSystemsFXJoystick;

/**
 *
 * @author ajc
 */
public class MotorsTesterController extends EventController implements BGSystemsFXJoystickListener, ButtonListener {

    private final IMotor[] motors;
    private final GRTBGSystemsFXJoystick joystick;
    int motor = 0;

    public MotorsTesterController(IMotor[] motors, GRTBGSystemsFXJoystick joystick, String name) {
        super(name);
        this.motors = motors;
        this.joystick = joystick;
    }

    protected void startListening() {
        System.out.println("start listening");
        joystick.addJoystickListener(this);
        joystick.addButtonListener(this);
    }

    protected void stopListening() {
        joystick.removeJoystickListener(this);
        joystick.removeButtonListener(this);

    }

    public void XAxisMoved(BGSystemsFXJoystickEvent e) {
    }

    public void YAxisMoved(BGSystemsFXJoystickEvent e) {
        motors[motor].setSpeed(e.getValue());
        System.out.println("motor id" + motor +" move at " + e.getValue());
    }

    public void angleChanged(BGSystemsFXJoystickEvent e) {
    }

    public void forceXMoved(BGSystemsFXJoystickEvent e) {
    }

    public void forceYMoved(BGSystemsFXJoystickEvent e) {
    }

    public void twistChanged(BGSystemsFXJoystickEvent e) {
    }

    public void buttonPressed(ButtonEvent e) {
    }

    public void buttonReleased(ButtonEvent e) {
        if (e.getButtonID() == 12) {
            motor = (motor + 1) % motors.length;
        }
        if (e.getButtonID() == 11) {
            motor = (motor - 1) % motors.length;
        }
        System.out.println("selected: motor" + motor + "in array");

    }

    private String getDescription(int id) {
        if (id < 8) {
            return "left module:" + id;
        } else {
            return "right module:" + (id - 8);
        }
    }
}
