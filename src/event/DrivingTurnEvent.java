/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.base.GRTRobotDriver;

/**
 *
 * @author nadia
 */
public class DrivingTurnEvent {

    private final GRTRobotDriver source;
    private final double degree;

    public DrivingTurnEvent(GRTRobotDriver source, double degree) {
        this.source = source;
        this.degree = degree;
    }
	
	public double getDegree() {
		return degree;
    }
}
