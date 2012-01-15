/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;
import core.Sensor;
import event.GyroEvent;
import event.GyroListener;
import event.RobotTiltEvent;
import event.RobotTiltListener;
import java.util.Vector;
import sensor.GRTGyro;

/**
 *
 * @author calvin
 */


public class RobotTiltGyro extends Sensor implements GyroListener{
    private Vector robotTiltListeners;
    private double zeroAngle = 0;
    private double angle;
    private GRTGyro g;
    
    public RobotTiltGyro(GRTGyro g, String name){
        super(name);
        this.g = g;
        robotTiltListeners = new Vector();
        angle = 0;
    }
    
    public void angleChanged(GyroEvent e) {
        updateAngle();
    }
    
    public void zero(){
        zero(0);
    }
    
    public void zero(double angle){
        zeroAngle = g.getAngle() + angle;
        System.out.println("Current angle has been defined as " + angle + " degrees");
    }
    
    private void updateAngle(){
        angle = g.getAngle() - zeroAngle;
        notifyStateChange(0, angle);
        notifyListeners(0, angle);
    }
    
    protected void notifyListeners(int id, double newDatum) {
        RobotTiltEvent e = new RobotTiltEvent(this, id, newDatum);
        for (int i = 0; i < robotTiltListeners.size(); i++) {
            ((RobotTiltListener) robotTiltListeners.elementAt(i)).RobotTiltChange(e);
        }


    }

    public void addRobotTiltListeners(RobotTiltListener l) {
        robotTiltListeners.addElement(l);
    }

    public void removeRobotTiltListeners(RobotTiltListener l) {
        robotTiltListeners.removeElement(l);
    }
    
    protected void startListening() {
        g.addListener(this);
    }

    protected void stopListening() {
        g.removeListener(this);
    }
}