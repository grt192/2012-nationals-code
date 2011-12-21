package com.grt192.core;

/**
 * MVC controller for logically issuing commands to mechanisms
 * based on desire robot function 
 * Runs in repetitive manner, calling act()
 * @author anand
 */
public abstract class StepController extends Controller {
	public static final int SLEEP_INTERVAL = 25; // ms

	public void run() {
		running = true;
		while (running) {
			try {
				act();
				// minimum loop sleep
				sleep(SLEEP_INTERVAL);
			} catch (Exception e) {
				// On exception kill this actuator, as it is
				// unsafe to continue operation
				e.printStackTrace();
				stopControl();
			}
		}
	}

	public abstract void act();
}
