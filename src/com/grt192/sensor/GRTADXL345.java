package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.ADXL345Event;
import com.grt192.event.component.ADXL345Listener;
import edu.wpi.first.wpilibj.ADXL345DigitalAccelerometer;
import java.util.Vector;

/**
 * 3 axis digital accelerometer from Analog Devices. Uses i2c bus
 * 
 * @author anand
 */
public class GRTADXL345 extends Sensor {

	private ADXL345DigitalAccelerometer accel;
	private int range;
	private Vector accelerometerListeners;
	
	private double spikeThreshold = 1.0;
	private double changeThreshold = .001;

	public GRTADXL345(int slot, int range, int polltime, String id) {
		this.range = range;
		accel = new ADXL345DigitalAccelerometer(slot);
		accel.intitialize();
		setRange(range);
		setState("X", 0.0);
		setState("Y", 0.0);
		setState("Z", 0.0);
		this.sleepTime = polltime;
		accelerometerListeners = new Vector();
		this.id = id;

	}

	public void poll() {

		double previousValue = getState("X");
		setState("X", accel.getXAxis());
		if (Math.abs(getState("X") - previousValue) >= spikeThreshold) {
			notifyAccelerometerSpike(ADXL345Event.X, getState("X"));
		}
		if (Math.abs(getState("X") - previousValue) >= changeThreshold) {
			notifyAccelerometerChange(ADXL345Event.X, getState("X"));
		}
		notifyADXL345Listeners(ADXL345Event.X, getState("X"));

		previousValue = getState("Y");
		setState("Y", accel.getYAxis());
		if (Math.abs(getState("Y") - previousValue) >= spikeThreshold) {
			notifyAccelerometerSpike(ADXL345Event.Y, getState("Y"));
		}
		if (Math.abs(getState("Y") - previousValue) >= changeThreshold) {
			notifyAccelerometerChange(ADXL345Event.Y, getState("Y"));
		}
		notifyADXL345Listeners(ADXL345Event.Y, getState("Y"));

		previousValue = getState("Z");
		setState("Z", accel.getZAxis());
		if (Math.abs(getState("Z") - previousValue) >= spikeThreshold) {
			notifyAccelerometerSpike(ADXL345Event.Z, getState("Z"));
		}
		if (Math.abs(getState("Z") - previousValue) >= changeThreshold) {
			notifyAccelerometerChange(ADXL345Event.Z, getState("Z"));
		}
		notifyADXL345Listeners(ADXL345Event.Z, getState("Z"));

	}

	public int getRange() {
		return range;
	}

    public double getSpikeThreshold() {
        return spikeThreshold;
    }
    
    public void setSpikeThreshold(double threshold) {
        spikeThreshold = threshold;
    }
    
    public double getChangeThreshold() {
        return changeThreshold;
    }
    
    public void setChangeThreshold(double threshold) {
        changeThreshold = threshold;
    }

	public void setRange(int range) {
		this.range = range;
		switch (range) {
		case 2:
			accel.setRange(ADXL345DigitalAccelerometer.DATA_FORMAT_02G);
			break;
		case 4:
			accel.setRange(ADXL345DigitalAccelerometer.DATA_FORMAT_04G);
			break;
		case 8:
			accel.setRange(ADXL345DigitalAccelerometer.DATA_FORMAT_08G);
			break;
		case 16:
			accel.setRange(ADXL345DigitalAccelerometer.DATA_FORMAT_16G);
			break;
		default:
		    range = 2;
			accel.setRange(ADXL345DigitalAccelerometer.DATA_FORMAT_02G);
		}
	}

	protected void notifyAccelerometerSpike(int axis, double value) {
		for (int i = 0; i < accelerometerListeners.size(); i++) {
			((ADXL345Listener) accelerometerListeners.elementAt(i))
					.didAccelerationSpike(new ADXL345Event(this, axis, value));
		}
	}

	protected void notifyAccelerometerChange(int axis, double value) {
		for (int i = 0; i < accelerometerListeners.size(); i++) {
			((ADXL345Listener) accelerometerListeners.elementAt(i))
					.didAccelerationChange(new ADXL345Event(this, axis, value));
		}
	}

	protected void notifyADXL345Listeners(int axis, double value) {
		for (int i = 0; i < accelerometerListeners.size(); i++) {
			((ADXL345Listener) accelerometerListeners.elementAt(i))
					.didReceiveAcceleration(new ADXL345Event(this, axis, value));
		}
	}

	public Vector getADXL345Listeners() {
		return accelerometerListeners;
	}

	public void addADXL345Listener(ADXL345Listener a) {
		accelerometerListeners.addElement(a);
	}

	public void removeADXL345Listener(ADXL345Listener a) {
		accelerometerListeners.removeElement(a);
	}
}
