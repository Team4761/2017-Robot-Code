package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * Use motion profiling to drive a distance
 * @author Jake Backer
 */
public class DriveDistanceProf extends Command {

	private double distance;
	private double velocity;

	/**
	 * Time in seconds
	 */
	private double time;

	/**
	 * The length of one step in seconds
	 */
	private final double stepLength;

	/**
	 * The amount of steps in the given time
	 */
	private double stepCount;

	/**
	 * The distance traveled in one step
	 */
	private double stepHeight;

	private double currentSetpoint;

	/**
	 * Initialize misc variables
	 * @param distance Distance in inches
	 * @param velocity Velocity in inches per second
	 */
	public DriveDistanceProf(double distance, double velocity) {
		this.distance = distance;
		this.velocity = velocity;

		this.time = this.distance / this.velocity;
		this.stepLength = 0.002;
		this.stepCount = this.time / this.stepLength;
		this.stepHeight = this.distance / this.stepCount;
	}

	protected void initialize() {
		this.currentSetpoint = RobotMap.leftEncoder.getDistance();
		Robot.drivetrain.leftPodPID.setSetpoint(this.currentSetpoint);
		Robot.drivetrain.leftPodPID.enable();
	}

	protected void execute() {

	}

	protected boolean isFinished() {
		return this.distance - RobotMap.leftEncoder.getDistance() <= 2.5; // 2.5 subject to change
	}

	protected void end() {
		Robot.drivetrain.leftPodPID.disable();
	}

	protected void interrupted() {
		end();
	}
}
