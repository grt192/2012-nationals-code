package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.LineTraceEvent;
import edu.wpi.first.wpilibj.*;
import java.util.Vector;
import com.grt192.event.component.LineTrackerListener;

/**
 * 2011
 * @author ajc
 */
public class GRTLineTracker extends Sensor {

    public static final double PRESENT = Sensor.TRUE;
    public static final double ABSENT = Sensor.FALSE;

    private DigitalInput input;
    private Vector lineStateListeners;

    public GRTLineTracker(int digitalChannel, int polltime, String id){
        lineStateListeners = new Vector();
        setSleepTime(polltime);
        input = new DigitalInput(digitalChannel);
        setState("Line", ABSENT);
        this.id = id;
    }

    public void poll() {
        double previous = getState("Line");
        setState("Line", input.get());
        if(previous != getState("Line")){
            notifyListeners(getState("Line") == PRESENT);
        }
    }

    private void notifyListeners(boolean present) {
        for (int i = 0; i < lineStateListeners.size(); i++) {
                ((LineTrackerListener) lineStateListeners.elementAt(i)).lineStateChange(new LineTraceEvent(this, present));
        }
    }

    public void addLineTrackerListener(LineTrackerListener listener) {
        lineStateListeners.addElement(listener);
    }

    public void removeLineTrackerListener(LineTrackerListener listener) {
        lineStateListeners.removeElement(listener);
    }


}