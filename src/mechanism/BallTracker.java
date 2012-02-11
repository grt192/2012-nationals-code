/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import core.PollingSensor;
import core.Sensor;
import event.BallEvent;
import event.BallTrackingListener;
import event.SwitchEvent;
import event.SwitchListener;
import java.util.Vector;
import sensor.GRTSwitch;

/**
 *
 * Entity that keeps track of all the balls 
 * currently controlled by the robot
 * 
 * @author gerberduffy
 */
public class BallTracker extends Sensor implements SwitchListener{
    
    //Ball position enumerations
    public static final int IN_LOWER_ROLLERS = 0;   //Right after being collected
    public static final int IN_HOPPER = 1;          //In the hopper 
    public static final int IN_UPPER_ROLLERS = 2;   //In the upper rollers
    public static final int IN_SHOOTING_QUEUE = 3;  //The ball is queued up for shooting
    
    //KEYS
    public static final int KEY_NUM_BALLS = 0;
    public static final int KEY_FIRST_BALL_POSITION = 1;
    public static final int KEY_SECOND_BALL_POSITION = 2;
    public static final int KEY_THIRD_BALL_POSITION = 3;
    
    public static final int NUM_DATA = 4;
    
    
    //THe limit switches that are responsible for ball tracking.
    private GRTSwitch lowerRollersSwitch;
    private GRTSwitch upperRollersSwitch;
    private GRTSwitch hopperSwitch;
    private GRTSwitch ballQueueSwitch;
    
    //Booleans
    private boolean ballQueued = false;     //True if a ball is waiting to be shot.
    
    //Tracking numbers
    private int totalBalls = 0;     //Total balls in system. Starts at 0
    
    //Listeners vector
    private Vector listeners;
    
    public BallTracker(double pollTime, 
                       GRTSwitch lowerRollers, 
                       GRTSwitch upperRollers,
                       GRTSwitch hopper, 
                       GRTSwitch ballQueue)
    {
        super("Ball Tracker");
        //Setup our instance variables
        this.lowerRollersSwitch = lowerRollers;
        this.hopperSwitch = hopper;
        this.upperRollersSwitch = upperRollers;
        this.ballQueueSwitch = ballQueue;
        
        this.listeners = new Vector();
    }

    /**
     * Add a ball listener
     * @param l 
     */
    public void addListener(BallTrackingListener l){
        listeners.addElement(l);
    }
    
    /**
     * Remove a ball listener
     * @param l 
     */
    public void removeListener(BallTrackingListener l){
        listeners.removeElement(l);
    }
    
    /**
     * Start listening to the count switches
     * 
     */
    protected void startListening() {
        lowerRollersSwitch.addSwitchListener(this);
        hopperSwitch.addSwitchListener(this);
        upperRollersSwitch.addSwitchListener(this);
        ballQueueSwitch.addSwitchListener(this);
    }
    
    /**
     * Stop listening to the switches.
     */
    protected void stopListening() {
        lowerRollersSwitch.removeSwitchListener(this);
        hopperSwitch.removeSwitchListener(this);
        upperRollersSwitch.removeSwitchListener(this);
        ballQueueSwitch.removeSwitchListener(this);
    }

    /**
     * Gets the total number of balls in the robot.
     * @return The number of balls contained in the robot
     */
    int getTotalBalls(){
        return totalBalls;
    }
    

    /**
     * On a switch state change, update the ball count as well as 
     * the current ball positions.
     * 
     * 
     * @param e 
     */
    public void switchStateChanged(SwitchEvent e) {
        BallEvent be = null;    //The event we are sending
        if (e.getState() == GRTSwitch.PRESSED){
            //If the switch is pressed, 
            if (e.getSource() == lowerRollersSwitch){
                be = new BallEvent(this, ++totalBalls, BallTracker.IN_LOWER_ROLLERS);
            } else if (e.getSource() == upperRollersSwitch){
                be = new BallEvent(this, ++totalBalls, BallTracker.IN_UPPER_ROLLERS);
            } else if (e.getSource() == ballQueueSwitch){
                be = new BallEvent(this, ++totalBalls, BallTracker.IN_SHOOTING_QUEUE);
            }
            
        } 
        //Logic for switch release.
        else if (e.getState() == GRTSwitch.RELEASED){
            //If the ball queue switch has been released after a ball 
            //has been queued, we know that we have shot a ball, so decrement 
            //the total ball count.
            if (e.getSource() == ballQueueSwitch && ballQueued){
                be = new BallEvent(this, --totalBalls, IN_HOPPER);
            }
        }
        
        for(int i=0; i < listeners.size(); i++){
            ((BallTrackingListener)listeners.elementAt(i)).ballPositionChanged(be);
        }
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
    }
    
}
