package org.robockets.steamworks.drivetrain;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveTime extends TimedCommand {
	
	private double speed;

	/**
	 * Timed drive, while setting both motors to same speed
	 * @param timeout Time to drive
	 * @param speed Speed of driving
	 */
	public DriveTime(double timeout, double speed) {
		super(timeout);
		requires(Robot.drivetrain);
		this.speed = speed;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.driveArcade(speed, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
