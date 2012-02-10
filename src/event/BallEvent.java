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
    private int newPosition;
    private int ballId;
    
    public BallEvent(BallTracker source, int ballId, int newPos){
        this.source = source;
        this.ballId = ballId;
        this.newPosition = newPos;
    }
    
    /**
     * Returns the new position of the ball.
     * @return 
     */
    public int getNewPosition(){
        return newPosition;
    }
    
    /**
     * Return the BallTracker the ball is tracked by.
     * Should always be the same, but in case of code 
     * reuse this is written for multiple ball trackers.
     */
    public BallTracker getSource(){
        return source;
    }
    
    /**
     * Return the id of the ball
     */
    public int getBallId(){
        return ballId;
    }
}
