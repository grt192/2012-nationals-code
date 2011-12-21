package com.grt192.sensor.canjaguar;

import com.grt192.actuator.GRTCANJaguar;
import com.grt192.actuator.exception.GRTCANJaguarException;
import com.grt192.core.Sensor;
import com.grt192.event.component.CANJaguarFaultListener;
import com.grt192.utils.Util;
import edu.wpi.first.wpilibj.CANJaguar;
import java.util.Vector;

/**
 * Reads the jaguar fault bitmask and notifies listeners on changes
 * @author ajc
 */
public class GRTJagFaultSensor extends Sensor {

    /** Fault boolean array indicies for use with heartbeat faults array*/
    public static final int FAULTS_CURRENT = 0;
    public static final int FAULTS_TEMPERATURE = 1;
    public static final int FAULTS_VOLTAGE = 2;
    public static final int FAULTS_GATE_DRIVER = 3;
    /** Fault keys for getState() */
    public static final String CURRENT_FAULT_KEY = "Current";
    public static final String TEMPERATURE_FAULT_KEY = "Temperature";
    public static final String VOLTAGE_FAULT_KEY = "Voltage";
    public static final String GATE_DRIVER_FAULT_KEY = "GateDriverFault";
    private final GRTCANJaguar jag;
    private short bitmask;
    private Vector listeners;
    /**
     * Called automatically from GRTCANJaguar's <code>getFaultSensor()</code> method.
     * Therefore use <code>getFaultSensor()</code>, not this.
     * @see GRTCANJaguar
     */
    public GRTJagFaultSensor(GRTCANJaguar jag, int pollTime, String id) {
        this.jag = jag;
        setSleepTime(1000);
        setId(id);
        listeners = new Vector();
    }

    /** True if the CANJaguar is reading a voltage fault **/
    public boolean isVoltageFault() {
        return (CANJaguar.Faults.kBusVoltageFault.value & bitmask) != 0;
    }

    /** True if the CANJaguar is reading a current fault **/
    public boolean isCurrentFault() {
        return (CANJaguar.Faults.kCurrentFault.value & bitmask) != 0;
    }

    /** True if the CANJaguar is reading a GateDriver Fault **/
    public boolean isGateDriverFault() {
        return (CANJaguar.Faults.kGateDriverFault.value & bitmask) != 0;
    }

    /** True if the CANJaguar is reading a temperature fault **/
    public boolean isTemperatureFault() {
        return (CANJaguar.Faults.kTemperatureFault.value & bitmask) != 0;
    }

    public void poll() {
        bitmask = jag.getFaults();
        double prevCurrentFault = getState(CURRENT_FAULT_KEY);
        double prevTemperatureFault = getState(TEMPERATURE_FAULT_KEY);
        double prevVoltageFault = getState(VOLTAGE_FAULT_KEY);
        double prevGateDriverFault = getState(GATE_DRIVER_FAULT_KEY);

        setState(CURRENT_FAULT_KEY, isCurrentFault());
        setState(TEMPERATURE_FAULT_KEY, isTemperatureFault());
        setState(VOLTAGE_FAULT_KEY, isVoltageFault());
        setState(GATE_DRIVER_FAULT_KEY, isGateDriverFault());

        if (prevVoltageFault != getState(VOLTAGE_FAULT_KEY)) {
            if (isVoltageFault()) {
                notifyVoltageFault();
            } else {
                notifyVoltageNormal();
            }
        }
        if (prevCurrentFault != getState(CURRENT_FAULT_KEY)) {
            if (isCurrentFault()) {
                notifyCurrentFault();
            } else {
                notifyCurrentNormal();
            }
        }
        if (prevGateDriverFault != getState(GATE_DRIVER_FAULT_KEY)) {
            if (isGateDriverFault()) {
                notifyGateDriverFault();
            } else {
                notifyGateDriverNormal();
            }
        }
        if (prevTemperatureFault != getState(TEMPERATURE_FAULT_KEY)) {
            if (isTemperatureFault()) {
                notifyTemperatureFault();
            } else {
                notifyTemperatureNormal();
            }
        }
        notifyFaultsReceived(
                new boolean[]{
                    isCurrentFault(),
                    isTemperatureFault(),
                    isVoltageFault(),
                    isGateDriverFault()});

    }

    /**
     * Starts notifying <code>CANTimeoutListener</code> l for all CANTimeouts
     * @param l <code>CANTimeoutListener</code> to notify
     */
    public void addCANFailureListener(CANJaguarFaultListener l) {
        listeners.addElement(l);
    }

    /**
     * Stops notifying <code>CANTimeoutListener</code> l for all CANTimeouts
     * @param l <code>CANTimeoutListener</code> to stop notifying
     */
    public void removeCANFailureListener(CANJaguarFaultListener l) {
        listeners.removeElement(l);
    }

    /**
     * "heartbeat" signal with all fault data
     * If an index is true, this indicates that it's function has failed.
     */
    private void notifyFaultsReceived(boolean[] faults) {
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagFaultsReceived(faults);
        }
    }

    /** Notifies all listeners that the jag has a Current fault **/
    private void notifyCurrentFault() {
        GRTCANJaguarException ex = new GRTCANJaguarException(GRTCANJaguarException.CURRENT_FAULT, jag);
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagCurrentFault(ex);
        }
    }

    /** Notifies all listeners that the jag has a Temperature fault **/
    private void notifyTemperatureFault() {
        GRTCANJaguarException ex = new GRTCANJaguarException(GRTCANJaguarException.TEMPERATURE_FAULT, jag);
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagTemperatureFault(ex);
        }
    }

    /** Notifies all listeners that the jag has a voltage fault **/
    private void notifyVoltageFault() {
        GRTCANJaguarException ex = new GRTCANJaguarException(GRTCANJaguarException.VOLTAGE_FAULT, jag);
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagBusVoltageFault(ex);
        }
    }

    /** Notifies all listeners that the jag has a GateDriver fault **/
    private void notifyGateDriverFault() {
        GRTCANJaguarException ex = new GRTCANJaguarException(GRTCANJaguarException.GATE_DRIVER_FAULT, jag);
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagGateDriverFault(ex);
        }
    }

    /** Notifies all listeners that the jag has no Current fault **/
    private void notifyCurrentNormal() {
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagCurrentNormal(jag);
        }
    }

    /** Notifies all listeners that the jag has no Temperature fault **/
    private void notifyTemperatureNormal() {
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagTemperatureNormal(jag);
        }
    }

    /** Notifies all listeners that the jag has no voltage fault **/
    private void notifyVoltageNormal() {
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagBusVoltageNormal(jag);
        }
    }

    /** Notifies all listeners that the jag has no GateDriver fault **/
    private void notifyGateDriverNormal() {
        for (int i = 0; i < listeners.size(); i++) {
            ((CANJaguarFaultListener) listeners.elementAt(i)).jagGateDriverNormal(jag);
        }
    }
}
