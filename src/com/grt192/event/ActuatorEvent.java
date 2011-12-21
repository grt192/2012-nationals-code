package com.grt192.event;

import com.grt192.core.Actuator;
import com.grt192.core.Command;

/**
 *
 * @author anand
 */
public class ActuatorEvent {
    public static final int COMMAND_COMPLETE = 0;
    public static final int COMMAND_FAILED = 1;


    protected Actuator source;
    protected int id;

    protected Command command;

    public ActuatorEvent(Actuator source, int id, Command command) {
        this.source = source;
        this.id = id;
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public int getId() {
        return id;
    }

    public Actuator getSource() {
        return source;
    }
    
}
