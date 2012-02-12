/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

/**
 *
 * An interface for classes that need to
 * know when and how a ball has changed 
 * position inside the robot.
 * 
 * @author gerberduffy
 */
public interface BallTrackingListener {
    
    /**
     * Respond to a ball entering a new positon in the robot
     * @param e The event
     */
    public void ballPositionChanged(BallEvent e);
    
    /**
     * Ball count change event.
     * @param e The event.
     */
    public void ballCountChanged(BallEvent e);
    
}
