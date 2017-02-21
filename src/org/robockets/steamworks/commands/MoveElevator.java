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
	private boolean isConveyorRandom;
	private int conveyorDirectionCounter = 0; // Used for keeping track for what direction to move in

	private final int CONVEYOR_DIRECTION_MAX_COUNTS = 5;

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double speed) {
		this.elevatorDirection = elevatorDirection;
		conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
		forever = true;
		this.speed = speed;
		this.isConveyorRandom = false;
	}

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double time, double speed) {
		// This cannot require the shooter because it would kill the SpinSpinners command
		// requires(Robot.shooter);
		this.elevatorDirection = elevatorDirection;
		this.time = time;
		conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
		forever = false;
		this.speed = speed;
		this.isConveyorRandom = false;
	}

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double speed, boolean isConveyorRandom) {
		this.elevatorDirection = elevatorDirection;
		conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
		forever = true;
		this.speed = speed;
		this.isConveyorRandom = isConveyorRandom;
	}

	protected void initialize() {
		if (!forever) {
			setTimeout(time);
		}
	}

	protected void execute() {
		Robot.elevator.moveElevator(elevatorDirection, speed);

		if (isConveyorRandom) {
			if (conveyorDirectionCounter % CONVEYOR_DIRECTION_MAX_COUNTS == 0) {
				switchConveyorDirection();
			}
		}

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

	private void switchConveyorDirection() {
		if (conveyorDirection == RelativeDirection.YAxis.FORWARD) {
			conveyorDirection = RelativeDirection.YAxis.BACKWARD;
		} else {
			conveyorDirection = RelativeDirection.YAxis.FORWARD;
		}
	}
}
