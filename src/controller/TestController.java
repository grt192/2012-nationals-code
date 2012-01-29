/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import actuator.GRTVictor;
import core.EventController;
import event.Attack3JoystickEvent;
import event.Attack3JoystickListener;
import sensor.GRTAttack3Joystick;

/**
 *
 * @author gerberduffy
 */
public class TestController extends EventController implements Attack3JoystickListener{

    private GRTVictor[] leftVictors;
    private GRTVictor[] rightVictors;
    
    private GRTAttack3Joystick left, right;
    
    public TestController(GRTVictor[] leftVictors, GRTVictor[] rightVictors, GRTAttack3Joystick left, GRTAttack3Joystick right){
        super("Test controller");
        
        this.leftVictors = leftVictors;
        this.rightVictors = rightVictors;
        
        this.left = left;
        this.right = right;
    }
    
    protected void startListening() {
        left.addJoystickListener(this);
        right.addJoystickListener(this);
    }

    protected void stopListening() {
        left.removeJoystickListener(this);
        right.removeJoystickListener(this);
    }

    public void xAxisMoved(Attack3JoystickEvent e) {
    }

    public void yAxisMoved(Attack3JoystickEvent e) {
        
        System.out.println("Y Change!!");
        
        if (e.getSource() == left){
            for (int i=0 ; i < leftVictors.length; i++){
                leftVictors[i].setSpeed(e.getValue());
            }
        } else {
            for (int i=0 ; i < rightVictors.length; i++){
                rightVictors[i].setSpeed(e.getValue());
            }
        }
    }

    public void angleChanged(Attack3JoystickEvent e) {
    }
    
}
