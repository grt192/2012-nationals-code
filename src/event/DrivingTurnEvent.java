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
    private final double degree;

    public DrivingTurnEvent(GRTDriverStation source, double degree) {
        this.source = source;
        this.degree = degree;
    }
	
	public double getDegree() {
		return degree;
    }
}
