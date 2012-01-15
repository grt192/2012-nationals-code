package rsh;


import core.GRTLoggedProcess;
import java.util.Vector;
import networking.GRTServer;
import networking.SocketEvent;
import networking.SocketListener;
import rsh.Commandable;
import util.Util;


/**
 * Network interface for scripting style actuation
 * @author ajc
 */
public class RSHServer extends Thread implements SocketListener {

    private GRTServer gs;
    private Vector listeners;

    public RSHServer(int port) {
        gs = new GRTServer(port);
        gs.addSocketListener(this);
        gs.start();
    }

//    private void initServer(boolean hangForClient) {
//        gs = new GRTServer(RSHD_PORT);
//        gs.addSocketListener(this);
//        if (hangForClient) {
//            gs.connect();
//        }
//        gs.start();
//    }

    public void onConnect(SocketEvent e) {
//        log("RSH client connected!");
        System.out.println("a client has connected");
    }

    public void onDisconnect(SocketEvent e) {
        System.out.println("RSH client disconnected!");
    }

    public void dataRecieved(SocketEvent e) {
        if (e.getData() == null) {
            System.out.println("null data");
            return;
        }
        System.out.println("new data: " + Util.arraytoString(Util.separateString(e.getData())));
        String[] commands = Util.separateString(e.getData());
        notifyListeners(commands);
    }

    public void notifyListeners(String[] args) {
        for (int i = 0; i < listeners.size(); i++) {
            ((Commandable) listeners.elementAt(i)).command(args);
            System.out.println(listeners.elementAt(i).getClass().getName() + " commanded");
        }
    }

    public void addCommandListener(Commandable c) {
        listeners.addElement(c);
    }

    public void removeCommandListener(Commandable c) {
        listeners.removeElement(c);
    }

    private String dToString(String[] s) {
        String retu = "[";
        for (int i = 0; i < s.length; i++) {
            retu += s[i] + ", ";
        }
        retu += " ]";
        return retu;
    }
}