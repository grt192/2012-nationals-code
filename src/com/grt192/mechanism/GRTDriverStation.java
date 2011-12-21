/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.mechanism;

import com.grt192.core.Mechanism;
import com.grt192.core.Sensor;
import com.grt192.sensor.GRTJoystick;

import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * A standard Driverstation with two joysticks connected Contains basic button
 * states and throttle access
 * 
 * @author Student
 */
public class GRTDriverStation extends Mechanism {

	private DriverStationLCD lcd;
	private int line;
	private String[] lines;

	public GRTDriverStation(int left, int right, int secondary) {
		this(new GRTJoystick(left, 50, "leftJoystick"), new GRTJoystick(right, 50,
				"rightJoystick"), new GRTJoystick(secondary, 50, "secondaryJoystick"));
	}

	public GRTDriverStation(GRTJoystick left, GRTJoystick right,
			GRTJoystick secondary) {
		left.start();
		right.start();
		secondary.start();
		addSensor("leftJoystick", left);
		addSensor("rightJoystick", right);
		addSensor("secondaryJoystick", secondary);
		lcd = DriverStationLCD.getInstance();
		line = 0;
		lines = new String[6];
		for(int i = 0; i<lines.length; i++){
			lines[i] = "";
		}
	}

	public double getLeftJoyStickAngle() {
		return getSensor("leftJoystick").getState("JoystickAngle");
	}

	public double getRightJoyStickAngle() {
		return getSensor("rightJoystick").getState("JoystickAngle");
	}

	public boolean getLeftButton(int button) {
		return getSensor("leftJoystick").getState("Button" + button) == Sensor.TRUE;
	}

	public boolean getRightButton(int button) {
		return getSensor("rightJoystick").getState("Button" + button) == Sensor.TRUE;
	}

	public boolean getSecondaryButton(int button) {
		return getSensor("secondaryJoystick").getState("Button" + button) == Sensor.TRUE;
	}

	public double getXLeftJoystick() {
		return getSensor("leftJoystick").getState("xValue");
	}

	public double getYLeftJoystick() {
		return getSensor("leftJoystick").getState("yValue");
	}

	public double getZLeftJoystick() {
		return getSensor("leftJoystick").getState("zValue");
	}

	public double getXRightJoystick() {
		return getSensor("rightJoystick").getState("xValue");
	}

	public double getYRightJoystick() {
		return getSensor("rightJoystick").getState("yValue");
	}

	public double getZRightJoystick() {
		return getSensor("rightJoystick").getState("zValue");
	}

	public double getLeftThrottle() {
		return getSensor("rightJoystick").getState("Throttle");
	}

	public double getRightThrottle() {
		return getSensor("rightJoystick").getState("Throttle");
	}

	public double getSecondaryThrottle() {
		return getSensor("secondaryJoystick").getState("Throttle");
	}

	public double getXSecondaryJoystick() {
		return getSensor("secondaryJoystick").getState("xValue");
	}

	public double getYSecondaryJoystick() {
		return getSensor("secondaryJoystick").getState("yValue");
	}

	public double getZSecondaryJoystick() {
		return getSensor("secondaryJoystick").getState("zValue");
	}

	public void printToLCD(String text) {
		if(line != lines.length){
			lines[line] = text;
		    line++;
		}else{
			for(int i = 0; i<lines.length-1; i++){
				lines[i] = lines[i+1];
			}
			lines[lines.length-1] = text;
		}
		refreshLCD();
	}
	
	public void refreshLCD(){
		lcd.println(DriverStationLCD.Line.kMain6, 1, lines[0]);
		lcd.println(DriverStationLCD.Line.kUser2, 1, lines[1]);
		lcd.println(DriverStationLCD.Line.kUser3, 1, lines[2]);
		lcd.println(DriverStationLCD.Line.kUser4, 1, lines[3]);
		lcd.println(DriverStationLCD.Line.kUser5, 1, lines[4]);
		lcd.println(DriverStationLCD.Line.kUser6, 1, lines[5]);
		lcd.updateLCD();
	}

	public int getLCDLine() {
		return line;
	}

	public String[] getLCDLines() {
		return lines;
	}
	
	
}
