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

	private double targetPosition;
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
	
	private double distanceRemaining;

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
		distanceRemaining = Math.abs(targetPosition) - Math.abs(newPosition);
		
		if (this.targetPosition>this.newPosition) {
			stepHeight = this.velocity * STEP_LENGTH;
		} else {
			stepHeight = this.velocity * -STEP_LENGTH;
		}
		
		if (distanceRemaining >= Math.abs(stepHeight)) {
			newPosition += stepHeight;
		} else {
			newPosition = targetPosition;
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

	/**
	 * Update misc parameters
	 * @param targetPosition The distance to travel
	 * @param velocity The velocity in which to travel
	 * @param currentPosition The current position based on the Encoder
	 */
	public void updateParameters(double targetPosition, double velocity, double currentPosition) {
		this.targetPosition = targetPosition;
		this.velocity = velocity;
		this.newPosition = currentPosition;
	}

	public void updateParameters(double targetPosition, double velocity) {
		this.targetPosition = targetPosition;
		this.velocity = velocity;
	}
	public void updateCurrentPosition(double currentPosition) {
		this.newPosition = currentPosition;
	}

	/**
	 * Get the new position to move to
	 */
	public double getNewPosition() {
		return newPosition;
	}
	
	public boolean isInPosition() {
		distanceRemaining = Math.abs(targetPosition) - Math.abs(newPosition);
		return distanceRemaining < Math.abs(stepHeight);
	}
}
