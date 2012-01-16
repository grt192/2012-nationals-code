/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor.balance;

import core.PollingSensor;
import core.Sensor;
import sensor.GRTSwitch;

/**
 *
 * @author ajc
 */
public class BalanceTunePosition extends Sensor {

    private final GRTSwitch frontLeft;
    private final GRTSwitch frontRight;
    private final GRTSwitch backLeft;
    private final GRTSwitch backRight;

    public BalanceTunePosition(GRTSwitch frontLeft, GRTSwitch frontRight,
            GRTSwitch backLeft, GRTSwitch backRight, String name) {
        super(name);
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }

    protected void startListening() {
    }

    protected void stopListening() {
    }
}
