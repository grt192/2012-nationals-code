/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

/**
 *
 * @author anand
 */
public interface PotentiometerListener {
    public void didReceivePosition(PotentiometerEvent e);
    public void positionChanged(PotentiometerEvent e);
}
