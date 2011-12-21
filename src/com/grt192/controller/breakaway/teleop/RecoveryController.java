/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.controller.breakaway.teleop;

import com.grt192.core.EventController;
import com.grt192.core.Sensor;
import com.grt192.event.SensorChangeListener;
import com.grt192.event.SensorEvent;
import com.grt192.event.component.ButtonListener;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.mechanism.breakaway.Recovery;
import com.grt192.sensor.GRTJoystick;
import com.grt192.sensor.GRTSwitch;

/**
 * 
 * @author klian
 */
public class RecoveryController extends EventController implements
		ButtonListener, SensorChangeListener {
	private boolean up = false;
	private boolean down = false;

	public RecoveryController(GRTDriverStation dStation, Recovery arm) {
		super();
		addMechanism("DriverStation", dStation);
		addMechanism("Arm", arm);
		((GRTJoystick) dStation.getSensor("secondaryJoystick"))
				.addButtonListener(this);
		((GRTJoystick) dStation.getSensor("leftJoystick"))
				.addButtonListener(this);

		((GRTSwitch) arm.getSensor("RecUpSwitch"))
				.addSensorChangeListener(this);
		((GRTSwitch) arm.getSensor("RecGroundSwitch"))
				.addSensorChangeListener(this);
	}

	public void buttonUp(SensorEvent e, String key) {
		if ((e.getSource().getId().equals("secondary") && (key
				.equals("Button4") || key.equals("Button1")))
				|| (e.getSource().getId().equals("left") && (key
						.equals("Button5") || key.equals("Button6")))) {
			((Recovery) getMechanism("Arm")).stopArm();
		}

	}

	public void buttonDown(SensorEvent e, String key) {
		System.out.println(key);
		if ((e.getSource().getId().equals("left") && key.equals("Button5"))
				|| (e.getSource().getId().equals("secondary") && key
						.equals("Button4"))) {
			if (!up) {
				((Recovery) getMechanism("Arm")).flip();
				System.out.println("flipping");
			}

		} else if ((key.equals("Button6") && e.getSource().getId().equals(
				"left"))
				|| (key.equals("Button1") && e.getSource().getId().equals(
						"secondary"))) {
			((Recovery) getMechanism("Arm")).retract();
			System.out.println("retracting");
		}
	}

	public void sensorStateChanged(SensorEvent e, String key) {
		System.out.println("switch hit");
		if (e.getSource().getId().equals("RecoveryUp")) {
			if (e.getData(key) == Sensor.FALSE) {
				up = true;
				((Recovery) getMechanism("Arm")).stopArm();
			} else {
				up = false;
			}
		}
		if (e.getSource().getId().equals("RecoveryDown")) {
			if (e.getData(key) == Sensor.FALSE) {
				down = true;
				((Recovery) getMechanism("Arm")).stopArm();
			} else {
				down = false;
			}
		}
	}
}
