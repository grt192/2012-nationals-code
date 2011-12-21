/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.BatteryVoltageEvent;
import com.grt192.event.component.BatteryVoltageListener;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Vector;

/**
 *
 * @author ajc
 */
public class BatterySensor extends Thread{

    private DriverStation ds;

    private Vector listeners;

    private double voltage;

    private boolean running;
    private boolean suspended;
    private int sleepTime;

    public BatterySensor(int sleepTime){
        ds = DriverStation.getInstance();
        this.sleepTime = sleepTime;

        listeners = new Vector();
    }

    public void run(){
        running = true;
        while(running){
//            System.out.println("USB23:" +voltage);
            double newVoltage = ds.getBatteryVoltage();
            if(voltage != newVoltage){
                notifyListeners(newVoltage);
            }
            voltage = newVoltage;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void notifyListeners(double newVoltage){
        BatteryVoltageEvent ev = new BatteryVoltageEvent(this, newVoltage);
        for(int i = 0; i<listeners.size(); i++){
            ((BatteryVoltageListener)listeners.elementAt(i)).batteryVoltageChanged(ev);
        }
    }

    public void addBatteryVoltageListener(BatteryVoltageListener l){
        listeners.addElement(l);
    }

    public void removeBatteryVoltageListener(BatteryVoltageListener l){
        listeners.removeElement(l);
    }
    

}
