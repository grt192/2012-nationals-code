/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

/**
 *
 * @author ajc
 */
public interface GRTDataLogger {

    public void log(int channel, double data);
    
    public void log (int channel, String msg);

    public void log(double data);
    
    public void log(String data);

}
