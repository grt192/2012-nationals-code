package com.grt192.actuator.exception;

import com.grt192.core.Actuator;

public abstract class ActuatorException extends Exception{

	private Actuator source;
	public ActuatorException(Actuator source) {
		this.source = source;
	}
	public Actuator getSource() {
		return source;
	}

}
