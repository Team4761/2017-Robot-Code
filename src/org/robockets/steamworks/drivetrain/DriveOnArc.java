package org.robockets.steamworks.drivetrain;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Brian Shin
 */
public class DriveOnArc extends Command {

	private RelativeDirection.XAxis direction;
	private double chordLength;
	private double radius;
	
    public DriveOnArc(RelativeDirection.XAxis direction, double chordLength, double radius) {
        requires(Robot.drivetrain);   
        this.direction = direction;
        this.chordLength = chordLength;
        this.radius = radius;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.driveArc(direction, chordLength, radius);
    	Robot.drivetrain.enableEncoderDistancePID();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.distanceEncodersOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    	Robot.drivetrain.disableEncoderDistancePID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
