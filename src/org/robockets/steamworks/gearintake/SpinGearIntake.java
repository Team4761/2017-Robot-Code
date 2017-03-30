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

	public SpinGearIntake(RelativeDirection.Malone direction, double speed) {
		this.direction = direction;
		this.speed = speed;
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.gearIntake.spinIntakeWheels(direction, speed);
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
