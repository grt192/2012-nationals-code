package com.grt192.controller.pid;

import java.util.Vector;

import com.grt192.core.Controller;
import com.grt192.event.controller.PIDEvent;
import com.grt192.event.controller.PIDListener;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class AsynchronousPIDController extends Controller {
	public static final int POLL_TIME = 5;

	private PIDController pidThread;
	private Vector pidListeners;
	private boolean enabled;
	private boolean onTarget;
	
	public AsynchronousPIDController(PIDController p) {
		pidThread = p;
		p.disable();
		enabled = false;
		pidListeners = new Vector();
		onTarget = p.onTarget();
	}

	public void run() {
		running = true;
		while (running) {
			if (enabled) {
				if (!onTarget && pidThread.onTarget()) {
					notifySetPointReached();
				}
			}
			onTarget = pidThread.onTarget();
			try {
				Thread.sleep(POLL_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void enable() {
		this.enabled = true;
		pidThread.enable();
	}

	public void disable() {
		enabled = false;
		pidThread.disable();
	}

	public void reset() {
		enabled = false;
		pidThread.reset();
	}

	public PIDController getPidThread() {
		return pidThread;
	}

	public void addPIDListener(PIDListener p) {
		pidListeners.addElement(p);
	}

	public void removePIDListener(PIDListener p) {
		pidListeners.removeElement(p);
	}

	protected void notifySetPointReached() {
		for (int i = 0; i < pidListeners.size(); i++) {
			((PIDListener) pidListeners.elementAt(i))
					.onSetpointReached(new PIDEvent(this,
							PIDEvent.SETPOINT_REACHED));
		}
	}
}
