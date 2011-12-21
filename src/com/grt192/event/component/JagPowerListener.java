package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagPowerSensor;

/**
 * A <code>JagPowerListener</code> Listens for <code>JagPowerEvent</code>s
 * sent by a <code>GRTJagPowerSensor</code>
 * @see JagPowerEvent
 * @see GRTJagPowerSensor
 * @author ajc
 */
public interface JagPowerListener {

    /** Called on current change from CANjaguar */
    public void currentChanged(JagPowerEvent e);

    /** Called on voltage change from CANJaguar */
    public void voltageChanged(JagPowerEvent e);

    /** Called on temperature change from CANJaguar */
    public void temperatureChanged(JagPowerEvent e);
}
