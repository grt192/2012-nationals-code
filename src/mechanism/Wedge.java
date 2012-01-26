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
public class Wedge extends GRTLoggedProcess implements SwitchListener{
    private IMotor motor;
    private GRTSwitch limitUp;
    private GRTSwitch limitDown;
    private int targetState;
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int DISABLED = 2;
    
    public Wedge(IMotor motor, GRTSwitch limitUp, GRTSwitch limitDown, String name){
        super(name);
        this.motor = motor;
        this.limitDown = limitDown;
        this.limitUp = limitUp;
    }

    public void switchStateChanged(SwitchEvent e) {
        if((e.getSource() == limitUp) && (targetState == UP)){
            if(e.getState() == GRTSwitch.PRESSED)
                motor.setSpeed(0);
            else if(e.getState() == GRTSwitch.RELEASED)
                motor.setSpeed(1);
        }
        
        else if((e.getSource() == limitDown) && (targetState == DOWN)){
            if(e.getState() == GRTSwitch.PRESSED)
                motor.setSpeed(0);
            else if(e.getState() == GRTSwitch.RELEASED)
                raiseWedge();
        }
    }
    
    public void raiseWedge(){
        targetState = UP;
        if(limitUp.getState(GRTSwitch.STATE) == GRTSwitch.RELEASED)
            motor.setSpeed(1);
    }
    
    public void stopWedge(){
        targetState = DISABLED;
        motor.setSpeed(0);
    }
    
    public void lowerWedge(){
        targetState = DOWN;
        if(limitDown.getState(GRTSwitch.STATE) == GRTSwitch.RELEASED)
            motor.setSpeed(-1);
    }
    
}
