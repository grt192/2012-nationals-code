/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import core.PollingSensor;
import edu.wpi.first.wpilibj.Joystick;
import event.BGSystemsFXJoystickEvent;
import event.BGSystemsFXJoystickListener;
import event.ButtonEvent;
import event.ButtonListener;
import java.util.Vector;

/**
 *
 * @author dfrei
 */
public class GRTBGSystemsFXJoystick extends PollingSensor{
     private final Vector joystickListeners;
    private final Vector buttonListeners;
    private final Joystick joystick;
    public static final int KEY_TRIGGER_HALF = 0;
    public static final int KEY_TRIGGER_FULL = 1;
    public static final int KEY_BLACK_LEFT = 2;
    public static final int KEY_BLACK_RIGHT = 3;
    public static final int KEY_BLACK_DOWN = 4;
    public static final int KEY_BLACK_UP = 5;
    public static final int KEY_BUTTON_BLACK = 6;
    public static final int KEY_BUTTON_RED = 7;
    public static final int KEY_BUTTON_THUMB = 8;
    public static final int KEY_BUTTON_PINKIE = 9;
    public static final int KEY_GRAY_LEFT = 10;
    public static final int KEY_GRAY_RIGHT = 11;
    public static final int KEY_GRAY_DOWN = 12;
    public static final int KEY_GRAY_UP = 13;
    public static final int KEY_X = 14;
    public static final int KEY_Y = 15;
    public static final int KEY_TWIST = 16;
    public static final int KEY_JOYSTICK_ANGLE = 17;
    public static final int KEY_FORCE_X = 18;
    public static final int KEY_FORCE_Y = 19;
    private final static int NUM_OF_BUTTONS=14;
    private final static int NUM_DATA=20;
    public static final double PRESSED = TRUE;
    public static final double RELEASED = FALSE;

    public GRTBGSystemsFXJoystick(int channel, int pollTime, String name){
        super(name, pollTime, NUM_DATA);
        joystick = new Joystick(channel);
        joystickListeners = new Vector();
        buttonListeners = new Vector();
        
    }
    
    protected void poll() {
        for (int i = 0; i < NUM_OF_BUTTONS; ++i){
            setState(i, joystick.getRawButton(i+1)?PRESSED:RELEASED);
        }
        setState(KEY_X, joystick.getX());
        setState(KEY_Y, joystick.getY());
        setState(KEY_TWIST, joystick.getRawAxis(6));
        setState(KEY_FORCE_X, joystick.getRawAxis(4));
        setState(KEY_FORCE_Y, joystick.getRawAxis(5));
        setState(KEY_JOYSTICK_ANGLE, joystick.getDirectionRadians());
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {       
        
       
        
        if (id < NUM_OF_BUTTONS) {
            System.out.println("Notifying button to "+ buttonListeners.size());
            //ID maps directly to button ID
            ButtonEvent e = new ButtonEvent(this, id, newDatum == PRESSED);
            if (newDatum == PRESSED) { //true
                for (int i = 0; i < buttonListeners.size(); i++) {
                    ((ButtonListener) buttonListeners.elementAt(i)).buttonPressed(e);
                }
            } else {
                for (int i = 0; i < buttonListeners.size(); i++) {
                    ((ButtonListener) buttonListeners.elementAt(i)).buttonReleased(e);
                }
            }

        } else {
             System.out.println("Notifying joystick to "+ joystickListeners.size());
            //we are now a joystick
            //only reach here if not a button
         BGSystemsFXJoystickEvent e = new BGSystemsFXJoystickEvent(this, id, newDatum);
         switch (id){
             case (KEY_X):
                 for (int i = 0; i < joystickListeners.size(); i++) {
                    ((BGSystemsFXJoystickListener)joystickListeners.elementAt(i)).XAxisMoved(e);
                 }
                 break;
             case (KEY_Y):
                 for (int i = 0; i < joystickListeners.size(); i++) {
                    ((BGSystemsFXJoystickListener)joystickListeners.elementAt(i)).YAxisMoved(e);
                 }
                 break;
             case (KEY_JOYSTICK_ANGLE):
                 for (int i = 0; i < joystickListeners.size(); i++) {
                    ((BGSystemsFXJoystickListener)joystickListeners.elementAt(i)).angleChanged(e);
                 }
                 break;
                  case (KEY_FORCE_X):
                 for (int i = 0; i < joystickListeners.size(); i++) {
                    ((BGSystemsFXJoystickListener)joystickListeners.elementAt(i)).forceXMoved(e);
                 }
                 break;
             case (KEY_FORCE_Y):
                 for (int i = 0; i < joystickListeners.size(); i++) {
                    ((BGSystemsFXJoystickListener)joystickListeners.elementAt(i)).forceYMoved(e);
                 }
                 break;
             case (KEY_TWIST):
                 for (int i = 0; i < joystickListeners.size(); i++) {
                    ((BGSystemsFXJoystickListener)joystickListeners.elementAt(i)).twistChanged(e);
                 }
                 break;
         }
        }
    }
    public void addButtonListener(ButtonListener b) {
        buttonListeners.addElement(b);
    }

    public void removeButtonListener(ButtonListener b) {
        buttonListeners.removeElement(b);
    }

    public void addJoystickListener(BGSystemsFXJoystickListener l) {
        joystickListeners.addElement(l);
    }

    public void removeJoystickListener(BGSystemsFXJoystickListener l) {
        joystickListeners.removeElement(l);
    }
    
}
