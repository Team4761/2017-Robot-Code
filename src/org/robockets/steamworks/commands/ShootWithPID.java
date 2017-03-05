package org.robockets.steamworks.commands;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootWithPID extends Command {

	private double speed;
	
    public ShootWithPID() {
        requires(Robot.shooter);
        this.speed = 50;
    }
    
    public ShootWithPID(double speed) {
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.shooterPIDController.enable();
    	Robot.shooter.shooterPIDController.setSetpoint(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.shooterPIDController.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
