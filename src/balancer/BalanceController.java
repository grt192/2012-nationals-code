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


/**
 *
 * @author calvin
 */
public class BalanceController extends EventController implements RobotTiltListener{
    
    private double previousAngle;
    private double currentAngle;
    private double deltaAngle;
    private double PConstant = .024;
    private double DConstant = 0;
    private final RobotTiltGyro robotTilt;
    private final GRTRobotBase base;
    private double DRIVE_THRESHOLD = .01;
    
    public BalanceController(GRTRobotBase base, RobotTiltGyro robotTilt, String name){
        super(name);
        this.base = base;
        this.robotTilt = robotTilt;
    }
    
    public void startBalancing(){
        startListening();
    }
    
    public void stopBalancing(){
        stopListening();
    }

    protected void startListening() {
        robotTilt.addRobotTiltListeners(this);
    }

    protected void stopListening() {
        robotTilt.removeRobotTiltListeners(this);
    }

    public void RobotTiltChange(RobotTiltEvent e) {
        currentAngle = e.getTilt();
        deltaAngle = currentAngle - previousAngle;
        double drivePower = PConstant * currentAngle - DConstant * deltaAngle;
        if(Math.abs(drivePower) >= DRIVE_THRESHOLD)
            base.tankDrive(drivePower, drivePower);
        else 
            base.tankDrive(0, 0);
        System.out.println(drivePower);
    }
    
    public void setPD(double P, double D){
        PConstant = P;
        DConstant = D;
        System.out.println("PD Constants updated: P = " + P + ", D = " + D);
    }
    
    
    
}
