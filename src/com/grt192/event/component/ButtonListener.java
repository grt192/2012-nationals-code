/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

import com.grt192.event.*;

/**
 *
 * @author anand
 */
public interface ButtonListener {
    public void buttonUp(SensorEvent e, String key);
    public void buttonDown(SensorEvent e, String key);
}
