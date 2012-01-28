/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

/**
 *
 * @author dan
 */
public interface Attack3JoystickListener {
    public void xAxisMoved(Attack3JoystickEvent e);
    public void yAxisMoved(Attack3JoystickEvent e);
    public void angleChanged(Attack3JoystickEvent e);
}
