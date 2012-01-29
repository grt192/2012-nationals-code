/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actuator;

import core.Actuator;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author ajc
 */
public class GRTVictor extends Actuator implements IMotor {

    Victor victor;
    int channel;
    /*
     * Creates a new Victor on the default module at the specified module
     */
    public GRTVictor(int channel, String name){
        super(name);
        victor = new Victor(channel);
        
        this.channel = channel;
    }

    /* Create a new Victor in the slot and channel given
     * Requires the slot number (on 2012 bot, either 1 or 2
     */
    public GRTVictor(int slot, int channel, String name) {
        super(name);
        victor = new Victor(slot, channel);
    }

    public void executeCommand(double command) {
        if(channel == 3){
            System.out.println("New command sent:" + command);
        }
        if (enabled) {
            victor.set(command);
        }
    }

    /*
     * Set the Victor's speed
     * @param speed the new speed to set
     */
    public void setSpeed(double speed){
        if(channel == 3){
            System.out.println("New command sent:" + speed);
        }
        if(enabled){
            victor.set(speed);
        }
    }
}
