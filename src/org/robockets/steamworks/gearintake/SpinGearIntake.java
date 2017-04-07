package org.robockets.steamworks.gearintake;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class SpinGearIntake extends Command {

	private RelativeDirection.Malone direction;
	private double speed;
	private boolean isForever = true;
	private double timeout;

	public SpinGearIntake(RelativeDirection.Malone direction, double speed) {
		this.direction = direction;
		this.speed = speed;
	}

	public SpinGearIntake(RelativeDirection.Malone direction, double speed, double timeout) {
		this.direction = direction;
		this.speed = speed;
		isForever = false;
		this.timeout = timeout;
	}

	protected void initialize() {
		if (!isForever) {
			setTimeout(timeout);
		}
	}

	protected void execute() {
		Robot.gearIntake.spinIntakeWheels(direction, speed);
	}

	protected boolean isFinished() {
		if (!isForever) {
			return isTimedOut();
		}
		return false;
	}

	protected void end() {
		Robot.gearIntake.stopWheels();
	}

	protected void interrupted() {
		end();
	}
}
