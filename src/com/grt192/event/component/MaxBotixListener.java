/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.event.component;

/**
 *
 * @author GRTstudent
 */
public interface MaxBotixListener {
    public void didReceiveRange(MaxBotixEvent e);
    public void didRangeSpike(MaxBotixEvent e);
    public void didRangeChange(MaxBotixEvent e);
}
