package org.robockets.steamworks.lights;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Cylon extends Command {

    public Cylon() {
    	requires(Robot.ledSubsystem);
    	System.out.println("Constructor");
        // Use requires() here to declare subsystem dependencies
        // Ego. requires(chases);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.ledSubsystem.cylon();
    	System.out.println("initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    	end();
    }
}
