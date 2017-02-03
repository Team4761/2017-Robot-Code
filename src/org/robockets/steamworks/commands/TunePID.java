package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class TunePID extends Command {

    public TunePID() {
        requires(Robot.drivetrain);
    }

    protected void initialize() {

    }

    protected void execute() {
        Robot.drivetrain.pidGo();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.leftPodPID.disable();
        Robot.drivetrain.stop();
    }

    protected void interrupted() {
        end();
    }
}
