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

    RelativeDirection.YAxis direction;

    /**
     * Move conveyor for a certain amount of time
     * @param time Time in seconds
     */
    public MoveConveyor(RelativeDirection.YAxis direction, double time) {
        requires(Robot.conveyor);
        this.time = time;
        this.direction = direction;
    }

    /**
     * Move conveyor forever
     */
    public MoveConveyor(RelativeDirection.YAxis direction) {
        requires(Robot.conveyor);
        this.direction = direction;
    }

    protected void initialize() {
        setTimeout(time);
    }

    protected void execute() {
        Robot.conveyor.moveConveyor(direction);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        Robot.conveyor.stop();
    }

    protected void interrupted() {
        end();
    }
}
