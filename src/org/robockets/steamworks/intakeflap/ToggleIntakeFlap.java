package org.robockets.steamworks.intakeflap;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleIntakeFlap extends Command {

    public ToggleIntakeFlap() {
    	requires(Robot.intakeFlap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.intakeFlap.toggle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
