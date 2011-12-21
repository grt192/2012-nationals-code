
package com.grt192.event.component;

import com.grt192.actuator.exception.GRTCANJaguarException;

/**
 * Listens for GRTCANJaguar CANTimeoutExceptions. 
 * @author ajc
 */
public interface CANTimeoutListener {

    public void CANTimedOut(GRTCANJaguarException ex);

}
