package com.grt192.core;

import com.grt192.networking.GRTServer;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import java.util.Vector;

/**
 *  A daemon which runs a log server and regulates CRIO output
 * @author ajc, data
 */
public class GRTLogger implements SocketListener {

    public static final int PORT = 192;
    /**
     * List of all keys to accept to print
     */
    private Vector printers;
    private GRTServer gs;

    public GRTLogger() {
        printers = new Vector();
        initServer();
        initPrinters();
    }

    public void initServer() {
        gs = new GRTServer(PORT);
        gs.addSocketListener(this);
//        gs.start();
    }

    public void initPrinters() {
        printers.addElement("GRTRobot");
    }

    public void write(String key, String data) {
        if (printers.contains(key)) {
            System.out.println("[" + key + "]: " + data);
        }

        gs.sendData(key + ": " + data);

    }

    public static void extLog(String source, String message) {
        GRTRobot.getInstance().getLogger().write(source, message);
    }

    public void addPrinter(String key) {
        printers.addElement(key);
    }

    public void removePrinter(String key) {
        printers.removeElement(key);
    }

    public void onConnect(SocketEvent e) {
        System.out.println("connection!");
    }

    public void onDisconnect(SocketEvent e) {
    }

    //TODO implement printer control
    public void dataRecieved(SocketEvent e) {
    }
}
