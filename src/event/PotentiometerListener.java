/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import event.PotentiometerEvent;
/**
 *
 * @author calvin
 */
public interface PotentiometerListener {
    public void valueChanged(PotentiometerEvent e);
}
