/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.GRTBGSystemsFXJoystick;

/**
 *
 * @author dfrei
 */
public class BGSystemsFXJoystickEvent {
    private final GRTBGSystemsFXJoystick src;
    private final int id;
    private final double datum;
    

    public BGSystemsFXJoystickEvent(GRTBGSystemsFXJoystick src, int id, double datum) {
        this.src = src;
        this.id = id;
        this.datum = datum;
    }
    public GRTBGSystemsFXJoystick getSource(){
        return src;
    }
    public int getId() {
        return id;
    }
    public double getValue() {
        return datum;
    }
    
}
