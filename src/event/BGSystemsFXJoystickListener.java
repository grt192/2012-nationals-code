/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

/**
 *
 * @author dfrei
 */
public interface BGSystemsFXJoystickListener {
    public void xAxisMoved(BGSystemsFXJoystickEvent e);
    public void yAxisMoved(BGSystemsFXJoystickEvent e);
    public void angleChanged(BGSystemsFXJoystickEvent e);
    public void forceXMoved(BGSystemsFXJoystickEvent e);
    public void forceYMoved(BGSystemsFXJoystickEvent e);
    public void twistChanged(BGSystemsFXJoystickEvent e);
}
