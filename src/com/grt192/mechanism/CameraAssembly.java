package com.grt192.mechanism;

import com.grt192.core.Mechanism;
import com.grt192.sensor.GRTAxisCamera;

/**
 *
 * @author anand
 */
public class CameraAssembly extends Mechanism {

    private GRTAxisCamera camera;

    public CameraAssembly() {
        camera = new GRTAxisCamera("Camera");
        camera.start();
        addSensor("camera", camera);
    }

}
