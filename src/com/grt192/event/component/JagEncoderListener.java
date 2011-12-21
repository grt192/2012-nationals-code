package com.grt192.event.component;

import com.grt192.sensor.canjaguar.GRTJagEncoder;

/**
 * A <code>JagEncoderListener</code> Listens for <code>GRTEncoderEvent</code>s
 * sent by a <code>GRTJagEncoder</code>
 * @see JagEncoderEvent
 * @see GRTJagEncoder
 * @author ajc
 */
public interface JagEncoderListener {

    /** Called when any change of encoder count occurs */
    public void countDidChange(JagEncoderEvent e);

    /** Called when encoder starts to move out of idle state */
    public void rotationDidStart(JagEncoderEvent e);

    /** Called when encoder detects zero movement */
    public void rotationDidStop(JagEncoderEvent e);

    /** Called when the direction of the encoder spin flips */
    public void changedDirection(JagEncoderEvent e);
}
