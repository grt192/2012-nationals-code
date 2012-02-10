/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import core.PollingSensor;
import edu.wpi.first.wpilibj.AnalogChannel;
import java.util.Vector;
import com.sun.squawk.util.MathUtils;
import event.SharpIREvent;
import event.SharpIRListener;

/**
 *
 * @author calvin
 */

public class GRTSharpIR extends PollingSensor{
    public static final int KEY_DISTANCE = 0;
    private static final int NUM_DATA = 1;
    
    public static final int SENSOR_A02 = 0;
    public static final int SENSOR_A21 = 1;
    
    private static final double A02_CONSTANT1 = 29.5276;
    private static final double A02_CONSTANT2 = 0.388731;
    private static final double A21_CONSTANT1 = 7.67122;
    private static final double A21_CONSTANT2 = 0.281735;
    
    private static int sensorType;
    private AnalogChannel analogChannel;
    private Vector sharpIRListeners;
    
    public GRTSharpIR(int channel, int pollTime, int sensorType, String name){
        super(name, pollTime, NUM_DATA);
        this.sensorType = sensorType;
        this.analogChannel = new AnalogChannel(channel);
    }

    protected void poll() {
        double voltage = analogChannel.getVoltage();
        setState(KEY_DISTANCE, scaledDistance(voltage));
    }

    private double scaledDistance(double voltage){
        //do not question magic regressional functions
        switch(sensorType){
            case SENSOR_A02: 
                return A02_CONSTANT1 / Math.tan(A02_CONSTANT2 * voltage);
            case SENSOR_A21:
                return A21_CONSTANT1 / Math.tan(A21_CONSTANT2 * voltage);
            default:
                return 0;
        }
        
    }
    
    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        if (id == KEY_DISTANCE) {
            SharpIREvent e = new SharpIREvent(this, id, newDatum);
            for (int i = 0; i < sharpIRListeners.size(); i++) {
                ((SharpIRListener) sharpIRListeners.elementAt(i)).distanceChanged(e);
            }
        }
    }
    
}
