/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.grtframework.rpc.connection;

import com.grt192.networking.GRTServer;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import java.util.Enumeration;
import java.util.Vector;
import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;
import com.googlecode.grtframework.rpc.RPCMessageListener;

/**
 *
 * @author ajc
 */
public class InternetRPC implements RPCConnection, SocketListener {

    private GRTServer connection;
    private Vector listeners = new Vector();

    public InternetRPC(int port) {
        connection = new GRTServer(port);
//        start();
    }

    public void start(){
        connection.addSocketListener(this);
        connection.start();
    }

    public void send(RPCMessage message) {
        connection.sendData(encode(message));
    }

    public void addMessageListener(RPCMessageListener l) {
        listeners.addElement(l);
    }

    public void removeMessageListener(RPCMessageListener l) {
        listeners.removeElement(l);
    }

    private void notifyListeners(String received) {
        if (isTelemetryLine(received)) {
            // RPCMessage message = new RPCMessage(getKey(received),
            // getData(received));
            RPCMessage message = decode(received);
//             System.out.println(message);
            // TODO only notify specific 'keyed' listeners
            for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
                ((RPCMessageListener) e.nextElement()).messageReceived(message);
            }

        }
    }

    private static String encode(RPCMessage m) {
        // newline to flush all buffers
        return ("USB" + m.getKey() + ":" + m.getData() + "\n");
    }

    private static RPCMessage decode(String received) {
        return new RPCMessage(getKey(received), getData(received));
    }

    private static boolean isTelemetryLine(String line) {
        return line.length() > 3 && line.substring(0, 3).equals("USB");// TODO
        // MAGICNUMBERS
    }

    private static int getKey(String line) {
        return Integer.parseInt(line.substring(3, line.indexOf(':')));
    }

    private static double getData(String line) {
        return Double.parseDouble((line.substring(line.indexOf(':') + 1)));
    }

    public void onConnect(SocketEvent e) { //TODO
    }

    public void onDisconnect(SocketEvent e) { //TODO
    }

    public void dataRecieved(SocketEvent e) {
//        System.out.println("Data received: " + e.getData());
        notifyListeners(e.getData());
    }
}
