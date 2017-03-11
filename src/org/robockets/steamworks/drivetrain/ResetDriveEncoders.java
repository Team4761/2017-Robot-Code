package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class ResetDriveEncoders extends Command {

	public ResetDriveEncoders() {
		this.setRunWhenDisabled(true);
	}

	protected void initialize() {
		Robot.drivetrain.resetEncoders();
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {
		end();
	}
}
