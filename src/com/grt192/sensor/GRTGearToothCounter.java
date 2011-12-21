package com.grt192.sensor;

import com.grt192.core.Sensor;

import edu.wpi.first.wpilibj.GearTooth;

/**
 * A hall-effect, geartooth counting sensor driver
 * 
 * @author Student
 */
public class GRTGearToothCounter extends Sensor {

	private GearTooth gearTooth;

	public GRTGearToothCounter(int channel, boolean directionSensitive,
			int pollTime, String id) {
		gearTooth = new GearTooth(channel, directionSensitive);
		setSleepTime(pollTime);
		setState("Count", 0.0);
		this.id = id;
	}

	public void poll() {
		setState("Count", gearTooth.get());
	}

	public String toString() {
		return "Gear tooth counter";
	}
}
