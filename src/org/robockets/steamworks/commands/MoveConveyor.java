package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * For testing purposes
 * @author Jake Backer
 */
public class MoveConveyor extends Command {

    double time = 0;
    boolean forever;

    RelativeDirection.YAxis direction;

    /**
     * Move conveyor for a certain amount of time
     * @param time Time in seconds
     */
    public MoveConveyor(RelativeDirection.YAxis direction, double time) {
        requires(Robot.conveyor);
        this.time = time;
        this.direction = direction;
        this.forever = false;
    }

    /**
     * Move conveyor forever
     */
    public MoveConveyor(RelativeDirection.YAxis direction) {
        requires(Robot.conveyor);
        this.direction = direction;
        this.forever = true;
    }

    protected void initialize() {
    	if(!forever) {
    		setTimeout(time);
    	}
    }

    protected void execute() {
        Robot.conveyor.moveConveyor(direction, 1);
    }

    protected boolean isFinished() {
    	if(!forever) {
    		return isTimedOut();
    	}
        return false;
    }

    protected void end() {
        Robot.conveyor.stop();
    }

    protected void interrupted() {
        end();
    }
}
