package org.robockets.steamworks.ballintake;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinIntake extends Command {
	private double speed;
    public SpinIntake(double speed) {
    	requires(Robot.ballIntake);
    	this.speed = speed;
    }
    
    public SpinIntake(double speed, double timeout) {
    	this(speed);
    	setTimeout(timeout);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.ballIntake.spinRollers(speed);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Robot.ballIntake.stopRollers();
    }

    protected void interrupted() {
    	end();
    }
}
