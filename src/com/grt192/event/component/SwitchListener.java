/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

import com.grt192.sensor.GRTSwitch;

/**
 *
 * @author anand
 */
public interface SwitchListener {
    public void switchPressed(GRTSwitch source);
    public void switchReleased(GRTSwitch source);
}
