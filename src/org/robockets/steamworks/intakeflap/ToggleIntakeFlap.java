package org.robockets.steamworks.intakeflap;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleIntakeFlap extends Command {
    public ToggleIntakeFlap() {
    	requires(Robot.intakeFlap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//s2.free();
    	setTimeout(0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeFlap.start();
    	//s2.setSpeed(0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeFlap.stop();
    	//s2.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
