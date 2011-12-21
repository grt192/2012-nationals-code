package com.grt192.core;

import java.util.Hashtable;
import java.util.Vector;

import com.grt192.controller.WatchDogController;
import com.grt192.event.GlobalEvent;
import com.grt192.event.GlobalListener;
import com.grt192.controller.DashBoardController;
import edu.wpi.first.wpilibj.SimpleRobot;

/**
 * 
 * @author anand, ajc
 */
public abstract class GRTRobot extends SimpleRobot {
    // Shared objects

    protected Hashtable globals;
    protected Vector autonomousControllers;
    protected Vector teleopControllers;
    protected Vector globalListeners;
    protected static GRTRobot instance;
    private WatchDogController watchDogCtl;
    private DashBoardController dashboard;
    private GRTLogger logger;

    public GRTRobot() {
        this(false, false, true);
    }
    public GRTRobot(boolean useWatchDog, boolean useDashBoard, boolean useLogger) {
        autonomousControllers = new Vector();
        teleopControllers = new Vector();

        globals = new Hashtable();
        globalListeners = new Vector();

        if (useWatchDog) {
            watchDogCtl = new WatchDogController(getWatchdog());
//            watchDogCtl.setPriority(Thread.MAX_PRIORITY);
            watchDogCtl.start();
        }

        if (useDashBoard) {
            //Sends hardware status data to dashboard
            dashboard = new DashBoardController();
            dashboard.start();
            System.out.println("Dashboard Streaming: \tREADY");
        }

        if (useLogger) {
            logger = new GRTLogger();
            log("Logger:              \tREADY");
        }

        instance = this;
        log("GRT Framework:       \tREADY");
        startRobot();
    }

    public abstract void startRobot();

    public synchronized Hashtable getGlobals() {
        return globals;
    }

    /**
     * Get an object shared between controllers
     *
     * @param key
     * @return value
     */
    public synchronized Object getGlobal(String key) {
        return globals.get(key);
    }

    /**
     * Add an object to be shared between controllers
     *
     * @param key
     * @param value
     */
    public synchronized void putGlobal(String key, Object value) {
        globals.put(key, value);
        notifyGlobalListeners(key);
    }

    /**
     * This method is called when the robot enters Autonomous mode if any Teleop
     * controllers are started, they should be stopped then, all autonomous
     * controllers are started
     */
    public void autonomous() {
        System.out.println("AUTO");
        // Stop teleopcontrollers if started
        for (int i = 0; i < teleopControllers.size(); i++) {
            Controller c = (Controller) teleopControllers.elementAt(i);
            if (c.isRunning()) {
                c.stopControl();
            }
        }

        // Start
        for (int i = 0; i < autonomousControllers.size(); i++) {
            Controller c = (Controller) autonomousControllers.elementAt(i);
            if (!c.isRunning()) {
                c.start();
            }
        }

    }

    /**
     * This method is called when the robot enters Operator Control mode if any
     * Autonomous controllers are started, they should be stopped then, all
     * teleop controllers are started
     */
    public void operatorControl() {
        // Stop AutonomousControllers if started
        System.out.println("OP");
        for (int i = 0; i < autonomousControllers.size(); i++) {
            Controller c = (Controller) autonomousControllers.elementAt(i);
            if (c.isRunning()) {
                c.stopControl();
            }
        }

        // Start
        for (int i = 0; i < teleopControllers.size(); i++) {
            Controller c = (Controller) teleopControllers.elementAt(i);
            if (!c.isRunning()) {
                c.start();
            }
        }
    }

    protected void notifyGlobalListeners(String key) {
        for (int i = 0; i < globalListeners.size(); i++) {
            ((GlobalListener) globalListeners.elementAt(i)).globalChanged(new GlobalEvent(GlobalEvent.DEFAULT, key,
                    globals));
        }
    }

    public void addGlobalListener(GlobalListener gl) {
        this.globalListeners.addElement(gl);
    }

    public void removeGlobalListener(GlobalListener gl) {
        this.globalListeners.removeElement(gl);
    }

    public static GRTRobot getInstance() {
        return instance;
    }

    public GRTLogger getLogger() {
        return logger;
    }

    protected void log(String message) {
        logger.write("GRTRobot", message);
    }

    protected void log(String type, String message) {
        logger.write(type, message);
    }

    protected void logVar(String name, String message) {
        logger.write("(var)" + name, message);
    }

    protected void logVar(String string, double i) {
        logVar(string, Double.toString(i));
    }
}
