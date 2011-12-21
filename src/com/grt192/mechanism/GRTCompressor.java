package com.grt192.mechanism;

import com.grt192.actuator.GRTRelay;
import com.grt192.core.Mechanism;
import com.grt192.event.component.SwitchListener;
import com.grt192.sensor.GRTSwitch;

/**
 *
 * @author anand
 */
public class GRTCompressor extends Mechanism implements SwitchListener {

    private GRTRelay compressorPower;
    private GRTSwitch pressureSwitch;
    
    private boolean started;

    public GRTCompressor(int powerChannel, int switchChannel) {
        this(new GRTRelay(powerChannel), new GRTSwitch(switchChannel, 5));
    }

    public GRTCompressor(GRTRelay power, GRTSwitch limit) {
        compressorPower = power;
        compressorPower.start();
        addActuator("powerRelay", power);
        pressureSwitch = limit;
        pressureSwitch.start();
        addSensor("pressureSwitch", pressureSwitch);
        pressureSwitch.addSwitchListener(this);
        started = false;
    }

    public void switchPressed(GRTSwitch source) {
        stopCompressor();
    }

    public void switchReleased(GRTSwitch source) {
        startCompressor();
    }

    public void startCompressor() {
        compressorPower.enqueueCommand(GRTRelay.RELAY_FORWARD);
        started = true;
    }

    public void stopCompressor() {
        compressorPower.halt();
    }
    
    public boolean isPressurized(){
    	return pressureSwitch.getState("Value") == GRTSwitch.CLOSED;
    }

	public boolean isStarted() {
		return started;
	}
    
}
