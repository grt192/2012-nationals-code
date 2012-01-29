/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.base.GRTRobotDriver;

/**
 *
 * @author ajc
 */
public class DrivingEvent {

    public static final int SIDE_LEFT = 0;
    public static final int SIDE_RIGHT = 1;
    private final GRTRobotDriver source;
    private final int sideID;
    private final double value;

    public DrivingEvent(GRTRobotDriver source, int sideID, double value) {
        this.source = source;
        this.sideID = sideID;
        this.value = value;
    }

    public int getSide() {
        return sideID;
    }

    public double getPercentSpeed() {
        return value;
    }
	
    public GRTRobotDriver getSource() {
        return source;
    }
}
