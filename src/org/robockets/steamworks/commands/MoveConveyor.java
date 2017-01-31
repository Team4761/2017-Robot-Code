package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class MoveConveyor extends Command {

    double time;
    boolean forever;

    RelativeDirection.YAxis direction;

    /**
     * Move conveyor for a certain amount of time
     * @param time Time in seconds
     */
    public MoveConveyor(double time, RelativeDirection.YAxis direction) {
        requires(Robot.conveyor);
        this.time = time;
        forever = false;
        this.direction = direction;
    }

    /**
     * Move conveyor forever
     */
    public MoveConveyor(RelativeDirection.YAxis direction) {
        requires(Robot.conveyor);
        forever = true;
        this.direction = direction;
    }

    protected void initialize() {
        if (!forever) {
            setTimeout(time);
        }
    }

    protected void execute() {
        Robot.conveyor.moveConveyor(direction);
    }

    protected boolean isFinished() {
        if (!forever) {
            return isTimedOut();
        } else {
            return false;
        }
    }

    protected void end() {
        Robot.conveyor.stop();
    }

    protected void interrupted() {
        end();
    }
}
