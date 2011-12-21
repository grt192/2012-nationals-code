package com.grt192.actuator.exception;

import com.grt192.actuator.GRTCANJaguar;

public class GRTCANJaguarException extends ActuatorException{

	//Jaguar Faults
	public static final int CURRENT_FAULT = 1;
	public static final int VOLTAGE_FAULT = 2;
	public static final int TEMPERATURE_FAULT = 3;
        public static final int GATE_DRIVER_FAULT = 4;
        //CAN Faults
        public static final int CAN_TIMEOUT = 5;
	
	private int faultId;
	
	public GRTCANJaguarException(int faultId, GRTCANJaguar source) {
		super(source);
		this.faultId = faultId;
	}
	public int getFaultId() {
		return faultId;
	}
	
	
}
