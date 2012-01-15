/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;
import balancer.RobotTiltGyro;
import core.EventController;
import event.RobotTiltListener;
import event.RobotTiltEvent;
import mechanism.GRTRobotBase;
import sensor.GRTAttack3Joystick;
import event.ButtonEvent;
import event.ButtonListener;


/**
 *
 * @author calvin
 */

/**
 * Gets information from a joystick--
 * the trigger activates the auto-balance
 * another button zeros the gyro
 * zeroing is to be done before driving robot onto ramp
 * 
 */
public class BalanceController extends EventController implements RobotTiltListener, ButtonListener {
    
    private double previousAngle;
    private double currentAngle;
    private double deltaAngle;
    private double PConstant = .024;
    private double DConstant = 1;
    private final RobotTiltGyro robotTilt;
    private final GRTRobotBase base;
    private double DRIVE_THRESHOLD = .01;
    private double MAX_DRIVE_THRESHOLD = .5;
    private GRTAttack3Joystick joystick;
    private boolean balancing = false;
    
    public BalanceController(GRTRobotBase base, RobotTiltGyro robotTilt, GRTAttack3Joystick joystick, String name){
        super(name);
        this.base = base;
        this.robotTilt = robotTilt;
        this.joystick = joystick;
    }
    
    public void startBalancing(){
        balancing = true;
    }
    
    public void stopBalancing(){
        balancing = false;
    }

    public void startListening() {
        robotTilt.addRobotTiltListeners(this);
        joystick.addButtonListener(this);
        
    }

    public void stopListening() {
        robotTilt.removeRobotTiltListeners(this);
        joystick.removeButtonListener(this);
    }

    public void RobotTiltChange(RobotTiltEvent e) {
        currentAngle = e.getTilt();
        deltaAngle = currentAngle - previousAngle;
        double drivePower = PConstant * currentAngle - DConstant * deltaAngle;
        if(balancing){
            if(Math.abs(drivePower) >= DRIVE_THRESHOLD)
                base.tankDrive(drivePower, drivePower);
            else
                base.tankDrive(0, 0);
        }
        //        System.out.println(drivePower);
    }
    
    public void setPD(double P, double D){
        PConstant = P;
        DConstant = D;
        System.out.println("PD Constants updated: P = " + P + ", D = " + D);
    }

    public void buttonPressed(ButtonEvent e) {
        
    }

    public void buttonReleased(ButtonEvent e) {
        
    }
    
    
    
}
