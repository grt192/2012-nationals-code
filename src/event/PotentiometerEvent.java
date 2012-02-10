/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.GRTPotentiometer;

/**
 *
 * @author calvin
 */
public class PotentiometerEvent {
    
    private GRTPotentiometer source;
    private double value;

    public PotentiometerEvent(GRTPotentiometer source, double value) {
        this.source = source;
        this.value = value;
    }

    public double getAngle() {
        return value;
    }

    public GRTPotentiometer getSource() {
        return source;
    }
    
}
