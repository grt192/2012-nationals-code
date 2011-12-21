package com.grt192.event.component;

import com.grt192.sensor.GRTGyro;

/**
 * 
 * @author anand
 */
public class GyroEvent {
	public static final int DEFAULT = 0;
	public static final int NEW_DATA = 1;
	public static final int CHANGE = 2;
	public static final int SPIKE = 3;
	private GRTGyro source;
	private int id;
	private double angle;

	public GyroEvent(GRTGyro source, int id, double angle) {
		this.source = source;
		this.id = id;
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

	public int getId() {
		return id;
	}

	public GRTGyro getSource() {
		return source;
	}

}
