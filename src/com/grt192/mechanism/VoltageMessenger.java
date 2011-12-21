/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism;

import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.grt192.event.component.BatteryVoltageEvent;
import com.grt192.event.component.BatteryVoltageListener;
import com.grt192.sensor.BatterySensor;

/**
 *
 * @author ajc
 */
public class VoltageMessenger implements BatteryVoltageListener{
    private final RPCConnection connection;
    private final int key;
    private final BatterySensor sensor;

    public VoltageMessenger(RPCConnection connection, int key,BatterySensor sensor){
        this.connection = connection;
        this.key = key;
        this.sensor = sensor;
        sensor.addBatteryVoltageListener(this);

    }

    public void batteryVoltageChanged(BatteryVoltageEvent ev) {
        System.out.println(new RPCMessage(key, ev.getVoltage()));
        connection.send(new RPCMessage(key, ev.getVoltage()));
    }

}
