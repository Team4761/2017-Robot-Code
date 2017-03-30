package org.robockets.steamworks.gearintake;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class MoveGearIntakeArm extends Command {

	private RelativeDirection.ZAxis direction;
	private double speed;

	public MoveGearIntakeArm(RelativeDirection.ZAxis direction, double speed) {
		this.direction = direction;
		this.speed = speed;
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.gearIntake.moveGearIntakeArm(direction, speed);
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
