/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.GRTSharpIR;

/**
 *
 * @author gerberduffy
 */
public class SharpIREvent {
    
    private GRTSharpIR source;
    private int id;
    private double distance;
	
    public SharpIREvent(GRTSharpIR source, int id, double distance){
        this.source = source;
        this.id = id;
        this.distance = distance;
    }
    public double getDistance() {
        return distance;
    }
    
    public int getID() {
        return id;
    }

    public GRTSharpIR getSource() {
        return source;
    }
    
}
