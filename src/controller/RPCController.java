/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import mechanism.GRTDriveTrain;
import rpc.RPCConnection;
import rpc.RPCMessage;
import rpc.RPCMessageListener;

/**
 *
 * A controller that sets Drive Train speeds based on RPC Messages
 * received over a network.
 * 
 * @author gerberduffy
 */
public class RPCController extends EventController implements RPCMessageListener {

    private GRTDriveTrain dt;
    private RPCConnection conn;
    private int[] rpcKeys;
    
    public static final int DT_KEY = 0;
    public static final int NUM_KEYS = 1;
    
    /**
     * Constructor. Takes in the RPC Connection, the RPC keys we listen on, and 
     * the Drive Train
     * @param conn The RPCConnection we are listening on
     * @param rpcKeys An array of RPC keys we care about
     * @param dt The Drive Train we are actuating
     */
    public RPCController(RPCConnection conn, int[] rpcKeys, GRTDriveTrain dt){
        super("RPC Controller");
        
        this.conn = conn;          //The rpc connection we are listening on
        this.dt = dt;              //The drivetrain being controlled
        
        this.rpcKeys = rpcKeys;         //The RPC key to listen for data on
    }
    
    /**
     * Start listening on the connection for new RPC Messages
     */
    protected void startListening() {
        conn.addMessageListener(this);
    }

    /**
     * Stop listening over the connection
     */
    protected void stopListening() {
        conn.removeMessageListener(this);
    }

    /**
     * On a new message, respond by setting the Drive Train speeds
     * 
     * @param message The RPC Message
     */
    public void messageReceived(RPCMessage message) {
        
        //If a message comes on on our key, we use the data from it           
        if(message.getKey() == rpcKeys[DT_KEY]){
            String command = message.getData();
            
            //Get the tank drive values, and drive at that speed
            double[] tankVals = getTankDriveSpeed(command);
            dt.tankDrive(tankVals[0], tankVals[1]);
        }
    }
    
    /**
     * Get the left and right tank drive values from the data of an RPCMessage.
     */
    private double[] getTankDriveSpeed(String command){
        
        //The first 7 characters are the left speed, the next 7 are the right side
        return new double[] { Double.parseDouble(command.substring(0, 7)), Double.parseDouble(command.substring(7, 14)) };
    }
    
}
