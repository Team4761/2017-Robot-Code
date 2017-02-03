package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class SpinDrivePod extends Command {

	RelativeDirection.XAxis direction;
	double distance;

	public SpinDrivePod(RelativeDirection.XAxis direction, double distance) {
		this.direction = direction;
		this.distance = distance;
	}

	protected void initialize() {
		Robot.drivetrain.leftPodPID.setSetpoint(distance);
		Robot.drivetrain.leftPodPID.enable();
	}

	protected void execute() {
		RobotMap.backLeftSpeedController.set(Robot.drivetrain.leftPodPID.get());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {

	}

	protected void interrupted() {
		end();
	}
}
