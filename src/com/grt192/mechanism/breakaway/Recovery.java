/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism.breakaway;

import com.grt192.actuator.GRTJaguar;
import com.grt192.core.Command;
import com.grt192.core.Mechanism;
import com.grt192.core.Sensor;
import com.grt192.sensor.GRTSwitch;

/**
 *
 * @author klian
 */
public class Recovery extends Mechanism{

    public static final double ARM_SPEED = -1.0;

    //variable to check if arm is flipping
    private boolean flipping;

    public Recovery(GRTJaguar motorA, GRTJaguar motorB, GRTSwitch upLimitSwitch,
            GRTSwitch groundLimitSwitch){

       //starts motor, both limitSwitches, and accelerometer(may or may not be used)
       if(!motorA.isRunning()) {
           motorA.start();
       }
       if(!motorB.isRunning()) {
           motorB.start();
       }

       upLimitSwitch.start();
       groundLimitSwitch.start();

       flipping = false;
       
       //adds Actuator and both sensors with string tags
       addActuator("RecMotorA", motorA);
       addActuator("RecMotorB", motorB);
       addSensor("RecUpSwitch", upLimitSwitch);
       addSensor("RecGroundSwitch", groundLimitSwitch);
    }

    //checks is top limit switch engaged
    public boolean isArmAtTop(){
        return getSensor("RecUpSwitch").getState("State") == Sensor.TRUE;
    }

    //if bottom limit switch is hit
    public boolean isArmRetracted(){
        return getSensor("RecGroundSwitch").getState("State") == Sensor.TRUE;
    }

    //controls motor speed
    private void setSpeed(double input){
        getActuator("RecMotorA").enqueueCommand(new Command(input));
        getActuator("RecMotorB").enqueueCommand(new Command(input));
    }

    //flips arm up
    public void flip(){
        setSpeed(ARM_SPEED);
        flipping = true;
    }

    //retracts arm
    public void retract(){
        setSpeed(-ARM_SPEED);
        flipping = true;
    }

    //stops movement
    public void stopArm(){
        setSpeed(0);
        flipping = false;
    }

    //checks if arm is active
    public boolean isFlipping(){
        return flipping;
    }
}
