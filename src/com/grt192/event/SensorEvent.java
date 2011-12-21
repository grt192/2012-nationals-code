package com.grt192.event;

import com.grt192.core.Sensor;
import java.util.Hashtable;

/**
 *
 * @author anand
 */
public class SensorEvent {
    public static final int DATA_AVAILABLE = 0;
    public static final int STATE_CHANGE = 1;
    public static final int SENSOR_ERROR = 2;
    
    protected Sensor source;
    protected int id;

    protected Hashtable data;
    public SensorEvent(Sensor source, int id, Hashtable data) {
        this.source = source;
        this.id = id;
        this.data = data;
    }

    public Hashtable getData() {
        return data;
    }

    public double getData(String key){
        return ((Double) data.get(key)).doubleValue();
    }

    public int getId() {
        return id;
    }
    
    public Sensor getSource() {
        return source;
    }
    
}
