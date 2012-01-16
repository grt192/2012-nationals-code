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

    public RPCLogger(RPCConnection conn){
        this.conn = conn;
    }

    public RPCLogger(RPCConnection conn, int channel){
        this.conn = conn;
        this.channel = channel;
    }

    public void log(int channel, double data) {
        conn.send(new RPCMessage(channel, data));
    }

    public void log(double data) {
        conn.send(new RPCMessage(channel, data));
    }

}
