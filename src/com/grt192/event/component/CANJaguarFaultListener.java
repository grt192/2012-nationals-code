package com.grt192.event.component;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.actuator.exception.GRTCANJaguarException;

/**
 * Listens for GRTCANJaguar faults
 * @author ajc
 */
public interface CANJaguarFaultListener {

    public void jagCurrentFault(GRTCANJaguarException ex);

    public void jagBusVoltageFault(GRTCANJaguarException ex);

    public void jagGateDriverFault(GRTCANJaguarException ex);

    public void jagTemperatureFault(GRTCANJaguarException ex);

    public void jagCurrentNormal(GRTCANJaguar jag);

    public void jagBusVoltageNormal(GRTCANJaguar jag);

    public void jagGateDriverNormal(GRTCANJaguar jag);

    public void jagTemperatureNormal(GRTCANJaguar jag);

    /** "Heartbeat" which also holds all data **/
    public void jagFaultsReceived(boolean[] faults);
}
