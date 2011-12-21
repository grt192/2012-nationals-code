package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.AccelerometerEvent;
import com.grt192.event.component.AccelerometerListener;
import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.PIDSource;
import java.util.Vector;

/**
 * GRTAccelerometer is a continuously running sensor driver that collects and
 * serves data from a single-axis accelerometer
 * 
 * @author anand
 */
public class GRTAccelerometer extends Sensor implements PIDSource {
	public static final double SPIKE_THRESHOLD = 1.0;
	public static final double CHANGE_THRESHOLD = .001;
	private Accelerometer accelerometer;
	private Vector accelerometerListeners;

	public GRTAccelerometer(int channel, int pollTime, String id) {
		accelerometer = new Accelerometer(channel);
		setSleepTime(pollTime);
		setState("Acceleration", 0.0);
		accelerometerListeners = new Vector();
		this.id = id;
	}

	public void poll() {
		double previousValue = getState("Acceleration");
		setState("Acceleration", accelerometer.getAcceleration());

		if (Math.abs(getState("Acceleration") - previousValue) >= SPIKE_THRESHOLD) {
			notifyAccelerometerSpike();
		}
		if (Math.abs(getState("Acceleration") - previousValue) >= CHANGE_THRESHOLD) {
			notifyAccelerometerChange();
		}
		notifyAccelerometerListeners();
	}

	protected void notifyAccelerometerSpike() {
		for (int i = 0; i < accelerometerListeners.size(); i++) {
			((AccelerometerListener) accelerometerListeners.elementAt(i))
					.didAccelerationSpike(new AccelerometerEvent(this,
							AccelerometerEvent.SPIKE, getState("Acceleration")));
		}
	}

	protected void notifyAccelerometerChange() {
		for (int i = 0; i < accelerometerListeners.size(); i++) {
			((AccelerometerListener) accelerometerListeners.elementAt(i))
					.didAccelerationChange(new AccelerometerEvent(this,
							AccelerometerEvent.CHANGE, getState("Acceleration")));
		}
	}

	protected void notifyAccelerometerListeners() {
		for (int i = 0; i < accelerometerListeners.size(); i++) {
			((AccelerometerListener) accelerometerListeners.elementAt(i))
					.didReceiveAcceleration(new AccelerometerEvent(this,
							AccelerometerEvent.NEW_DATA,
							getState("Acceleration")));
		}
	}

	public Vector getAccelerometerListeners() {
		return accelerometerListeners;
	}

	public void addAccelerometerListener(AccelerometerListener a) {
		accelerometerListeners.addElement(a);
	}

	public void removeAccelerometerListener(AccelerometerListener a) {
		accelerometerListeners.removeElement(a);
	}

	public String toString() {
		return "accelerometer: " + getState("Acceleration");
	}

	public double pidGet() {
		return accelerometer.pidGet();
	}

}
