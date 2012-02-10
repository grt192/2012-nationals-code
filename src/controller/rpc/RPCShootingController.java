/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.rpc;

import core.EventController;
import mechanism.ShootingSystem;
import rpc.RPCConnection;
import rpc.RPCMessage;
import rpc.RPCMessageListener;

/**
 * A controller that 
 * @author gerberduffy
 */
public class RPCShootingController extends EventController implements RPCMessageListener {
    
    private RPCConnection rpcConn;
    private ShootingSystem shootingSystem;
    private int rpcKey;
    
    /**
     * 
     * @param rpcConn
     * @param ss
     * @param rpcKey 
     */
    public RPCShootingController(RPCConnection rpcConn, ShootingSystem ss, int rpcKey){
        super("RPC Shooting controller");
        this.rpcConn = rpcConn;
        this.shootingSystem = ss;
        
        this.rpcKey = rpcKey;
    }

    /**
     * On message receive, convert to turret speed
     * @param message 
     */
    public void messageReceived(RPCMessage message) {
        if (message.getKey() == this.rpcKey){
            double panSpeed = getTurretVelocityFromRPCString(message.getData());
            shootingSystem.setPanSpeed(panSpeed);
        }
    }
    
    /*
     * Utility function to convert 
     * an RPC string to the turret 
     * velocity.
     * 
     * @param msg The RPCMessage to get velocity from
     */
    private double getTurretVelocityFromRPCString(String msg){
        return Double.parseDouble(msg);
    }

    protected void startListening() {
        rpcConn.addMessageListener(this);
    }

    protected void stopListening() {
        rpcConn.removeMessageListener(this);
    }
    
}
