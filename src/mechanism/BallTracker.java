/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import core.Sensor;
import event.SwitchEvent;
import event.SwitchListener;
import sensor.GRTSwitch;

/**
 *
 * Entity that keeps track of all the balls 
 * currently controlled by the robot
 * 
 * @author gerberduffy
 */
public class BallTracker implements SwitchListener{
    
    //Ball position enumerations
    public static final int IN_LOWER_ROLLERS = 0;   //Right after being collected
    public static final int IN_HOPPER = 1;          //In the hopper 
    public static final int IN_UPPER_ROLLERS = 2;   //In the upper rollers
    public static final int IN_SHOOTING_QUEUE = 3;  //The ball is queued up for shooting
    
    //THe limit switches that are responsible for ball tracking.
    private GRTSwitch lowerRollersSwitch;
    private GRTSwitch upperRollersSwitch;
    private GRTSwitch hopperSwitch;
    private GRTSwitch ballQueueSwitch;
    
    //Tracking numbers
    private int totalBalls = 0;     //Total balls in system. Starts at 0
    
    public BallTracker(GRTSwitch lowerRollers, 
                       GRTSwitch upperRollers,
                       GRTSwitch hopper, 
                       GRTSwitch ballQueue)
    {        
        //Setup our instance variables
        this.lowerRollersSwitch = lowerRollers;
        this.hopperSwitch = hopper;
        this.upperRollersSwitch = upperRollers;
        this.ballQueueSwitch = ballQueue;
    }

    /**
     * Start listening to the count switches
     * 
     */
    protected void enable() {
        lowerRollersSwitch.addSwitchListener(this);
        hopperSwitch.addSwitchListener(this);
        upperRollersSwitch.addSwitchListener(this);
        ballQueueSwitch.addSwitchListener(this);
    }
    
    /**
     * Stop listening to the switches.
     */
    protected void disable() {
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
        //TODO: Implement this.
    }
    
}
