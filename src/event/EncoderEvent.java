/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.GRTADXL345;
import sensor.GRTEncoder;

/**
 *
 * @author gerberduffy
 */
public class EncoderEvent {
    
	private GRTEncoder source;
    private int id;
    private double distance;
    private double angle;
    private double direction;
    private double rate;
	
    public EncoderEvent(GRTEncoder source, int id, double distance, double angle, double direction, double rate){
        this.source = source;
        this.id = id;
        this.distance = distance;
        this.direction = direction;
        this.angle = angle;
        this.rate = rate;
    }
    public double getDistance() {
        return distance;
    }
    
    public double getDirection() {
        return direction;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public double getRate() {
        return rate;
    }

    public int getID() {
        return id;
    }

    public GRTEncoder getSource() {
        return source;
    }
    
}
