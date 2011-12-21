/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.grt192.actuator;

import com.grt192.actuator.GRTSolenoid;
import com.googlecode.grtframework.rpc.RPCConnection;

/**
 *
 * @author ajc
 */
public class RPCSolenoidValveFactory {

    /**
     * Constructs an RPCSolenoidValve from a GRTSolenoid and starts it.
     * @param slotID CRIO relay module slot ID
     * @param solenoidID Relay module breakout pin ID
     * @param conn RPCConnection to receive commands from
     * @param rpcKey RPC key to listen on
     * @return
     */
    public static RPCSolenoidValve starteFromIDs(int slotID, int solenoidID, RPCConnection conn, int rpcKey){
        GRTSolenoid s = new GRTSolenoid(slotID, solenoidID);
        s.start();

        RPCSolenoidValve rpcsv = new RPCSolenoidValve(s, conn, rpcKey);
        rpcsv.startListening();
        return rpcsv;
    }



}
