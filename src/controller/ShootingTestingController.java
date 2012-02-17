/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import event.BGSystemsFXJoystickEvent;
import event.BGSystemsFXJoystickListener;
import event.ButtonEvent;
import event.ButtonListener;
import mechanism.ShootingSystem;
import sensor.GRTBGSystemsFXJoystick;

/**
 *
 * @author dan, gerberduffy
 */
public class ShootingTestingController extends EventController
implements ButtonListener,
        BGSystemsFXJoystickListener  {
    
    //Mechanisms
    private ShootingSystem shootingSystem;
    
    //Joysticks
    private final GRTBGSystemsFXJoystick joy;
    
    
    public ShootingTestingController(GRTBGSystemsFXJoystick joy, ShootingSystem sys){
        
        super("Mechanism Controller");
        
        //Set joystick fields
        this.joy = joy;

        
        //Set mechanism fields
        this.shootingSystem = sys;

        
    }

    protected void startListening() {
        System.out.println("listening for BG joy");
        joy.addJoystickListener(this);
        joy.addButtonListener(this);
    }

    protected void stopListening() {
        joy.removeJoystickListener(this);
        joy.removeButtonListener(this);
    }


    public void buttonPressed(ButtonEvent e) {
        if (e.getSource() == joy){
            switch (e.getButtonID()){
                case GRTBGSystemsFXJoystick.KEY_BLACK_UP:
                    shootingSystem.incrementFlywheelSpeed(0.05);
                    break;
                case GRTBGSystemsFXJoystick.KEY_BLACK_DOWN:
                    shootingSystem.incrementFlywheelSpeed(-0.05);
                    break;
                case GRTBGSystemsFXJoystick.KEY_BUTTON_BLACK:
                    shootingSystem.toggleFlywheel();
                    break;
                case GRTBGSystemsFXJoystick.KEY_BUTTON_RED:
                    shootingSystem.toggleTopTransition();
                    
            }
        }
    }

    public void XAxisMoved(BGSystemsFXJoystickEvent e) {
        
    }

    /**
     * Ofser stick y-axis movement.
     * 
     * @param e The joystick event received.
     */
    public void YAxisMoved(BGSystemsFXJoystickEvent e) {
    }

    public void angleChanged(BGSystemsFXJoystickEvent e) {
    }

    public void forceXMoved(BGSystemsFXJoystickEvent e) {
    }

    public void forceYMoved(BGSystemsFXJoystickEvent e) {
            shootingSystem.setVisorSpeed(e.getValue());
    }

    public void twistChanged(BGSystemsFXJoystickEvent e) {
        shootingSystem.setPanSpeed(-e.getValue());
    }

    public void buttonReleased(ButtonEvent e) {
    }

    public void xAxisMoved(BGSystemsFXJoystickEvent e) {
    }

    public void yAxisMoved(BGSystemsFXJoystickEvent e) {
    }
    
}
