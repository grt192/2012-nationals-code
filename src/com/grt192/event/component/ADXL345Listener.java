/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

/**
 *
 * @author anand
 */
public interface ADXL345Listener {
    public void didReceiveAcceleration(ADXL345Event e);
    public void didAccelerationSpike(ADXL345Event e);
    public void didAccelerationChange(ADXL345Event e);
}
