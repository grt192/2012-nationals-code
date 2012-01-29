package controller;

import sensor.GRTXBoxJoystick;
import mechanism.Shooter;
import core.EventController;
import event.ButtonEvent;
import event.ButtonListener;
import event.XboxJoystickEvent;
import event.XboxJoystickListener;

public class ShooterXboxController extends EventController implements ButtonListener, XboxJoystickListener{
	
	private Shooter shooter;
	private GRTXBoxJoystick xbox;

	public ShooterXboxController(Shooter shooter, GRTXBoxJoystick xbox){
		super("Shooter Teleop Xbox Controller");
		this.shooter = shooter;
		this.xbox = xbox;
	}

	
	protected void startListening() {
		// TODO Auto-generated method stub
		xbox.addButtonListener(this);
		xbox.addJoystickListener(this);
	}

	
	protected void stopListening() {
		// TODO Auto-generated method stub
		xbox.removeButtonListener(this);
		xbox.removeJoystickListener(this);
	}

	
	public void leftXAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void leftYAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void leftAngleChanged(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void rightXAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void rightYAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == xbox){
			shooter.setSpinnerSpeed(e.getValue());
		}
	}

	
	public void padMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void triggerMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void buttonPressed(ButtonEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == xbox && e.getButtonID() == GRTXBoxJoystick.KEY_BUTTON_RB){
			shooter.shoot();
		}
	}

	
	public void buttonReleased(ButtonEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}