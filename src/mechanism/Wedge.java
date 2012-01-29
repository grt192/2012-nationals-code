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
 * @author calvin, gerberduffy
 */
public class Wedge extends GRTLoggedProcess implements SwitchListener {
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

    public void startListening(){
        limitUp.addSwitchListener(this);
        limitDown.addSwitchListener(this);
    }
    
    public void stopListening(){
        limitUp.removeSwitchListener(this);
        limitDown.removeSwitchListener(this);
    }
    
    public void switchStateChanged(SwitchEvent e) {
        if((e.getSource() == limitUp) && (targetState == UP)){
            if(e.getState() == GRTSwitch.PRESSED)
                stopWedge();
            else if(e.getState() == GRTSwitch.RELEASED)
                raiseWedge();
        }
        
        else if((e.getSource() == limitDown) && (targetState == DOWN)){
            if(e.getState() == GRTSwitch.PRESSED)
                stopWedge();
            else if(e.getState() == GRTSwitch.RELEASED)
                lowerWedge();
        }
    }
    
    public void raiseWedge(){
        System.out.println("Raising wedge");
        targetState = UP;
        if(limitUp.getState(GRTSwitch.KEY_STATE) == GRTSwitch.RELEASED)
            motor.setSpeed(1);
    }
    
    public void stopWedge(){
        targetState = DISABLED;
        motor.setSpeed(0);
    }
    
    public void lowerWedge(){
        System.out.println("Lowering wedge");
        targetState = DOWN;
        if(limitDown.getState(GRTSwitch.KEY_STATE) == GRTSwitch.RELEASED)
            motor.setSpeed(-1);
    }
    
}
