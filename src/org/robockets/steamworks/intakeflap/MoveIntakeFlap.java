package org.robockets.steamworks.intakeflap;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class MoveIntakeFlap extends Command {

	// FIXME: THIS MAY NOT WORK DUE TO THE CHANGE IN SERVOS AGAIN!!!! (thanks mechanical...)

	private RelativeDirection.YAxis direction;

	public MoveIntakeFlap(RelativeDirection.YAxis direction) {
		this.direction = direction;
	}

	protected void initialize() {
	}

	protected void execute() {
		if (direction == RelativeDirection.YAxis.FORWARD) {
			Robot.intakeFlap.move(1);
		} else {
			Robot.intakeFlap.move(-1);
		}
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
