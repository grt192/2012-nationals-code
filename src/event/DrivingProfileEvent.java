/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.base.GRTRobotDriver;
import sensor.base.IDriverProfile;

/**
 *
 * @author ajc
 */
public class DrivingProfileEvent {

    private final GRTRobotDriver source;
    private final IDriverProfile profile;

    public DrivingProfileEvent(GRTRobotDriver source, IDriverProfile profile) {
        this.source = source;
        this.profile = profile;

    }

    public IDriverProfile getProfile() {
        return profile;
    }

    public GRTRobotDriver getSource() {
        return source;
    }
}
