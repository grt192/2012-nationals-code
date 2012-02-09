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
     * Respond to a ball's position 
     * in the robot being changed.
     * @param e 
     */
    public void ballPositionChanged(BallEvent e);
    
}
