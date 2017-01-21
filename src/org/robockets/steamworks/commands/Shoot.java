package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.YAxisRelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class Shoot extends Command {

    public Shoot() {

    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.shooter.spinUp();
        Robot.conveyor.moveConveyor(YAxisRelativeDirection.FORWARD);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.shooter.stop();
        Robot.conveyor.stop();
    }

    protected void interrupted() {
        end();
    }
}
