package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagSwitchPair;

/**
 * A <code>JagSwitchListener</code> Listens for <code>JagSwitchEvent</code>s
 * sent by a <code>GRTJagSwitchPair</code>
 * @see JagPotentiometerEvent
 * @see GRTJagSwitchPair
 * @author Data
 */
public interface JagSwitchListener {

    /** Called on left switch press */
    public void leftSwitchPressed(JagSwitchEvent e);

    /** Called on left switch release */
    public void leftSwitchReleased(JagSwitchEvent e);

    /** Called on right switch press */
    public void rightSwitchPressed(JagSwitchEvent e);

    /** Called on right switch release */
    public void rightSwitchReleased(JagSwitchEvent e);
}
