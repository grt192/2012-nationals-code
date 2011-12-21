package com.grt192.core;

/**
 * Message carrying object that represents an order for a specific actuator
 * @see Actuator
 * @author Ryo
 */
public class Command {

    private double value;
    private int sleepTime;
    private boolean atomic;

    /**
     * Constructs a new <code>Command</code> with zero sleep time that
     * doesn't halt the actuator
     * @param value
     */
    public Command(double value) {
        this(value, 0);
    }

    /**
     * Constructs a new <code>Command</code>  that ensures <code>sleepTime</code>
     * amount of actuator run time that doesn't halt the actuator
     * @param value
     * @param sleepTime minimum actuation time
     */
    public Command(double value, int sleepTime) {
        this(value, sleepTime, false);
    }

    /**
     * Constructs a new <code>Command</code>  that ensures <code>sleepTime</code>
     * amount of actuator run time that may halt the actuator
     * @param value
     * @param sleepTime minimum actuation time
     * @param reset halt actuator after execution and sleep
     */
    public Command(double value, int sleepTime, boolean reset) {
        this.value = value;
        this.sleepTime = sleepTime;
        this.atomic = atomic;
    }

    /**
     * Sets the amount of time that the Command will be enabled for
     * before executing next command in queue
     */
    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    /** Gets the amount guaranteed execution time **/
    public int getSleepTime() {
        return sleepTime;
    }

    /** Gets the value, which stores command data as read by actuators **/
    public double getValue() {
        return value;
    }

    /** Tests if this command is atomic: executed followed by actuator halt **/
    public boolean isAtomic() {
        return atomic;
    }
}
