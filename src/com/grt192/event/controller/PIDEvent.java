package com.grt192.event.controller;

import com.grt192.controller.pid.AsynchronousPIDController;

public class PIDEvent {
	public static final int SETPOINT_REACHED = 0;

	private AsynchronousPIDController source;
	private int id;

	public PIDEvent(AsynchronousPIDController source, int id) {
		this.source = source;
		this.id = id;
	}

	public AsynchronousPIDController getSource() {
		return source;
	}

	public int getId() {
		return id;
	}

	
}
