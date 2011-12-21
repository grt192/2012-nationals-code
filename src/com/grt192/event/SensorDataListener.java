/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event;


/**
 *
 * @author anand
 */
public interface SensorDataListener{
    
    public void didRecieveData(SensorEvent e);

    public void didRecieveError(SensorEvent e);
    
}
