package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class MoveElevator extends Command {

	RelativeDirection.ZAxis direction;

	double time;

	boolean isForever;

	public MoveElevator(RelativeDirection.ZAxis direction) {
		this.direction = direction;
		isForever = true;
	}

	public MoveElevator(RelativeDirection.ZAxis direction, double time) {
		// This cannot require the shooter because it would kill the SpinSpinners command
		// requires(Robot.shooter);
		this.direction = direction;
		this.time = time;
		isForever = false;
	}

	protected void initialize() {
		if (!isForever) {
			setTimeout(time);
		}
	}

	protected void execute() {
		Robot.elevator.moveElevator(direction);
	}

	protected boolean isFinished() {
		if (!isForever) {
			return isTimedOut();
		} else {
			return false;
		}
	}

	protected void end() {
		Robot.elevator.stop();
	}

	protected void interrupted() {
		end();
	}
}
