package com.grt192.event.component;

import com.grt192.sensor.GRTLineTracker;

/**
 *
 * @author anand
 */
public interface LineTrackerListener {
    public void lineStateChange(LineTraceEvent event);
}