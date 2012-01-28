/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.GRTSwitch;

/**
 *
 * @author gerberduffy
 */
public class SwitchEvent {
    
    private double state;
    private GRTSwitch sw;
    
    public SwitchEvent(GRTSwitch sw, double newState){
        state = newState;
        this.sw = sw;
    }
    
    public GRTSwitch getSource(){
        return this.sw;
    }
    
    public double getState(){
        return this.state;
    }
}
