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
        limitDown.addSwitchListener(this);
        limitUp.addSwitchListener(this);
    }

    
    public void switchStateChanged(SwitchEvent e) {
        //if upper limit switch is tripped and we are going up...
        if((e.getSource() == limitUp) && (targetState == UP)){
            
            //if we are at upper limit whent trying to raise:
            if(e.getState() == GRTSwitch.PRESSED)       //If the upper switch is pressed, stop raising
                stopWedge();
        }
        
        //Otherwise, if we are going down and there is a change in the lower limit switch
        else if((e.getSource() == limitDown) && (targetState == DOWN)){
            if(e.getState() == GRTSwitch.PRESSED)
                stopWedge();
        }
    }
    
    public void raiseWedge(){
        //Set our goal to go up
        targetState = UP;
        
        //If the upper limit has not been reached, keep raising
        System.out.println("Limit Up State: " + limitUp.getState(GRTSwitch.KEY_STATE));
        if(limitUp.getState(GRTSwitch.KEY_STATE) != GRTSwitch.PRESSED){
            
            System.out.println("Raising wedge");

            motor.setSpeed(1.0);
        }
    }

   
    /**
     * 
     */
    public void lowerWedge(){
        
        //We want to go down now
        targetState = DOWN;
        
        System.out.println("Limit Down State: " + limitDown.getState(GRTSwitch.KEY_STATE));
        //Set motor speed to go down if we have not reached the lower limit
        if(limitDown.getState(GRTSwitch.KEY_STATE) != GRTSwitch.PRESSED){
            motor.setSpeed(-1.0);
            
            System.out.println("Lowering wedge");
        }
    }
    
    public void stopWedge(){
        targetState = DISABLED;
        motor.setSpeed(0.0);
    }

    
}
