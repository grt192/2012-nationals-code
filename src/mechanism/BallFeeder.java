/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import core.GRTLoggedProcess;
import event.SwitchEvent;
import event.SwitchListener;
import sensor.GRTSwitch;

/**
 *
 * @author calvin
 */
public class BallFeeder extends GRTLoggedProcess implements SwitchListener{
    private IMotor pickupMotor;
    private IMotor turretFeeder;
    private GRTSwitch ballPickupSwitch;
    public BallFeeder(IMotor pickupMotor, IMotor turretFeeder, GRTSwitch ballPickupSwitch, String name){
        super(name);
        this.pickupMotor = pickupMotor;
        this.turretFeeder = turretFeeder;
        this.ballPickupSwitch = ballPickupSwitch;
    }

    public void pickUpBall(){
        pickupMotor.setSpeed(1.0);
    }
    
    public void stopPickUp(){
        pickupMotor.setSpeed(0.0);
    }
    
    public void switchStateChanged(SwitchEvent e) {
        if (e.getSource() == ballPickupSwitch)
            if(e.getState() == GRTSwitch.PRESSED)
                stopPickUp();
    }
}
