/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

/**
 *
 * @author anand
 */
public interface JoystickListener{
    public void xAxisMoved(JoystickEvent e);
    public void yAxisMoved(JoystickEvent e);
    public void zAxisMoved(JoystickEvent e);
    public void throttleMoved(JoystickEvent e);
}
