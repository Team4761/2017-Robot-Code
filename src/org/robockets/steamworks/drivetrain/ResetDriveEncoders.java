package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class ResetDriveEncoders extends Command {

	public ResetDriveEncoders() {

	}

	protected void initialize() {
		RobotMap.leftEncoder.reset();
		RobotMap.rightEncoder.reset();
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
