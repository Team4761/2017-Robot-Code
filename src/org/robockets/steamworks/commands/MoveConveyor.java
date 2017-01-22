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

    /**
     * Move conveyor for a certain amount of time
     * @param time Time in seconds
     */
    public MoveConveyor(double time) {
        requires(Robot.conveyor);
        this.time = time;
        forever = false;
    }

    /**
     * Move conveyor forever
     */
    public MoveConveyor() {
        requires(Robot.conveyor);
        forever = true;
    }

    protected void initialize() {
        if (!forever) {
            setTimeout(time);
        }
    }

    protected void execute() {
        Robot.conveyor.moveConveyor(RelativeDirection.YAxis.FORWARD);
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
