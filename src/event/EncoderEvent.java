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
    private double value;
	
    public EncoderEvent(GRTEncoder source, int id, double value){
        this.source = source;
		this.id = id;
		this.value = value;
    }
	public double getValue() {
        return value;
    }

    public int getID() {
        return id;
    }

    public GRTEncoder getSource() {
        return source;
    }
    
}
