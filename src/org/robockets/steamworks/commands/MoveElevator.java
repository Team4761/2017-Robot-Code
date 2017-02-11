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

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection) {
		this.elevatorDirection = elevatorDirection;
		conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
	}

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double time) {
		// This cannot require the shooter because it would kill the SpinSpinners command
		// requires(Robot.shooter);
		this.elevatorDirection = elevatorDirection;
		this.time = time;
		conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
	}

	protected void initialize() {
		setTimeout(time);
	}

	protected void execute() {
		Robot.elevator.moveElevator(elevatorDirection);
		Robot.conveyor.moveConveyor(conveyorDirection);
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		Robot.elevator.stop();
	}

	protected void interrupted() {
		end();
	}
}
