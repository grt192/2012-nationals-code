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
import event.SwitchListener;//If the collection switch has been pressed/released:
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
    
    //Our switches.
    private final GRTSwitch collectionSwitch;
    private final GRTSwitch hopperEntranceSwitch;
    private final GRTSwitch upperTransitionSwitch;
    private final GRTSwitch queueSwitch;

    //Numbers
    private int totalBalls = 0;       //Number of total balls. Should start with 0.
    
    //Positon constants
    public static final int IN_COLLECTION = 0;
    public static final int IN_HOPPER = 1;
    public static final int IN_UPPER_TRANSITION = 2;
    public static final int IN_QUEUE = 3;
    public static final int BALL_SHOT = 4;
    
    //Know if a ball is queued
    private boolean ballQueued = false;
    
    //Our listeners
    private Vector listeners;
    private boolean hopperPressed = false;
    

    /**
     * Constructor. Takes in all our switches.
     * 
     * @param collectionSwitch The switch on the collection mechanism.
     * @param hopperEntranceSwitch Switch at the entrance to the hopper.
     * @param upperTransitionSwitch Switch that tracks when a ball enters the upper transition area.
     * @param queueSwitch Switch that senses when a ball is queued.
     */
    public BallTracker(
            GRTSwitch collectionSwitch,
            GRTSwitch hopperEntranceSwitch,
            GRTSwitch upperTransitionSwitch,
            GRTSwitch queueSwitch
            )
    {
        super("Ball Tracker");
        
        this.collectionSwitch = collectionSwitch;
        this.hopperEntranceSwitch = hopperEntranceSwitch;
        this.upperTransitionSwitch = upperTransitionSwitch;
        this.queueSwitch = queueSwitch;
        
        listeners = new Vector();
    }
    
    /**
     * Start listening to our switches.
     */
    protected void startListening() {
        collectionSwitch.addSwitchListener(this);
        hopperEntranceSwitch.addSwitchListener(this);
        upperTransitionSwitch.addSwitchListener(this);
        queueSwitch.addSwitchListener(this);
    }

    /**
     * Stop listening to our switches.
     */
    protected void stopListening() {
        collectionSwitch.removeSwitchListener(this);
        hopperEntranceSwitch.removeSwitchListener(this);
        upperTransitionSwitch.removeSwitchListener(this);
        queueSwitch.removeSwitchListener(this);
    }

    
    /**
     * Add a listener.
     */
    public void addBallListener(BallTrackingListener bl){
        listeners.addElement(bl);
    }
    
    /**
     * Remove a listener.
     */
    public void removeBallListener(BallTrackingListener bl){
        listeners.removeElement(bl);
    }
    
    
    /**
     * Respond to a switch event.
     * @param e The switch event we are responding to.
     */
    public void switchStateChanged(SwitchEvent e) {
        System.out.println("Ball Tracker: Switch press");
        if (e.getSource() == collectionSwitch){
            //If the collection switch has been pressed, we know we have one more ball
            if (e.getState() == GRTSwitch.PRESSED){
                BallEvent be = new BallEvent(this, ++totalBalls, IN_COLLECTION);
                for (int i=0; i < listeners.size(); i++){
                    ((BallTrackingListener)listeners.elementAt(i)).ballPositionChanged(be);
                    ((BallTrackingListener)listeners.elementAt(i)).ballCountChanged(be);
                }
            }
            
            else if (e.getState() == GRTSwitch.RELEASED){
                //Maybe something...
            }
        }
        
        
        //Hopper switch pressed or released.
        else if (e.getSource() == hopperEntranceSwitch){
            
            //Hopper switched: PRESSED
            if (e.getState() == GRTSwitch.RELEASED && hopperPressed){
                //We have pressed the hopper switch.
                hopperPressed = true;
            }
            
            //If the hopper entrance switch is released after being pressed, alert listeners.
            if (e.getState() == GRTSwitch.RELEASED && hopperPressed){
                BallEvent be = new BallEvent(this, totalBalls, IN_HOPPER);
                hopperPressed = false;
                for (int i=0; i < listeners.size(); i++){
                    ((BallTrackingListener)listeners.elementAt(i)).ballPositionChanged(be);
                }
            }
        }
        
        //The upper rollers.
        else if (e.getSource() == upperTransitionSwitch){
            //If pressed, we know that a ball has entered the upper transition area and can become queued.
            if (e.getState() == GRTSwitch.PRESSED){
                BallEvent be = new BallEvent(this, ++totalBalls, IN_COLLECTION);
                for (int i=0; i < listeners.size(); i++){
                    ((BallTrackingListener)listeners.elementAt(i)).ballPositionChanged(be);
                    ((BallTrackingListener)listeners.elementAt(i)).ballCountChanged(be);
                }
            }
        }
        
        else if (e.getSource() == queueSwitch){
            if (e.getState() == GRTSwitch.PRESSED){
                ballQueued = true;
                BallEvent be = new BallEvent(this, totalBalls, IN_QUEUE);
                for (int i=0; i < listeners.size(); i++){
                    ((BallTrackingListener)listeners.elementAt(i)).ballPositionChanged(be);
                }
            }
            
            else if (e.getState() == GRTSwitch.RELEASED){
                BallEvent be = null;
                if (ballQueued == true){
                    be = new BallEvent(this, --totalBalls, BALL_SHOT);
                } 
                else {
                    be = new BallEvent(this, totalBalls, BALL_SHOT);
                }
                for (int i=0; i < listeners.size(); i++){
                    ((BallTrackingListener)listeners.elementAt(i)).ballPositionChanged(be);
                    ((BallTrackingListener)listeners.elementAt(i)).ballCountChanged(be);
                }
            }
        }
    }
    
}
