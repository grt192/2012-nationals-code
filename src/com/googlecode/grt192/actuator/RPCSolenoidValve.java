/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.grt192.actuator;

import com.grt192.actuator.GRTSolenoid;
import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;

/**
 * An RPCSolenoidValve is an rpc interface to Solenoid Valve actuators.
 * @author ajc
 */
public class RPCSolenoidValve implements RPCMessageListener {

    public static final double ON = 1.0;
    public static final double OFF = 0.0;

    private final GRTSolenoid actuator;
    private final RPCConnection conn;
    private final int key;

    /**
     * Constructs a Solenoid Valve RPC interface
     * 
     * Precondition: actuator and connection are started
     *
     *
     * @param actuator SolenoidValve to be commanded
     * @param conn connection to listen for commands
     * @param key key to listen commands
     */
    public RPCSolenoidValve(GRTSolenoid actuator, RPCConnection conn, int key){
        this.actuator = actuator;
        this.conn = conn;
        this.key = key;
    }

    public void messageReceived(RPCMessage rpcm) {
//        System.out.println("receive" + rpcm);
        if(rpcm.getKey() == key){
            System.out.println("COMMAND RECEIVED:" + key + ":" + rpcm.getData());
            actuator.enqueueCommand(rpcm.getData());
        }
    }

    public void startListening() {
        conn.addMessageListener(this);
    }

    public void stopListening() {
        conn.removeMessageListener(this);
    }

}
