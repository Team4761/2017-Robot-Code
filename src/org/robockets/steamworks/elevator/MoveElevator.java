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

	private boolean waitUntilBreakbeam = false;

	private Timer timer;

	public MoveElevator(double speed, boolean waitUntilBreakbeam) {
		elevatorDirection = RelativeDirection.ZAxis.UP;
		this.speed = speed;
		waitUntilBreakbeam = true;
	}

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double speed) {
		this.elevatorDirection = elevatorDirection;
		//conveyorDirection = (this.elevatorDirection == RelativeDirection.ZAxis.UP) ? RelativeDirection.YAxis.FORWARD : RelativeDirection.YAxis.BACKWARD;
		forever = true;
		this.speed = speed;
	}

	public MoveElevator(RelativeDirection.ZAxis elevatorDirection, double speed, double time) {
		// This cannot require the shooter because it would kill the SpinSpinners command
		// requires(Robot.shooter);
		this.elevatorDirection = elevatorDirection;
		this.time = time;
		forever = false;
		this.speed = speed;
	}

	protected void initialize() {
		if (!forever) {
			setTimeout(time);
		}
		timer = new Timer();
		timer.start();
	}

	protected void execute() {
		Robot.elevator.moveElevator(elevatorDirection, speed);
	}

	protected boolean isFinished() {
		if (waitUntilBreakbeam) {
			return !RobotMap.elevatorBreakbeamSensor.get();
		}
		return !forever && isTimedOut();
	}

	protected void end() {
		Robot.elevator.stop();
		//Robot.conveyor.stop();
	}

	protected void interrupted() {
		end();
	}

}
