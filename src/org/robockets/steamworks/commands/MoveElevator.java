package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class MoveElevator extends Command {

	private RelativeDirection.ZAxis elevatorDirection;
	private RelativeDirection.YAxis conveyorDirection;

	private double time;
	private boolean forever;
	private double speed;

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double speed) {
		this.elevatorDirection = elevatorDirection;
		conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
		forever = true;
		this.speed = speed;
	}

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double time, double speed) {
		// This cannot require the shooter because it would kill the SpinSpinners command
		// requires(Robot.shooter);
		this.elevatorDirection = elevatorDirection;
		this.time = time;
		conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
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
		Robot.conveyor.moveConveyor(conveyorDirection, speed);
	}

	protected boolean isFinished() {
		return !forever && isTimedOut();
	}

	protected void end() {
		Robot.elevator.stop();
		Robot.conveyor.stop();
	}

	protected void interrupted() {
		end();
	}
}
