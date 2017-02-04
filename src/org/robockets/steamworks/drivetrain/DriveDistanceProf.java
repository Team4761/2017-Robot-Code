package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	 * The length of one step in seconds
	 */
	private final double STEP_LENGTH = 0.02;

	/**
	 * The distance traveled in one step
	 */
	private double stepHeight;

	/**
	 * New position to move to
	 */
	private double newPosition;

	/*
	 * Initialize misc variables
	 * @param distance Distance in inches
	 * @param velocity Velocity in inches per second
	 */
	/*
	public DriveDistanceProf(double distance, double velocity) {
		this.distance = distance;
		this.velocity = velocity;
	}*/

	public DriveDistanceProf() {

	}

	protected void initialize() {
	}

	protected void execute() {
		double distanceLeft = Math.abs(distance) - Math.abs(newPosition);

		if (distanceLeft >= Math.abs(stepHeight)) {
			newPosition += stepHeight;
		} else {
			newPosition = distance;
		}

		SmartDashboard.putNumber("MoProfSetpoint", Robot.drivetrain.leftPodPID.getSetpoint()); // For testing
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}

	/**
	 * Update misc parameters
	 * @param distance The distance to travel
	 * @param velocity The velocity in which to travel
	 * @param currentPosition The current position based on the Encoder
	 */
	public void updateParameters(double distance, double velocity, double currentPosition) {
		this.distance = distance;
		this.velocity = velocity;
		this.newPosition = currentPosition;

		if (this.distance>this.newPosition) {
			stepHeight = this.velocity * STEP_LENGTH;
		} else {
			stepHeight = this.velocity * -STEP_LENGTH;
		}
	}

	/**
	 * Get the new position to move to
	 */
	public double getNewPosition() {
		return newPosition;
	}
}
