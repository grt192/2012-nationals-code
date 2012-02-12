/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import mechanism.BallTracker;

/**
 *
 * @author gerberduffy
 */
public class BallEvent {
    
    private BallTracker source;
    private int position;
    private int numBalls;
    
    
    public BallEvent(BallTracker source, int numBalls, int position){
        this.source = source;
        this.numBalls = numBalls;
        this.position = position;
    }
    
    /**
     * Returns the number of total balls at the time of the event.
     * @return 
     */
    public double getNumBalls(){
        return numBalls;
    }
    
    /**
     * Return the position that event occured at.
     */
    public double getBallPosition(){
        return position;
    }
    
    /**
     * Return the BallTracker the ball is tracked by.
     * Should always be the same, but in case of code 
     * reuse this is written for multiple ball trackers.
     */
    public BallTracker getSource(){
        return source;
    }
}
