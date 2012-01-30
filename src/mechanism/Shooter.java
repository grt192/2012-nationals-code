/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import core.GRTLoggedProcess;
import event.SwitchEvent;
import event.SwitchListener;
import sensor.GRTEncoder;
import sensor.GRTSwitch;

/**
 *
 * @author dfrei
 */
public class Shooter extends GRTLoggedProcess implements SwitchListener{
    private final IMotor primarySpinnerMotor;
    private final IMotor secondarySpinnerMotor;
    private final GRTEncoder spinnerEncoder;
    private final IMotor beltMotor1;
    private final IMotor beltMotor2;
    private final GRTSwitch ballShotSwitch;
    
    public Shooter(IMotor primarySpinnerMotor,IMotor secondarySpinnerMotor, 
            GRTEncoder spinnerEncoder,
            GRTSwitch ballShotSwitch,
            IMotor beltMotor1,
            IMotor beltMotor2){
        super("Shooter");
        this.primarySpinnerMotor = primarySpinnerMotor;
        this.secondarySpinnerMotor = secondarySpinnerMotor;
        this.spinnerEncoder = spinnerEncoder;
        this.beltMotor1 = beltMotor1;
        this.beltMotor2 = beltMotor2;
        this.ballShotSwitch = ballShotSwitch;
    }
    /**
     * Sets the desired actual speed of the flywheel and maintains it using encoders.
     * @param speed The desired actual speed.
     */
    public void setSpinnerSpeed(double speed){
        primarySpinnerMotor.setSpeed(speed);
        secondarySpinnerMotor.setSpeed(speed);
    }
    /**
     * Start the feeding belt, and wait for the limit switch to trigger off 
     * before we stop it.
     */
    public void shoot(){
        beltMotor1.setSpeed(1);
        beltMotor2.setSpeed(1);
    }

    public void switchStateChanged(SwitchEvent e) {
        if (e.getSource() == ballShotSwitch){
            if (e.getState() == GRTSwitch.RELEASED){
                //ball has been loaded.
                beltMotor1.setSpeed(0);
                beltMotor2.setSpeed(0);
            }
        }
    }
}
