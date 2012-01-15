/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.base.GRTDriverStation;

/**
 *
 * @author nadia
 */
public class DrivingTurnEvent {

    private final GRTDriverStation source;
    private final double value;

    public DrivingTurnEvent(GRTDriverStation source, double value) {
        this.source = source;
        this.value = value;
    
    }
	
	public double getMotorDegreeChange() {
		return value;
	}

	public double getRobotDegreeChange() {
		return value;
	}

    public GRTDriverStation getSource() {
        return source;
    }
}
