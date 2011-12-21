package com.grt192.controller;

import com.grt192.core.StepController;
import edu.wpi.first.wpilibj.Watchdog;

/**
 *
 * @author anand
 */
public class WatchDogController extends StepController {

    private Watchdog dog;

    public WatchDogController(Watchdog d) {
        dog = d;
//        dog.setEnabled(false);
        System.out.println("Watchdog at " + dog.getExpiration());
    }

    public void act() {
        dog.feed();
    }
}
