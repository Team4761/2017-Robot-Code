package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.YAxisRelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.subsystems.Conveyor;

/**
 * @author Jake Backer
 */
public class MoveConveyorTime extends Command {

    double time;

    public MoveConveyorTime(double time) {
        requires(Robot.conveyor);
        this.time = time;
    }

    protected void initialize() {
        setTimeout(time);
    }

    protected void execute() {
        Robot.conveyor.moveConveyor(YAxisRelativeDirection.FORWARD);
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
