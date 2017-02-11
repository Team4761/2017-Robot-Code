package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A command to toggle a boolean used in Joyride.java for switching drive mode.
 * @author Simon Andrews & Brian Shin
 */
public class ToggleDriveMode extends Command {

    public static boolean isArcade;
	
    public ToggleDriveMode() {
    	isArcade = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	isArcade = !isArcade;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
