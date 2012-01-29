/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import core.PollingSensor;
import edu.wpi.first.wpilibj.DigitalInput;
import event.SwitchEvent;
import event.SwitchListener;
import java.util.Vector;

/**
 *
 * @author gerberduffy
 */
public class GRTSwitch extends PollingSensor {

    public static final double PRESSED = 1.0;
    public static final double RELEASED = 0.0;
    private DigitalInput in;
    public static final int KEY_STATE = 0;
    public static final int NUM_DATA = 1;
    private Vector listeners;

    /**
     * Create a switch on the specified channel of the default module.
     * 
     * @param channel The channel
     * @param polltime  Time in between polls
     * @param id String name of the switch
     */
    public GRTSwitch(int channel, int polltime, String id) {

        super(id, polltime, NUM_DATA);

        in = new DigitalInput(channel);

        listeners = new Vector();
    }
    
    /**
     * Create a switch on the specified channel of the specified module.
     * 
     * @param module The module number
     * @param channel The channel
     * @param polltime  Time in between polls
     * @param id String name of the switch
     */
    public GRTSwitch(int module, int channel, int polltime, String id) {

        super(id, polltime, NUM_DATA);

        in = new DigitalInput(module, channel);

        listeners = new Vector();
    }
    
    
    

    public void addSwitchListener(SwitchListener l) {
        listeners.addElement(l);
    }

    public void removeSwitchListener(SwitchListener l) {
        listeners.removeElement(l);
    }

    private boolean isOn() {
        return in.get();
    }

    protected void poll() {
        setState(KEY_STATE, isOn() ? RELEASED : PRESSED);
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        System.out.println(newDatum);
        SwitchEvent e = new SwitchEvent(this, newDatum);
        for (int i = 0; i < listeners.size(); i++) {
            ((SwitchListener) listeners.elementAt(i)).switchStateChanged(e);
        }
    }
}
