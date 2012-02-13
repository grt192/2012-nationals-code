/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logger;

import core.GRTDataLogger;
import rpc.RPCConnection;
import rpc.RPCMessage;

/*as*
 *
 * @author ajc
 */
public class RPCLogger implements GRTDataLogger {
    
    private final RPCConnection conn;
    private int channel;
    
    public static final int DEFAULT_LOGGING_CHANNEL = 111;

    public RPCLogger(RPCConnection conn){
        this.conn = conn;
        this.channel = DEFAULT_LOGGING_CHANNEL;     //MAGIC NUMBER: DEFAULT CHANNEL
    }

    public RPCLogger(RPCConnection conn, int channel){
        this.conn = conn;
        this.channel = channel;
    }

    public void log(int chan, double data) {
        conn.send(new RPCMessage(chan, data));
    }

    public void log(double data) {
        conn.send(new RPCMessage(channel, data));
    }

    public void log(int chan, String msg) {
        conn.send(new RPCMessage(chan, msg));
    }

    public void log(String data) {
        conn.send(new RPCMessage(channel, data));
    }

}
