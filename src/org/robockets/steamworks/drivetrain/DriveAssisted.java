package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class DriveAssisted extends Command {

	public DriveAssisted() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		Robot.drivetrain.leftPodSpeedPID.enable();
		Robot.drivetrain.rightPodSpeedPID.enable();
	}

	protected void execute() {
		double leftValue = -OI.joystick.getRawAxis(1);
		double rightValue = -OI.joystick.getRawAxis(5);

		Robot.drivetrain.driveWithPID(leftValue, rightValue);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.leftPodSpeedPID.disable();
		Robot.drivetrain.rightPodSpeedPID.disable();
	}

	protected void interrupted() {
		end();
	}
}
