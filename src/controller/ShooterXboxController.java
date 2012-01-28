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

	@Override
	protected void startListening() {
		// TODO Auto-generated method stub
		xbox.addButtonListener(this);
		xbox.addJoystickListener(this);
	}

	@Override
	protected void stopListening() {
		// TODO Auto-generated method stub
		xbox.removeButtonListener(this);
		xbox.removeJoystickListener(this);
	}

	@Override
	public void leftXAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftYAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftAngleChanged(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightXAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightYAxisMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == xbox){
			shooter.setSpinnerSpeed(e.getValue());
		}
	}

	@Override
	public void padMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerMoved(XboxJoystickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonPressed(ButtonEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == xbox && e.getButtonID() == GRTXBoxJoystick.KEY_BUTTON_5){//RB Button
			shooter.shoot();
		}
	}

	@Override
	public void buttonReleased(ButtonEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
