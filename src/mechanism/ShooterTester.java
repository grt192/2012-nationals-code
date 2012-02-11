/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

/**
 *
 * @author dan
 */
public class ShooterTester{
    private final ShootingSystem ss;
    public ShooterTester(ShootingSystem ss){
        this.ss = ss;
    }
    public ShooterTester(ShootingSystem ss, boolean UseEncoders){
        this.ss = ss;
    }
    public void loadBall(){
        ss.setBotTransitionSpeed(1);
    }
    public void setPower(double speed){
        ss.setFlywheelSpeed(speed);
    }
    public void setVisorAngle(double angle){
     ss.setVisorSpeed(angle);
    }
}
