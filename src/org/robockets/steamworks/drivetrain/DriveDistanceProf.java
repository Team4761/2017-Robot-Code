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

	private double currentPosition;
	/**
	 * Initialize misc variables
	 * @param distance Distance in inches
	 * @param velocity Velocity in inches per second
	 */
	public DriveDistanceProf(double distance, double velocity) {
		this.distance = distance;
		this.velocity = velocity;
	}

	protected void initialize() {
		currentPosition = RobotMap.leftEncoder.getDistance();
		Robot.drivetrain.leftPodPID.setSetpoint(this.currentPosition); // Prevent from things freaking out

		if (distance>currentPosition) {
			stepHeight = velocity * STEP_LENGTH;
		} else {
			stepHeight = velocity * -STEP_LENGTH;
		}
		//Robot.drivetrain.leftPodPID.enable();
	}

	protected void execute() {
		// This may or may not work. We may have to put it in its own Thread
		currentPosition += stepHeight;

		Robot.drivetrain.leftPodPID.setSetpoint(currentPosition);

		System.out.println(Robot.drivetrain.leftPodPID.getSetpoint());
		SmartDashboard.putNumber("MoProfSetpoint", Robot.drivetrain.leftPodPID.getSetpoint());
	}

	protected boolean isFinished() {
		return velocity >= 0 || velocity <= 0 || Math.abs(distance - currentPosition) < Math.abs(stepHeight);
	}

	protected void end() {
		Robot.drivetrain.leftPodPID.disable(); // ???
	}

	protected void interrupted() {
		end();
	}
}
