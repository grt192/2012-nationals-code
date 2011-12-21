package com.grt192.sensor;

import java.util.Vector;

import com.grt192.core.Sensor;
import com.grt192.event.component.GyroEvent;
import com.grt192.event.component.GyroListener;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * GRTGyro is a continuously running sensor driver that collects and serves data
 * from a single-axis gyro. Note that integration is done in hardware, independent of polltime
 * 
 * @author anand
 */
public class GRTGyro extends Sensor implements PIDSource {

	private Gyro gyro;
	private Vector gyroListeners;
	private double spikeThreshold = 1.0;
	private double changeThreshold = .01;

	public GRTGyro(int channel, int pollTime, String id) {
		gyro = new Gyro(channel);
		setSleepTime(pollTime);
		setState("Angle", 0.0);
		gyroListeners = new Vector();
		this.id = id;
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

	public void poll() {
		double previousValue = getState("Angle");
		double angle = gyro.getAngle();

		setState("Angle", angle);

		if (Math.abs(getState("Angle") - previousValue) >= spikeThreshold) {
			notifyGyroSpike();
		}
		if (Math.abs(getState("Angle") - previousValue) >= changeThreshold) {
			notifyGyroChange();
		}
		notifyGyroListeners();
	}

	protected void notifyGyroSpike() {
		for (int i = 0; i < gyroListeners.size(); i++) {
			((GyroListener) gyroListeners.elementAt(i))
					.didAngleSpike(new GyroEvent(this, GyroEvent.SPIKE,
							getState("Angle")));
		}
	}

	protected void notifyGyroChange() {
		for (int i = 0; i < gyroListeners.size(); i++) {
			((GyroListener) gyroListeners.elementAt(i))
					.didAngleChange(new GyroEvent(this, GyroEvent.CHANGE,
							getState("Angle")));
		}
	}

	protected void notifyGyroListeners() {
		for (int i = 0; i < gyroListeners.size(); i++) {
			((GyroListener) gyroListeners.elementAt(i))
					.didReceiveAngle(new GyroEvent(this, GyroEvent.NEW_DATA,
							getState("Angle")));
		}
	}

	public Vector getGyroListeners() {
		return gyroListeners;
	}

	public void addGyroListener(GyroListener a) {
		gyroListeners.addElement(a);
	}

	public void removeGyroListener(GyroListener a) {
		gyroListeners.removeElement(a);
	}

	public String toString() {
		return "gyro: " + getState("Angle");
	}

	public double pidGet() {
		//TODO: fix asap
		return getState("Angle");
	}

}
