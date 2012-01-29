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
    public void XAxisMoved(BGSystemsFXJoystickEvent e);
    public void YAxisMoved(BGSystemsFXJoystickEvent e);
    public void angleChanged(BGSystemsFXJoystickEvent e);
    public void forceXMoved(BGSystemsFXJoystickEvent e);
    public void forceYMoved(BGSystemsFXJoystickEvent e);
    public void twistChanged(BGSystemsFXJoystickEvent e);
}
