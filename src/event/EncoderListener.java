/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

/**
 *
 * @author gerberduffy
 */
public interface EncoderListener {
    
    public void directionChanged(EncoderEvent e);
    
    public void degreeChanged(EncoderEvent e);
    
    public void distanceChanged(EncoderEvent e);
    
}
