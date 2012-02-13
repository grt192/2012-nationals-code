package core;

import java.util.Vector;
import rpc.RPCConnection;

/**
 * A GRTLoggedProcess is a controllable process. It can be initiated/terminated.
 * 
 * When a GRTLoggedProcess is constructed, it is immediately run, but not enabled.
 * 
 * @author ajc
 * 
 */
public abstract class GRTLoggedProcess extends Thread implements IProcess {


    private Vector loggers;

    protected final String name;
    protected boolean enabled = false;
    protected boolean running = true;


    public GRTLoggedProcess(String name) {
        this.name = name;      
        this.loggers =  new Vector();
    }

    public void addDataLogger(GRTDataLogger logger){
        loggers.addElement(logger);
    }
    
    public void removeDataLogger(GRTDataLogger l){
        loggers.removeElement(l);
    }

    /**
     * Network log of the message on the default channel.
     * 
     * @param message
     */
    protected void log(String message) {
        System.out.println(toString() + "\t" + message);
    }

    /**
     * Logs in format: "[[ClassName:Id]]    @name   message
     * @param name
     * @param message
     */
    protected void log(String name, String message) {
        System.out.println(toString() + "\t" + name + "\t" + message); 
    }

    /**
     * Network printing of the data on the RPC channel
     * @param channel
     * @param data 
     */
    protected void log(int channel, double data){
        for( int i=0; i < loggers.size(); i++){
            ((GRTDataLogger)loggers.elementAt(i)).log(channel, data);
        }
    }
    
    /**
     * Network printing of "msg" on the specified RPC "channel"
     * @param channel
     * @param msg 
     */
    protected void log (int channel, String msg){
        for( int i=0; i < loggers.size(); i++){
            ((GRTDataLogger)loggers.elementAt(i)).log(channel, msg);
        }
    }

    /**
     * Network printing of double values
     * @param data
     */
    protected void log(double data) {
        for( int i=0; i < loggers.size(); i++){
            ((GRTDataLogger)loggers.elementAt(i)).log(data);
        }
    }

    /**
     * Console-only print
     * @param name
     * @param data
     */
    protected void log(String name, double data) {
        System.out.println(toString() + "\t" + name + "\t" + data);
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void halt() {
        running = false;
        disable(); 
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Name
     * 
     * @return
     */
    public String getID() {
        return name;
    }
    
    /*
     * To string method, returns loggable string in the formate
     * [[ClassName:Id]]
     */
    public String toString(){
        return "[[" + getClass().getName() + ":" + getID();
    }
}
