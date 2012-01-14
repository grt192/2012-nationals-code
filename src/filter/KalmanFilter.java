/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import core.DataFilter;
import core.Sensor;
import event.SensorEvent;

/**
 *
 * @author gerberduffy
 */
public class KalmanFilter extends DataFilter {

    private double[] data;
    
    public KalmanFilter(Sensor s){
        super(s);
    }
    
    public double filter(double newData) {
        return 0.0;
    }
    
}
