package org.robockets.steamworks.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class MoveElevator extends Command {

	private RelativeDirection.ZAxis elevatorDirection;

	private double time;
	private boolean forever;
	private double speed;

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double speed) {
		this.elevatorDirection = elevatorDirection;
		forever = true;
		this.speed = speed;
	}

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double speed, double time) {
		this.elevatorDirection = elevatorDirection;
		this.time = time;
		forever = false;
		this.speed = speed;
	}

	protected void initialize() {
		if (!forever) {
			setTimeout(time);
		}
	}

	protected void execute() {
		Robot.elevator.moveElevator(elevatorDirection, speed);
	}

	protected boolean isFinished() {
		return !forever && isTimedOut();
	}

	protected void end() {
		Robot.elevator.stop();
	}

	protected void interrupted() {
		end();
	}

}
