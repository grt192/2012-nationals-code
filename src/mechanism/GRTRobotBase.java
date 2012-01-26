/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import event.EncoderEvent;
import event.EncoderListener;
import sensor.GRTBatterySensor;
import sensor.GRTEncoder;

/**
 * Encapsulates all components on the robot base.
 * 
 * @author ajc
 */
public class GRTRobotBase implements EncoderListener{

    private final GRTDriveTrain dt;
    private final GRTBatterySensor s;
	
	private double leftEncoderDegrees;
	private double rightEncoderDegrees;
	private double heading;
	private final GRTEncoder leftEncoder;
	private final GRTEncoder rightEncoder;

    public GRTRobotBase(GRTDriveTrain dt, GRTBatterySensor s, GRTEncoder left, GRTEncoder right) {
        this.dt = dt;
        this.s = s;
		this.leftEncoder = left;
		this.rightEncoder = right;
    }

    public GRTDriveTrain getDriveTrain() {
        return dt;
    }
    
    public GRTBatterySensor getBatterySensor(){
        return s;
    }
    
	public void startListening() {
		leftEncoder.addEncoderListener(this);
		rightEncoder.addEncoderListener(this);
	}
	
	public void stopListening() {
		leftEncoder.removeEncoderListener(this);
		rightEncoder.removeEncoderListener(this);
	}
	
    public void tankDrive(double leftVelocity, double rightVelocity){
        dt.tankDrive(leftVelocity, rightVelocity);
    }
	//commands a turn
	public void turn(double degrees) {
		startListening();
		//save current heading as startheading
		double startHeading = heading;
			//while current heading is left of desired turn right
			while (getHeading() < degrees + startHeading) dt.tankDrive(-1, 1);
			//while current heading is right of desired turn left
			while (getHeading() > degrees + startHeading) dt.tankDrive(1, -1);
	}
	
	/**
	 * 
	 * @return heading which is the compass direction 
	 */
	private double getHeading(){
		return heading;
	}

	public void directionChanged(EncoderEvent e) {
	}

	public void degreeChanged(EncoderEvent e) {
		double ratio = 8.0/45; // ratio between wheel rotation and robot rotation is (wheel diameter) / (2*robot width)
		if (e.getSource()== leftEncoder) {
			heading = heading + (e.getValue() - leftEncoderDegrees) / ratio;  
			leftEncoderDegrees = e.getValue();
		}
		if (e.getSource() == rightEncoder) {
			heading = heading - ( e.getValue() - rightEncoderDegrees) / ratio;
			rightEncoderDegrees = e.getValue();
		}
	}
	
	public void distanceChanged(EncoderEvent e) {
	}
}
