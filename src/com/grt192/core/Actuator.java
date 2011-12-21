/*
 * Superclass for all specific actuators on a robot.
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.core;

import com.grt192.actuator.exception.ActuatorException;
import com.grt192.event.ActuatorCommandListener;
import com.grt192.event.ActuatorEvent;
import java.util.Vector;

/**
 * Superclass for all specific actuators on a robot.
 * Runs continously, waiting for commands. A driver of sorts
 * @author anand
 */
public abstract class Actuator extends GRTObject {

    protected static final int MAGIC = 999; //protects actuators from overriding
                                            //this must be passed to clear a command queue
    public static final int SLEEP_INTERVAL = 5 ; //ms
    private boolean running;
    private boolean suspended;
    
    private Vector commands;  //Queue of commands to be executed
    private Command current;  //Current command being executed
    private Vector actuatorCommandListeners;

    public Actuator(){
        commands = new Vector();
        current = null;
        running = false;
        suspended  = false;
        actuatorCommandListeners = new Vector();
    }

    /**
     * Start continuous execution of commands from the command queue
     * Actuator will wait for a command to be present and execute it
     * if suspended the actuator will continue running but ignore commands
     * on exception the actuator will halt safely
     */
    public void run() {
        running = true;
        while(running){
            try{
                if(!suspended){
                    current = dequeue();
                    if(current!=null){
                        //Issue order to hardware for operation
                        executeCommand(current);
                        //Allow for hardware operation delays
                        if(current.getSleepTime()>0){
                            sleep(current.getSleepTime());
                            if(current.isAtomic())
                                this.halt();
                        }
                        for(int i=0; i<actuatorCommandListeners.size(); i++){
                            ((ActuatorCommandListener)
                                actuatorCommandListeners.elementAt(i)).commandDidComplete(
                                           new ActuatorEvent(this, 
                                           ActuatorEvent.COMMAND_COMPLETE,
                                           this.current));
                        }
                        
                    }
                }
                //minimum loop sleep
                sleep(SLEEP_INTERVAL);
            }catch(Exception e){
                //On exception kill this actuator, as it is
                //unsafe to continue operation
                for(int i=0; i<actuatorCommandListeners.size(); i++){
                    ((ActuatorCommandListener)
                        actuatorCommandListeners.elementAt(i)).commandDidComplete(
                                           new ActuatorEvent(this,
                                           ActuatorEvent.COMMAND_FAILED,
                                           this.current));
                }
                e.printStackTrace();
                stopActuator();
            }
        }
    }

    /**
     * Executes the command next command. Only called from the Run method.
     * Each actuator must have this method.
     * @param c
     */ 
    protected abstract void executeCommand(Command c) throws ActuatorException;

    public Vector getCommands() {
        return commands;
    }

    /**
     * Add a command to be executed by this actuator
     * @param c
     */
    public synchronized void enqueueCommand(double c) {
        commands.addElement(new Command(c));
    }

    public synchronized void enqueueCommand(Command c) {
        commands.addElement(c);
    }

    /**
     * Add a series of command to be executed in order by this Actuator
     * @param commandList
     */
    public synchronized void enqueueCommands(Vector commandList){
        for(int i=0; i<commandList.size(); i++){
            enqueueCommand((Command) commandList.elementAt(i));
        }
    }

    public synchronized void enqueueCommands(Command[] commandList){
        for(int i=0; i<commandList.length; i++){
            enqueueCommand(commandList[i]);
        }
    }

    public synchronized void enqueueCommands(double[] commandList){
        for(int i=0; i<commandList.length; i++){
            enqueueCommand(commandList[i]);
        }
    }

    /**
     * remove a specific command from the actuator's queue without executing
     * @param c
     */
    public void removeCommand(Command c) {
        commands.removeElement(c);
    }

    /**
     * Remove the first item from the command queue
     * @return command
     */
    protected Command dequeue(){
       if(commands.isEmpty())
           return null;
       Command removed = (Command) (commands.elementAt(0));
       commands.removeElementAt(0);
       return removed;
    }

    /**
     * Supercedes commands in the queue and does the command provided
     * @param c
     * @throws ActuatorException 
     */
    public void doCommand(Command c) throws ActuatorException{
        this.pause();
        this.executeCommand(c);
        this.resume();
    }

    public void doCommand(double c) throws ActuatorException{
        doCommand(new Command(c));
    }


    /**
     * Clear All commands from the queue. 
     * Use carefully, a magic number is required to force clear the queue.
     * @param magicNumber
     */
    public synchronized void clearQueue(int magicNumber) {
        if (magicNumber == MAGIC) {
            commands.removeAllElements();
        }
    }

    /**
     * get the current command being executed
     * @return current
     */
    public Command getCurrent() {
        return current;
    }

    public boolean isRunning() {
        return running;
    }

    //Safely bring the actuator to a disabled state.
    protected abstract void halt();

    /**
     * stopActuator restores hardware to "safe" state. Then stops actuator Thread.
     */
    public void stopActuator(){
        halt();
        running = false;
    }

    public boolean isSuspended() {
        return suspended;
    }

    /**
     * Pause execution of commands in the queue and stop the action of this actuator
     */
    public void suspend() {
        halt();
        this.suspended = true;
    }

    /**
     * Pause execution of new commands in the queue
     */
    public void pause(){
        this.suspended = true;
    }

    /**
     * Resume execution of commands in the queue from where Actutator left off
     */
    public void resume() {
        suspended = false;
    }

    public Vector getActuatorCommandListeners() {
        return actuatorCommandListeners;
    }

    /**
     * add a listener to be notify when commands are executed
     * @param a
     */
    public void addActuatorCommandListener(ActuatorCommandListener a){
        actuatorCommandListeners.addElement(a);
    }

    public void removeActuatorCommandListener(ActuatorCommandListener a){
        actuatorCommandListeners.removeElement(a);
    }
}
