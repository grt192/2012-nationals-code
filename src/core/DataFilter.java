/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import event.SensorChangeListener;
import event.SensorEvent;


/**
 *
 * @author gerberduffy
 */
public abstract class DataFilter implements SensorChangeListener {
    
    private double data[];
    
    public DataFilter(Sensor s){
        s.addSensorStateChangeListener(this);
    }
    
    public abstract double filter(double newData);

    public void sensorStateChanged(SensorEvent e) {
        filter(e.getData());
    }
    
    
    
}
