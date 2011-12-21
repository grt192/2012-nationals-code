package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagPotentiometer;

/**
 * A <code>JagPotentiometerListener</code> Listens for <code>JagPotentiometerEvent</code>s
 * sent by a <code>GRTJagPotentiometer</code>
 * @see JagPotentiometerEvent
 * @see GRTJagPotentiometer
 * @author Data
 */
public interface JagPotentiometerListener {

    /** Called when any change of the potentiometer signal occurs */
    public void countDidChange(JagPotentiometerEvent e);

    /** Called when potentiometer starts to move out of an idle state */
    public void rotationDidStart(JagPotentiometerEvent e);

    /** Called when potentiometer detects zero movement.*/
    public void rotationDidStop(JagPotentiometerEvent e);

    /** Called when the direction of the potentiometer spin flips. */
    public void changedDirection(JagPotentiometerEvent e);
}
