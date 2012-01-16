/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor.base;

import event.Attack3JoystickEvent;
import event.Attack3JoystickListener;
import event.ButtonEvent;
import event.ButtonListener;
import sensor.GRTAttack3Joystick;

/**
 * Driver station using 2 Logitech Attack 3 Joysticks
 * @author dan
 */
public class GRTAttack3DriverStation extends GRTDriverStation implements Attack3JoystickListener, ButtonListener{
    private final GRTAttack3Joystick left;
    private final GRTAttack3Joystick right;
	private final int turn180Button;
	private final int turn90Button;
    
    public GRTAttack3DriverStation(GRTAttack3Joystick left, GRTAttack3Joystick right,
            int[] profileButtons, IDriverProfile[] curves, int turn180Button, int turn90Button, String name){
        super(profileButtons, curves, name);
        this.left= left;
        this.right = right;
		this.turn180Button = turn180Button;
		this.turn90Button = turn90Button;
    }
    
    protected void startListening() {
        left.addJoystickListener(this);
        left.addButtonListener(this);
        right.addJoystickListener(this);
        right.addButtonListener(this);
    }

    protected void stopListening() {
        left.removeJoystickListener(this);
        left.removeButtonListener(this);
        right.removeJoystickListener(this);
        right.removeButtonListener(this);
    }

    public void XAxisMoved(Attack3JoystickEvent e) {
    }

    public void YAxisMoved(Attack3JoystickEvent e) {
        if (e.getSource()==left){
            notifyLeftDriveSpeed(e.getValue());
            notifyStateChange(KEY_LEFT_VELOCITY, e.getValue());
        }
        else if (e.getSource() ==right){
            notifyRightDriveSpeed(e.getValue());
            notifyStateChange(KEY_RIGHT_VELOCITY, e.getValue());
        }
    }

    public void AngleChanged(Attack3JoystickEvent e) {
    }

    public void buttonPressed(ButtonEvent e) {
    }

    public void buttonReleased(ButtonEvent e) {
        int profileID = getIndex(profileButtons, e.getButtonID());
        if (profileID != -1) {//meaning it exists, see #getIndex(int[], int)
            notifyProfileChange(profileID);
            notifyStateChange(KEY_PROFILE_ID, profileID);
        }
		if(e.getButtonID() == turn180Button){
			notifyDriveTurn(180);
		}
		if(e.getButtonID() == turn90Button) {
			notifyDriveTurn(90);
		}
    }
    private static int getIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return -1;
    }
    
}
