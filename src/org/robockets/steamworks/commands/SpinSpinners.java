package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class SpinSpinners extends Command {

    public SpinSpinners() {
        requires(Robot.shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.shooter.spinUp();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }
}
