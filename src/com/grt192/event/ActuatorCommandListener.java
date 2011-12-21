package com.grt192.event;

/**
 *
 * @author anand
 */
public interface ActuatorCommandListener {
    
    public void commandDidComplete(ActuatorEvent e);

    public void commandDidFail(ActuatorEvent e);
}
