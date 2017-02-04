package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

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

	}

	protected void execute() {


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
