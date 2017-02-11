package org.robockets.steamworks.drivetrain;

import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Motion profiled driving
 * @author Brian Shin
 */
public class DriveWithMP extends Command {
	
	private LinearSetpointGenerator mp;
	private double targetPosition;
	private double velocity;

	/**
	 * Method for driving with motion profiling
	 * @param targetPosition Distance desired, in inches, and relative
	 * @param velocity Inches per second
	 */
    public DriveWithMP(double targetPosition, double velocity) {
        requires(Robot.drivetrain);
        this.targetPosition = targetPosition;
        this.velocity = velocity;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	mp = new LinearSetpointGenerator(targetPosition , velocity, RobotMap.leftEncoder.getDistance());
    	Robot.drivetrain.leftPodPID.enable();
    	Robot.drivetrain.rightPodPID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double setpoint = mp.next();
    	Robot.drivetrain.setDistanceInInches(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return !mp.hasNext();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableEncoderPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
